package com.carlfolkesson.medicinehandler;

/**
 * Created by kalle on 2017-08-23.
 */

public class MedicineItem {

    private String name;
    private String amount_in_stock;

    public MedicineItem(String name, String amount_in_stock) {
        this.name = name;
        this.amount_in_stock = amount_in_stock;
    }

    public String getName() {
        return name;
    }

    public String getAmount_in_stock() {
        return amount_in_stock;
    }
}
