package com.venuebooking;

import javax.swing.SwingUtilities;
import com.venuebooking.ui.LoginFrame;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new LoginFrame().setVisible(true);
        });
    }
}
