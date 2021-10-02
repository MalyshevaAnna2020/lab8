package client.gui.simplify;

import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.ResourceBundle;

public class TextRegular {
    public javafx.scene.text.Text getText(String key, ResourceBundle resource){
        javafx.scene.text.Text text = new javafx.scene.text.Text(resource.getString(key) + " ");
        text.setFont(new Font("Times New Roman", 24));
        return text;
    }
    public void setText(ResourceBundle resource, BorderPane root, String text){
        Text alert = new TextRegular().getText(text, resource);
        alert.setStyle("-fx-fill: #FF3333;");
        root.setCenter(alert);
    }
    public TextField setPromptText(String text){
        TextField textField = new TextField();
        textField.setPromptText(text);
        return textField;
    }
}
