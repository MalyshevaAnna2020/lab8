package client.gui.commands;

import client.gui.simplify.LocaleRegular;
import client.main.Client;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;

public class InterfaceOfCommands {
    LocaleRegular locale;
    String message;
    Client client;

    public InterfaceOfCommands(LocaleRegular locale, String message, Client client){
        this.locale = locale;
        this.message = message;
        this.client = client;
    }

    public void show(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setResources(locale.getBundle());
        BorderPane root = loader.load(getClass().getResourceAsStream("commands.fxml"));

        ControlCommands controller = loader.getController();
        controller.setLocale(locale);
        controller.setClient(client);
        controller.setText();

        Text text = (Text) root.getChildren().stream().filter(c -> c.getClass() == Text.class).toArray()[0];
        text.setText(locale.getBundle().getString(message));

        stage.setScene(new Scene(root));

        // На весь экран
        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
        stage.setX(primaryScreenBounds.getMinX());
        stage.setY(primaryScreenBounds.getMinY());
        stage.setWidth(primaryScreenBounds.getWidth());
        stage.setHeight(primaryScreenBounds.getHeight());
        stage.resizableProperty().setValue(Boolean.FALSE);

        controller.setStage(stage);

        stage.show();
    }

}
