package com.bradley.kasicuisines.customerFoodPanel;

public class Cart {

    private String restaurantId, mealId, mealName, mealQuantity, price, totalPrice;

    public Cart() {
    }

    public Cart(String restaurantId, String mealId, String mealName, String mealQuantity, String price, String totalPrice) {
        this.restaurantId = restaurantId;
        this.mealId = mealId;
        this.mealName = mealName;
        this.mealQuantity = mealQuantity;
        this.price = price;
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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }
}
