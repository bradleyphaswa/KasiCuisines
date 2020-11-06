package com.bradley.kasicuisines;

public class Restaurant {
    private String email, firstName, lastName, mobileNo, streetNo, province, city, suburb, storeName;

    public Restaurant() {
    }

    public Restaurant(String email, String firstName, String lastName, String mobileNo, String streetNo, String province, String city, String suburb, String storeName) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.mobileNo = mobileNo;
        this.streetNo = streetNo;
        this.province = province;
        this.city = city;
        this.suburb = suburb;
        this.storeName = storeName;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getSuburb() {
        return suburb;
    }

    public void setSuburb(String suburb) {
        this.suburb = suburb;
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

    public String getMobileNo() {
        return mobileNo;
    }

    public String getStreetNo() {
        return streetNo;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public void setStreetNo(String streetNo) {
        this.streetNo = streetNo;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }
}
