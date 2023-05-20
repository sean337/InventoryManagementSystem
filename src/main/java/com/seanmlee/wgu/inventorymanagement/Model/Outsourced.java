package com.seanmlee.wgu.inventorymanagement.Model;

/**
 * class Outsourced.java
 */

/**
 *
 * @author Sean Lee
 */

public class Outsourced extends Part {

    private String companyName;

    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        this.setId(id);
        this.setName(name);
        this.setPrice(price);
        this.setStock(stock);
        this.setMin(min);
        this.setMax(max);
        this.companyName = companyName;
    }


    /**
     * Set company name
     * @param companyName
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    /**
     * Get company name
     * @return companyName
     */
    public String getCompanyName() {
        return this.companyName;
    }
}

