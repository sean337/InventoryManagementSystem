package com.seanmlee.wgu.inventorymanagement.Model;

/**
 * class InHouse.java
 */

/**
 *
 * @author Sean Lee
 */

public class InHouse extends Part {

    private int machineId;

    public InHouse(int id, String name, double price, int stock, int min, int max, int machineId) {
        super(id, name, price, stock, min, max);
        this.setId(id);
        this.setName(name);
        this.setPrice(price);
        this.setStock(stock);
        this.setMin(min);
        this.setMax(max);
        this.machineId = machineId;

    }

    /**
     * Set Machine ID
     * @param machineId
     */
    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }

    /**
     * Get Machine ID
     * @return
     */
    public int getMachineId() {
        return this.machineId;
    }
}

