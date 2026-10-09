package com.venuebooking.ui;

import com.venuebooking.model.Booking;
import com.venuebooking.model.Venue;
import com.venuebooking.model.User;
import com.venuebooking.store.DataStore;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class UserFrame extends JFrame {

    private User user;
    private DefaultListModel<Venue> venueModel = new DefaultListModel<>();
    private DefaultListModel<String> bookingModel = new DefaultListModel<>();

    private JList<Venue> venueList = new JList<>(venueModel);
    private JList<String> bookingList = new JList<>(bookingModel);

    public UserFrame(User user) {
        this.user = user;

        setTitle("User Panel - " + user.getUsername());
        setSize(900,550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        //---------------- TOP PANEL (LOGOUT BUTTON ADDED) ----------------//
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton logoutBtn = new JButton("Logout");
        topPanel.add(logoutBtn);
        add(topPanel, BorderLayout.NORTH);

        logoutBtn.addActionListener(e -> {
            new LoginFrame().setVisible(true);
            dispose(); // close user panel
        });

        //---------------- MAIN CONTENT AREA ----------------//
        JPanel center = new JPanel(new GridLayout(1,2));

        // LEFT — Available Venues
        JPanel left = new JPanel(new BorderLayout());
        left.setBorder(BorderFactory.createTitledBorder("Available Venues"));
        left.add(new JScrollPane(venueList), BorderLayout.CENTER);

        JButton bookBtn = new JButton("Book Selected");
        JPanel leftBtn = new JPanel();
        leftBtn.add(bookBtn);
        left.add(leftBtn, BorderLayout.SOUTH);

        // RIGHT — My Bookings
        JPanel right = new JPanel(new BorderLayout());
        right.setBorder(BorderFactory.createTitledBorder("My Bookings"));
        right.add(new JScrollPane(bookingList), BorderLayout.CENTER);

        JButton cancelBtn = new JButton("Cancel Booking");
        JPanel rightBtn = new JPanel();
        rightBtn.add(cancelBtn);
        right.add(rightBtn, BorderLayout.SOUTH);

        center.add(left);
        center.add(right);
        add(center, BorderLayout.CENTER);

        loadVenues();
        loadMyBookings();

        // BUTTON ACTIONS
        bookBtn.addActionListener(e -> bookSelected());
        cancelBtn.addActionListener(e -> cancelSelectedBooking());
    }


    private void loadVenues() {
        venueModel.clear();
        List<Venue> venues = DataStore.getInstance().getAllVenues();
        venues.forEach(venueModel::addElement);
    }

    private void loadMyBookings() {
        bookingModel.clear();
        for(Booking b : DataStore.getInstance().getBookingsForUser(user.getId())) {
            bookingModel.addElement("B#" + b.getId() +
                    " | Venue:" + b.getVenueId() +
                    " | " + b.getDate() +
                    " | " + b.getStatus());
        }
    }

    private void bookSelected() {
        Venue v = venueList.getSelectedValue();
        if (v == null) {
            JOptionPane.showMessageDialog(this,"Select a venue first");
            return;
        }

        String date = JOptionPane.showInputDialog(this,"Enter Date (YYYY-MM-DD):");
        if(date == null || date.trim().isEmpty()) return;

        try {
            DataStore.getInstance().createBooking(v.getId(), user.getId(), date.trim());
            JOptionPane.showMessageDialog(this,"Booking Confirmed");
            loadMyBookings();
        } catch(Exception ex) {
            JOptionPane.showMessageDialog(this,"Booking Failed:\n"+ex.getMessage());
        }
    }

    private void cancelSelectedBooking() {
        String sel = bookingList.getSelectedValue();
        if(sel == null){
            JOptionPane.showMessageDialog(this,"Select booking first");
            return;
        }

        int id = Integer.parseInt(sel.split("#")[1].split("\\|")[0].trim());
        boolean ok = DataStore.getInstance().cancelBooking(id, user.getId(), false);

        if(ok){
            JOptionPane.showMessageDialog(this,"Booking Cancelled");
            loadMyBookings();
        } else {
            JOptionPane.showMessageDialog(this,"Cannot cancel booking");
        }
    }
}
