package com.seanmlee.wgu.inventorymanagement.Controller;

import com.seanmlee.wgu.inventorymanagement.InventoryManagementMain;
import com.seanmlee.wgu.inventorymanagement.Model.*;
import javafx.collections.ObservableList;
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
 * Class modifies part
 * @author Sean Lee
 */
public class ModifyPartController implements Initializable {
    public TextField partformID;
    @FXML
    private ToggleGroup partType;
    @FXML
    private RadioButton partFormOutsourcedButton;
    @FXML
    private Button partFormSaveButton;
    @FXML
    private Button partFormCancelButton;
    @FXML
    private TextField partFormName;
    @FXML
    private TextField partFormInventory;
    @FXML
    private TextField partFormPrice;
    @FXML
    private TextField partFormMin;
    @FXML
    private TextField partFormMax;
    @FXML
    private TextField partFormMachineIdOrCompanyName;
    @FXML
    private RadioButton partFormInHouseButton;
    @FXML
    private Label machineIdOrCompanyLabel;

    private Part selectedPart;

    private int selectedIndex;

    private ObservableList<Part> associatedPartList;


    /**
     * Sets Selected Index.
     * @param selectedIndex index to be selected
     */
    public void setSelectedIndex(int selectedIndex) {
        this.selectedIndex = selectedIndex;
    }


    /**
     * Method sets an associated Part list.
     * Sets Associated Part List to a product.
     * @param associatedPartList list of associated parts
     */
    public void setAssociatedPartList(ObservableList<Part> associatedPartList) {
        this.associatedPartList = associatedPartList;
    }

    /**
     * Method sets a selected part.
     * Sets selected Part.
     * @param selectedPart current selected part
     */
    public void setSelectedPart(Part selectedPart) {
        this.selectedPart = selectedPart;
    }


    /**
     * Method Extracts data passed from previous controller.
     * Extracts Passed part data.
     */
    public void extractPartData() {
        partformID.setPromptText(String.valueOf(selectedPart.getId()));
        partFormName.setText(selectedPart.getName());
        partFormInventory.setText((String.valueOf(selectedPart.getStock())));
        partFormMin.setText(String.valueOf(selectedPart.getMin()));
        partFormMax.setText(String.valueOf((selectedPart.getMax())));
        partFormPrice.setText(String.valueOf(selectedPart.getPrice()));

        if (selectedPart instanceof InHouse) {
            partFormMachineIdOrCompanyName.setText(String.valueOf(((InHouse) selectedPart).getMachineId()));
            partFormMachineIdOrCompanyName.setVisible(true);
            partFormInHouseButton.setSelected(true);
            machineIdOrCompanyLabel.setText("Machine ID");
        } else {
            partFormMachineIdOrCompanyName.setText(((Outsourced) selectedPart).getCompanyName());
            partFormMachineIdOrCompanyName.setVisible(true);
            partFormOutsourcedButton.setSelected(true);
            machineIdOrCompanyLabel.setText("Company Name");

        }
    }


    /**
     * Makes the part In House
     * @param event unassigned
     */
    public void onInHouseRadioClick(ActionEvent event) {
        if (partFormInHouseButton.isSelected()) {
            machineIdOrCompanyLabel.setText("Machine ID");
        }
    }


    /**
     * Makes the Part outsourced
     * @param event unassigned
     */
    public void onOutsourcedRadioClicked(ActionEvent event) {
        if (partFormOutsourcedButton.isSelected()) {
            //partFormMachineIdOrCompanyName.setVisible(true);
            machineIdOrCompanyLabel.setText("Company Name");
        }
    }


