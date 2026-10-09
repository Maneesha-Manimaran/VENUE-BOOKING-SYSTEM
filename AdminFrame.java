package com.venuebooking.ui;

import com.venuebooking.model.Venue;
import com.venuebooking.model.Booking;
import com.venuebooking.model.User;
import com.venuebooking.store.DataStore;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class AdminFrame extends JFrame {

    private User admin;
    private DefaultListModel<String> venueModel = new DefaultListModel<>();
    private DefaultListModel<String> bookingModel = new DefaultListModel<>();

    private JList<String> venueList = new JList<>(venueModel);
    private JList<String> bookingList = new JList<>(bookingModel);

    public AdminFrame(User admin) {
        this.admin = admin;

        setTitle("Admin Panel - " + admin.getUsername());
        setSize(900,550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        //-------------------------- TOP PANEL (LOGOUT ADDED) --------------------------
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton logoutBtn = new JButton("Logout");
        topPanel.add(logoutBtn);
        add(topPanel, BorderLayout.NORTH);

        logoutBtn.addActionListener(e -> {
            new LoginFrame().setVisible(true);
            dispose(); // close admin screen
        });

        //-------------------------- CENTER MAIN CONTENT -------------------------------
        JPanel center = new JPanel(new GridLayout(1,2));

        // LEFT — VENUES
        JPanel left = new JPanel(new BorderLayout());
        left.setBorder(BorderFactory.createTitledBorder("Venues"));
        left.add(new JScrollPane(venueList), BorderLayout.CENTER);

        JButton addVenueBtn = new JButton("Add Venue");
        JButton removeVenueBtn = new JButton("Remove Selected Venue");

        JPanel leftBtns = new JPanel();
        leftBtns.add(addVenueBtn);
        leftBtns.add(removeVenueBtn);
        left.add(leftBtns, BorderLayout.SOUTH);

        // RIGHT — BOOKINGS
        JPanel right = new JPanel(new BorderLayout());
        right.setBorder(BorderFactory.createTitledBorder("Bookings (User + Venue Details)"));
        right.add(new JScrollPane(bookingList), BorderLayout.CENTER);

        JButton cancelBookingBtn = new JButton("Cancel Selected Booking");

        JPanel rightBtns = new JPanel();
        rightBtns.add(cancelBookingBtn);
        right.add(rightBtns, BorderLayout.SOUTH);

        center.add(left);
        center.add(right);
        add(center, BorderLayout.CENTER);

        //-------------------------- LOAD DATA --------------------------
        loadVenues();
        loadBookings();

        //-------------------------- BUTTON ACTIONS --------------------------
        addVenueBtn.addActionListener(e -> addVenue());
        removeVenueBtn.addActionListener(e -> removeVenue());
        cancelBookingBtn.addActionListener(e -> cancelBooking());
    }

    private void loadVenues() {
        venueModel.clear();
        for (Venue v : DataStore.getInstance().getAllVenues()) {
            venueModel.addElement(v.getId() + " | " + v.getName() +
                    " | Cap:" + v.getCapacity() + " | ₹" + v.getPrice());
        }
    }

    private void loadBookings() {
        bookingModel.clear();
        for (Booking b : DataStore.getInstance().getAllBookings()) {
            String user = DataStore.getInstance().getUserNameById(b.getUserId());
            String venue = DataStore.getInstance().getVenueNameById(b.getVenueId());

            bookingModel.addElement("Booking #" + b.getId() +
                    " | User: " + user +
                    " | Venue: " + venue +
                    " | Date: " + b.getDate() +
                    " | Status: " + b.getStatus());
        }
    }

    private void addVenue() {
        String name = JOptionPane.showInputDialog(this, "Enter Venue Name:");
        if(name == null || name.trim().isEmpty()) return;

        try {
            int cap = Integer.parseInt(JOptionPane.showInputDialog(this, "Enter Capacity:"));
            double price = Double.parseDouble(JOptionPane.showInputDialog(this, "Enter Price:"));
            DataStore.getInstance().addVenue(name, cap, price);
            loadVenues();
        } catch(Exception e) {
            JOptionPane.showMessageDialog(this, "Invalid Input!");
        }
    }

    private void removeVenue() {
        int index = venueList.getSelectedIndex();
        if(index == -1) {
            JOptionPane.showMessageDialog(this, "Select a venue to remove");
            return;
        }

        String item = venueModel.get(index);
        int vid = Integer.parseInt(item.split("\\|")[0].trim());

        DataStore.getInstance().removeVenue(vid);
        loadVenues();
        loadBookings();
    }

    private void cancelBooking() {
        int index = bookingList.getSelectedIndex();
        if(index == -1) {
            JOptionPane.showMessageDialog(this, "Select booking to cancel");
            return;
        }

        String item = bookingModel.get(index);
        int bookingId = Integer.parseInt(item.split("#")[1].split("\\|")[0].trim());

        DataStore.getInstance().cancelBooking(bookingId, admin.getId(), true);
        loadBookings();
    }
}
