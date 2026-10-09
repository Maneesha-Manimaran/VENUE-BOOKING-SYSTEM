package com.venuebooking.model;

public class Booking {
    private int id;
    private int venueId;
    private int userId;
    private String date; // YYYY-MM-DD
    private String status; // CONFIRMED / CANCELLED

    public Booking(int id, int venueId, int userId, String date) {
        this.id = id;
        this.venueId = venueId;
        this.userId = userId;
        this.date = date;
        this.status = "CONFIRMED";
    }

    public int getId() { return id; }
    public int getVenueId() { return venueId; }
    public int getUserId() { return userId; }
    public String getDate() { return date; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    @Override
    public String toString() {
        return "Booking#" + id + " Venue:" + venueId + " User:" + userId + " Date:" + date + " [" + status + "]";
    }
}
