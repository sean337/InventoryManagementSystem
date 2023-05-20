package com.seanmlee.wgu.inventorymanagement.Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Product class
 * @author Sean Lee
 */
public class Product {
    private ObservableList<Part> associatedParts;
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;

    /**
     * Constructor to creat product object
     * @param id
     * @param name
     * @param price
     * @param stock
     * @param min
     * @param max
     */
    public Product(int id, String name, double price, int stock, int min, int max) {

        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
        this.associatedParts = FXCollections.observableArrayList();
    }

    /**
     * Get Product ID
     * @return id
     */
    public int getId() {
        return id;
    }

    /**
     * Set Product ID
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Get product name
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Set product name
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    /**
     * Set product price
     * @param price
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Get stock
     * @return stock
     */
    public int getStock() {
        return stock;
    }

    /**
     * Set stock
     * @param stock
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * Get min value
     * @return min
     */
    public int getMin() {
        return min;
    }

    /**
     * Set min value
     * @param min
     */
    public void setMin(int min) {
        this.min = min;
    }

    /**
     * Get max value
     * @return max
     */
    public int getMax() {
        return max;
    }

    /**
     * Set max value
     * @param max
     */
    public void setMax(int max) {
        this.max = max;
    }

    /**
     * Add part for product
     * @param part
     */
    public void addAssociatedPart(Part part) {

        try {
            if (part == null) {
                throw new NullPointerException("Part can't be empty or null");
            }
            associatedParts.add(part);
        } catch (NullPointerException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    /**
     * Get all associated parts for product
     * @return
     */
    public ObservableList<Part> getAllAssociatedParts() {
        return associatedParts;
    }

    /**
     * Delete associated part
     * @param selectedAssociatedPart
     * @return
     */
    public boolean deleteAssociatedPart(Part selectedAssociatedPart) {
        return associatedParts.remove(selectedAssociatedPart);
    }


}

