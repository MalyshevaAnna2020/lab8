package server.process.commands;

import server.command.Answer;
import server.command.Command;
import server.database.DataBase;
import server.spacemarine.SpaceMarine;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Hashtable;

public class Process {
    public Answer process(Command command) throws IOException, SQLException {
        System.out.println("Обработка команды " + command.getNameOfCommand() + "!");
        return new Play().play(command);
    }
}
