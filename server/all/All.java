package server.all;

import server.authorization.Password;
import server.command.Answer;
import server.command.Command;

import java.io.Serializable;

public class All implements Serializable {
    // Логин, пароль, команда
    private static final long serialVersionUID = 1L;
    private int option;
    // 1 - логин & пароль, 2 - команда
    private Password password;
    private Command command;
    private Answer answer;

    public void setOption(int option) {
        this.option = option;
    }

    public void setPassword(Password password) {
        this.password = password;
    }

    public void setCommand(Command command) {
        this.command = command;
    }

    public void setAnswer(Answer answer) {
        this.answer = answer;
    }

    public int getOption() {
        return option;
    }

    public Password getPassword() {
        return password;
    }

    public Command getCommand() {
        return command;
    }

    public Answer getAnswer() {
        return answer;
    }
}