    /**
     * Method controls the save part button.
     * Saves Modified Part information.
     * @param event
     * @throws IOException
     */
    public void onSavePartButtonClick(ActionEvent event) throws IOException {

        try {

            if (partFormInHouseButton.isSelected()) {
                InHouse inHousePart = new InHouse(selectedPart.getId(), "null", 0, 0, 0, 0, 0);


                if (!partFormMachineIdOrCompanyName.getText().isEmpty()) {
                    inHousePart.setMachineId(Integer.parseInt(partFormMachineIdOrCompanyName.getText()));
                } else {
                    inHousePart.setMachineId(0);
                    System.out.println("Text Field: " + partFormMachineIdOrCompanyName.getText());
                }
                inHousePart.setMachineId(Integer.parseInt(partFormMachineIdOrCompanyName.getText()));
                inHousePart.setName(partFormName.getText());
                inHousePart.setMax(Integer.parseInt(partFormMax.getText()));
                inHousePart.setMin(Integer.parseInt(partFormMin.getText()));
                inHousePart.setPrice(Double.parseDouble(partFormPrice.getText()));
                inHousePart.setStock(Integer.parseInt(partFormInventory.getText()));
                Inventory.updatePart(selectedIndex, inHousePart);
            } else if (partFormOutsourcedButton.isSelected()) {
                Outsourced outsourcedPart = new Outsourced(selectedPart.getId(), "", 0, 0, 0, 0, "");
                outsourcedPart.setCompanyName(partFormMachineIdOrCompanyName.getText());
                outsourcedPart.setName(partFormName.getText());
                outsourcedPart.setMax(Integer.parseInt(partFormMax.getText()));
                outsourcedPart.setMin(Integer.parseInt(partFormMin.getText()));
                outsourcedPart.setPrice(Double.parseDouble(partFormPrice.getText()));
                outsourcedPart.setStock(Integer.parseInt(partFormInventory.getText()));
                Inventory.updatePart(selectedIndex, outsourcedPart);
            } else {
                selectedPart.setName(partFormName.getText());
                selectedPart.setMax(Integer.parseInt(partFormMax.getText()));
                selectedPart.setMin(Integer.parseInt(partFormMin.getText()));
                selectedPart.setPrice(Double.parseDouble(partFormPrice.getText()));
                selectedPart.setStock(Integer.parseInt(partFormInventory.getText()));
            }
            // Check that the input values are valid
            if (Integer.parseInt(partFormInventory.getText()) < 0) {
                throw new IllegalArgumentException("Stock cannot be negative.");
            }
            if (Double.parseDouble(partFormPrice.getText()) <= 0) {
                throw new IllegalArgumentException("Price must be greater than 0.");
            }
            if (Integer.parseInt(partFormMin.getText()) < 0 || Integer.parseInt(partFormMax.getText()) < 0) {
                throw new IllegalArgumentException("Minimum and maximum values cannot be negative.");
            }
            if (Integer.parseInt(partFormMin.getText()) > Integer.parseInt(partFormMax.getText())) {
                throw new IllegalArgumentException("Minimum value must be less than or equal to maximum value.");
            }
            if (Integer.parseInt(partFormInventory.getText()) < Integer.parseInt(partFormMin.getText()) || Integer.parseInt(partFormInventory.getText()) > Integer.parseInt(partFormMax.getText())) {
                throw new IllegalArgumentException("Inventory must be between minimum and maximum values.");
            }

            Stage stage = (Stage) partFormSaveButton.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(InventoryManagementMain.class.getResource("MainWindow.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 1458, 567);
            stage.setTitle("Inventory Management System!");
            stage.setScene(scene);
            stage.show();
        } catch (IllegalArgumentException e) {
            // Handle input validation errors
            Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage());
            alert.showAndWait();
        }
    }


    /**
     * Method controls the cancel button.
     * Cancels without saving.
     * @param event
     * @throws IOException
     */
    public void onCancelButtonClick (ActionEvent event) throws IOException {
            Stage stage = (Stage) partFormCancelButton.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(InventoryManagementMain.class.getResource("MainWindow.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 1458, 567);
            stage.setTitle("Inventory Management System!");
            stage.setScene(scene);
            stage.show();


        }


        @Override
        public void initialize (URL url, ResourceBundle resourceBundle){

            System.out.println("Add Part Initialized");
        }


    }
