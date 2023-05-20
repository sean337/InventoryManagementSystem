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
 * This class is the Main Menu controller that will bring up the initial window with total inventory of parts and products on them.
 */
public class MainController implements Initializable {
    @FXML private Label warningLabel;
    @FXML
    private Label allPartsTextHeader;
    @FXML private Button exitButton;

    //Part Table variables
    @FXML private TableView<Part> partsTableView;
    @FXML private TableColumn<Part, Integer> partIdColumn;
    @FXML private TableColumn<Part, String> partNameColumn;
    @FXML private TableColumn<Part, Integer> partInventoryColumn;
    @FXML private TableColumn<Part, Double> partCostColumn;
    @FXML private Button addPartButton;
    @FXML private Button modifyPartButton;
    @FXML private Button deletePartButton;
    @FXML private TextField searchByPartTextField;

    //Product Table variables
    @FXML private TableView<Product> productsTableView;
    @FXML private TableColumn<Product, Integer> productIdColumn;
    @FXML private TableColumn<Product, String> productNameColumn;
    @FXML private TableColumn<Product, Integer> productInventoryColumn;
    @FXML private TableColumn<Product, Double> productCostColumn;
    @FXML private Button addProductButton;
    @FXML private Button modifyProductButton;
    @FXML private Button deleteProductButton;
    @FXML private TextField searchByProductTextField;

    private ObservableList associatedParts = FXCollections.observableArrayList();



    public MainController() {

    }


    /**
     * Method Sets associated parts.
     * Sets associated part list for selected product.
     * @param associatedParts
     */
    public void setAssociatedParts(ObservableList associatedParts) {
        this.associatedParts = associatedParts;
    }

