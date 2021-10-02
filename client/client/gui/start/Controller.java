package client.gui.start;

import client.gui.simplify.ButtonRegular;
import client.gui.commands.InterfaceOfCommands;
import client.gui.simplify.LocaleRegular;
import client.gui.simplify.TextRegular;
import client.main.Client;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import server.all.All;

import java.io.IOException;
import java.util.ResourceBundle;

public class Controller {
    private final Client client = new Client();
    private final LocaleRegular locale = new LocaleRegular();
    private GridPane mainPane;
    private Stage primaryStage;

    public Controller() throws IOException {
    }

    public void setMainPane(GridPane gridPane){
        mainPane = gridPane;
    }
    public void setPrimaryStage(Stage stage){
        primaryStage = stage;
    }

    @FXML
    private Text lang;
    @FXML
    private ComboBox<String> comboBox;
    @FXML
    private JFXButton btn;

    @FXML
    private void click(ActionEvent event) throws IOException {
        mainPane.getChildren().removeAll(btn, comboBox, lang);

        GridPane box = new GridPane();
        // Username
        javafx.scene.text.Text username = new TextRegular().getText("username", locale.getBundle());
        TextField userField = new TextField();
        userField.setFont(new Font("Times New Roman", 24));
        Text alertUser = new Text();
        // Password
        javafx.scene.text.Text password = new TextRegular().getText("password", locale.getBundle());
        PasswordField passField = new PasswordField();
        passField.setFont(new Font("Times New Roman", 24));
        // Buttons
        JFXButton logIn = new ButtonRegular().getButton(locale.getBundle().getString("logIn"));
        JFXButton signUp = new ButtonRegular().getButton(locale.getBundle().getString("signUp"));
        
        box.addRow(1, username, userField, alertUser);
        box.addRow(2, password, passField);
        box.setVgap(10);
        box.setHgap(10);

        GridPane hBox = new GridPane();
        hBox.add(logIn, 0, 0, 1, 1);
        hBox.add(signUp, 1, 0, 1, 1);
        hBox.setAlignment(Pos.CENTER);
        hBox.setHgap(100);
        mainPane.addRow(3, box);
        mainPane.add(hBox, 0, 4, 4, 1);
        primaryStage.show();

        // actions
        logIn.setOnAction(event1 -> {
            String user = userField.getText();
            String pass = passField.getText();
            boolean check;
            try {
                if ((user.length() != 0) && (pass.length() != 0)) {
                    All all = client.loginAndPassword(user, pass, false);
                    check = all.getAnswer().getCheck();
                } else {
                    check = false;
                }
                if (check) {
                    try {
                        InterfaceOfCommands interfaceOfCommands = new InterfaceOfCommands(locale, "greeting", client);
                        interfaceOfCommands.show(primaryStage);
                    } catch (IOException e) {
                        System.out.println("Oh, no");
                        e.printStackTrace();
                    }
                } else {
                    alertUser.setText(locale.getBundle().getString("incorrect"));
                    alertUser.setFont(new Font("Times New Roman", 24));
                    alertUser.setStyle("-fx-fill: #FF3333;");
                    box.getChildren().remove(alertUser);
                    box.add(alertUser, 3, 0, 1, 3);
                    mainPane.getChildren().remove(box);
                    mainPane.addRow(3, box);
                    primaryStage.show();
                }
            }catch(NullPointerException e){
                allert(alertUser, locale.getBundle(), box);
            }
        });
        signUp.setOnAction(event1 -> {
            alertUser.setText("");
            box.getChildren().remove(alertUser);
            box.add(alertUser, 3, 0, 1, 3);

            Button next = new ButtonRegular().getButton(locale.getBundle().getString("continue"));

            mainPane.getChildren().removeAll(hBox);
            mainPane.add(next, 0, 4, 4, 1);
            GridPane.setHalignment(next, HPos.CENTER);
            primaryStage.show();

            next.setOnAction(event2 -> {
                String user = userField.getText();
                String pass = passField.getText();
                boolean check;
                try{
                    if ((user.length() != 0) && (pass.length() != 0)) {
                        All all = client.loginAndPassword(user, pass, true);
                        check = all.getAnswer().getCheck();
                        if (check) {
                            try {
                                InterfaceOfCommands interfaceOfCommands = new InterfaceOfCommands(locale, "new_user", client);
                                interfaceOfCommands.show(primaryStage);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        } else {
                            alertUser.setText(locale.getBundle().getString("exists"));
                            alertUser.setFont(new Font("Times New Roman", 24));
                            alertUser.setStyle("-fx-fill: #FF3333;");
                            box.getChildren().remove(alertUser);
                            box.add(alertUser, 3, 0, 1, 3);
                            mainPane.getChildren().remove(box);
                            mainPane.addRow(3, box);
                            primaryStage.show();
                        }
                    }
                }catch(NullPointerException e){
                    allert(alertUser, locale.getBundle(), box);
                }
            });
        });
    }

    @FXML
    private void changeLanguage(ActionEvent event){
        locale.setLocale(comboBox.getValue());
        mainPane.getChildren().removeAll(lang, btn);
        lang = new TextRegular().getText("chooselang", locale.getBundle());
        btn.setText((new TextRegular().getText("continue", locale.getBundle())).getText());
        mainPane.addRow(3, lang);
        mainPane.addRow(5, btn);
        GridPane.setHalignment(lang, HPos.CENTER);
        GridPane.setHalignment(btn, HPos.CENTER);
    }

    private void allert(Text alertUser, ResourceBundle resource, GridPane box){
        alertUser.setText(resource.getString("alert"));
        alertUser.setFont(new Font("Times New Roman", 24));
        alertUser.setStyle("-fx-fill: #FF3333;");
        box.getChildren().remove(alertUser);
        box.add(alertUser, 3, 0, 1, 3);
        mainPane.getChildren().remove(box);
        mainPane.addRow(3, box);
        primaryStage.show();
    }
}
