package server.authorization;

import java.io.Serializable;

public class Password implements Serializable {
    private static final long serialVersionUID = 100L;
    private byte [] hash;
    private String login;
    boolean registration;

    public void setPassword(byte[] password){ this.hash = password;}
    public byte[] getPassword(){ return hash;}

    public void setLogin(String login){ this.login = login;}
    public String getLogin(){ return login;}

    public void setRegistration(boolean registration){
        this.registration = registration;
    }
    public boolean getRegistration(){
        return registration;
    }
}
