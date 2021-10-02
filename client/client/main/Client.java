package client.main;

import server.all.All;
import server.authorization.Password;
import server.command.Command;
import server.command.NameOfCommand;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Random;

public class Client {

    public static final int PORT = 5002;
    public static final String HOST = "localhost";

    private Password password;

    private double hue;

    public void setPassword(Password password) {
        this.password = password;
    }

    public Password getPassword() {
        return password;
    }

    public double getHue() {
        return hue;
    }

    public Client() {
    }

    public Socket connect(){
        try {
            // Подключение
            return new Socket(HOST, PORT);
        }catch(IOException e){
            return null;
        }
    }

    // Логин & Пароль
    public All loginAndPassword(String username, String password, boolean registration){
        try {
            // Логин
            // Пароль
            Socket s = connect();
            All all = new All();
            all.setOption(1);

            try {
                Password p = new Password();
                //Логин
                p.setLogin(username);

                //Пароль
                char[] passwd = password.toCharArray();
                MessageDigest md = MessageDigest.getInstance("SHA-384");
                byte[] hash = md.digest(Arrays.toString(passwd).getBytes(StandardCharsets.UTF_8));
                p.setPassword(hash);
                p.setRegistration(registration);
                this.setPassword(p);

                all.setPassword(p);

                //Запись на сервер
                write(all, s);

                // Чтение ответа сервера
                ObjectInputStream objectInputStream = new ObjectInputStream(s.getInputStream());
                all = ((All) objectInputStream.readObject());
                s.close();

                Random rand = new Random();
                hue = rand.nextDouble();

                return all;
            }catch(IOException e){
                System.out.println("Сервер временно не доступен. " +
                        "Как только соединение будет восстановленно, работа возобновится!");
                s.close();
                return null;
            }
        }catch(ClassNotFoundException | NoSuchAlgorithmException | IOException e){
            return null;
        }
    }

    public All command(Command command) throws ClassNotFoundException, IOException {
        // Команды
        Socket s = connect();
            All all = new All();
            all.setOption(2);

            all.setPassword(this.getPassword());
            all.setCommand(command);

            try {
                write(all, s);

                // Чтение результата работы сервера
                ObjectInputStream objectInputStream = new ObjectInputStream(s.getInputStream());
                all = (All) objectInputStream.readObject();

                if (command.getNameOfCommand() == NameOfCommand.EXIT) System.exit(0);
                s.close();
                return all;
            }catch(IOException e){
                System.out.println("Сервер временно не доступен. " +
                        "Как только соединение будет восстановленно, работа возобновится!");
                s.close();
                return null;
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
