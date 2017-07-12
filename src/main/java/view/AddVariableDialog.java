package view;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.util.Pair;

/**
 * Created by SBT-Mordashkin-AB on 11.07.2017.
 */
public class AddVariableDialog extends SimpleDialog<Pair<String, String>> {
    private TextField variableName;
    private TextField defaultValue;

    public AddVariableDialog() {
        super();
        this.setTitle("Add variable");

        // Create text fields
        variableName = new TextField();
        variableName.setPromptText("variable name");

        defaultValue = new TextField();
        defaultValue.setPromptText("default value");

        // Add grid
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));
        grid.add(new Label("Variable name"), 0, 0);
        grid.add(variableName, 1, 0);
        grid.add(new Label("Default value"), 0, 1);
        grid.add(defaultValue, 1, 1);
        this.getDialogPane().setContent(grid);

        // Disable ok button if variable name not specified
        okButton.setDisable(true);
        variableName.textProperty().addListener((observable, oldValue, newValue) -> {
            okButton.setDisable(newValue.trim().isEmpty());
        });

        // Set focus on variable name field
        Platform.runLater(() -> variableName.requestFocus());

        // Get results
        this.setResultConverter(dialogButton -> {
            if(dialogButton == ButtonType.OK) {
                return new Pair<>(variableName.getText(), defaultValue.getText());
            }
            return null;
        });

        // Reset text fields
        this.setOnShowing(e -> {
            reset();
        });
    }

    private void reset() {
        variableName.setText("");
        defaultValue.setText("");
    }
}
