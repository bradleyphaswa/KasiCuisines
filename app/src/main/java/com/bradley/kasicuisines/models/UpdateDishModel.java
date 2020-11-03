package com.bradley.kasicuisines.models;

public class UpdateDishModel {

    private String mealName, randomUID, description, quantity, price, imageURL, restaurantId;

    public UpdateDishModel() {
    }

    public UpdateDishModel(String mealName, String randomUID, String description, String quantity, String price, String imageURL, String restaurantId) {
        this.mealName = mealName;
        this.randomUID = randomUID;
        this.description = description;
        this.quantity = quantity;
        this.price = price;
        this.imageURL = imageURL;
        this.restaurantId = restaurantId;
    }

    public String getMealName() {
        return mealName;
    }

    public void setMealName(String mealName) {
        this.mealName = mealName;
    }

    public String getRandomUID() {
        return randomUID;
    }

    public void setRandomUID(String randomUID) {
        this.randomUID = randomUID;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(String restaurantId) {
        this.restaurantId = restaurantId;
    }
}
