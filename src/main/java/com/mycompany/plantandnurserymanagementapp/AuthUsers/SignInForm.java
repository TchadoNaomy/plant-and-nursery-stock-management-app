/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.plantandnurserymanagementapp.AuthUsers;

/**
 *
 * @author Kapnang
 */
import com.mycompany.plantandnurserymanagementapp.DBConnection;
import com.mycompany.plantandnurserymanagementapp.NurseryManagerDashboard;
import com.mycompany.plantandnurserymanagementapp.PasswordResetForm;
import com.mycompany.plantandnurserymanagementapp.SupplierDashboard;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class SignInForm extends JFrame {

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton forgotPasswordButton;
    private JButton registerButton; // Add register button

    public SignInForm() {
        setTitle("Sign In");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        
         setPreferredSize(new Dimension(400, 200));

        // Input Panel
        JPanel inputPanel = new JPanel(new GridLayout(3, 2, 5, 5)); // Include space for register button if needed
        JLabel usernameLabel = new JLabel("Username:");
        JLabel passwordLabel = new JLabel("Password:");

        usernameField = new JTextField(20);
        passwordField = new JPasswordField(20);

        inputPanel.add(usernameLabel);
        inputPanel.add(usernameField);
        inputPanel.add(passwordLabel);
        inputPanel.add(passwordField);


        // Button Panel
        JPanel buttonPanel = new JPanel(new FlowLayout());
        loginButton = new JButton("Login");
        forgotPasswordButton = new JButton("Forgot Password?");
        registerButton = new JButton("Register"); // Initialize register button

        buttonPanel.add(loginButton);
        buttonPanel.add(forgotPasswordButton);
        buttonPanel.add(registerButton); // Add register button to the panel


        // Add panels to the frame
        add(inputPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        // Action Listeners
        forgotPasswordButton.addActionListener(e -> showPasswordResetForm());

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                if (username.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(SignInForm.this, "Please enter both username and password.");
                    return; // Don't proceed if fields are empty
                }
                try (Connection conn = DBConnection.getConnection()) {
                    String sql = "SELECT uid, role FROM users WHERE username = ? AND password = ?"; // Check password
                    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                        stmt.setString(1, username);
                        stmt.setString(2, password); // In real app, hash password before storing/comparing
                        try (ResultSet rs = stmt.executeQuery()) {
                            if (rs.next()) {
                                int userId = rs.getInt("uid");
                                String role = rs.getString("role");
                                if (role.equals("manager")) {
                                    new NurseryManagerDashboard(userId).setVisible(true);
                                } else if (role.equals("supplier")) {
                                    new SupplierDashboard(userId).setVisible(true);
                                }
                                dispose(); // Close login form
                            } else {
                                JOptionPane.showMessageDialog(SignInForm.this, "Invalid username or password.");
                            }
                        }
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace(); // REMOVE IN PRODUCTION
                    JOptionPane.showMessageDialog(SignInForm.this, "Database Error: " + ex.getMessage()); // User-friendly message
                }
            }
        });

        registerButton.addActionListener(e -> new SignUpPage().setVisible(true)); // Open SignUpPage


        pack(); // Adjusts window size to fit components
        setLocationRelativeTo(null); // Centers the window
        setVisible(true);
    }

    private void showPasswordResetForm() {
        new PasswordResetForm().setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new SignInForm().setVisible(true));
    }
}