package server.accept;

import server.all.All;
import server.authorization.Password;
import server.command.Answer;
import server.database.DataBase;
import server.process.commands.Process;

import java.io.IOException;
import java.nio.channels.SocketChannel;
import java.sql.SQLException;

public class Work extends Thread{

    private SocketChannel client;
    public SocketChannel getClient(){
        return client;
    }

    public void setClient(SocketChannel client) {
        this.client = client;
    }

    private All all;

    public All getAll() {
        return all;
    }

    public void setAll(All all) {
        this.all = all;
    }
    private int action;

    public int getAction() {
        return action;
    }

    public void setAction(int action) {
        this.action = action;
    }

    public Work(SocketChannel socketChannel){
        this.client = socketChannel;
    }

    @Override
    public void run(){
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
}
