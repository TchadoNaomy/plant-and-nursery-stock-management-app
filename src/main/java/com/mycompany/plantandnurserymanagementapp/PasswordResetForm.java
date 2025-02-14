/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.plantandnurserymanagementapp;

/**
 *
 * @author Kapnang
 */
import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.util.UUID; // For generating reset tokens

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.UUID;

public class PasswordResetForm extends JFrame {
    private JTextField emailField;
    private JButton resetButton;
    private JButton cancelButton; // Added cancel button

    public PasswordResetForm() {
        setTitle("Password Reset");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Close only this window
        setLayout(new BorderLayout());

        // Input Panel
        JPanel inputPanel = new JPanel(new GridLayout(1, 2, 5, 5)); // 1 row, 2 cols
        JLabel emailLabel = new JLabel("Email:");

        emailField = new JTextField(20);

        inputPanel.add(emailLabel);
        inputPanel.add(emailField);

        // Button Panel
        JPanel buttonPanel = new JPanel(new FlowLayout());
        resetButton = new JButton("Reset Password");
        cancelButton = new JButton("Cancel");

        buttonPanel.add(resetButton);
        buttonPanel.add(cancelButton);

        // Add panels to the frame
        add(inputPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        // Action Listeners
        resetButton.addActionListener(e -> {
            String email = emailField.getText();

            // Basic email validation (you can improve this)
            if (email.isEmpty() || !email.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
                JOptionPane.showMessageDialog(this, "Please enter a valid email address.");
                return;
            }

            try (Connection conn = DBConnection.getConnection()) {
                // 1. Check if email exists
                String checkSql = "SELECT uid FROM users WHERE email = ?";
                try (PreparedStatement checkStmt = conn.prepareStatement(checkSql)) {
                    checkStmt.setString(1, email);
                    try (ResultSet rs = checkStmt.executeQuery()) {
                        if (rs.next()) {
                            int userId = rs.getInt("uid");

                            // 2. Generate a unique reset token
                            String resetToken = UUID.randomUUID().toString();

                            // 3. Store the token in the database (and email it to the user)
                            String updateSql = "UPDATE users SET reset_token = ? WHERE uid = ?";
                            try (PreparedStatement updateStmt = conn.prepareStatement(updateSql)) {
                                updateStmt.setString(1, resetToken);
                                updateStmt.setInt(2, userId);
                                updateStmt.executeUpdate();

                                // 4. Email reset link to user (Placeholder - Replace with actual email sending)
                                String resetLink = "http://yourdomain.com/resetpassword?token=" + resetToken; // Replace with your actual domain/page
                                JOptionPane.showMessageDialog(this,
                                        "A password reset link has been sent to your email address:\n" + resetLink); // For testing only

                                dispose(); // Close reset form
                            }
                        } else {
                            JOptionPane.showMessageDialog(this, "Email address not found.");
                        }
                    }
                }
            } catch (SQLException ex) {
                ex.printStackTrace(); // REMOVE IN PRODUCTION
                JOptionPane.showMessageDialog(this, "Database Error: " + ex.getMessage());
            }
        });

        cancelButton.addActionListener(e -> dispose()); // Close window on cancel

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new PasswordResetForm().setVisible(true));
    }
}