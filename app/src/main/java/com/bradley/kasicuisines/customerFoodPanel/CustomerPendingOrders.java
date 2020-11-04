package com.bradley.kasicuisines.customerFoodPanel;

public class CustomerPendingOrders {

    private String restaurantId, mealId, mealName, mealQuantity, mealPrice, totalPrice;

    public CustomerPendingOrders() {
    }

    public CustomerPendingOrders(String restaurantId, String mealId, String mealName, String mealQuantity, String mealPrice, String totalPrice) {
        this.restaurantId = restaurantId;
        this.mealId = mealId;
        this.mealName = mealName;
        this.mealQuantity = mealQuantity;
        this.mealPrice = mealPrice;
        this.totalPrice = totalPrice;
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

    public String getMealPrice() {
        return mealPrice;
    }

    public void setMealPrice(String mealPrice) {
        this.mealPrice = mealPrice;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }
}
