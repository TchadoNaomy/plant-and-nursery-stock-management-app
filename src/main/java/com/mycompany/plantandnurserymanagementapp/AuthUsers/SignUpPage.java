/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.plantandnurserymanagementapp.AuthUsers;

/**
 *
 * @author Kapnang
 */
import com.mycompany.plantandnurserymanagementapp.Database.DBConnection;
import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SignUpPage extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JTextField emailField;
    private JComboBox<String> roleComboBox; // Combo box for role
    private JButton registerButton;
    private JButton cancelButton;

    public SignUpPage() {
        setTitle("Registration");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        
        // size of the Login form
        setPreferredSize(new Dimension(600, 200));

        // Input Panel
        JPanel inputPanel = new JPanel(new GridLayout(4, 2, 5, 5)); // Added row for role
        JLabel usernameLabel = new JLabel("Username:");
        JLabel passwordLabel = new JLabel("Password:");
        JLabel emailLabel = new JLabel("Email:");
        JLabel roleLabel = new JLabel("Role:"); // Label for role

        usernameField = new JTextField(20);
        passwordField = new JPasswordField(20);
        emailField = new JTextField(20);

        // Role Combo Box
        roleComboBox = new JComboBox<>(new String[]{"manager", "supplier"}); // Options
        roleComboBox.setSelectedIndex(0); // Set default selection

        inputPanel.add(usernameLabel);
        inputPanel.add(usernameField);
        inputPanel.add(emailLabel);
        inputPanel.add(emailField);
        inputPanel.add(passwordLabel);
        inputPanel.add(passwordField);
        inputPanel.add(roleLabel); // Add role label
        inputPanel.add(roleComboBox); // Add role combo box

        // Button Panel
        JPanel buttonPanel = new JPanel(new FlowLayout());
        registerButton = new JButton("Register");
        cancelButton = new JButton("Cancel");

        buttonPanel.add(registerButton);
        buttonPanel.add(cancelButton);

        // Add panels to the frame
        add(inputPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        // Action Listeners
        registerButton.addActionListener(e -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            String email = emailField.getText();
            String role = (String) roleComboBox.getSelectedItem(); // Get selected role

            // Input Validation (Add more robust validation as needed)
            if (username.isEmpty() || password.isEmpty() || email.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill in all fields.");
                return;
            }

            // Password complexity check (example)
            if (password.length() < 8) {
                JOptionPane.showMessageDialog(this, "Password must be at least 8 characters long.");
                return;
            }

            // Email validation (example)
            if (!email.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
                JOptionPane.showMessageDialog(this, "Invalid email format.");
                return;
            }

            try (Connection conn = DBConnection.getConnection()) {
                String sql = "INSERT INTO users (username, password, email, role) VALUES (?, ?, ?, ?)";
                try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                    stmt.setString(1, username);
                    stmt.setString(2, password); // In real app, hash password before storing/comparing
                    stmt.setString(3, email);
                    stmt.setString(4, role); // Insert selected role

                    stmt.executeUpdate();
                    JOptionPane.showMessageDialog(this, "Registration successful.");
                    dispose();
                }
            } catch (SQLException ex) {
                ex.printStackTrace(); // REMOVE IN PRODUCTION
                JOptionPane.showMessageDialog(this, "Registration failed: " + ex.getMessage());
            }
        });

        cancelButton.addActionListener(e -> dispose());

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new SignUpPage().setVisible(true));
    }
}