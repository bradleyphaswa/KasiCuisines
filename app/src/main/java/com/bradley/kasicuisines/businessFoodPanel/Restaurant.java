package com.bradley.kasicuisines.businessFoodPanel;

public class Restaurant {
    private String email, firstName, lastName, mobileNumber, storeAddress, storeName;

    public Restaurant(String email, String firstName, String lastName, String mobileNumber, String storeAddress, String storeName) {
        this.storeAddress = storeAddress;
        storeName = storeName;
        email = email;
        firstName = firstName;
        lastName = lastName;
        mobileNumber = mobileNumber;

    }

    public Restaurant() {
    }

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public String getStoreAddress() {
        return storeAddress;
    }

    public String getStoreName() {
        return storeName;
    }
}
