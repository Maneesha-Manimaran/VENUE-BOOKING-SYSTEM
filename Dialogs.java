package com.venuebooking.ui;

import javax.swing.*;

public class Dialogs {
    public static void info(String msg) {
        JOptionPane.showMessageDialog(null, msg);
    }
    public static void error(String msg) {
        JOptionPane.showMessageDialog(null, msg, "Error", JOptionPane.ERROR_MESSAGE);
    }
}
