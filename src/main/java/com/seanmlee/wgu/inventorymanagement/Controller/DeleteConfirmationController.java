package com.seanmlee.wgu.inventorymanagement.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 * This class confirms that user wants to delete something prior to actually deleting it
 * @author Sean Lee
 */
public class DeleteConfirmationController {
    @FXML public Button yesButton;
    @FXML public Button cancelButton;

    private boolean confirmed = false;

    /**
     * Method controls the Yes button on screen.
     * Confirms deletion.
     * @param event unassigned
     */
    public void onYesButtonClick(ActionEvent event) {
        confirmed = true;
        ((Stage)(((Button)event.getSource()).getScene().getWindow())).close();
    }

    /**
     * Method controls the cancel button.
     * Cancels without changes.
     * @param event unassigned
     */
    public void onCancelButtonClick(ActionEvent event) {
        confirmed = false;
        ((Stage)(((Button)event.getSource()).getScene().getWindow())).close();
    }

    /**
     * Boolean to confirm decision
     * @return returns which button is selected
     */
    public boolean isConfirmed() {
        return confirmed;
    }

}
