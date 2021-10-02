package client.gui.execute;

import client.gui.commands.ControlCommands;
import client.gui.simplify.ButtonRegular;
import client.gui.simplify.LocaleRegular;
import client.gui.simplify.TableColumnComparator;
import client.gui.simplify.TextRegular;
import client.main.Client;
import com.jfoenix.controls.JFXButton;
import com.sun.javafx.scene.control.TableColumnComparatorBase;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Callback;
import server.all.All;
import server.command.Command;
import server.command.NameOfCommand;
import server.spacemarine.AstartesCategory;
import server.spacemarine.Chapter;
import server.spacemarine.Coordinates;
import server.spacemarine.SpaceMarine;

import java.awt.*;
import java.io.IOException;
import java.util.*;

public class ExecuteInfo {
    public void show(All all, BorderPane mainPane, Stage stage, VBox vBox, Locale locale, Client client, LocaleRegular localeRegular, ControlCommands controlCommands) throws NullPointerException{
        Hashtable<String, SpaceMarine> spaceMarineHashtable = all.getAnswer().getSpaceMarineList();
        for (String key : spaceMarineHashtable.keySet()){
            spaceMarineHashtable.get(key).setKey(key);
            spaceMarineHashtable.get(key).setLocale(locale);
        }

        TextRegular textRegular = new TextRegular();
        TextField keyText = textRegular.setPromptText("key");
        TextField idText = textRegular.setPromptText("id");
        TextField nameText = textRegular.setPromptText("name");
        TextField coordinatesText = textRegular.setPromptText("coordinates");
        TextField creationDateText = textRegular.setPromptText("creationDate");
        TextField healthText = textRegular.setPromptText("health");
        TextField heartCountText = textRegular.setPromptText("heartCount");
        TextField achievementsText = textRegular.setPromptText("achievements");
        TextField categoryText = textRegular.setPromptText("category");
        TextField chapterText = textRegular.setPromptText("chapter");
        TextField userText = textRegular.setPromptText("user");

        HBox hBox = new HBox();
        hBox.getChildren().addAll(keyText, idText, nameText, coordinatesText, creationDateText, healthText, heartCountText,
                achievementsText, categoryText, chapterText, userText);
        TextField[] arr = {keyText, idText, nameText, coordinatesText, creationDateText, healthText, heartCountText,
                achievementsText, categoryText, chapterText, userText};

        // Таблица
        ObservableList<SpaceMarine> listOfSpaceMarines = FXCollections.observableArrayList();
        listOfSpaceMarines.addAll(spaceMarineHashtable.values());
        // определяем таблицу и устанавливаем данные
        TableView<SpaceMarine> table = new TableView<>(listOfSpaceMarines);

        // key
        TableColumn<SpaceMarine, String> key = new TableColumn<>("key");
        key.setCellValueFactory(new PropertyValueFactory<>("key"));
        table.getColumns().add(key);
        // id
        TableColumn<SpaceMarine, Integer> id = new TableColumn<>("id");
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        table.getColumns().add(id);
        // name
        TableColumn<SpaceMarine, String> name = new TableColumn<>("name");
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        table.getColumns().add(name);
        // coordinates
        TableColumn<SpaceMarine, Coordinates> coordinates = new TableColumn<>("coordinates");
        coordinates.setCellValueFactory(new PropertyValueFactory<>("coordinates"));
        table.getColumns().add(coordinates);
        // creationDate
        TableColumn<SpaceMarine, String> creationDate = new TableColumn<>("creationDate");
        creationDate.setCellValueFactory(new PropertyValueFactory<>("creationDate"));
        table.getColumns().add(creationDate);
        // health
        TableColumn<SpaceMarine, Long> health = new TableColumn<>("health");
        health.setCellValueFactory(new PropertyValueFactory<>("health"));
        table.getColumns().add(health);
        // heartCount
        TableColumn<SpaceMarine, Integer> heartCount = new TableColumn<>("heartCount");
        heartCount.setCellValueFactory(new PropertyValueFactory<>("heartCount"));
        table.getColumns().add(heartCount);
        // achievements
        TableColumn<SpaceMarine, String> achievements = new TableColumn<>("achievements");
        achievements.setCellValueFactory(new PropertyValueFactory<>("achievements"));
        table.getColumns().add(achievements);
        // category
        TableColumn<SpaceMarine, AstartesCategory> category = new TableColumn<>("category");
        category.setCellValueFactory(new PropertyValueFactory<>("category"));
        table.getColumns().add(category);
        // chapter
        TableColumn<SpaceMarine, Chapter> chapter = new TableColumn<>("chapter");
        chapter.setCellValueFactory(new PropertyValueFactory<>("chapter"));
        table.getColumns().add(chapter);
        // user
        TableColumn<SpaceMarine, String> user = new TableColumn<>("user");
        user.setCellValueFactory(new PropertyValueFactory<>("user"));
        table.getColumns().add(user);

        table.setPrefSize(stage.getWidth() - vBox.getPrefWidth() - 100, stage.getHeight() - 200);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableView.TableViewSelectionModel<SpaceMarine> selectionModel = table.getSelectionModel();
        selectionModel.selectedItemProperty().addListener(new ChangeListener<SpaceMarine>(){

            @Override
            public void changed(ObservableValue<? extends SpaceMarine> observable, SpaceMarine oldVal, SpaceMarine newVal) {
                Point p = MouseInfo.getPointerInfo().getLocation();
                if ((newVal != null) && (newVal.getUser().equals(client.getPassword().getLogin()))) {
                    JFXButton button = new ButtonRegular().getButton(localeRegular.getBundle().getString("update"));
                    button.setMinWidth(200);
                    JFXButton remove = new ButtonRegular().getButton(localeRegular.getBundle().getString("remove"));
                    remove.setMinWidth(200);
                    VBox box = new VBox(button, remove);

                    Rectangle rectangle = new Rectangle(p.getX(), p.getY() - 30,
                            250, 125);
                    rectangle.setFill(Color.LAVENDER);
                    rectangle.setArcHeight(30);
                    rectangle.setArcWidth(30);

                    box.setLayoutX(p.getX() + 125);
                    box.setLayoutY(p.getY() + 25);
                    box.setAlignment(Pos.CENTER);
                    box.setSpacing(10);

                    mainPane.getChildren().add(rectangle);
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
                            controller.setTexts(newVal);

                            mainPane.setCenter(root);
                            root.setAlignment(Pos.CENTER);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
                    remove.setOnAction(event -> {
                        Command command = new Command();
                        command.setNameOfCommand(NameOfCommand.REMOVE_KEY);
                        command.setKey(newVal.getKey());
                        command.setPassword(client.getPassword());

                        try {
                            All all = client.command(command);

                            selectionModel.clearSelection();
                            controlCommands.getInfo();
                        } catch (ClassNotFoundException | IOException e) {
                            e.printStackTrace();
                            controlCommands.serverError();
                        }
                    });
                }
            }
        });

        // 1. Wrap the ObservableList in a FilteredList (initially display all data).
        FilteredList<SpaceMarine> filteredData = new FilteredList<>(listOfSpaceMarines, p -> true);

        for (int i = 0; i < 11; i++){
            key(i, arr[i], table, filteredData, arr);
        }

        VBox vBox1 = new VBox();
        vBox1.getChildren().addAll(hBox, table);
        vBox1.setPrefWidth(stage.getWidth() - vBox.getPrefWidth() -100);
        FlowPane flowPane = new FlowPane(vBox1);
        flowPane.setAlignment(Pos.CENTER);
        flowPane.setPrefWidth(stage.getWidth() - vBox.getPrefWidth() -100);
        flowPane.setPrefHeight(stage.getHeight());
        mainPane.setCenter(flowPane);
    }

