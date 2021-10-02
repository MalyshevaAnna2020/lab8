package client.gui.execute;

import client.gui.commands.ControlCommands;
import client.gui.simplify.LocaleRegular;
import client.main.Client;
import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import server.all.All;
import server.command.Command;
import server.command.NameOfCommand;
import server.spacemarine.AstartesCategory;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

public class ExecuteCategory {
    public void show(ResourceBundle resource, BorderPane mainPane, Client client, Stage stage, VBox vBox, Locale locale, LocaleRegular localeRegular, ControlCommands controlCommands){
        Text text = new Text("category");
        text.setFont(new Font("Times New Roman", 24));

        ObservableList<AstartesCategory> list = FXCollections.observableArrayList(AstartesCategory.values());
        ComboBox<AstartesCategory> categories = new ComboBox<>(list);
        categories.setStyle("-fx-font: normal normal 24 \"Times New Roman\"; -fx-background-color: #99CCFF;");
        categories.setValue(AstartesCategory.SUPPRESSOR);

        JFXButton button = new JFXButton();
        button.setButtonType(JFXButton.ButtonType.RAISED);
        button.setStyle("-fx-background-color: #99CCFF;");
        button.setTextAlignment(TextAlignment.CENTER);
        button.setFont(new Font("Times New Roman", 24));
        button.setText(resource.getString("show"));
        button.setAlignment(Pos.CENTER);

        GridPane gridPane = new GridPane();
        gridPane.add(text, 0, 0);
        gridPane.add(categories, 1, 0);
        gridPane.add(button, 0, 1, 2, 1);
        GridPane.setHalignment(button, HPos.CENTER);
        gridPane.setHgap(20);
        gridPane.setVgap(50);

        mainPane.setCenter(gridPane);
        gridPane.setAlignment(Pos.CENTER);

        button.setOnAction(event -> {
            Command command = new Command();
            command.setNameOfCommand(NameOfCommand.PRINT_FIELD_DESCENDING_CATEGORY);
            command.setKey(categories.getValue().toString());
            try{
                All all = client.command(command);

                new ExecuteInfo().show(all, mainPane, stage, vBox, locale, client, localeRegular, controlCommands);
            }catch(ClassNotFoundException | IOException | NullPointerException e){
                controlCommands.serverError();
            }
        });
    }
}
