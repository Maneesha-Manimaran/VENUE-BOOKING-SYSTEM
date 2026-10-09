package com.venuebooking.model;

public class Venue {
    private int id;
    private String name;
    private int capacity;
    private double price; // per day
    private boolean active;

    public Venue(int id, String name, int capacity, double price) {
        this.id = id;
        this.name = name;
        this.capacity = capacity;
        this.price = price;
        this.active = true;
    }

    // getters & setters
    public int getId() { return id; }
    public String getName() { return name; }
    public int getCapacity() { return capacity; }
    public double getPrice() { return price; }
    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }
    public void setName(String name) { this.name = name; }
    public void setCapacity(int capacity) { this.capacity = capacity; }
    public void setPrice(double price) { this.price = price; }

    @Override
    public String toString() {
        return id + " - " + name + " (Cap: " + capacity + ", ₹" + price + ")";
    }
}
