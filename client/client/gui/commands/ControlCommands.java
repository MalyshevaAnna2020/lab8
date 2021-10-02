package client.gui.commands;

import client.gui.execute.*;
import client.gui.simplify.FullScreen;
import client.gui.simplify.LocaleRegular;
import client.gui.simplify.TextRegular;
import client.main.Client;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import server.all.All;
import server.command.Command;
import server.command.NameOfCommand;
import server.spacemarine.Chapter;
import server.spacemarine.Coordinates;
import server.spacemarine.SpaceMarine;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class ControlCommands {
    private LocaleRegular locale;
    private Client client;
    private Stage stage;

    public void setLocale(LocaleRegular locale){
        this.locale = locale;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
    public void setText(){
        current_user.setText(locale.getBundle().getString("current_user") + "\n" + client.getPassword().getLogin());
    }

    @FXML
    private BorderPane mainPane;
    @FXML
    private VBox vBox;
    @FXML
    private Text current_user;

    @FXML
    private void getHelp(){
        delete();
        new ExecuteHelp().show(locale.getBundle(), mainPane, vBox);
    }
    @FXML
    public void getInfo() {
        delete();

        Command command = new Command();
        command.setNameOfCommand(NameOfCommand.INFO);
        try{
            All all = client.command(command);
            new ExecuteInfo().show(all, mainPane, stage, vBox, locale.getBundle().getLocale(), client, locale, this);
        }catch(ClassNotFoundException | IOException | NullPointerException e){
            serverError();
        }
    }

    private boolean checkShow = false;
    public boolean getCheckShow(){
        return checkShow;
    }
    @FXML
    private void getShow() {
        delete();
        checkShow = true;

        Command command = new Command();
        command.setNameOfCommand(NameOfCommand.SHOW);
        try {
            All all = client.command(command);

            new ExecuteShow().show(all, mainPane, stage, locale.getBundle().getLocale(), this, client, locale);
        }catch(ClassNotFoundException | IOException | NullPointerException e){
            serverError();
        }
    }
    @FXML
    public void getInsert() throws IOException {
        delete();

        new ExecuteInsert().show(locale.getBundle(), client, mainPane, this);

    }
    @FXML
    private void getUpdate() throws IOException {
        delete();
        new ExecuteUpdate().show(locale.getBundle(), client, mainPane, this);
    }
    @FXML
    private void getRemoveKey(){
        delete();
        ExecuteRemove executeRemoveKey = new ExecuteRemove();
        GridPane gridPane = executeRemoveKey.show("key", locale.getBundle().getString("remove"));
        mainPane.setCenter(gridPane);
        gridPane.setAlignment(Pos.CENTER);

        JFXButton button = (JFXButton) gridPane.getChildren().stream().filter(c -> c.getClass() == JFXButton.class).collect(Collectors.toList()).get(0);
        button.setOnAction(event -> {
            try {
                executeRemoveKey.action(client, locale.getBundle(), mainPane, NameOfCommand.REMOVE_KEY, true);
            }catch(ClassNotFoundException | IOException | NullPointerException e){
                serverError();
            }
        });
    }
    @FXML
    private void getRemoveLower(){
        delete();
        ExecuteRemove executeRemoveKey = new ExecuteRemove();
        GridPane gridPane = executeRemoveKey.show("id", locale.getBundle().getString("remove"));
        mainPane.setCenter(gridPane);
        gridPane.setAlignment(Pos.CENTER);
        JFXButton button = (JFXButton) gridPane.getChildren().stream().filter(c -> c.getClass() == JFXButton.class).collect(Collectors.toList()).get(0);
        button.setOnAction(event -> {
            try {
                executeRemoveKey.action(client, locale.getBundle(), mainPane, NameOfCommand.REMOVE_LOWER, true);
            }catch(ClassNotFoundException | IOException | NullPointerException e){
                serverError();
            }
        });
    }
    @FXML
    private void getRemoveGreaterKey(){
        delete();
        ExecuteRemove executeRemoveKey = new ExecuteRemove();
        // resource
        // key length
        GridPane gridPane = executeRemoveKey.show("key length", locale.getBundle().getString("remove"));
        mainPane.setCenter(gridPane);
        gridPane.setAlignment(Pos.CENTER);
        JFXButton button = (JFXButton) gridPane.getChildren().stream().filter(c -> c.getClass() == JFXButton.class).collect(Collectors.toList()).get(0);
        button.setOnAction(event -> {
            try{
            executeRemoveKey.action(client, locale.getBundle(), mainPane, NameOfCommand.REMOVE_GREATER_KEY, true);
            }catch(ClassNotFoundException | IOException | NullPointerException e){
                serverError();
            }
        });
    }
    @FXML
    private void getRemoveLowerKey(){
        delete();
        ExecuteRemove executeRemoveKey = new ExecuteRemove();
        // resource
        // key length
        GridPane gridPane = executeRemoveKey.show("key length", locale.getBundle().getString("remove"));
        mainPane.setCenter(gridPane);
        gridPane.setAlignment(Pos.CENTER);
        JFXButton button = (JFXButton) gridPane.getChildren().stream().filter(c -> c.getClass() == JFXButton.class).collect(Collectors.toList()).get(0);
        button.setOnAction(event -> {
            try{
            executeRemoveKey.action(client, locale.getBundle(), mainPane, NameOfCommand.REMOVE_LOWER_KEY, true);

            }catch(ClassNotFoundException | IOException | NullPointerException e){
                serverError();
            }
        });
    }
    @FXML
    private void getRemoveAnyByChapter(){
        delete();
        ExecuteRemove executeRemoveKey = new ExecuteRemove();
        GridPane gridPane = executeRemoveKey.show("chapter", locale.getBundle().getString("remove"));
        mainPane.setCenter(gridPane);
        gridPane.setAlignment(Pos.CENTER);
        JFXButton button = (JFXButton) gridPane.getChildren().stream().filter(c -> c.getClass() == JFXButton.class).collect(Collectors.toList()).get(0);
        button.setOnAction(event -> {
            try{
            executeRemoveKey.action(client, locale.getBundle(), mainPane, NameOfCommand.REMOVE_ANY_BY_CHAPTER, true);
            }catch(ClassNotFoundException | IOException | NullPointerException e){
                serverError();
            }
        });
    }
    @FXML
    private void getClear(){
        delete();

        ExecuteRemove executeRemove = new ExecuteRemove();
        try{
            executeRemove.action(client, locale.getBundle(), mainPane, NameOfCommand.CLEAR, false);
        }catch(ClassNotFoundException | IOException | NullPointerException e){
            serverError();
        }
    }
    @FXML
    private void getFilterGreaterThanAchievements(){
        delete();
        ExecuteRemove executeShow = new ExecuteRemove();
        GridPane gridPane = executeShow.show("string length of the achievements field", "show");
        mainPane.setCenter(gridPane);
        gridPane.setAlignment(Pos.CENTER);
        JFXButton button = (JFXButton) gridPane.getChildren().stream().filter(c -> c.getClass() == JFXButton.class).collect(Collectors.toList()).get(0);
        button.setOnAction(event -> {
            Command command = new Command();
            command.setNameOfCommand(NameOfCommand.FILTER_GREATER_THAN_ACHIEVEMENTS);
            try {
                All all = client.command(command);

                new ExecuteInfo().show(all, mainPane, stage, vBox, locale.getBundle().getLocale(), client, locale, this);
            }catch(ClassNotFoundException | IOException | NullPointerException e){
                serverError();
            }
        });
    }
    @FXML
    private void getPrintFieldDescendingCategory(){
        delete();
        new ExecuteCategory().show(locale.getBundle(), mainPane, client, stage, vBox, locale.getBundle().getLocale(), locale, this);
    }

    public void delete(){
        checkShow = false;
        mainPane.getChildren().removeAll(mainPane.getCenter());
        mainPane.getChildren().removeAll(mainPane.getChildren()
                .stream()
                .filter(s -> {
                            if ((s.getClass() == Rectangle.class) || (s.getClass() == Circle.class) ||
                                    (s.getClass() == Text.class) &&
                                            (!((Text) s).getText().equals(current_user.getText()))) return true;
                            if (s.getClass() == VBox.class) {
                                return mainPane.getLeft() != s;
                            }
                            return false;
                        }
                    ).collect(Collectors.toList()));
    }
    public void serverError(){
        Text alert = new TextRegular().getText("alert", locale.getBundle());
        alert.setStyle("-fx-fill: #FF3333;");
        mainPane.setCenter(alert);
    }

    private final List<File> files = new ArrayList<>();
    @FXML
    private void getExecuteScript(){
        delete();

        ExecuteRemove executeShow = new ExecuteRemove();
        GridPane gridPane = executeShow.show("path to the file", "execute");
        mainPane.setCenter(gridPane);
        gridPane.setAlignment(Pos.CENTER);

        TextField textField1 = (TextField) gridPane
                .getChildren()
                .stream()
                .filter(c->
                        c.getClass() == TextField.class)
                .collect(Collectors.toList()).get(0);
        JFXButton button = (JFXButton) gridPane.getChildren().stream().filter(c -> c.getClass() == JFXButton.class).collect(Collectors.toList()).get(0);
        button.setOnAction(event -> {
            String path = textField1.getText();
            File file = new File(path);
            executeScript(file);
        });
    }
    public void executeScript(File file) {
        FullScreen screen = new FullScreen();
        if (!file.exists()) {
            //Файл не существует! Эта команда не передана серверу!
            new TextRegular().setText(locale.getBundle(), mainPane, "file_does_not_exists");
        } else if (!file.canRead()) {
            //Файл не доступен для чтения! Эта команда не передана серверу!
            new TextRegular().setText(locale.getBundle(), mainPane, "file_is_not_available");
        } else {
            if (!files.contains(file)) {
                files.add(file);
                try {
                    //execute script
                    StringBuilder stringBuilder = new StringBuilder();
                    while (file.length() > 4096) {
                        stringBuilder.append(this.writeCommandsFromFiles(file));
                    }
                    stringBuilder.append(this.writeCommandsFromFiles(file));
                    String s = stringBuilder.toString() + "\n";
                    ExecuteScript executeScript = ExecuteScript.getInstance();
                    while (s.contains("\n")) {
                        // Команда
                        String newcommand = s.substring(0, s.indexOf("\n"));
                        System.out.println(newcommand);
                        // Такую команду не стоит обрабатывать
                        if (newcommand.equals("")) continue;
                        // Создание настоящей команды
                        Command command = executeScript.setCommand(newcommand);
                        command.setPassword(client.getPassword());
                        System.out.println(command.getNameOfCommand());
                        if (!(command.getNameOfCommand() == NameOfCommand.EXECUTE_SCRIPT
                                || command.getNameOfCommand() == NameOfCommand.HELP
                                || command.getNameOfCommand() == NameOfCommand.NOTHING)) {
                            // Если команда - insert или update, надо команде добавить SpaceMarine
                            SpaceMarine spmarine = new SpaceMarine();
                            // insert & update
                            if (command.getNameOfCommand() == NameOfCommand.INSERT) {
                                if (s.contains("\n")) {
                                    s = s.substring(s.indexOf("\n") + 1);
                                    if (s.equals("\n") || s.equals("")) break;
                                    spmarine.setKey(s.substring(0, s.indexOf("\n")));
                                    spmarine.setCreationDate();
                                } else {
                                    break;
                                }
                            }
                            if (command.getNameOfCommand() == NameOfCommand.UPDATE) {
                                if (s.contains("\n")) {
                                    s = s.substring(s.indexOf("\n") + 1);
                                    if (s.equals("\n") || s.equals("")) break;
                                    try {
                                        int x = Integer.parseInt(s.substring(0, s.indexOf("\n")).trim());
                                        spmarine.setId(x);
                                        System.out.println(x);
                                        command.setKey(String.valueOf(x));
                                    } catch (NumberFormatException e) {
                                        command.setKey("Error");
                                        System.out.println("Error");
                                    }
                                } else {
                                    break;
                                }
                            }
                            if (command.getNameOfCommand() == NameOfCommand.INSERT || command.getNameOfCommand() == NameOfCommand.UPDATE) {
                                int count = 0;
                                Coordinates coordinates = new Coordinates();
                                Chapter chapter = new Chapter();
                                while (s.contains("\n")) {
                                    s = s.substring(s.indexOf("\n") + 1);
                                    if (s.equals("\n") || s.equals("")) break;
                                    spmarine = executeScript.setSpacemarine(s, spmarine, count, coordinates, chapter);
                                    count++;
                                }
                                for (int i = count; i < 9; i++) {
                                    spmarine = executeScript.setSpacemarine(i, spmarine, coordinates, chapter);
                                }
                                command.setSpaceMarine(spmarine);
                            }
                            //remove key, remove any by chapter, print field descending category
                            if (command.getNameOfCommand() == NameOfCommand.REMOVE_KEY
                                    || command.getNameOfCommand() == NameOfCommand.REMOVE_ANY_BY_CHAPTER
                                    || command.getNameOfCommand() == NameOfCommand.PRINT_FIELD_DESCENDING_CATEGORY) {
                                if (s.contains("\n")) {
                                    String key = s.substring(command.getNameOfCommand().toString().length() + 1, s.indexOf("\n"));
                                    s = s.substring(s.indexOf("\n") + 1);
                                    if (command.getNameOfCommand() == NameOfCommand.PRINT_FIELD_DESCENDING_CATEGORY){
                                        if (!(key.equals("SUPPRESSOR") || key.equals("TERMINATOR")
                                                || key.equals("LIBRARIAN") || key.equals("APOTHECARY"))){
                                            continue;
                                        }
                                    }
                                    command.setKey(key);
                                } else {
                                    break;
                                }
                            }
                            //remove lower, remove lower key, remove greater key, filter greater than achievements
                            if (command.getNameOfCommand() == NameOfCommand.REMOVE_GREATER_KEY
                                    || command.getNameOfCommand() == NameOfCommand.REMOVE_LOWER
                                    || command.getNameOfCommand() == NameOfCommand.REMOVE_LOWER_KEY
                                    || command.getNameOfCommand() == NameOfCommand.FILTER_GREATER_THAN_ACHIEVEMENTS) {
                                if (s.contains("\n")) {
                                    String key = s.substring(command.getNameOfCommand().toString().length() + 1, s.indexOf("\n")).trim();
                                    s = s.substring(s.indexOf("\n") + 1);
                                    try {
                                        int i = Integer.parseInt(key);
                                        System.out.println(i);
                                        command.setKey(String.valueOf(i));
                                    } catch (NumberFormatException e) {
                                        command.setKey("Error");
                                    }
                                } else {
                                    break;
                                }
                            }
                        }
                        if (command.getNameOfCommand() != NameOfCommand.NOTHING
                                && command.getNameOfCommand() != NameOfCommand.EXECUTE_SCRIPT) {
                            BorderPane root;
                            LocaleRegular locale = new LocaleRegular();
                            FXMLLoader loader = new FXMLLoader();
                            loader.setResources(locale.getBundle());
                            try {
                                root = loader.load(getClass().getResourceAsStream("commands.fxml"));
                                ControlCommands controller = loader.getController();
                                controller.setClient(client);
                                controller.setLocale(locale);

                                Stage stage = new Stage();
                                screen.fullScreen(stage);
                                stage.setScene(new Scene(root));
                                controller.setStage(stage);
                                controller.setText();
                                System.out.println(current_user.getText());
                                All all = null;
                                if (command.getNameOfCommand() != NameOfCommand.HELP) {
                                    try{
                                        all = client.command(command);
                                    }catch(ClassNotFoundException | IOException e){
                                        serverError();
                                        break;
                                    }
                                }
                                ExecuteRemove show = new ExecuteRemove();
                                switch (command.getNameOfCommand()) {
                                    case HELP:
                                        new ExecuteHelp().show(locale.getBundle(), root, vBox);
                                        break;
                                    case INFO:
                                    case FILTER_GREATER_THAN_ACHIEVEMENTS:
                                    case PRINT_FIELD_DESCENDING_CATEGORY:
                                        assert all != null;
                                        new ExecuteInfo().show(all, root, stage, vBox, locale.getBundle().getLocale(), client, locale, this);
                                        break;
                                    case SHOW:
                                        assert all != null;
                                        new ExecuteShow().show(all, root, stage, locale.getBundle().getLocale(), this, client, locale);
                                        break;
                                    case INSERT:
                                        assert all != null;
                                        insert(all, command, root);
                                        break;
                                    case UPDATE:
                                        assert all != null;
                                        update(all, command, root);
                                        break;
                                    case REMOVE_KEY:
                                    case REMOVE_LOWER:
                                    case REMOVE_LOWER_KEY:
                                    case REMOVE_GREATER_KEY:
                                    case REMOVE_ANY_BY_CHAPTER:
                                    case CLEAR:
                                        assert all != null;
                                        show.show(all, locale.getBundle(), root);
                                        break;
                                }
                                stage.show();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }

                        //execute script
                        if (newcommand.contains("execute_script")) {
                            File file1 = new File(newcommand.substring
                                    (newcommand.indexOf("execute_script") + ("execute_script").length() + 1).trim());
                            executeScript(file1);
                        }
                        s = s.substring(s.indexOf("\n") + 1);
                        if (s.length() <= 1) break;
                    }
                } catch (IOException | NullPointerException e) {
                    serverError();
                }
            } else {
                BorderPane root;
                LocaleRegular locale = new LocaleRegular();
                FXMLLoader loader = new FXMLLoader();
                loader.setResources(locale.getBundle());
                try {
                    root = loader.load(getClass().getResourceAsStream("commands.fxml"));
                    Text alert = new Text("execute_script " + file.getAbsolutePath() +
                            locale.getBundle().getString("recursion"));
                    alert.setStyle("-fx-fill: #FF3333;");
                    root.setCenter(alert);
                    ControlCommands controller = loader.getController();
                    controller.setClient(client);
                    controller.setLocale(locale);
                    controller.setText();

                    Stage stage = new Stage();
                    stage.setScene(new Scene(root));
                    screen.fullScreen(stage);
                    stage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        files.clear();
    }
    public String writeCommandsFromFiles(File file) throws IOException {
        char [] chars = new char[4096];
        FileReader reader = new FileReader(file);
        reader.read(chars);
        String s = (new String(chars)).trim();
        reader.close();
        return s;
    }
    private void insert(All all, Command command, BorderPane root){
        Text text = new Text();
        text.setWrappingWidth(800);
        text.setFont(new Font("Times New Roman", 24));
        text.setFill(Paint.valueOf("#ff3333"));
        if (all.getAnswer().getCheck()){
            text.setText(locale.getBundle().getString("space_marine") + " " + command.getSpaceMarine().getKey() + " " +
                    locale.getBundle().getString("insert_spacemarine"));
        }else{
            text.setText(locale.getBundle().getString("space_marine") + " " +
                    locale.getBundle().getString("with") + " \"" + command.getSpaceMarine().getKey() + "\" " +
                    locale.getBundle().getString("already_exists"));
        }
        root.setCenter(text);
        BorderPane.setAlignment(text, Pos.TOP_CENTER);
        BorderPane.setMargin(text, new Insets(100, 0, 0, 0));
    }
    private void update(All all, Command command, BorderPane root){
        String option = all.getAnswer().getKey().substring(0, 1);

        Text text = new Text();
        text.setWrappingWidth(800);
        text.setFont(new Font("Times New Roman", 24));
        text.setFill(Paint.valueOf("#ff3333"));

        // 1 - добавлен
        // 2 - ограничение доступа
        // 3 - spacemarine не найден
        if (option.equals("1")){
            text.setText(locale.getBundle().getString("space_marine") + " " +
                    locale.getBundle().getString("with") + " " + command.getSpaceMarine().getId() + " " +
                    locale.getBundle().getString("update_spacemarine"));
        }else{
            if (option.equals("2")) {
                text.setText(locale.getBundle().getString("space_marine") + " " +
                        locale.getBundle().getString("with") + " id=" + command.getSpaceMarine().getId() + " " +
                        locale.getBundle().getString("another_user"));
            }else{
                text.setText(locale.getBundle().getString("space_marine") + " " +
                        locale.getBundle().getString("with") + " id=" + command.getSpaceMarine().getId() + " " +
                        locale.getBundle().getString("spacemarine_exists") + "!");
            }
        }
        root.setCenter(text);
        BorderPane.setAlignment(text, Pos.TOP_CENTER);
        BorderPane.setMargin(text, new Insets(100, 0, 0, 0));
    }
    @FXML
    private JFXButton help;
    @FXML
    private JFXButton info;
    @FXML
    private JFXButton show;
    @FXML
    private JFXButton insert;
    @FXML
    private JFXButton update;
    @FXML
    private JFXButton remove_key;
    @FXML
    private JFXButton clear;
    @FXML
    private JFXButton execute_script;
    @FXML
    private JFXButton remove_lower_key;
    @FXML
    private JFXButton remove_lower;
    @FXML
    private JFXButton remove_greater_key;
    @FXML
    private JFXButton remove_any_by_chapter;
    @FXML
    private JFXButton filter_greater_than_achievements;
    @FXML
    private JFXButton print_field_descending_category;
    @FXML
    private ComboBox<String> comboBox;
    @FXML
    private void changeLanguage(ActionEvent event){
        locale.setLocale(comboBox.getValue());

        help.setText(locale.getBundle().getString("help"));
        info.setText(locale.getBundle().getString("info"));
        show.setText(locale.getBundle().getString("show"));
        insert.setText(locale.getBundle().getString("insert"));
        update.setText(locale.getBundle().getString("update"));
        remove_key.setText(locale.getBundle().getString("remove_key"));
        clear.setText(locale.getBundle().getString("clear"));
        execute_script.setText(locale.getBundle().getString("execute_script"));
        remove_lower_key.setText(locale.getBundle().getString("remove_lower_key"));
        remove_lower.setText(locale.getBundle().getString("remove_lower"));
        remove_greater_key.setText(locale.getBundle().getString("remove_greater_key"));
        remove_any_by_chapter.setText(locale.getBundle().getString("remove_any_by_chapter"));
        filter_greater_than_achievements.setText(locale.getBundle().getString("filter_greater_than_achievements"));
        print_field_descending_category.setText(locale.getBundle().getString("print_field_descending_category"));
    }
}
