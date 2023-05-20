package com.seanmlee.wgu.inventorymanagement.Controller;

import com.seanmlee.wgu.inventorymanagement.InventoryManagementMain;
import com.seanmlee.wgu.inventorymanagement.Model.Inventory;
import com.seanmlee.wgu.inventorymanagement.Model.Part;
import com.seanmlee.wgu.inventorymanagement.Model.Product;
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
 * This class controls the Modify Product window
 * @author Sean Lee
 */
public class ModifyProductController implements Initializable {
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
    @FXML
    private Button productFormSaveButton;
    @FXML private Button productFormCancelButton;
    @FXML private TextField productFormID;
    @FXML private TextField productFormName;
    @FXML private TextField productFormInventory;
    @FXML private TextField productFormPrice;
    @FXML private TextField productFormMin;
    @FXML private TextField productFormMax;
    @FXML private TextField searchPartTextField;

    private Product selectedProduct;

    private int selectedIndex;

    public ModifyProductController() {

    }

    /**
     * Method Gets associated parts.
     * Gets Associated Part list from product.
     * @return returns the list
     */
    public ObservableList<Part> getAssociatedParts() {
        return associatedPartList;
    }

    /**
     * Method sets associated part list.
     * Sets associated part list to product.
     * @param associatedPartList sets the list
     */
    public void setAssociatedParts(ObservableList<Part> associatedPartList) {
        this.associatedPartList = associatedPartList;
    }

    /**
     * Method sets Selected product.
     * Sets selected product.
     * @param selectedProduct sets the product
     */
    public void setSelectedProduct(Product selectedProduct) {
        this.selectedProduct = selectedProduct;
    }


    /**
     * Method Extracts passed data.
     * Extracts selected data passed from previous window.
     */
    public void extractPartData() {

        productFormID.setPromptText(String.valueOf(selectedProduct.getId()));
        productFormName.setText(selectedProduct.getName());
        productFormInventory.setText((String.valueOf(selectedProduct.getStock())));
        productFormMin.setText(String.valueOf(selectedProduct.getMin()));
        productFormMax.setText(String.valueOf((selectedProduct.getMax())));
        productFormPrice.setText(String.valueOf(selectedProduct.getPrice()));
        associatedPartList = selectedProduct.getAllAssociatedParts();
        associatedPartsTableView.setItems(associatedPartList);
        associatedPartsTableView.refresh();
    }

    /**
     * Method sets selected index.
     * Sets index to be selected.
     * @param selectedIndex the int for index being searched for.
     */
    public void setSelectedIndex(int selectedIndex) {
        this.selectedIndex = selectedIndex;
    }



    /**
     * Method Controls add part button.
     * Adds Part to associated part list for product.
     * @param event unassigned
     */
    public void onAddPartToProduct(ActionEvent event) {
        Part selectedPart = (Part) partsTableView.getSelectionModel().getSelectedItem();
        associatedPartList.add(selectedPart);
    }


    /**
     * Method controls remove part button.
     * Removes an associated part from associated part list
     * @param event unassigned
     */
    public void onRemovePartFromProduct(ActionEvent event) {
        Part selectedPart = (Part) associatedPartsTableView.getSelectionModel().getSelectedItem();
        associatedPartList.remove(selectedPart);
        associatedPartsTableView.setItems(associatedPartList);
    }


    /**
     * Method controls the save product button.
     * Saves Modified product information.
     * @param event unassigned
     * @throws IOException
     */
    public void onSaveProductButtonClick(ActionEvent event) throws IOException {
        try {

            //add the product to the all products list
            //Show the scene
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

            // Check that the input values are valid
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
            Product newProduct = new Product(selectedProduct.getId(), name, price, stock, min, max);
            Inventory.updateProduct(selectedIndex, newProduct);
            System.out.println(selectedProduct.getAllAssociatedParts().toString());

            for (Part part : selectedProduct.getAllAssociatedParts()) {
                newProduct.addAssociatedPart(part);
            }
        } catch (IllegalArgumentException e) {
            // Handle input validation errors
            Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage());
            alert.showAndWait();
        }
    }

    /**
     * Method controls the cancel button.
     * Cancels without changes.
     * @param event unassigned.
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
     * this will allow the user to search for a part by ID or by name.
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
