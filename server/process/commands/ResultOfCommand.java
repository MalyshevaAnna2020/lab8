package server.process.commands;

import server.command.Command;
import server.spacemarine.AstartesCategory;
import server.spacemarine.SpaceMarine;

import java.util.Comparator;
import java.util.Hashtable;
import java.util.List;
import java.util.stream.Collectors;

public class ResultOfCommand {
    public String info(Hashtable<String, SpaceMarine> hashtable){
        StringBuilder info = new StringBuilder();
        if (!hashtable.isEmpty()) {
            // Сортировка по размеру
            List<SpaceMarine> sortedSpaceMarines = hashtable
                    .values()
                    .stream()
                    .sorted(Comparator.comparingInt(SpaceMarine::getSize))
                    .collect(Collectors.toList());
            // Ответ клиенту
            for (String key : hashtable.keySet())
                for (SpaceMarine spaceMarine : sortedSpaceMarines)
                    if (hashtable.get(key) == spaceMarine){
                        info.append("key=\"").append(key).append("\"\t")
                            .append("id=\"").append(hashtable.get(key).getId()).append("\"\t")
                            .append("name=\"").append(hashtable.get(key).getName()).append("\"\t")
                            .append("coordinates=\"").append(hashtable.get(key).getCoordinates().toString()).append("\"\t")
                            .append("creationDate=\"").append(hashtable.get(key).getCreationDate()).append("\"\t")
                            .append("health=\"").append(hashtable.get(key).getHealth()).append("\"\t")
                            .append("heartCount=\"").append(hashtable.get(key).getHeartCount()).append("\"\t")
                            .append("achievements=\"").append(hashtable.get(key).getAchievements()).append("\"\t")
                            .append("category=\"").append(hashtable.get(key).getCategory()).append("\"\t")
                            .append("chapter=\"").append(hashtable.get(key).getChapter()).append("\"\n")
                            .append("user=\"").append(hashtable.get(key).getUser()).append("\"\n");
                    if (hashtable.get(key).getHealth() == null) info.append("\nЗначение переменной health равно null!\n");
                    if (hashtable.get(key).getAchievements() == null) info.append("\nЗначение переменной achievements равно null!\n");
                    if (hashtable.get(key).getCategory() == null) info.append("\nЗначение переменной category равно null!\n");
                    if (hashtable.get(key).getChapter() == null) info.append("\nЗначение переменной chapter равно null!\n");
                }
        }else info.append("В коллекции нет элементов!\n");
        return info.toString();
    }
    public String show(Hashtable<String, SpaceMarine> hashtable){
        StringBuilder show = new StringBuilder();
        if (!hashtable.isEmpty()) {
            // Сортировка по размеру
            List<SpaceMarine> sortedSpaceMarines = hashtable
                    .values()
                    .stream()
                    .sorted(Comparator.comparingInt(SpaceMarine::getSize))
                    .collect(Collectors.toList());
            // Ответ клиенту
            show.append("Элементы коллекции: ");
            for (String key : hashtable.keySet())
                for (SpaceMarine spaceMarine : sortedSpaceMarines)
                    if (hashtable.get(key) == spaceMarine)
                        show.append(key).append(" ");
            show.append("\n");
        }
        else show.append("В коллекции нет элементов!\n");
        return show.toString();
    }

    public boolean removeKey(Command command, Hashtable<String, SpaceMarine> hashFromSQL){
        String user = command.getPassword().getLogin();
        String userOfSpaceMarine = hashFromSQL.get(command.getKey()).getUser();
        return user.equals(userOfSpaceMarine);
    }

