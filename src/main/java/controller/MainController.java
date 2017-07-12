package controller;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.MapValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.Pair;
import javafx.util.StringConverter;
import view.AddValueDialog;
import view.AddVariableDialog;

import java.io.File;
import java.net.URL;
import java.util.*;

public class MainController implements Initializable{

    private FileChooser fileChooser;
    private AddVariableDialog addVariableDialog;
    private AddValueDialog addValueDialog;

    private ObservableList<Map> tableData;

    // VariableName->DefaultValue
    private Map<String, String> columns;

    private Stage mainStage;

    @FXML
    private BorderPane mainBorderPane;

    @FXML
    private Label tableLabel;

    @FXML
    private VBox tableVBox;

    @FXML
    private Button addVariableBtn;

    @FXML
    private Button addValueBtn;

    @FXML
    private Button openFileBtn;

    @FXML
    private Pane mainPane;

    @FXML
    private Button generateBtn;

    private TableView<Map> table;

    @FXML
    private Label resultCountCaption;

    @FXML
    private Button savePresetBtn;


    @FXML
    void addValue(ActionEvent event) {
        addValueDialog = new view.AddValueDialog(columns);
        Optional<Map<String, String>> result = addValueDialog.showAndWait();
        result.ifPresent(r -> {
            tableData.addAll(result.get());
                });
        table.refresh();
        System.out.println("Adding value...");

    }

    @FXML
    void openFile(ActionEvent event) {
        fileChooser.setTitle("Open file template");
        File file = fileChooser.showOpenDialog(mainStage);
        if(file != null) {
            System.out.println("Read file: " + file.getAbsolutePath());
        } else {
            System.out.println("File is null.");
        }
    }


    @FXML
    void addVariable(ActionEvent event) {
        System.out.println("Add variable clicked...");
        // Open dialog
        addVariable(addVariableDialog.showAndWait());
    }

    public void setMainStage(Stage mainStage) {
        this.mainStage = mainStage;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("Initializing MainController...");
        fileChooser = new FileChooser();
        createAddVariableDialog();
        setupTable();
        System.out.println("MainController has been initialized...");
    }


    private void createAddVariableDialog() {
        addVariableDialog = new AddVariableDialog();
    }

    private void setupTable() {
        tableData = FXCollections.observableArrayList();
        columns = new HashMap<>();

        table = new TableView<>(tableData);

        table.setEditable(true);
        table.getSelectionModel().setCellSelectionEnabled(true);
        tableVBox.getChildren().add(table);
        table.refresh();
    }

    private void addVariable(Optional<Pair<String, String>> result) {
        result.ifPresent(kv -> {
            if(columns.containsKey(kv.getKey())) {
                columns.put(kv.getKey(), kv.getValue());
            } else {
                columns.put(kv.getKey(), kv.getValue());
                TableColumn col = new TableColumn(kv.getKey());
                col.setId(kv.getKey());
                table.getColumns().addAll(col);
                col.setCellValueFactory(new MapValueFactory(kv.getKey()));
                col.setCellFactory(getCellFactoryForMap(kv.getValue()));
                col.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent>() {
                    @Override
                    public void handle(TableColumn.CellEditEvent e) {
                        Object currentKey = e.getTableColumn().getId();
                        ((HashMap) e.getTableView().getItems().get(e.getTablePosition().getRow())).put(currentKey, e.getNewValue());
                }
                });
                System.out.println("Key: " + kv.getKey() + " Default: " + kv.getValue() + " added");
            }
        });
    }

    private Callback<TableColumn<Map, String>, TableCell<Map, String>> getCellFactoryForMap(String defaultValue) {
        return p -> new TextFieldTableCell(new StringConverter() {
            @Override
            public String toString(Object t) {
                if(t != null)
                    return t.toString();
                else
                    return defaultValue;
            }
            @Override
            public Object fromString(String string) {
                return string;
            }
        });
    }
}
