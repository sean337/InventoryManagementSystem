package com.seanmlee.wgu.inventorymanagement;

import com.seanmlee.wgu.inventorymanagement.Model.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * This class creates an app that will manage an invenory system.
 *
 * the JAVADOC folder is included in this document here: com/seanmlee/wgu/inventorymanagement/javadoc
 *
 * @author Sean Lee
 *  FUTURE ENHANCEMENT: Consider implementing a more user-friendly form validation mechanism that provides
 *  real-time feedback to users as they input information. For instance highlighting fields with invalid
 * input and displaying inline error messages to help users identify and correct issues more easily.
 *
 */
public class InventoryManagementMain extends Application {

    /**
     * This is method populates data.
     * Adds some demo data to test the UI first.
     */
    public static void populateData() {
        //Setting initial Product List
        Inventory.addProduct(new Product( Inventory.getNextId(), "First Demo Product", 9.99, 10, 1, 50));
        Inventory.addProduct(new Product(Inventory.getNextId(),  "Second Demo Product", 12.99, 20, 1, 100));
        Inventory.addProduct(new Product(Inventory.getNextId(), "Third Demo Product", 20.99, 30, 1, 100));
        System.out.println("product list ran");

        //Setting initial Part List
        Inventory.addPart(new Outsourced(Inventory.getNextId(), "First Demo Part", 29.99, 12, 1, 100, "Demo Company"));
        Inventory.addPart(new InHouse(Inventory.getNextId(), "Second Demo Part", 29.99, 12, 1, 100, 32));
        System.out.println("part List ran");
    }

    /**
     * This method starts the first scene.
     * Allows the window system to popup.
     * @param stage the first window to popup
     * @throws IOException
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(InventoryManagementMain.class.getResource("MainWindow.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1458, 567);
        stage.setTitle("Inventory Management System!");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * This is the main method.
     * this is the first method that gets called when the program starts.
     * @param args
     */
    public static void main(String[] args) {
        populateData();
        launch();
    }
}