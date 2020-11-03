package com.bradley.kasicuisines.customerFoodPanel;

public class Customer {

    private String streetNo, province, city, suburb, email, firstName, lastName, mobileNo;

    public Customer() {
    }

    public Customer(String streetNo, String province, String city, String suburb, String email, String firstName, String lastName, String mobileNo) {
        this.streetNo = streetNo;
        this.province = province;
        this.city = city;
        this.suburb = suburb;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.mobileNo = mobileNo;
    }

    public String getStreetNo() {
        return streetNo;
    }

    public void setStreetNo(String streetNo) {
        this.streetNo = streetNo;
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

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }
}
