package com.bradley.kasicuisines.businessFoodPanel;

public class RestaurantPendingOrders {

    private String restaurantId, mealId, mealName, mealQuantity, price, randomUID, totalPrice, userId;

    public RestaurantPendingOrders() {
    }

    public RestaurantPendingOrders(String restaurantId, String mealId, String mealName, String mealQuantity, String price, String randomUID, String totalPrice, String userId) {
        this.restaurantId = restaurantId;
        this.mealId = mealId;
        this.mealName = mealName;
        this.mealQuantity = mealQuantity;
        this.price = price;
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

    public String getMealQuantity() {
        return mealQuantity;
    }

    public void setMealQuantity(String mealQuantity) {
        this.mealQuantity = mealQuantity;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
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
