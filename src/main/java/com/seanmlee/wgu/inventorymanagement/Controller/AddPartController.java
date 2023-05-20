package com.seanmlee.wgu.inventorymanagement.Controller;

import com.seanmlee.wgu.inventorymanagement.InventoryManagementMain;
import com.seanmlee.wgu.inventorymanagement.Model.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


/**
 * This class allows adding a part to the inventory.
 * @author Sean Lee
 */
public class AddPartController implements Initializable {

    @FXML private ToggleGroup partType;
    @FXML private RadioButton partFormOutsourcedButton;
    @FXML private Button partFormSaveButton;
    @FXML private Button partFormCancelButton;
    @FXML private TextField partFormCompanyName;
    @FXML private TextField partFormName;
    @FXML private TextField partFormInventory;
    @FXML private TextField partFormPrice;
    @FXML private TextField partFormMin;
    @FXML private TextField partFormMax;
    @FXML private TextField partFormMachineId;
    @FXML private RadioButton partFormInHouseButton;
    @FXML private Label machineIdOrCompanyLabel;



    public AddPartController() {

    }


    /**
     * In House part radio button.
     * This method makes sure that the correct fields are visible when this button is selected.
     * @param event action event not assigned
     */
    public void onInHouseRadioClick(ActionEvent event) {
        if (partFormInHouseButton.isSelected()) {
            partFormMachineId.setVisible(true);
            partFormCompanyName.setVisible(false);
            machineIdOrCompanyLabel.setText("Machine ID");

        }
    }


    /**
     * Outsourced part radio button
     * This method makes sure that the correct fields are visible when this button is selected.
     * @param event action even is unassigned
     */
    public void onOutsourcedRadioClicked(ActionEvent event) {
        if (partFormOutsourcedButton.isSelected()) {
            partFormMachineId.setVisible(false);
            partFormCompanyName.setVisible(true);
            machineIdOrCompanyLabel.setText("Company Name");
        }
    }


    /**
     * This method saves the part
     * Saves the new part based on the input from the user.
     * @param event unassigned
     * @throws IOException
     * RUNTIME ERROR: This handles the validity of the values inserted into the fields (i.e. min is not greater than max, Inventory level validly sits between min and max)
     * An IllegalArgumentException is thrown for invalid input values which is caught and handled by displaying a message to the user
     */
    public void onSavePartButtonClick(ActionEvent event) throws IOException {
        try {

            //Set new window
            Stage stage = (Stage) partFormSaveButton.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(InventoryManagementMain.class.getResource("MainWindow.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 1458, 567);
            stage.setTitle("Inventory Management System!");
            stage.setScene(scene);
            stage.show();

            //Parse data from the text inputs
            String name = partFormName.getText();
            int stock = Integer.parseInt(partFormInventory.getText());
            double price = Double.parseDouble(partFormPrice.getText());
            int min = Integer.parseInt(partFormMin.getText());
            int max = Integer.parseInt(partFormMax.getText());


            //check the number values for validity
            if (stock < 0) {
                throw new IllegalArgumentException("Stock cannot be negative.");
            }
            if (price <= 0) {
                throw new IllegalArgumentException("Price must be greater than 0.");
            }
            if (min < 0 || max < 0) {
                throw new IllegalArgumentException("Minimum and maximum values cannot be negative.");
            }
            if (min > max) {
                throw new IllegalArgumentException("Minimum value must be less than or equal to maximum value.");
            }
            if (stock < min || stock > max) {
                throw new IllegalArgumentException("Inventory must be between minimum and maximum values.");
            }

            //Create new part and add the parsed data to the object
            Part newPart;
            if (partFormOutsourcedButton.isSelected()) {
                newPart = new Outsourced(Inventory.getNextId(), name, price, stock, min, max, String.valueOf(partFormCompanyName.getText()));
                System.out.println("Outsourced part added " + partFormCompanyName.getText());
            } else {
                newPart = new InHouse(Inventory.getNextId(), name, price, stock, min, max, Integer.parseInt(partFormMachineId.getText()));
                System.out.println("InHouse part added " + partFormMachineId.getText());
            }
            Inventory.addPart(newPart);
        } catch (IllegalArgumentException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage());
            alert.showAndWait();
        }
    }



    /**
     * This method controls the cancel button functionality.
     * Cancels without changes.
     * @param event unassigned
     * @throws IOException
     */
    public void onCancelButtonClick(ActionEvent event) throws IOException {
        //Set new window
        Stage stage = (Stage) partFormCancelButton.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(InventoryManagementMain.class.getResource("MainWindow.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1458, 567);
        stage.setTitle("Inventory Management System!");
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
