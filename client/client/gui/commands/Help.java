package client.gui.commands;

import javafx.beans.property.SimpleStringProperty;
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

public class Help {
    private final SimpleStringProperty name;
    private final SimpleStringProperty explanation;

    public Help(String name, String explanation){
        this.name = new SimpleStringProperty(name);
        this.explanation = new SimpleStringProperty(explanation);
    }

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getExplanation() {
        return explanation.get();
    }

    public void setExplanation(String explanation) {
        this.explanation.set(explanation);
    }

}
