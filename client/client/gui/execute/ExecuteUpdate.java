package client.gui.execute;

import client.gui.commands.ControlCommands;
import client.main.Client;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.util.ResourceBundle;

public class ExecuteUpdate {
    public void show(ResourceBundle resource, Client client, BorderPane mainPane, ControlCommands controlCommands) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setResources(resource);
        GridPane root = loader.load(getClass().getResourceAsStream("update.fxml"));

        ControlUpdate controller = loader.getController();
        controller.setResource(resource);
        controller.setClient(client);
        controller.setMainPane(mainPane);
        controller.setOnlyNumbers();
        controller.setComboBoxes();
        controller.setControlCommands(controlCommands);

        mainPane.setCenter(root);
        root.setAlignment(Pos.CENTER);
    }
}
