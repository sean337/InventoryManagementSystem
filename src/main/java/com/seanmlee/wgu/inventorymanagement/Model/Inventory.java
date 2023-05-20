package com.seanmlee.wgu.inventorymanagement.Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Locale;

/**
 * Inventory class
 * @author Sean Lee
 */
public class Inventory {

    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();


    private static int nextId = 0;


    public static int getNextId() {
        return ++nextId;
    }

    /**
     * Adds new part
     * @param newPart
     */
    public static void addPart(Part newPart) {
        newPart.setId(getNextId());
        allParts.add(newPart);
    }

    /**
     * Adds new product
     * @param newProduct
     */
    public static void addProduct(Product newProduct) {
        newProduct.setId(getNextId());
        allProducts.add(newProduct);

    }

    /**
     * Look up part by ID
     * @param partId
     * @return part at particular index
     */
    public static Part lookupPart(int partId) {
        for (Part part : allParts) {
            if (part.getId() == partId){
                return part;
            }
        }
        return null;
    }

    /**
     * Look up product by ID
     * @param productId
     * @return
     */
    public static Product lookupProduct(int productId) {
        for (Product product : allProducts) {
            if (product.getId() == productId) {
                return product;
            }
        }
        return null;
    }

    /**
     * Lookup all parts by name
     * @param partName
     * @return
     */
    public static ObservableList<Part> lookupPart(String partName) {
        ObservableList<Part> filteredList = FXCollections.observableArrayList();
        for (Part part : allParts) {
            if (part.getName().toLowerCase().contains(partName.toLowerCase())) {
                filteredList.add(part);
            }
        }
        return filteredList;
    }

    /**
     * Lookup all products by name
     * @param productName
     * @return
     */
    public static ObservableList<Product> lookupProduct(String productName) {
        ObservableList<Product> filteredList = FXCollections.observableArrayList();
        for (Product product : allProducts) {
            if (product.getName().toLowerCase(Locale.ROOT).contains(productName) || product.getName().toUpperCase(Locale.ROOT).contains(productName)) {
                filteredList.add(product);
            }
        }
        return filteredList;
    }

    /**
     * Update product
     * @param index
     * @param newProduct
     */
    public static void updateProduct(int index, Product newProduct) {
        allProducts.set(index, newProduct);
    }

    /**
     * Update part
     * @param index
     * @param selectedPart
     */
    public static void updatePart(int index, Part selectedPart) {
        allParts.set(index, selectedPart);
    }

    /**
     * Delete selected part
     * @param selectedPart
     * @return
     */
    public static boolean deletePart(Part selectedPart) {
        return allParts.remove(selectedPart);
    }

    /**
     * Delete product
     * @param selectedProduct
     * @return
     */
    public static boolean deleteProduct(Product selectedProduct) {
        return allProducts.remove(selectedProduct);
    }

    /**
     * Get all parts
     * @return allParts
     */
    public static ObservableList<Part> getAllParts() {
        return allParts;
    }

    /**
     * Get all products
     * @return allProducts
     */
    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }
}

