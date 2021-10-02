package server.accept;

import server.all.All;
import server.authorization.Password;
import server.command.Answer;
import server.database.DataBase;
import server.process.commands.Process;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.sql.SQLException;

public class Action {
    private All all;

    SocketChannel client;
    public Action(SocketChannel socketChannel){
        client = socketChannel;
    }

    int action = 0;
    public int getAction(){
        return action;
    }

    public void read() {
        try {
            if (client.isOpen()) {
                ByteBuffer buffer = ByteBuffer.allocate(1000000);
                client.read(buffer);
                ObjectInput in = new ObjectInputStream(new ByteArrayInputStream(buffer.array()));
                all = (All) in.readObject();
                action = 1;
            } else {
                System.out.println("client is closed");
            }
        }catch(IOException | ClassNotFoundException e){
        }
    }

    public void work(){
        try{
            if (action == 1) {
                System.out.println("work");
                Manager manager = new Manager();
                String login = all.getPassword().getLogin();
                Password password = all.getPassword();
                if (all.getOption() == 1) {
                    System.out.println("Обработка запроса (логин и пароль). Поток " + Thread.currentThread());
                    Answer answer = new Answer();
                    boolean check = DataBase.getInstance().findUser(login);
                    if ((check && password.getRegistration())
                            || (!check && !password.getRegistration())) {
                        answer.setCheck(false);
                    } else {
                        if (password.getRegistration()) {
                            DataBase.getInstance().addUser(login, password.getPassword());
                            answer.setCheck(true);
                        } else {
                            check = manager.readPassword(login, password);
                            answer.setCheck(check);
                        }
                    }
                    all.setOption(0);
                    all.setAnswer(answer);
                } else if (all.getOption() == 2) {
                    // До сюда не доходит all с countActions = -1...
                    System.out.println("Обработка запроса (команда). Поток " + Thread.currentThread());
                    Answer answer = new Process().process(all.getCommand());
                    all.setAnswer(answer);
                }
                action = 2;
            }
        } catch (SQLException | IOException | NullPointerException e) {
        }
    }

    public void write(){
        try {
            if (action == 2) {
                System.out.println("write");
                writeAll();
                action = 0;
            }
        } catch (IOException e) {
        }
    }
    public void writeAll() throws IOException {
        if (client.isOpen()) {
            // Запись ответа сервера клиенту
            ByteBuffer buffer = ByteBuffer.allocate(1000000);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream out = new ObjectOutputStream(bos);
            out.writeObject(all);
            out.flush();
            byte[] b = bos.toByteArray();
            buffer.clear();
            for (byte value : b) {
                buffer.put(value);
            }

            buffer.flip();
            client.write(buffer);
            if (buffer.hasRemaining()) {
                buffer.compact();
            } else {
                buffer.clear();
            }
        }else{
        }
    }
}
