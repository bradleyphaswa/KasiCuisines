package com.bradley.kasicuisines.businessFoodPanel;

public class RestaurantPaymentOrders {

    private String restaurantId,mealId,mealName,mealPrice,mealQuantity,randomUID,totalPrice,userId;

    public RestaurantPaymentOrders() {
    }

    public RestaurantPaymentOrders(String restaurantId, String mealId, String mealName, String mealPrice, String mealQuantity, String randomUID, String totalPrice, String userId) {
        this.restaurantId = restaurantId;
        this.mealId = mealId;
        this.mealName = mealName;
        this.mealPrice = mealPrice;
        this.mealQuantity = mealQuantity;
        this.randomUID = randomUID;
        this.totalPrice = totalPrice;
        this.userId = userId;
    }

    public String getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(String restaurantId) {
        this.restaurantId = restaurantId;
    }

    public String getMealId() {
        return mealId;
    }

    public void setMealId(String mealId) {
        this.mealId = mealId;
    }

    public String getMealName() {
        return mealName;
    }

    public void setMealName(String mealName) {
        this.mealName = mealName;
    }

    public String getMealPrice() {
        return mealPrice;
    }

    public void setMealPrice(String mealPrice) {
        this.mealPrice = mealPrice;
    }

    public String getMealQuantity() {
        return mealQuantity;
    }

    public void setMealQuantity(String mealQuantity) {
        this.mealQuantity = mealQuantity;
    }

    public String getRandomUID() {
        return randomUID;
    }

    public void setRandomUID(String randomUID) {
        this.randomUID = randomUID;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
