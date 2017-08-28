package com.carlfolkesson.medicinehandler;

/**
 * Created by Carl Folkesson on 2017-08-23.
 * Class that represets a medicine
 */

public class MedicineItem {

    private String name;
    private int stock;
    private int dose;

    public MedicineItem(String name, int stock, int dose) {
        this.name = name;
        this.stock = stock;
        this.dose = dose;
    }

    public String getName() {
        return name;
    }

    public int getStock() {
        return stock;
    }

    public int getDose() {
        return dose;
    }
}