    /**
     * Method controls the add part button.
     * Brings up new menu to add a part to the part list.
     * @param actionEvent unassigned
     * @throws IOException
     */
    @FXML private void onAddPartButtonClick(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(InventoryManagementMain.class.getResource("AddPartWindow.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = (Stage) addPartButton.getScene().getWindow();
        stage.setTitle("Add a Part!");
        stage.setScene(scene);
        stage.show();
        System.out.println("Add Part Button Clicked");
    }


    /**
     * Method controls modify part button.
     * Brings up the modify part window.
     * @param actionEvent unassigned
     * @throws IOException
     */
    @FXML private void onModifyPartButtonClick(ActionEvent actionEvent) throws IOException {
        warningLabel.setVisible(false);
        Part selectedPart;
        partsTableView.refresh();
        selectedPart = partsTableView.getSelectionModel().getSelectedItem();

        if (selectedPart == null) {
            System.out.println("Need To select a part!!");
        }
        else {
            FXMLLoader fxmlLoader = new FXMLLoader(InventoryManagementMain.class.getResource("ModifyPartWindow.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = (Stage) modifyPartButton.getScene().getWindow();
            stage.setTitle("Modify Part");
            stage.setScene(scene);
            stage.show();
            ModifyPartController modifyPartController = fxmlLoader.getController();
            modifyPartController.setSelectedPart(selectedPart);
            modifyPartController.setAssociatedPartList(associatedParts);
            modifyPartController.extractPartData();
            modifyPartController.setSelectedIndex(partsTableView.getSelectionModel().getSelectedIndex());
            System.out.println("Modify Part Button Clicked");
            System.out.println("Part ID iS: " + selectedPart.getId());
            System.out.println("Part Index iS: " + partsTableView.getSelectionModel().getSelectedIndex());
        }

    }


    /**
     * Deletes the part selected
     * @throws IOException
     */
    @FXML private void onDeletePartButtonClick() throws IOException {
        Part toBeDeleted = partsTableView.getSelectionModel().getSelectedItem();
        FXMLLoader fxmlLoader = new FXMLLoader(InventoryManagementMain.class.getResource("DeleteConfirmationWindow.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        DeleteConfirmationController deleteConfirmationController = fxmlLoader.getController();
        Stage stage = (Stage) new Stage();
        stage.setTitle("Delete Confirmation");
        stage.setScene(scene);
        stage.showAndWait();
        if (deleteConfirmationController.isConfirmed()) {
            Inventory.getAllParts().remove(toBeDeleted);
            partsTableView.getItems().remove(toBeDeleted);
            partsTableView.refresh();
            System.out.println("Deleted");
        } else {
            System.out.println("Delete Canceled");
        }
    }


    /**
     * Method controls the add product button.
     * Brings up the add new part window.
     * @throws IOException
     */
    @FXML private void onAddProductButtonClick() throws IOException {
        warningLabel.setVisible(false);
        FXMLLoader fxmlLoader = new FXMLLoader(InventoryManagementMain.class.getResource("AddProductWindow.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = (Stage) addProductButton.getScene().getWindow();
        stage.setTitle("Add a Part!");
        stage.setScene(scene);
        stage.show();
        System.out.println("Add Product Button Clicked");
    }

    /**
     * Method controls the modify product button.
     * Brings up the modify part controller.
     * @throws IOException
     */
    @FXML private void onModifyProductButtonClick() throws IOException {
        warningLabel.setVisible(false);
        Product selectedProduct;
        productsTableView.refresh();
        selectedProduct = productsTableView.getSelectionModel().getSelectedItem();

        if (selectedProduct == null) {
            System.out.println("Need To select a product!!");
        }
        else {
            FXMLLoader fxmlLoader = new FXMLLoader(InventoryManagementMain.class.getResource("ModifyProductWindow.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = (Stage) modifyProductButton.getScene().getWindow();
            stage.setTitle("Modify Product");
            stage.setScene(scene);
            stage.show();

            //Pass Data
            ModifyProductController modifyProductController = fxmlLoader.getController();
            modifyProductController.setSelectedProduct(selectedProduct);
            modifyProductController.setSelectedIndex(selectedProduct.getId());
            modifyProductController.extractPartData();
            modifyProductController.setSelectedIndex(productsTableView.getSelectionModel().getSelectedIndex());
            stage.setOnHidden(e -> {
                        ObservableList<Part> associatedPartList = modifyProductController.getAssociatedParts();
                        setAssociatedParts(associatedPartList);
            System.out.println("Modify Part Button Clicked");
        });
        System.out.println("Modify Product Button Clicked");
    }
    }


    /**
     * Method controls the delete button.
     *  Deletes products and checks to make sure that there are no associated parts first.
     * @throws IOException
     */
    @FXML private void onDeleteProductButtonClick() throws IOException {
        Product toBeDeleted = productsTableView.getSelectionModel().getSelectedItem();
        if (!toBeDeleted.getAllAssociatedParts().isEmpty()) {
            warningLabel.setVisible(true);
            System.out.println("Remove Associated Parts First");
            return;
        }
        FXMLLoader fxmlLoader = new FXMLLoader(InventoryManagementMain.class.getResource("DeleteConfirmationWindow.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        DeleteConfirmationController deleteConfirmationController = fxmlLoader.getController();
        Stage stage = (Stage) new Stage();
        stage.setTitle("Delete Confirmation");
        stage.setScene(scene);
        stage.showAndWait();
        if (deleteConfirmationController.isConfirmed()) {
            Inventory.getAllProducts().remove(toBeDeleted);
            productsTableView.getItems().remove(toBeDeleted);
            productsTableView.refresh();
            System.out.println("Deleted");
        } else {
            System.out.println("Delete Canceled");
        }

    }


    /**
     * Method controls the exit button.
     * exits the app completely.
     * @param event
     */
    @FXML private void onExitButtonClick(ActionEvent event) {
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
        System.out.println("Exit button clicked");
    }



    /**
     * This method controls the search by part text field.
     * this will allow the user to search for a part by ID or by name.
     */
    @FXML
    private void onSearchByPartTextField() {
        String searchText = searchByPartTextField.getText();
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

    /**
     * This method controls the search by product text field.
     * this will allow the user to search for a product by ID or by name.
     */
    @FXML
    private void onSearchByProductTextField() {
        String searchText = searchByProductTextField.getText();
        if (searchText.trim().isEmpty()) {
            productsTableView.setItems(Inventory.getAllProducts());
        } else {
            try {
                int id = Integer.parseInt(searchText);
                Product foundProduct = Inventory.lookupProduct(id);
                if (foundProduct != null) {
                    productsTableView.setItems(FXCollections.observableArrayList(foundProduct));
                } else {
                    productsTableView.setItems(FXCollections.emptyObservableList());
                }
            } catch (NumberFormatException e) {
                ObservableList<Product> filteredList = Inventory.lookupProduct(searchText);
                productsTableView.setItems(filteredList);
            }
        }
        productsTableView.refresh();
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        partIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInventoryColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partCostColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        partsTableView.setItems(Inventory.getAllParts());
        partsTableView.refresh();

        productIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        productNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        productInventoryColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productCostColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        productsTableView.setItems(Inventory.getAllProducts());
        productsTableView.refresh();

        searchByPartTextField.setOnAction(event -> onSearchByPartTextField());
        searchByProductTextField.setOnAction(event -> onSearchByProductTextField());

    }


}
