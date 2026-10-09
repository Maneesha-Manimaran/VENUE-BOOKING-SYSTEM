package com.venuebooking.ui;

import com.venuebooking.store.DataStore;
import javax.swing.*;

public class RegisterFrame extends JFrame {

    JTextField username;
    JPasswordField password, confirm;

    public RegisterFrame() {
        setTitle("User Registration");
        setSize(350,260);
        setLayout(null);
        setLocationRelativeTo(null);

        JLabel u = new JLabel("Username:");
        u.setBounds(30,30,80,25); add(u);

        username = new JTextField();
        username.setBounds(130,30,160,25); add(username);

        JLabel p = new JLabel("Password:");
        p.setBounds(30,70,80,25); add(p);

        password = new JPasswordField();
        password.setBounds(130,70,160,25); add(password);

        JLabel c = new JLabel("Confirm:");
        c.setBounds(30,110,80,25); add(c);

        confirm = new JPasswordField();
        confirm.setBounds(130,110,160,25); add(confirm);

        JButton reg = new JButton("Register");
        reg.setBounds(120,160,100,30); add(reg);

        reg.addActionListener(e -> registerUser());
    }

    private void registerUser() {
        String user = username.getText().trim();
        String pass = String.valueOf(password.getPassword());
        String conf = String.valueOf(confirm.getPassword());

        if (user.isEmpty() || pass.isEmpty()){
            JOptionPane.showMessageDialog(this, "Fields cannot be empty");
            return;
        }
        if (!pass.equals(conf)){
            JOptionPane.showMessageDialog(this, "Passwords do not match");
            return;
        }

        if(DataStore.getInstance().registerNewUser(user,pass)){
            JOptionPane.showMessageDialog(this,"Registration Successful");
            dispose();
        } else {
            JOptionPane.showMessageDialog(this,"Username already exists");
        }
    }
}
