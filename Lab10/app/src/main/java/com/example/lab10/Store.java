package com.example.lab10;

public class Store {
    private final String name;
    private final String address;
    private final String phone;
    private final String workingHours;

    public Store(String name, String address, String phone, String workingHours) {
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.workingHours = workingHours;
    }

    // Getters
    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getPhone() {
        return phone;
    }

    public String getWorkingHours() {
        return workingHours;
    }

    @Override
    public String toString() {
        return name + "\n" + address;
    }
}
