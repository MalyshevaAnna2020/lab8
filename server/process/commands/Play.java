package server.process.commands;

import server.command.Answer;
import server.command.Command;
import server.database.DataBase;
import server.spacemarine.Chapter;
import server.spacemarine.Coordinates;
import server.spacemarine.SpaceMarine;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;

public class Play {
    private final List<String> files = new ArrayList<>();

    public Answer play (Command command) throws IOException, SQLException {
        Answer answer = new Answer();

        // Действия перенесены в другой класс, чтобы не загромождать
        ResultOfCommand resultOfCommand = new ResultOfCommand();

        // Чтение коллекции из PostgreSQL
        Hashtable<String, SpaceMarine> hashFromSQL = DataBase.getInstance().updateCollection();
        int size = hashFromSQL.size();

        // Обновлять состояние коллекции в памяти только при успешном добавлении объекта в БД
        boolean updateSQL = false;

        // Выбор команд (сделано с помощью switch,
        // так как многие команды меняют не только ответ клиенту (добавление предложения в конец "конечной" строки),
        // но и коллекцию
        switch (command.getNameOfCommand()){
            case EXIT:
                //message.append(resultOfCommand.info(hashFromSQL)).append("Завершение работы!");
                //mainHashtable.put(hashFromSQL, message.toString());
                // Коллекция уже автоматически записана в файле, поэтому дополнительно записывать ее не требуется
                // ОДНАКО КОЛЛЕКЦИЮ НАДО ПЕРЕДАТЬ КЛИЕНТУ
                break;
            case INFO:
            case SHOW:
                answer.setSpaceMarineList(hashFromSQL);
                break;
            case REMOVE_KEY:
                String answer2;
                if (hashFromSQL.containsKey(command.getKey())) {
                    if (resultOfCommand.removeKey(command, hashFromSQL)) {
                        hashFromSQL.remove(command.getKey());
                        updateSQL = true;
                        answer2 = "1";
                    }else{
                        answer2 = "2";
                    }
                }else{
                    answer2 = "3";
                }
                answer.setKey(answer2);

                if (updateSQL){
                    DataBase.getInstance().deleteSpaceMarineKey(command.getKey());
                }
                break;
            case CLEAR:
                for (String key : hashFromSQL.keySet()){
                    hashFromSQL.get(key).setKey(key);
                }
                int countRemove = 0;
                String answer5;
                if (hashFromSQL.size() > 0) {
                    for (SpaceMarine sp : hashFromSQL.values()){
                        if (sp.getUser().equals(command.getPassword().getLogin())){
                            countRemove++;
                            DataBase.getInstance().deleteSpaceMarineKey(sp.getKey());
                        }
                    }
                    if (countRemove > 0) {
                        answer5 = "1";
                    }else{
                        answer5 = "2";
                    }
                }
                else{ answer5 = "3"; }
                answer.setKey(answer5);
                break;
            case PRINT_FIELD_DESCENDING_CATEGORY:
                List<SpaceMarine> list1 = DataBase.getInstance().selectCategory(command.getKey());
                Hashtable<String, SpaceMarine> hashtable1 = new Hashtable<>();
                for (SpaceMarine spaceMarine : list1){
                    hashtable1.put(spaceMarine.getKey(), spaceMarine);
                }
                System.out.println(hashtable1.size());
                answer.setSpaceMarineList(hashtable1);
                break;
            case REMOVE_ANY_BY_CHAPTER:
                String answer3;
                hashFromSQL = resultOfCommand.remove_any_by_chapter(hashFromSQL, command);
                if (size !=hashFromSQL.size()) {
                    answer3 = "1";
                    updateSQL = true;
                }else{
                    answer3 = "3";
                    SpaceMarine marine = null;
                    for (SpaceMarine spaceMarine : hashFromSQL.values()){
                        if (spaceMarine.getChapter().getName().equals(command.getKey())) {
                            marine = spaceMarine;
                            break;
                        }
                    }
                    assert marine != null;
                    for (SpaceMarine spaceMarine : hashFromSQL.values()){
                        if (spaceMarine.getId() == marine.getId()) {
                            answer3 = "2";
                            break;
                        }
                    }
                }
                answer.setKey(answer3);
                if (updateSQL){
                    DataBase.getInstance().deleteSpaceMarineChapter(command.getKey(), command.getPassword().getLogin());
                }
                break;
            case REMOVE_LOWER_KEY:
                for (String key : hashFromSQL.keySet()){
                    hashFromSQL.get(key).setKey(key);
                }
                Hashtable<String, SpaceMarine> oldHashtable = hashFromSQL;
                hashFromSQL = resultOfCommand.remove_lower_key(hashFromSQL, command);
                if (size !=hashFromSQL.size()) {
                    answer3 = "1";
                    updateSQL = true;
                }else{
                    answer3 = "3";
                    for (SpaceMarine spaceMarine : hashFromSQL.values()){
                        if (spaceMarine.getId() == Integer.parseInt(command.getKey())){
                            answer3 = "2";
                        }
                    }
                }
                if (updateSQL){
                    for (SpaceMarine spaceMarine : oldHashtable.values()){
                        if (!hashFromSQL.contains(spaceMarine)){
                            DataBase.getInstance().deleteSpaceMarineKey(spaceMarine.getKey());
                        }
                    }
                }
                answer.setKey(answer3);
                break;
            case REMOVE_LOWER:
                System.out.println(hashFromSQL.size());
                hashFromSQL = resultOfCommand.remove_lower(hashFromSQL, command);
                System.out.println(hashFromSQL.size());
                if (size !=hashFromSQL.size()) {
                    answer3 = "1";
                    updateSQL = true;
                }else{
                    answer3 = "3";
                    for (SpaceMarine spaceMarine : hashFromSQL.values()){
                        if (spaceMarine.getId() == Integer.parseInt(command.getKey())){
                            answer3 = "2";
                        }
                    }
                }
                System.out.println(answer3);
                answer.setKey(answer3);

                if (updateSQL){
                    DataBase.getInstance().deleteSpaceMarineId(Integer.parseInt(command.getKey()), command.getPassword().getLogin());
                }

                break;
            case REMOVE_GREATER_KEY:
                for (String key : hashFromSQL.keySet()){
                    hashFromSQL.get(key).setKey(key);
                }
                Hashtable<String, SpaceMarine> oldHashtable1 = hashFromSQL;
                hashFromSQL = resultOfCommand.remove_greater_key(hashFromSQL, command);
                if (size !=hashFromSQL.size()) {
                    answer3 = "1";
                    updateSQL = true;
                }else{
                    answer3 = "3";
                    for (SpaceMarine spaceMarine : hashFromSQL.values()){
                        if (spaceMarine.getId() == Integer.parseInt(command.getKey())){
                            answer3 = "2";
                        }
                    }
                }
                if (updateSQL){
                    for (SpaceMarine spaceMarine : oldHashtable1.values()){
                        if (!hashFromSQL.contains(spaceMarine)){
                            DataBase.getInstance().deleteSpaceMarineKey(spaceMarine.getKey());
                        }
                    }
                }
                answer.setKey(answer3);
                break;
            case FILTER_GREATER_THAN_ACHIEVEMENTS:
                List<SpaceMarine> list = resultOfCommand.filter_greater_than_achievements(hashFromSQL, command);
                Hashtable<String, SpaceMarine> hashtable = new Hashtable<>();
                for (String key : hashFromSQL.keySet()){
                    if (list.contains(hashFromSQL.get(key))){
                        hashtable.put(key, hashFromSQL.get(key));
                    }
                }
                answer.setSpaceMarineList(hashtable);
                break;
            case INSERT:
                SpaceMarine spaceMarine = command.getSpaceMarine();

                spaceMarine.setId(hashFromSQL.size() + 1);
                spaceMarine.setUser(command.getPassword().getLogin());

                hashFromSQL.put(command.getKey(), spaceMarine);
                updateSQL = size != hashFromSQL.size();
                answer.setCheck(updateSQL);

                // Теперь запись не в файл, а в PostgreSQL
                if (updateSQL){
                    DataBase.getInstance().addSpaceMarine(hashFromSQL.get(command.getKey()), command.getKey());
                }

                break;
            case UPDATE:
                SpaceMarine spaceMarine1 = command.getSpaceMarine();
                hashFromSQL = resultOfCommand.update(hashFromSQL, command);
                String answer1 = resultOfCommand.update(command, hashFromSQL);
                answer.setKey(answer1);
                if (answer1.charAt(0) == '1') {
                    for (SpaceMarine spaceMarine2 : hashFromSQL.values()){
                        if (spaceMarine2.getId() == spaceMarine1.getId()){
                            spaceMarine1.setKey(spaceMarine2.getKey());
                            System.out.println("data:" + spaceMarine2.getCreationDate());
                            spaceMarine1.setCreationDate(spaceMarine2.getCreationDate());
                        }
                    }
                    updateSQL = true;
                }

                if (updateSQL){
                    DataBase.getInstance().updateSpaceMarine(spaceMarine1);
                }

                break;
        }


        // new WriteToFile().write(DataBase.getInstance().getHashtable(), DataBase.getInstance().getFile_main());
        // mainHashtable.put(DataBase.getInstance().getHashtable(), message.toString());
        return answer;
    }
}