package com.venuebooking.store;

import com.venuebooking.model.*;
import java.util.*;
import java.util.stream.Collectors;

public class DataStore {
    private List<Venue> venues = new ArrayList<>();
    private List<User> users = new ArrayList<>();
    private List<Booking> bookings = new ArrayList<>();
    private int nextVenueId = 1;
    private int nextBookingId = 1;
    private int nextUserId = 1;

    // Singleton pattern for simplicity
    private static DataStore instance = new DataStore();
    public static DataStore getInstance() { return instance; }
    private DataStore() {
        // seed with sample data
        addUser(new User(nextUserId++, "admin", "admin123", true));
        addUser(new User(nextUserId++, "user1", "user123", false));
        addVenue("Main Auditorium", 200, 5000);
        addVenue("Conference Hall A", 80, 2500);
        addVenue("Banquet Hall", 150, 4500);
    }
    
    //register 
    public boolean registerNewUser(String u, String p){
        for(User user : users){
            if(user.getUsername().equalsIgnoreCase(u)) return false;
        }
        users.add(new User(nextUserId++, u, p, false)); // false = regular user
        return true;
    }
    
    // login
    public User login(String username, String password) {
        for (User u : users) {
            if (u.getUsername().equals(username) && u.getPassword().equals(password)) {
                return u; // login success
            }
        }
        return null; // login failed
    }


    
    // user methods
    public void addUser(User u) { users.add(u); }
    public Optional<User> validateUser(String username, String password) {
        return users.stream().filter(u -> u.getUsername().equals(username) && u.getPassword().equals(password)).findFirst();
    }

    // venue methods
    public Venue addVenue(String name, int cap, double price) {
        Venue v = new Venue(nextVenueId++, name, cap, price);
        venues.add(v);
        return v;
    }
    public boolean removeVenue(int id) {
        return venues.removeIf(v -> v.getId() == id);
    }
    public List<Venue> getAllVenues() { return new ArrayList<>(venues); }
    public Optional<Venue> getVenueById(int id) {
        return venues.stream().filter(v -> v.getId() == id).findFirst();
    }

    // booking methods
    public Booking createBooking(int venueId, int userId, String date) throws IllegalStateException {
        // check double booking for same venue & date and same confirmed status
        boolean exists = bookings.stream()
                .anyMatch(b -> b.getVenueId() == venueId && b.getDate().equals(date) && b.getStatus().equals("CONFIRMED"));
        if (exists) {
            throw new IllegalStateException("Venue already booked for date " + date);
        }
        Booking b = new Booking(nextBookingId++, venueId, userId, date);
        bookings.add(b);
        return b;
    }
    public List<Booking> getBookingsForUser(int userId) {
        return bookings.stream().filter(b -> b.getUserId() == userId).collect(Collectors.toList());
    }
    public List<Booking> getAllBookings() { return new ArrayList<>(bookings); }

    public boolean cancelBooking(int bookingId, int userId, boolean adminAction) {
        Optional<Booking> opt = bookings.stream().filter(b -> b.getId() == bookingId).findFirst();
        if (!opt.isPresent()) return false;
        Booking b = opt.get();
        if (!adminAction && b.getUserId() != userId) return false;
        b.setStatus("CANCELLED");
        return true;
    }
    public String getUserNameById(int id) {
        for (User u : users) {
            if (u.getId() == id) return u.getUsername();
        }
        return "Unknown";
    }

    public String getVenueNameById(int id) {
        for (Venue v : venues) {
            if (v.getId() == id) return v.getName();
        }
        return "Unknown";
    }

}
