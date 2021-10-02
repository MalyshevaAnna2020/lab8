package client.gui.simplify;

import com.jfoenix.controls.JFXButton;
import javafx.scene.text.Font;

public class ButtonRegular {
    public JFXButton getButton(String name){
        JFXButton button = new JFXButton(name);
        button.setFont(new Font("Times New Roman", 24));
        button.setStyle("-fx-background-color: #99CCFF");
        button.setButtonType(JFXButton.ButtonType.RAISED);
        return button;
    }
}
