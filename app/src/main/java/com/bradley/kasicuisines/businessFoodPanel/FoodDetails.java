package com.bradley.kasicuisines.businessFoodPanel;

public class FoodDetails {

    private String mealName,quantity, description, price, ImageURL, randomUID, restaurantId;

    public FoodDetails() {
    }

    public FoodDetails(String mealName, String description, String quantity, String price, String imageURL, String randomUID, String restaurantId) {
        this.mealName = mealName;
        this.description = description;
        this.price = price;
        this.ImageURL = imageURL;
        this.randomUID = randomUID;
        this.quantity = quantity;
        this.restaurantId = restaurantId;
    }

    public String getMealName() {
        return mealName;
    }

    public void setMealName(String mealName) {
        this.mealName = mealName;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getImageURL() {
        return ImageURL;
    }

    public void setImageURL(String imageURL) {
        ImageURL = imageURL;
    }

    public String getRandomUID() {
        return randomUID;
    }

    public void setRandomUID(String randomUID) {
        this.randomUID = randomUID;
    }

    public String getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(String restaurantId) {
        this.restaurantId = restaurantId;
    }
}
