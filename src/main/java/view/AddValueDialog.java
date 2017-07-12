package view;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by SBT-Mordashkin-AB on 11.07.2017.
 */
public class AddValueDialog extends SimpleDialog<Map<String, String>> {
    Map<String, TextField> textFields = new HashMap<>();

    public AddValueDialog(Map<String, String> values) {
        super();
        this.setTitle("Add value");

        // Add grid
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        // Create text fields
        Integer row = 0;
        for(Map.Entry<String, String> e :values.entrySet()) {
            TextField tf = new TextField();
            tf.setText(e.getValue()); // default value
            textFields.put(e.getKey(), tf);
            Label l = new Label(e.getKey()); //variable name
            grid.add(l, 0, row);
            grid.add(tf, 1, row);
            row++;
        }

        this.getDialogPane().setContent(grid);

        //TODO Set focus on field
        //Platform.runLater(() -> variableName.requestFocus());

        // Get results
        this.setResultConverter(dialogButton -> {
            if(dialogButton == ButtonType.OK) {
                Map<String, String> result = new HashMap<>();
                textFields.entrySet().forEach(e -> {
                    result.put(e.getKey(), e.getValue().getText());
                });
                return result;
            }
            return null;
        });
    }
}
