package com.bradley.kasicuisines.businessFoodPanel;

public class RestaurantFinalOrders1 {

    private String address, GrandTotalPrice,mobileNumber,name,randomUID, Status;

    public RestaurantFinalOrders1() {
    }

    public RestaurantFinalOrders1(String address, String grandTotalPrice, String mobileNumber, String name, String randomUID, String status) {
        this.address = address;
        GrandTotalPrice = grandTotalPrice;
        this.mobileNumber = mobileNumber;
        this.name = name;
        this.randomUID = randomUID;
        this.Status = status;
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

    public String getRandomUID() {
        return randomUID;
    }

    public void setRandomUID(String randomUID) {
        this.randomUID = randomUID;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        this.Status = status;
    }
}
