package org.example;

public class Reader extends person{

    public Reader(String fullName, String address, String stateOfOrigin, String libaryNo, String status, String phoneNumber) {
        super(fullName, address, stateOfOrigin, libaryNo, status, phoneNumber);
    }

    public Reader() {
        super();
    }

    @Override
    public String getFullName() {
        return super.getFullName();
    }

    @Override
    public void setFullName(String fullName) {
        super.setFullName(fullName);
    }

    @Override
    public String getAddress() {
        return super.getAddress();
    }

    @Override
    public void setAddress(String address) {
        super.setAddress(address);
    }

    @Override
    public String getStateOfOrigin() {
        return super.getStateOfOrigin();
    }

    @Override
    public void setStateOfOrigin(String stateOfOrigin) {
        super.setStateOfOrigin(stateOfOrigin);
    }

    @Override
    public String getLibaryNo() {
        return super.getLibaryNo();
    }

    @Override
    public void setLibaryNo(String libaryNo) {
        super.setLibaryNo(libaryNo);
    }

    @Override
    public String getStatus() {
        return super.getStatus();
    }

    @Override
    public void setStatus(String status) {
        super.setStatus(status);
    }

    @Override
    public String getPhoneNumber() {
        return super.getPhoneNumber();
    }

    @Override
    public void setPhoneNumber(String phoneNumber) {
        super.setPhoneNumber(phoneNumber);
    }

    @Override
    public String toString(){
        return "Reader [Name = " + getFullName() + ", Address = " + getAddress() +
                ", State of origin = " + getStateOfOrigin() + ", id = " + getLibaryNo() +
                "status = " + getStatus() + ", Phone number = " + getPhoneNumber() + "]";
    }
}