package client.main;

import server.all.All;
import server.authorization.Password;
import server.command.Answer;
import server.command.Command;
import server.command.NameOfCommand;

import java.io.*;
import java.net.Socket;

public class ExecuteCommand {

    public All command(Socket s, Password password, Command command) throws ClassNotFoundException {
        All all = new All();
        all.setOption(2);
        all.setPassword(password);
        all.setCommand(command);

        try {
            write(all, s);

            // Чтение результата работы сервера
            ObjectInputStream objectInputStream = new ObjectInputStream(s.getInputStream());
            all = (All) objectInputStream.readObject();
            
            if (command.getNameOfCommand() == NameOfCommand.EXIT) System.exit(0);
            return all;
        }catch(IOException e){
            System.out.println("Сервер временно не доступен. " +
                    "Как только соединение будет восстановленно, работа возобновится!");
            return all;
        }
    }
    public void write(All all, Socket s) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(bos);
        out.writeObject(all);
        out.flush();
        byte[] yourBytes = bos.toByteArray();
        OutputStream os = s.getOutputStream();
        os.write(yourBytes);
    }
}
