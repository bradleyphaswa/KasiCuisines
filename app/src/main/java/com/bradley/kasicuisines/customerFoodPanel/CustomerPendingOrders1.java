package com.bradley.kasicuisines.customerFoodPanel;

public class CustomerPendingOrders1 {

    private String address, GrandTotalPrice, mobileNumber, name, note;

    public CustomerPendingOrders1() {
    }

    public CustomerPendingOrders1(String address, String grandTotalPrice, String mobileNumber, String name, String note) {
        this.address = address;
        GrandTotalPrice = grandTotalPrice;
        this.mobileNumber = mobileNumber;
        this.name = name;
        this.note = note;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getGrandTotalPrice() {
        return GrandTotalPrice;
    }

    public void setGrandTotalPrice(String grandTotalPrice) {
        GrandTotalPrice = grandTotalPrice;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
