package com.bradley.kasicuisines.deliveryFoodPanel;

public class DeliveryShipFinalOrders1 {

    private String address, restaurantId, restaurantName, GrandTotalPrice, mobileNumber, name, randomUID, userId;

    public DeliveryShipFinalOrders1(String address, String restaurantId, String restaurantName, String grandTotalPrice, String mobileNumber, String name, String randomUID, String userId) {
        this.address = address;
        this.restaurantId = restaurantId;
        this.restaurantName = restaurantName;
        GrandTotalPrice = grandTotalPrice;
        this.mobileNumber = mobileNumber;
        this.name = name;
        this.randomUID = randomUID;
        this.userId = userId;
    }

    public DeliveryShipFinalOrders1()
    {

    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(String restaurantId) {
        this.restaurantId = restaurantId;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
