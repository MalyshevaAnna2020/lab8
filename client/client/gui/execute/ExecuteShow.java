package client.gui.execute;

import client.gui.commands.ControlCommands;
import client.gui.simplify.ButtonRegular;
import client.gui.simplify.CircleRegular;

import client.gui.simplify.LocaleRegular;
import client.main.Client;
import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import server.all.All;
import server.command.Command;
import server.command.NameOfCommand;
import server.spacemarine.SpaceMarine;

import java.io.IOException;
import java.util.Hashtable;
import java.util.Locale;

public class ExecuteShow {
    public void show(All all, BorderPane mainPane, Stage stage, Locale locale, ControlCommands controlCommands, Client client, LocaleRegular localeRegular) throws NullPointerException{
        Hashtable<String, SpaceMarine> spaceMarineHashtable = all.getAnswer().getSpaceMarineList();
        for (String key : spaceMarineHashtable.keySet()){
            spaceMarineHashtable.get(key).setKey(key);
        }

        CircleRegular circleRegular = new CircleRegular();
        VBox vBox = (VBox) mainPane.getLeft();
        double width = vBox.getPrefWidth();

        double minX = getMinX(spaceMarineHashtable);
        double minY = getMinY(spaceMarineHashtable);
        double maxX = getMaxX(spaceMarineHashtable);
        double maxY = getMaxY(spaceMarineHashtable);

        double k_x = (stage.getWidth() - width - 350) / (maxX - minX);
        double k_y = (stage.getHeight() - 500) / (maxY - minY);

        for (SpaceMarine spaceMarine : spaceMarineHashtable.values()){
            Circle circle = circleRegular.getCircle(spaceMarine, width,
                    stage, k_x, k_y, minY, minX, maxY, maxX);
            mainPane.getChildren().add(circle);

            circle.setOnMouseClicked(event -> {
                click(spaceMarine, circle, mainPane, locale, localeRegular, controlCommands, client);
            });
        }
        /*
        while(controlCommands.getCheckShow()){
            Command command = new Command();
            command.setNameOfCommand(NameOfCommand.SHOW);
            all = client.command(command);

            Hashtable<String, SpaceMarine> newSpaceMarineHashtable = all.getAnswer().getSpaceMarineList();
            for (String key : newSpaceMarineHashtable.keySet()){
                newSpaceMarineHashtable.get(key).setKey(key);
            }

            for (SpaceMarine spaceMarine : newSpaceMarineHashtable.values()){
                if (!spaceMarineHashtable.contains(spaceMarine)) {
                    Circle circle = circleRegular.getCircle(spaceMarine, width,
                            stage, spaceMarineHashtable.size(), k_x, k_y, minY, minX);
                    mainPane.getChildren().add(circle);

                    circle.setOnMouseClicked(event -> {
                        click(spaceMarine, circle, mainPane, locale);
                    });
                }
            }
        }

         */

    }

    private double getMinX(Hashtable<String, SpaceMarine> spaceMarineHashtable){
        double minX = 956;
        for (SpaceMarine spaceMarine : spaceMarineHashtable.values()){
            if (minX > spaceMarine.getCoordinates().getX()){
                minX = spaceMarine.getCoordinates().getX();
            }
        }
        return minX;
    }
    private double getMaxX(Hashtable<String, SpaceMarine> spaceMarineHashtable) {
        double maxX = Double.MIN_VALUE;
        for (SpaceMarine spaceMarine : spaceMarineHashtable.values()) {
            if (maxX < spaceMarine.getCoordinates().getX()) {
                maxX = spaceMarine.getCoordinates().getX();
            }
        }
        return maxX;
    }
    private int getMinY(Hashtable<String, SpaceMarine> spaceMarineHashtable){
        int minY = Integer.MAX_VALUE;
        for (SpaceMarine spaceMarine : spaceMarineHashtable.values()){
            if (minY > spaceMarine.getCoordinates().getY()){
                minY = spaceMarine.getCoordinates().getY();
            }
        }
        return minY;
    }
    private int getMaxY(Hashtable<String, SpaceMarine> spaceMarineHashtable) {
        int maxY = Integer.MIN_VALUE;
        for (SpaceMarine spaceMarine : spaceMarineHashtable.values()) {
            if (maxY < spaceMarine.getCoordinates().getY()) {
                maxY = spaceMarine.getCoordinates().getY();
            }
        }
        return maxY;
    }

