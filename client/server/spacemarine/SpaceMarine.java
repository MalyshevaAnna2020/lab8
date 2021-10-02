package server.spacemarine;

import java.io.Serializable;
import java.text.DateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Date;
import java.util.Locale;

public class SpaceMarine implements Serializable {
    private static final long serialVersionUID = 70000000L;
    private String key;
    private int id; //Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private String creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private Long health = null; //Поле может быть null, Значение поля должно быть больше 0
    private int heartCount; //Значение поля должно быть больше 0, Максимальное значение поля: 3
    private String achievements = null; //Поле может быть null
    private AstartesCategory category = null; //Поле может быть null
    private Chapter chapter = null; //Поле может быть null
    private String user;

    private Locale locale;
    public void setLocale(Locale locale){
        this.locale = locale;
    }

    public void setKey(String key){ this.key = key;}
    public void setId(int id){this.id = id;}
    public void setName(String name){this.name = name;}
    public void setCoordinates(Coordinates coordinates){
        this.coordinates = coordinates;
    }
    public void setCreationDate(){
        this.creationDate = Instant.now().toString();
    }
    public void setHealth(Long health){this.health = health; }
    public void setHeartCount(int heartCount){this.heartCount = heartCount; }
    public void setAchievements(String achievements){this.achievements = achievements;}
    public void setCategory(String category){
        switch (category) {
            case "TERMINATOR":
                this.category = AstartesCategory.TERMINATOR;
                break;
            case "LIBRARIAN":
                this.category = AstartesCategory.LIBRARIAN;
                break;
            case "APOTHECARY":
                this.category = AstartesCategory.APOTHECARY;
                break;
            case "SUPPRESSOR":
                this.category = AstartesCategory.SUPPRESSOR;
                break;
        }
    }
    public void setChapter(Chapter chapter) {
        this.chapter = chapter;
    }
    public void setUser(String user) {
        this.user = user;
    }

    public String getKey(){return key;}
    public int getId(){
        return id;
    }
    public String getName(){
        return name;
    }
    public Coordinates getCoordinates(){
        return coordinates;
    }
    public String getCreationDate(){
        DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime( FormatStyle.SHORT ).withLocale( locale );
        ZoneOffset offset = ZoneId.systemDefault().getRules().getOffset(Instant.now()) ;
        ZonedDateTime creation = ZonedDateTime.parse(creationDate).plusSeconds(offset.getTotalSeconds());
        return creation.format(formatter);
    }
    public Long getHealth(){
        return health;
    }
    public int getHeartCount(){
        return heartCount;
    }
    public String getAchievements(){
        return achievements;
    }
    public AstartesCategory getCategory(){
        return category;
    }
    public Chapter getChapter(){
        return chapter;
    }
    public String getUser() {
        return user;
    }

    @Override
    public String toString(){
        return this.getName() + " " + this.getId();
    }

}