package server.spacemarine;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class SpaceMarine implements Serializable {
    private static final long serialVersionUID = 70000000L;
    private String key;
    private int id; //Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private String creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private Long health = 0L; //Значение поля должно быть больше 0
    private int heartCount; //Значение поля должно быть больше 0, Максимальное значение поля: 3
    private String achievements = null; //Поле может быть null
    private AstartesCategory category = null; //Поле может быть null
    private Chapter chapter = null; //Поле может быть null
    private String user;

    // сеттеры
    public void setKey(String key){this.key = key;}
    public void setId(int id){this.id = id;}
    public void setName(String name){this.name = name;}
    public void setCoordinates(Coordinates coordinates){ this.coordinates = coordinates; }
    public void setCreationDate(String date){
        this.creationDate = date;
    }
    public void setHealth(long health){ this.health = health; }
    public void setHeartCount(int heartCount){ this.heartCount = heartCount; }
    public void setAchievements(String achievements){this.achievements = achievements;}
    public void setCategory(String category){
        switch (category) {
            case "SUPPRESSOR":
                this.category = AstartesCategory.SUPPRESSOR;
                break;
            case "TERMINATOR":
                this.category = AstartesCategory.TERMINATOR;
                break;
            case "LIBRARIAN":
                this.category = AstartesCategory.LIBRARIAN;
                break;
            case "APOTHECARY":
                this.category = AstartesCategory.APOTHECARY;
                break;
            default:
                System.out.println("Значение поля category элемента с id=" + this.getId() + " равно null!");
                break;
        }
    }
    public void setChapter(Chapter chapter) { this.chapter = chapter;}
    public void setUser(String user) {this.user = user;}

    // геттеры

    public String getKey() { return key;}
    public int getId(){ return id;}
    public String getName(){ return name;}
    public Coordinates getCoordinates(){ return coordinates;}
    public String getCreationDate(){ return creationDate;}
    public Long getHealth(){ return health;}
    public int getHeartCount(){ return heartCount;}
    public String getAchievements(){ return achievements;}
    public AstartesCategory getCategory(){ return category;}
    public Chapter getChapter(){ return chapter;}
    public String getUser(){ return user;}

    // toString()
    @Override
    public String toString(){
        return this.getName() + " " + this.getId();
    }
    public int getSize(){

        try{
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);

            objectOutputStream.writeObject(this);
            return byteArrayOutputStream.size();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Что-то пошло не так?
        return -1;
    }
}