    private void key(int i, TextField filterField, TableView<SpaceMarine> table, FilteredList<SpaceMarine> filteredData, TextField [] arr){
        // 2. Set the filter Predicate whenever the filter changes.
        filterField.textProperty().addListener((observable, oldValue, newValue) -> filteredData.setPredicate(spaceMarine -> {
            // If filter text is empty, display all persons.
            if (newValue == null || newValue.isEmpty()) {
                return true;
            }
            // Compare first name and last name of every person with filter text.
            switch (i){
                case 0:
                    return spaceMarine.getKey().contains(newValue)
                            && checkText(i, arr, spaceMarine);
                case 1:
                    return String.valueOf(spaceMarine.getId()).contains(newValue)
                            && checkText(i, arr, spaceMarine);
                case 2:
                    return spaceMarine.getName().contains(newValue)
                            && checkText(i, arr, spaceMarine);
                case 3:
                    return spaceMarine.getCoordinates().toString().contains(newValue)
                            && checkText(i, arr, spaceMarine);
                case 4:
                    return spaceMarine.getCreationDate().contains(newValue)
                            && checkText(i, arr, spaceMarine);
                case 5:
                    return String.valueOf(spaceMarine.getHealth()).contains(newValue)
                            && checkText(i, arr, spaceMarine);
                case 6:
                    return String.valueOf(spaceMarine.getHeartCount()).contains(newValue)
                            && checkText(i, arr, spaceMarine);
                case 7:
                    return spaceMarine.getAchievements().contains(newValue)
                            && checkText(i, arr, spaceMarine);
                case 8:
                    return spaceMarine.getCategory().toString().contains(newValue)
                            && checkText(i, arr, spaceMarine);
                case 9:
                    return spaceMarine.getChapter().toString().contains(newValue)
                            && checkText(i, arr, spaceMarine);
                case 10:
                    return spaceMarine.getUser().contains(newValue)
                            && checkText(i, arr, spaceMarine);
            }
            return false;
        }));
        // 3. Wrap the FilteredList in a SortedList.
        SortedList<SpaceMarine> sortedData = new SortedList<>(filteredData);
        // 4. Bind the SortedList comparator to the TableView comparator.
        sortedData.comparatorProperty().bind(table.comparatorProperty());
        // 5. Add sorted (and filtered) data to the table.
        table.setItems(sortedData);
    }
    private boolean checkText(int j, TextField[] arr, SpaceMarine spaceMarine){
        for (int i = 0; i < 11; i++){
            if (arr[i].getText() == null || arr[i].getText().isEmpty()) {
                continue;
            }
            if (i != j){
                switch (i){
                    case 0:
                        if (!spaceMarine.getKey().contains(arr[i].getText())){
                            return false;
                        }
                    case 1:
                        if ((!String.valueOf(spaceMarine.getId()).contains(arr[i].getText()))){
                            return  false;
                        }
                    case 2:
                        if (!spaceMarine.getName().contains(arr[i].getText())){
                            return false;
                        }
                    case 3:
                        if (!spaceMarine.getCoordinates().toString().contains(arr[i].getText())){
                            return  false;
                        }
                    case 4:
                        if (!spaceMarine.getCreationDate().contains(arr[i].getText())){
                            return  false;
                        };
                    case 5:
                        if (!String.valueOf(spaceMarine.getHealth()).contains(arr[i].getText())){
                            return  false;
                        }
                    case 6:
                        if (!String.valueOf(spaceMarine.getHeartCount()).contains(arr[i].getText())){
                            return  false;
                        }
                    case 7:
                        if (spaceMarine.getAchievements().contains(arr[i].getText())){
                            return  false;
                        }
                    case 8:
                        if (!spaceMarine.getCategory().toString().contains(arr[i].getText())){
                            return  false;
                        }
                    case 9:
                        if (!spaceMarine.getChapter().toString().contains(arr[i].getText())) {
                            return false;
                        }
                    case 10:
                        if (!spaceMarine.getUser().contains(arr[i].getText())){
                            return  false;
                        }
                }
            }
        }
        return true;
    }
}
