package client.gui.execute;

import client.gui.commands.ControlCommands;
import client.main.Client;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import server.all.All;
import server.command.Command;
import server.command.NameOfCommand;
import server.spacemarine.AstartesCategory;
import server.spacemarine.Chapter;
import server.spacemarine.Coordinates;
import server.spacemarine.SpaceMarine;

import java.io.IOException;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;

public class ControlInsert {
    private ResourceBundle resource;
    public void setResource(ResourceBundle resource) {
        this.resource = resource;
    }

    private Client client;
    public void setClient(Client client) {
        this.client = client;
    }

    private BorderPane mainPane;
    public void setMainPane(BorderPane borderPane) {
        mainPane = borderPane;
    }

    @FXML
    private GridPane gridPane;

    private Node[][] gridPaneArray;
    private void initializeGridPaneArray()
    {
        this.gridPaneArray = new Node[12][3];
        for(Node node : this.gridPane.getChildren()) {
            this.gridPaneArray[GridPane.getRowIndex(node)][GridPane.getColumnIndex(node)] = node;
        }
    }

    @FXML
    private TextField insertKey;
    @FXML
    private TextField insertName;
    @FXML
    private TextField insertX;
    @FXML
    private TextField insertY;
    @FXML
    private TextField insertHealth;
    @FXML
    private ComboBox<Integer> insertHeartCount;
    public void setComboBoxes(){
        ObservableList<Integer> heartCounts = FXCollections.observableArrayList(1, 2, 3);
        insertHeartCount.setItems(heartCounts);
        insertHeartCount.setValue(1);

        ObservableList<AstartesCategory> categories = FXCollections.observableArrayList(AstartesCategory.values());
        insertCategory.setItems(categories);
        insertCategory.setValue(AstartesCategory.SUPPRESSOR);
    }
    @FXML
    private TextField insertAchievements;
    @FXML
    private TextField insertNameOfChapter;
    @FXML
    private TextField insertMarinesCount;
    @FXML
    private ComboBox<AstartesCategory> insertCategory;

    @FXML
    private Text alert_0;
    @FXML
    private Text alert_1;
    @FXML
    private Text alert_2;
    @FXML
    private Text alert_3;
    @FXML
    private Text alert_4;
    @FXML
    private Text alert_5;
    @FXML
    private Text alert_6;
    @FXML
    private Text alert_7;
    @FXML
    private Text alert_8;
    @FXML
    private Text alert_9;

