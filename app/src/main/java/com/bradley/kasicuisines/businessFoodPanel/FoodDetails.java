package com.bradley.kasicuisines.businessFoodPanel;

public class FoodDetails {

    private String mealName,quantity, description, price, ImageURL, randomUID, chefId;

    public FoodDetails(String mealName, String quantity, String description, String price, String imageURL, String randomUID, String chefId) {
        this.mealName = mealName;
        this.description = description;
        this.price = price;
        this.ImageURL = imageURL;
        this.randomUID = randomUID;
        this.quantity = quantity;
        this.chefId = chefId;
    }
}
