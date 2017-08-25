package com.carlfolkesson.medicinehandler;

/**
 * Created by kalle on 2017-08-23.
 */

public class MedicineItem {

    private String name;
    private int stock;

    public MedicineItem(String name, int stock) {
        this.name = name;
        this.stock = stock;
    }

    public String getName() {
        return name;
    }

    public int getStock() {
        return stock;
    }
}
