package ru.itis;

import javax.swing.*;
import java.awt.BorderLayout;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class Chat extends JFrame {
    private static final String WINDOW_TITLE = "CHAT WITH churkas";
    private static final SimpleDateFormat TIME_FORMAT = new SimpleDateFormat("HH:mm");
    private static final List<String> DEFAULT_USERS = Arrays.asList("admin", "xuesos");

    private JComboBox<String> authorPicker;
    private JTextArea logArea;
    private JButton sendBtn;
    private JTextArea inputArea;
    private JLabel authorLabel;

    public Chat() {
        initFrame();
        initComponents();
        buildUi();
        wireActions();
    }

    private void initFrame() {
        setTitle(WINDOW_TITLE);
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void initComponents() {
        authorPicker = new JComboBox<>(DEFAULT_USERS.toArray(String[]::new));
        authorLabel = new JLabel("Кто пишет:");

        sendBtn = new JButton("Отправить");

        inputArea = new JTextArea();
        inputArea.setRows(2);

        logArea = new JTextArea();
        logArea.setEditable(false);
    }

    private void buildUi() {
        JPanel topPanel = new JPanel();
        topPanel.add(authorLabel);
        topPanel.add(authorPicker);
        add(topPanel, BorderLayout.NORTH);

        add(new JScrollPane(logArea), BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(new JScrollPane(inputArea), BorderLayout.CENTER);
        bottomPanel.add(sendBtn, BorderLayout.EAST);

        add(bottomPanel, BorderLayout.SOUTH);
    }

    private void wireActions() {
        sendBtn.addActionListener(e -> {
            appendMessage();
            inputArea.setText("");
        });
    }

    private void appendMessage() {
        String timestamp = TIME_FORMAT.format(new Date());
        String author = (String) authorPicker.getSelectedItem();
        String text = inputArea.getText();

        logArea.append(String.format("%s %s: %s%n", timestamp, author, text));
    }
}
