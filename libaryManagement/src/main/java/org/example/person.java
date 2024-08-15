package org.example;

public class person {
    private String fullName;
    private String address;
    private String stateOfOrigin;
    private String libaryNo;
    private String status;
    private String phoneNumber;



    public person(String fullName, String address, String stateOfOrigin, String libaryNo, String phoneNumber, String status) {
        this.fullName = fullName;
        this.address = address;
        this.stateOfOrigin = stateOfOrigin;
        this.libaryNo = libaryNo;
        this.phoneNumber = phoneNumber;
        this.status = status;
    }

    public person() {

    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getStateOfOrigin() {
        return stateOfOrigin;
    }

    public void setStateOfOrigin(String stateOfOrigin) {
        this.stateOfOrigin = stateOfOrigin;
    }

    public String getLibaryNo() {
        return libaryNo;
    }

    public void setLibaryNo(String libaryNo) {
        this.libaryNo = libaryNo;
    }
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}