    public String print(Hashtable<String, SpaceMarine> hashtable){
        StringBuilder show = new StringBuilder();
        if (!hashtable.isEmpty()) {
            List<SpaceMarine> sortedList = hashtable
                    .values()
                    .stream()
                    .sorted((o1, o2) -> {
                        int i1 = 0;
                        if (o1.getCategory() == null) i1 = 5;
                        else switch (o1.getCategory()) {
                            case SUPPRESSOR:
                                i1 = 1;
                                break;
                            case TERMINATOR:
                                i1 = 2;
                                break;
                            case LIBRARIAN:
                                i1 = 3;
                                break;
                            case APOTHECARY:
                                i1 = 4;
                                break;
                        }
                        int i2 = 0;
                        if (o2.getCategory() == null) i2 = 5;
                        else switch (o2.getCategory()) {
                            case SUPPRESSOR:
                                i2 = 1;
                                break;
                            case TERMINATOR:
                                i2 = 2;
                                break;
                            case LIBRARIAN:
                                i2 = 3;
                                break;
                            case APOTHECARY:
                                i2 = 4;
                                break;
                        }
                        return i1 - i2;
                    })
                    .collect(Collectors.toList());
            boolean count = false;
            for (SpaceMarine sp : sortedList) {
                if (sp.getCategory() == AstartesCategory.APOTHECARY) {
                    if (!count) show.append("category:APOTHECARY\n");
                    count = true;
                    for (String key : hashtable.keySet())
                        if (hashtable.get(key) == sp) show.append(key).append(" ");
                    show.append("\n");
                }
            }
            if (count) show.append("\n");
            count = false;
            for (SpaceMarine sp : sortedList) {
                if (sp.getCategory() == AstartesCategory.LIBRARIAN) {
                    if (!count) show.append("category:LIBRARIAN\n");
                    count = true;
                    for (String key : hashtable.keySet())
                        if (hashtable.get(key) == sp) show.append(key).append(" ");
                    show.append("\n");
                }
            }
            if (count) show.append("\n");
            count = false;
            for (SpaceMarine sp : sortedList) {
                if (sp.getCategory() == AstartesCategory.SUPPRESSOR) {
                    if (!count) show.append("category:SUPPRESSOR\n");
                    count = true;
                    for (String key : hashtable.keySet())
                        if (hashtable.get(key) == sp) show.append(key).append(" ");
                    show.append("\n");
                }
            }
            if (count) show.append("\n");
            count = false;
            for (SpaceMarine sp : sortedList) {
                if (sp.getCategory() == AstartesCategory.TERMINATOR) {
                    if (!count) show.append("category:TERMINATOR\n");
                    count = true;
                    for (String key : hashtable.keySet())
                        if (hashtable.get(key) == sp) show.append(key).append(" ");
                    show.append("\n");
                }
            }
            if (count) show.append("\n");
            count = false;
            for (SpaceMarine sp : sortedList) {
                if (sp.getCategory() == null) {
                    if (!count) show.append("category:null\n");
                    count = true;
                    for (String key : hashtable.keySet())
                        if (hashtable.get(key) == sp) show.append(key).append(" ");
                    show.append("\n");
                }
            }
            if (count) show.append("\n");

        }else show.append("В коллекции нет элементов!\n");
        return show.toString();
    }
    public Hashtable<String, SpaceMarine> remove_any_by_chapter(Hashtable<String, SpaceMarine> hashtable, Command command){
        List<SpaceMarine> sortedList = hashtable
                .values()
                .stream()
                .filter(s-> ((!s.getChapter().getName().equals(command.getKey())) ||
                        ((!command.getPassword().getLogin().equals(s.getUser())))) )
                .collect(Collectors.toList());
        Hashtable<String, SpaceMarine> hashtable1 = new Hashtable<>();
            for (SpaceMarine sp : sortedList)
                for (String key : hashtable.keySet()) {
                    if (hashtable.get(key) == sp) hashtable1.put(key, sp);
                }
            return hashtable1;
    }
    public String remove_any_by_chapter(Command command, int size1, int size2){
        if (size1 != size2) return "Элементы коллекции, принадлежащие " +
                command.getKey() +
                " успешно удалены!\n";
        else return "В коллекции не найдены элементы с полем chapter, название которого равно " +
                command.getKey() + "! Команда info помжет Вам убедиться в правильности написания поля Chapter\n";
    }
    public Hashtable<String, SpaceMarine> remove_lower(Hashtable<String, SpaceMarine> hashtable, Command command){
        try {
            List<SpaceMarine> sortedList = hashtable.values()
                    .stream()
                    .filter(s -> (s.getId() >= Integer.parseInt(command.getKey())) ||
                            ((!command.getPassword().getLogin().equals(s.getUser()))))
                    .collect(Collectors.toList());
            System.out.println(sortedList.size());
            Hashtable<String, SpaceMarine> subhashtable = new Hashtable<>();
            for (SpaceMarine sp : sortedList)
                for (String key : hashtable.keySet())
                    if (sp == hashtable.get(key)) subhashtable.put(key, hashtable.get(key));
            return subhashtable;
        }catch(NumberFormatException e){
            return hashtable;
        }
    }
    public String remove_lower(Command command, int size1, int size2){
        try {
            if (size1 != size2) return "Элементы коллекции, размер которых меньше " +
                    Integer.parseInt(command.getKey()) +
                    " успешно удалены!";
            else return "Элементы коллекции, размер которых меньше " +
                    Integer.parseInt(command.getKey()) +
                    " не найдены!\n";
        }catch (NumberFormatException e){
            return "Размер элемента представляет собой целое число!";
        }
    }
    public Hashtable<String, SpaceMarine> remove_greater_key(Hashtable<String, SpaceMarine> hashtable, Command command){
        try {
            List<String> listOfKeys = hashtable.keySet()
                    .stream()
                    .filter(s ->
                            (s.length() < Integer.parseInt(command.getKey())) ||
                                    ((!command.getPassword().getLogin().equals(hashtable.get(s).getUser()))))
                    .collect(Collectors.toList());
            Hashtable<String, SpaceMarine> hashtable1 = new Hashtable<>();
            for (String keyOfList : listOfKeys)
                for (String key : hashtable.keySet())
                    if (keyOfList.equals(key)) hashtable1.put(key, hashtable.get(key));
            return hashtable1;
        }catch (NumberFormatException e){
            return hashtable;
        }
    }
    public String remove_greater_key(Command command, int size1, int size2){
        try {
            if (size1 != size2) return "Элементы коллекции с длиной ключа больше " +
                    Integer.parseInt(command.getKey()) +
                    " успешно удалены!";
            else return "Элементы коллекции с длиной ключа больше " +
                    Integer.parseInt(command.getKey()) +
                    " не найдены!";
        }catch (NumberFormatException e){
            return "Длина ключа - целое число!";
        }
    }
    public Hashtable<String, SpaceMarine> remove_lower_key (Hashtable<String, SpaceMarine> hashtable, Command command){
        try {
            List<String> listOfKeys = hashtable.keySet()
                    .stream()
                    .filter(s ->
                            ((s.length() > Integer.parseInt(command.getKey()))) ||
                                    ((!command.getPassword().getLogin().equals(hashtable.get(s).getUser()))))
                    .collect(Collectors.toList());
            Hashtable<String, SpaceMarine> hashtable1 = new Hashtable<>();
            for (String keyOfList : listOfKeys)
                for (String key : hashtable.keySet())
                    if (keyOfList.equals(key)) hashtable1.put(key, hashtable.get(key));
            return hashtable1;
        }catch (NumberFormatException e){
            return hashtable;
        }
    }
    public String remove_lower_key (Command command, int size1, int size2){
        try {
            if (size1 != size2) return "Элементы коллекции с длиной ключа меньше " +
                    Integer.parseInt(command.getKey()) +
                    " успешно удалены!\n";
            else return "Элементы коллекции с длиной ключа меньше " +
                    Integer.parseInt(command.getKey()) +
                    " не найдены!\n";
        }catch(NumberFormatException e){
            return "Длина ключа - целое число!\n";
        }
    }
    public List<SpaceMarine> filter_greater_than_achievements(Hashtable<String, SpaceMarine> hashtable, Command command){
        return hashtable
                    .values()
                    .stream()
                    .filter(s ->
                            ((s.getAchievements().length() > Integer.parseInt(command.getKey()))) ||
                                    ((!command.getPassword().getLogin().equals(s.getUser()))))
                    .collect(Collectors.toList());
    }
    public String insert(Command command){
        return "Элемент коллекции с ключом \"" +
                command.getKey() +
                "\" успешно добавлен/обновлен!";
    }
    public Hashtable<String, SpaceMarine> update(Hashtable<String, SpaceMarine> hashtable, Command command){
        try {
            for (String key : hashtable.keySet()){
                hashtable.get(key).setKey(key);
            }
            String cUser = command.getPassword().getLogin();
            SpaceMarine spaceMarine = null;
            for (String key : hashtable.keySet()) {
                String s = command.getKey();
                String hUser = hashtable.get(key).getUser();
                if ((s.equals(Integer.toString(hashtable.get(key).getId()))) &&
                        (hUser.equals(cUser))){
                    spaceMarine = command.getSpaceMarine();
                    spaceMarine.setUser(hashtable.get(key).getUser());
                    spaceMarine.setId(hashtable.get(key).getId());
                    spaceMarine.setKey(hashtable.get(key).getKey());
                    spaceMarine.setCreationDate(hashtable.get(key).getCreationDate());
                }
            }
            if (spaceMarine != null){
                hashtable.put(spaceMarine.getKey(), spaceMarine);
            }
            return hashtable;
        }catch (NumberFormatException e){
            return hashtable;
        }
    }
    public String update(Command command, Hashtable<String, SpaceMarine> hashtable){
        try {
            String cUser = command.getPassword().getLogin();
            int id = -1;
            String hUser = null;
            for (String key : hashtable.keySet()) {
                hUser = hashtable.get(key).getUser();
                if ((hashtable.get(key).getId() == Integer.parseInt(command.getKey().trim())) &&
                        (hUser.equals(cUser))){
                    id = hashtable.get(key).getId();
                }
            }
            if (id > -1) return "1";
            if (hUser == null) return "3";
            if (hUser.equals(cUser)) return "2";
        }catch (NumberFormatException e){
            return "4";
        }
        return null;
    }
}
