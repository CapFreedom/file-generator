package view;

import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;

/**
 * Created by SBT-Mordashkin-AB on 11.07.2017.
 */
public class SimpleDialog<R> extends Dialog<R> {

    Button okButton;
    Button cancelButton;

    public SimpleDialog() {
        super();
        // Add buttons
        this.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        okButton = (Button) this.getDialogPane().lookupButton(ButtonType.OK);
        cancelButton = (Button) this.getDialogPane().lookupButton(ButtonType.CANCEL);
    }

    public Button getOkButton() {
        return okButton;
    }

    public Button getCancelButton() {
        return cancelButton;
    }
}