    private void click(SpaceMarine spaceMarine, Circle circle, BorderPane mainPane, Locale locale,
                       LocaleRegular localeRegular, ControlCommands controlCommands, Client client) {
        spaceMarine.setLocale(locale);
        Text text = new Text("key: " + spaceMarine.getKey() + "\n" +
                "id: " + spaceMarine.getId() + "\n" +
                "name: " + spaceMarine.getName() + "\n" +
                "coordinates: " + spaceMarine.getCoordinates() + "\n" +
                "creation date: " + spaceMarine.getCreationDate() + "\n" +
                "health: " + spaceMarine.getHealth() + "\n" +
                "heart count: " + spaceMarine.getHeartCount() + "\n" +
                "achievements: " + spaceMarine.getAchievements() + "\n" +
                "category: " + spaceMarine.getCategory() + "\n" +
                "chapter: " + spaceMarine.getChapter() + "\n" +
                "user: " + spaceMarine.getUser());
        if (spaceMarine.getUser().equals(client.getPassword().getLogin())) {
            JFXButton button = new ButtonRegular().getButton(localeRegular.getBundle().getString("update"));
            button.setMinWidth(200);
            JFXButton remove = new ButtonRegular().getButton(localeRegular.getBundle().getString("remove"));
            remove.setMinWidth(200);
            Rectangle rectangle = new Rectangle(circle.getCenterX() - 10, circle.getCenterY(),
                    text.getLayoutBounds().getWidth() + 50, 400);
            rectangle.setFill(Color.LAVENDER);
            rectangle.setArcHeight(30);
            rectangle.setArcWidth(30);
            mainPane.getChildren().add(rectangle);

            VBox box = new VBox(text, button, remove);
            box.setLayoutX(circle.getCenterX() + text.getLayoutBounds().getWidth() / 2 + 10);
            box.setLayoutY(circle.getCenterY() + 200);
            box.setAlignment(Pos.CENTER);
            box.setSpacing(10);
            mainPane.getChildren().add(box);

            button.setOnAction(event -> {
                try {
                    controlCommands.delete();
                    FXMLLoader loader = new FXMLLoader();
                    loader.setResources(localeRegular.getBundle());
                    GridPane root = loader.load(getClass().getResourceAsStream("update.fxml"));

                    ControlUpdate controller = loader.getController();
                    controller.setResource(localeRegular.getBundle());
                    controller.setClient(client);
                    controller.setMainPane(mainPane);
                    controller.setOnlyNumbers();
                    controller.setComboBoxes();
                    controller.setTexts(spaceMarine);

                    mainPane.setCenter(root);
                    root.setAlignment(Pos.CENTER);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            remove.setOnAction(event -> {
                Command command = new Command();
                command.setNameOfCommand(NameOfCommand.REMOVE_KEY);
                command.setKey(spaceMarine.getKey());
                command.setPassword(client.getPassword());

                try {
                    All all = client.command(command);

                    mainPane.getChildren().removeAll(box, rectangle, circle);
                }catch(ClassNotFoundException | IOException e){
                    controlCommands.serverError();
                }
            });

            circle.setOnMouseClicked(event1 -> {
                mainPane.getChildren().removeAll(box, rectangle);
                circle.setOnMouseClicked(event2 -> click(spaceMarine, circle, mainPane, locale, localeRegular, controlCommands, client));
            });
        }
        else{
            Rectangle rectangle = new Rectangle(circle.getCenterX() - 10, circle.getCenterY(),
                    text.getLayoutBounds().getWidth() + 50, 260);
            rectangle.setFill(Color.LAVENDER);
            rectangle.setArcHeight(30);
            rectangle.setArcWidth(30);
            mainPane.getChildren().add(rectangle);

            VBox box = new VBox(text);
            box.setLayoutX(circle.getCenterX() + text.getLayoutBounds().getWidth() / 2 + 10);
            box.setLayoutY(circle.getCenterY() + 125);
            box.setAlignment(Pos.CENTER);
            box.setSpacing(10);
            mainPane.getChildren().add(box);

            circle.setOnMouseClicked(event1 -> {
                mainPane.getChildren().removeAll(box, rectangle);
                circle.setOnMouseClicked(event2 -> click(spaceMarine, circle, mainPane, locale, localeRegular, controlCommands, client));
            });
        }
    }
}
