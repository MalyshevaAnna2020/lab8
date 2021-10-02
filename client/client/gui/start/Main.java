package client.gui.start;

import client.gui.simplify.LocaleRegular;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        LocaleRegular locale = new LocaleRegular();
        FXMLLoader loader = new FXMLLoader();
        loader.setResources(locale.getBundle());
        GridPane root = loader.load(getClass().getResourceAsStream("interface.fxml"));

        Controller controller = loader.getController();
        controller.setMainPane(root);
        controller.setPrimaryStage(primaryStage);

        primaryStage.setTitle("SpaceMarine");
        primaryStage.setScene(new Scene(root));

        // На весь экран
        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
        primaryStage.setX(primaryScreenBounds.getMinX());
        primaryStage.setY(primaryScreenBounds.getMinY());
        primaryStage.setWidth(primaryScreenBounds.getWidth());
        primaryStage.setHeight(primaryScreenBounds.getHeight());
        primaryStage.resizableProperty().setValue(Boolean.FALSE);

        primaryStage.resizableProperty().setValue(Boolean.FALSE);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
