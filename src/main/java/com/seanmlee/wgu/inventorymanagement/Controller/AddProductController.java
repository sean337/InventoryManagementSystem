package com.seanmlee.wgu.inventorymanagement.Controller;

import com.seanmlee.wgu.inventorymanagement.InventoryManagementMain;
import com.seanmlee.wgu.inventorymanagement.Model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * This class controls the Add Product window.
 * @author Sean Lee
 */
public class AddProductController implements Initializable {

    private ObservableList<Part> associatedPartList = FXCollections.observableArrayList();
    public TableView partsTableView;
    public TableColumn partIdColumn;
    public TableColumn partNameColumn;
    public TableColumn partInventoryColumn;
    public TableColumn partCostColumn;
    public TableView associatedPartsTableView;
    public TableColumn associatedPartIdColumn;
    public TableColumn associatedPartNameColumn;
    public TableColumn associatedPartInventoryColumn;
    public TableColumn associatedPartCostColumn;
    public Button addSelectedPartToProduct;
    public Button removeSelectedPartButton;
    @FXML private Button productFormSaveButton;
    @FXML private Button productFormCancelButton;
    @FXML private TextField productFormName;
    @FXML private TextField productFormInventory;
    @FXML private TextField productFormPrice;
    @FXML private TextField productFormMin;
    @FXML private TextField productFormMax;
    @FXML private TextField searchPartTextField;

    private Product selectedProduct;



    public AddProductController() {

    }

    /**
     * This method Sets an associated part list.
     * Sets associated part list to new product.
     * @param associatedPartList
     */
    public void setAssociatedPartList(ObservableList<Part> associatedPartList) {
        this.associatedPartList = associatedPartList;
    }


    /**
     * This method adds a part.
     * adds selected part part to new product.
     * @param event unassigned
     */
    public void onAddPartToProduct(ActionEvent event) {
        Part selectedPart = (Part) partsTableView.getSelectionModel().getSelectedItem();
        associatedPartList.add(selectedPart);
        associatedPartsTableView.setItems(associatedPartList);
    }

    /**
     * This method removes a part.
     * Removes selected part from new product.
     * @param event
     */
    public void onRemovePartFromProduct(ActionEvent event) {
        Part selectedPart = (Part) associatedPartsTableView.getSelectionModel().getSelectedItem();
        associatedPartList.remove(selectedPart);
        associatedPartsTableView.refresh();
    }


    /**
     * This method saves the product
     * Saves the new product based on the input from the user.
     * @param event unassigned
     * @throws IOException
     * RUNTIME ERROR: This handles the validity of the values inserted into the fields (i.e. min is not greater than max, Inventory level validly sits between min and max)
     * An IllegalArgumentException is thrown for invalid input values which is caught and handled by displaying a message to the user
     */
    public void onSaveProductButtonClick(ActionEvent event) throws IOException {

        try {

            //add the product to the all products list
            Stage stage = (Stage) productFormSaveButton.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(InventoryManagementMain.class.getResource("MainWindow.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 1458, 567);
            stage.setTitle("Inventory Management System!");
            stage.setScene(scene);
            stage.show();

            //Gather information from the text inputs
            String name = productFormName.getText();
            int stock = Integer.parseInt(productFormInventory.getText());
            double price = Double.parseDouble(productFormPrice.getText());
            int min = Integer.parseInt(productFormMin.getText());
            int max = Integer.parseInt(productFormMax.getText());

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


            //Create new product and add the parsed data to the object
            Product newProduct = new Product(Inventory.getNextId(), name, price, stock, min, max);
            Inventory.addProduct(newProduct);
            //add the parts from the associated parts list to the new products associated part list
            for (Part part : associatedPartList) {
                newProduct.addAssociatedPart(part);
            }
        } catch (IllegalArgumentException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage());
            alert.showAndWait();
        }
    }


    /**
     * This method cancels the product creation without changes.
     * Cancels without changes
     * @param event unassigned
     * @throws IOException
     */
    public void onCancelButtonClick(ActionEvent event) throws IOException {
        Stage stage = (Stage) productFormCancelButton.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(InventoryManagementMain.class.getResource("MainWindow.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1458, 567);
        stage.setTitle("Inventory Management System!");
        stage.setScene(scene);
        stage.show();

    }

    /**
     * This method controls the search by part text field.
     * this will allow the user to search for an existing part by ID or by name.
     */
    private void onSearchByPartTextField() {
        String searchText = searchPartTextField.getText();
        if (searchText.trim().isEmpty()) {
            partsTableView.setItems(Inventory.getAllParts());
        } else {
            try {
                int id = Integer.parseInt(searchText);
                Part foundPart = Inventory.lookupPart(id);
                if (foundPart != null) {
                    partsTableView.setItems(FXCollections.observableArrayList(foundPart));
                } else {
                    partsTableView.setItems(FXCollections.emptyObservableList());
                }
            } catch (NumberFormatException e) {
                ObservableList<Part> filteredList = Inventory.lookupPart(searchText);
                partsTableView.setItems(filteredList);
            }
        }
        partsTableView.refresh();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        partIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInventoryColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partCostColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        partsTableView.setItems(Inventory.getAllParts());
        partsTableView.refresh();

        associatedPartIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        associatedPartNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        associatedPartInventoryColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        associatedPartCostColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        associatedPartsTableView.refresh();
        searchPartTextField.setOnAction(event -> onSearchByPartTextField());
        System.out.println("Add Part Initialized");
    }

}
