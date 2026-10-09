package com.venuebooking.ui;

import javax.swing.*;
import com.venuebooking.model.User;
import com.venuebooking.store.DataStore;

public class LoginFrame extends JFrame {

    JTextField userField;
    JPasswordField passField;

    public LoginFrame() {
        setTitle("Venue Booking System - Login");
        setSize(380,260);
        setLayout(null);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel title = new JLabel("Venue Booking System");
        title.setBounds(100,10,200,25);
        add(title);

        JLabel u = new JLabel("Username:");
        u.setBounds(40,60,100,25);
        add(u);

        userField = new JTextField();
        userField.setBounds(140,60,180,25);
        add(userField);

        JLabel p = new JLabel("Password:");
        p.setBounds(40,100,100,25);
        add(p);

        passField = new JPasswordField();
        passField.setBounds(140,100,180,25);
        add(passField);

        JButton loginBtn = new JButton("Login");
        loginBtn.setBounds(80,150,100,30);
        add(loginBtn);

        JButton exitBtn = new JButton("Exit");
        exitBtn.setBounds(200,150,100,30);
        add(exitBtn);

        // 🔥 Register Button - NOW ADDED PROPERLY
        JButton regBtn = new JButton("Register");
        regBtn.setBounds(140,190,100,30);
        add(regBtn);

        // LOGIN ACTION
        loginBtn.addActionListener(e -> loginUser());

        // EXIT
        exitBtn.addActionListener(e -> System.exit(0));

        // OPEN REGISTER PAGE
        regBtn.addActionListener(e -> new RegisterFrame().setVisible(true));
    }

    private void loginUser(){
        String u = userField.getText().trim();
        String p = String.valueOf(passField.getPassword()).trim();

        User found = DataStore.getInstance().login(u,p);
        if(found == null){
            JOptionPane.showMessageDialog(this,"Invalid Login!");
            return;
        }

        if(found.isAdmin())
            new AdminFrame(found).setVisible(true);
        else
            new UserFrame(found).setVisible(true);

        dispose(); // close login window
    }
}
