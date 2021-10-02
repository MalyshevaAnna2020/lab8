package client.gui.execute;

import client.gui.commands.Help;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.Control;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.ResourceBundle;

public class ExecuteHelp {
    public void show(ResourceBundle resource, BorderPane mainPane, VBox vBox){
        // создаем список объектов
        String [] names = {"help", "info", "show", "insert", "update", "remove_key", "clear", "execute_script",
                "remove_lower", "remove_greater_key", "remove_lower_key", "remove_any_by_chapter",
                "filter_greater_than_achievements", "print_field_descending_category"};
        ObservableList<Help> listOfCommands = FXCollections.observableArrayList();
        for (String name : names) {
            listOfCommands.add(new Help(resource.getString(name), resource.getString(name + "_explanation")));
        }
        // определяем таблицу и устанавливаем данные
        TableView<Help> table = new TableView<>(listOfCommands);

        // столбец для вывода имени
        TableColumn<Help, String> nameOfCommand = new TableColumn<>(resource.getString("nameOfCommand"));
        // определяем фабрику для столбца с привязкой к свойству name
        nameOfCommand.setCellValueFactory(new PropertyValueFactory<>("name"));
        // Перенос строки
        nameOfCommand.setCellFactory(c -> {
            TableCell<Help, String> cell = new TableCell<>();
            Text text = new Text();
            cell.setGraphic(text);
            cell.setPrefHeight(Control.USE_COMPUTED_SIZE);
            text.wrappingWidthProperty().bind(nameOfCommand.widthProperty());
            text.textProperty().bind(cell.itemProperty());
            cell.setStyle("-fx-background-color: #66FFFF;-fx-border-style: solid;-fx-border-color:#CCCCCC;-fx-border-width: 1px;");
            text.setFont(new Font("Times New Roman", 24));
            return cell;
        });

        // столбец для вывода имени
        TableColumn<Help, String> explanationOfCommand = new TableColumn<>(resource.getString("explanationOfCommand"));
        // Определяем фабрику для столбца с привязкой к свойству name
        explanationOfCommand.setCellValueFactory(new PropertyValueFactory<>("explanation"));
        // Перенос строки
        explanationOfCommand.setCellFactory(c -> {
            TableCell<Help, String> cell = new TableCell<>();
            Text text = new Text();
            cell.setGraphic(text);
            cell.setPrefHeight(Control.USE_COMPUTED_SIZE);
            text.wrappingWidthProperty().bind(explanationOfCommand.widthProperty());
            text.textProperty().bind(cell.itemProperty());
            text.setFont(new Font("Times New Roman", 24));
            return cell;
        });

        // добавляем столбцы
        table.getColumns().add(nameOfCommand);
        table.getColumns().add(explanationOfCommand);
        // На всю ширину экрана
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        // Попытки ограничить количество строк
        table.setPrefSize(mainPane.getPrefWidth() - vBox.getPrefWidth(), vBox.getPrefHeight());
        FlowPane flowPane = new FlowPane(table);
        flowPane.setAlignment(Pos.CENTER);

        mainPane.setCenter(flowPane);
    }
}
