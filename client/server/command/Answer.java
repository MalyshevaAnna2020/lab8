package server.command;

import server.spacemarine.SpaceMarine;

import java.io.Serializable;
import java.util.Hashtable;

public class Answer implements Serializable {

    private static final long serialVersionUID = 1000L;
    private String answer;
    private boolean check;
    private Hashtable<String, SpaceMarine> spaceMarineList;

    public void setKey(String answer){
        this.answer = answer;
    }
    public String getKey(){
        return answer;
    }

    public void setCheck(boolean check){
        this.check = check;
    }
    public boolean getCheck(){
        return check;
    }

    public void setSpaceMarineList(Hashtable<String, SpaceMarine> spaceMarineList) {
        this.spaceMarineList = spaceMarineList;
    }

    public Hashtable<String, SpaceMarine> getSpaceMarineList() {
        return spaceMarineList;
    }

}
