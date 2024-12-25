package com.example.lab9;

public class Store {
    private String name;
    private String address;
    private String phone;
    private String workingHours;

    public Store(String name, String address, String phone, String workingHours) {
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.workingHours = workingHours;
    }

    // Getters
    public String getName() { return name; }
    public String getAddress() { return address; }
    public String getPhone() { return phone; }
    public String getWorkingHours() { return workingHours; }

    @Override
    public String toString() {
        return name + "\n" + address;
    }
}
