package ru.itis;

import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Chat chatWindow = new Chat();
            chatWindow.setVisible(true);
        });
    }
}