package client.gui.execute;

import client.main.Client;
import com.jfoenix.controls.JFXButton;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import server.all.All;
import server.command.Command;
import server.command.NameOfCommand;

import java.io.IOException;
import java.util.ResourceBundle;

public class ExecuteRemove {
    private final TextField textField = new TextField();
    public GridPane show(String fillText, String fillButton){
        Text text = new Text(fillText);
        text.setFont(new Font("Times New Roman", 24));

        textField.setFont(new Font("Times New Roman", 24));

        JFXButton button = new JFXButton();
        button.setButtonType(JFXButton.ButtonType.RAISED);
        button.setStyle("-fx-background-color: #99CCFF;");
        button.setTextAlignment(TextAlignment.CENTER);
        button.setFont(new Font("Times New Roman", 24));
        button.setText(fillButton);
        button.setAlignment(Pos.CENTER);

        GridPane gridPane = new GridPane();
        gridPane.add(text, 0, 0);
        gridPane.add(textField, 1, 0);
        gridPane.add(button, 0, 1, 2, 1);
        GridPane.setHalignment(button, HPos.CENTER);
        gridPane.setHgap(20);
        gridPane.setVgap(50);

        return gridPane;
    }
    public void action(Client client, ResourceBundle resource, BorderPane mainPane,
                       NameOfCommand nameOfCommand, boolean textFieldExists) throws IOException, ClassNotFoundException, NullPointerException {
        Command command = new Command();
        command.setNameOfCommand(nameOfCommand);
        if (textFieldExists) {
            command.setKey(textField.getText());
        }
        command.setPassword(client.getPassword());

        All all = client.command(command);
        show(all, resource, mainPane);
    }
    public void show(All all, ResourceBundle resource, BorderPane mainPane){
        Text text1 = new Text();
        text1.setFont(new Font("Times New Roman", 24));
        text1.setStyle("-fx-fill: #FF3333;");
        // 1 - добавлен
        // 2 - ограничение доступа
        // 3 - spacemarine не найден
        int result = Integer.parseInt(all.getAnswer().getKey());
        if (result == 1){
            text1.setText(resource.getString("space_marine") + " " +
                    resource.getString("removed"));
        }
        if (result == 2){
            text1.setText(resource.getString("space_marine") + " " +
                    resource.getString("another_user"));
        }
        if (result == 3){
            text1.setText(resource.getString("space_marine") + " " +
                    resource.getString("spacemarine_exists") + "!");
        }
        mainPane.setCenter(text1);
    }
}