    @FXML
    private void insert(){
        boolean insert = true;

        alert_2.setStyle("-fx-fill: #FF3333;");
        alert_3.setStyle("-fx-fill: #FF3333;");
        alert_4.setStyle("-fx-fill: #FF3333;");
        alert_9.setStyle("-fx-fill: #FF3333;");
        this.initializeGridPaneArray();
        for (Node textField : gridPane.getChildren()){
            if (textField.getClass() == TextField.class){
                int row = GridPane.getRowIndex(textField);
                Text alert = (Text) this.gridPaneArray[row][2];
                if (((TextField) textField).getText().length() == 0
                        && !textField.equals(insertAchievements)){
                    alert.setText(resource.getString("empty_field"));
                    insert=false;
                }else{
                    alert.setText("");
                }
            }
        }
        String xStr = insertX.getText();
        if (!xStr.equals("")) {
            if (xStr.endsWith(".")){
                alert_2.setText(resource.getString("dot"));
                insert = false;
            }else {
                if (xStr.endsWith(",")) {
                    alert_2.setText(resource.getString("comma"));
                    insert = false;
                }else {
                    if (xStr.contains(",")) xStr = xStr.replace(",", ".");
                    double x = Double.parseDouble(xStr);
                    if (x > 955) {
                        alert_2.setText("x ≤ 955!");
                        insert = false;
                    }
                }
            }
        }
        String healthStr = insertHealth.getText();
        if (!healthStr.equals("")) {
            if (Integer.parseInt(healthStr) == 0) {
                alert_4.setText(resource.getString("positiveInteger") + "!");
                insert = false;
            }
        }
        String marinesStr = insertMarinesCount.getText();
        if (!marinesStr.equals("")) {
            int marines = Integer.parseInt(marinesStr);
            if (marines == 0) {
                alert_9.setText(resource.getString("positiveInteger") + "!");
                insert = false;
            }
            if (marines > 1000){
                alert_9.setText("marines count ≤ 1000");
                insert = false;
            }
        }

        if (insert) {
            Command command = new Command();
            command.setNameOfCommand(NameOfCommand.INSERT);
            command.setSpaceMarine(setSpaceMarine());
            command.setPassword(client.getPassword());
            command.setKey(insertKey.getText());

            try {
                All all = client.command(command);

                if (all.getAnswer().getCheck()) {
                    Text text = new Text();
                    text.setWrappingWidth(800);
                    text.setFont(new Font("Times New Roman", 24));
                    text.setFill(Paint.valueOf("#ff3333"));
                    text.setText(resource.getString("space_marine") + " " + insertKey.getText() + " " +
                            resource.getString("insert_spacemarine"));
                    mainPane.setCenter(text);
                    BorderPane.setAlignment(text, Pos.TOP_CENTER);
                    BorderPane.setMargin(text, new Insets(100, 0, 0, 0));
                } else {
                    alert_0.setText(resource.getString("space_marine") + " " +
                            resource.getString("with") + " \"" + insertKey.getText() + "\" " +
                            resource.getString("already_exists"));
                }
            }catch(ClassNotFoundException | IOException | NullPointerException e){
                controlCommands.serverError();
            }
        }
    }

    private ControlCommands controlCommands;
    public void setControlCommands(ControlCommands controlCommands){
        this.controlCommands = controlCommands;
    }

    private void setFormat(TextField textField, String format) {
        Pattern pattern = Pattern.compile(format);
        TextFormatter formatter = new TextFormatter(
                (UnaryOperator<TextFormatter.Change>) change ->
                        pattern
                                .matcher(change.getControlNewText())
                                .matches() ? change : null);
        textField.setTextFormatter(formatter);
    }

    public void setOnlyNumbers() {
        this.setFormat(insertX, "\\d*|\\d+\\,\\d*|-\\d*|-\\d+\\,\\d*|\\d+\\.\\d*|-\\d+\\.\\d*");
        this.setFormat(insertY, "\\d*|-\\d*");
        this.setFormat(insertHealth, "\\d*");
        this.setFormat(insertMarinesCount, "\\d*");
        this.setFormat(insertKey, "\\w*");
        this.setFormat(insertName, "\\w*");
        this.setFormat(insertAchievements, "\\w*");
        this.setFormat(insertNameOfChapter, "\\w*");
    }

    private SpaceMarine setSpaceMarine(){
        SpaceMarine spaceMarine = new SpaceMarine();

        spaceMarine.setKey(insertKey.getText());
        spaceMarine.setName(insertName.getText());

        Coordinates coordinates = new Coordinates();
        coordinates.setX(Double.parseDouble(insertX.getText()));
        coordinates.setY(Integer.parseInt(insertY.getText()));
        spaceMarine.setCoordinates(coordinates);

        spaceMarine.setCreationDate();

        spaceMarine.setHealth(Long.parseLong(insertHealth.getText()));
        spaceMarine.setHeartCount(insertHeartCount.getValue());
        spaceMarine.setAchievements(insertAchievements.getText());
        spaceMarine.setCategory(insertCategory.getValue().name());

        Chapter chapter = new Chapter();
        chapter.setName(insertNameOfChapter.getText());
        chapter.setMarinesCount(Integer.parseInt(insertMarinesCount.getText()));
        spaceMarine.setChapter(chapter);

        return spaceMarine;
    }
}
