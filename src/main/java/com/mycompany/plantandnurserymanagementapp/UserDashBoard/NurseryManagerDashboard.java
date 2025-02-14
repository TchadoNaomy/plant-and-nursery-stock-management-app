/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.plantandnurserymanagementapp.UserDashBoard;

/**
 *
 * @author Kapnang
 */
import com.mycompany.plantandnurserymanagementapp.Database.DBConnection;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class NurseryManagerDashboard extends JFrame {
    private int userId;
    private JTable plantsTable;
    private DefaultTableModel plantsTableModel;
    private JButton addPlantButton;
    private JButton updatePlantButton;
    private JButton deletePlantButton;
    private JTextField nameField;
    private JTextField descriptionField;
    private JTextField categoryField;
    private JTextField quantityField;
    private JTextField priceField;
    private JComboBox<String> supplierComboBox;
    private JTextArea orderArea;
    private JButton sendOrderRequestButton;
    private JButton viewApprovedOrdersButton;

    public NurseryManagerDashboard(int userId) {
        this.userId = userId;
        setTitle("Nursery Manager Dashboard");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Table Model for Plants
        plantsTableModel = new DefaultTableModel(
                new Object[]{"ID", "Name", "Description", "Category", "Quantity", "Price", "Supplier", "Date Added"}, 0);
        plantsTable = new JTable(plantsTableModel);
        JScrollPane plantsScrollPane = new JScrollPane(plantsTable); // Scroll pane for table

        // Input Fields
        nameField = new JTextField(20);
        descriptionField = new JTextField(20);
        categoryField = new JTextField(20);
        quantityField = new JTextField(10);
        priceField = new JTextField(10);

        // Combo Box for Suppliers
        supplierComboBox = new JComboBox<>();
        loadSuppliers();

        // Buttons
        addPlantButton = new JButton("Add Plant");
        updatePlantButton = new JButton("Update Plant");
        deletePlantButton = new JButton("Delete Plant");
        sendOrderRequestButton = new JButton("Send Order Request");
        viewApprovedOrdersButton = new JButton("View Approved Orders");


        // Order Area
        orderArea = new JTextArea(5, 20);
        orderArea.setLineWrap(true);
        JScrollPane orderScrollPane = new JScrollPane(orderArea); // Scroll pane for order area


        // Layout (Use GridBagLayout for more flexibility)
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Add some padding

        // Plants Table
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 4; // Span across 4 columns
        gbc.weightx = 1.0;  // Take up horizontal space
        gbc.fill = GridBagConstraints.BOTH; // Fill both horizontally and vertically
        add(plantsScrollPane, gbc);

        gbc.gridwidth = 1; // Reset gridwidth

        // Input Fields and Labels
        gbc.gridx = 0; gbc.gridy = 1; add(new JLabel("Name:"), gbc);
        gbc.gridx = 1; gbc.gridy = 1; add(nameField, gbc);
        gbc.gridx = 2; gbc.gridy = 1; add(new JLabel("Category:"), gbc);
        gbc.gridx = 3; gbc.gridy = 1; add(categoryField, gbc);

        gbc.gridx = 0; gbc.gridy = 2; add(new JLabel("Description:"), gbc);
        gbc.gridx = 1; gbc.gridy = 2; add(descriptionField, gbc);
        gbc.gridx = 2; gbc.gridy = 2; add(new JLabel("Quantity:"), gbc);
        gbc.gridx = 3; gbc.gridy = 2; add(quantityField, gbc);

        gbc.gridx = 0; gbc.gridy = 3; add(new JLabel("Price:"), gbc);
        gbc.gridx = 1; gbc.gridy = 3; add(priceField, gbc);
        gbc.gridx = 2; gbc.gridy = 3; add(new JLabel("Supplier:"), gbc);
        gbc.gridx = 3; gbc.gridy = 3; add(supplierComboBox, gbc);

        // Order Area
        gbc.gridx = 0; gbc.gridy = 4;
        gbc.gridwidth = 4; // Span all columns
        gbc.fill = GridBagConstraints.HORIZONTAL; // Only fill horizontally
        add(new JLabel("Order Request:"), gbc);
        gbc.gridy = 5; add(orderScrollPane, gbc);

        gbc.gridwidth = 1; // Reset gridwidth

        // Buttons
        gbc.gridx = 0; gbc.gridy = 6; add(addPlantButton, gbc);
        gbc.gridx = 1; gbc.gridy = 6; add(updatePlantButton, gbc);
        gbc.gridx = 2; gbc.gridy = 6; add(deletePlantButton, gbc);
        gbc.gridx = 3; gbc.gridy = 6; add(sendOrderRequestButton, gbc);
        gbc.gridx = 0; gbc.gridy = 7; // New row for View Approved Orders
        gbc.gridwidth = 4; // Span all columns
        add(viewApprovedOrdersButton, gbc);


        // Load data and set up actions
        loadPlants();

        addPlantButton.addActionListener(e -> addPlant());
        updatePlantButton.addActionListener(e -> updatePlant());
        deletePlantButton.addActionListener(e -> deletePlant());
        sendOrderRequestButton.addActionListener(e -> sendOrderRequest());
        viewApprovedOrdersButton.addActionListener(e -> viewApprovedOrders());

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }



    private void loadPlants() {
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT p.pid, p.name, p.description, p.category, p.quantity_in_stock, p.price, p.name as supplier_name, p.date_added " +
                    "FROM plants p JOIN users u ON p.supplier_id = u.uid"; // Join with users for supplier name
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                try (ResultSet rs = stmt.executeQuery()) {
                    plantsTableModel.setRowCount(0); // Clear existing data
                    while (rs.next()) {
                        plantsTableModel.addRow(new Object[]{
                                rs.getInt("pid"),
                                rs.getString("name"),
                                rs.getString("description"),
                                rs.getString("category"),
                                rs.getInt("quantity_in_stock"),
                                rs.getDouble("price"),
                                rs.getString("supplier_name"), // Display supplier name
                                rs.getString("date_added")
                        });
                    }
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database Error: " + ex.getMessage());
        }
    }

    private void loadSuppliers() {
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT uid, username FROM users WHERE role = 'supplier'";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        supplierComboBox.addItem(rs.getString("username"));
                    }
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database Error: " + ex.getMessage());
        }
    }


   private void addPlant() {
        try {
            String name = nameField.getText();
            String description = descriptionField.getText();
            String category = categoryField.getText();
            int quantity = Integer.parseInt(quantityField.getText());
            double price = Double.parseDouble(priceField.getText());
            String supplierName = (String) supplierComboBox.getSelectedItem();

            int supplierId = getSupplierId(supplierName);
            if (supplierId == -1) {
                JOptionPane.showMessageDialog(this, "Supplier not found.");
                return;
            }

            try (Connection conn = DBConnection.getConnection()) {
                String sql = "INSERT INTO plants (name, description, category, quantity_in_stock, price, supplier_id, date_added) VALUES (?, ?, ?, ?, ?, ?, NOW())";
                try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                    stmt.setString(1, name);
                    stmt.setString(2, description);
                    stmt.setString(3, category);
                    stmt.setInt(4, quantity);
                    stmt.setDouble(5, price);
                    stmt.setInt(6, supplierId);

                    stmt.executeUpdate();
                    loadPlants(); // Refresh the table
                    clearPlantInputFields(); // Clear the input fields
                    JOptionPane.showMessageDialog(this, "Plant added successfully.");
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Database Error: " + ex.getMessage());
            }

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid input. Please enter numbers for quantity and price.");
        }
    }
   
   private void updatePlant() {
        int selectedRow = plantsTable.getSelectedRow();
        if (selectedRow != -1) {
            try {
                int plantId = (int) plantsTableModel.getValueAt(selectedRow, 0); // Get plant ID from the table
                String name = nameField.getText();
                String description = descriptionField.getText();
                String category = categoryField.getText();
                int quantity = Integer.parseInt(quantityField.getText());
                double price = Double.parseDouble(priceField.getText());
                String supplierName = (String) supplierComboBox.getSelectedItem();

                int supplierId = getSupplierId(supplierName);
                if (supplierId == -1) {
                    JOptionPane.showMessageDialog(this, "Supplier not found.");
                    return;
                }

                try (Connection conn = DBConnection.getConnection()) {
                    String sql = "UPDATE plants SET name = ?, description = ?, category = ?, quantity_in_stock = ?, price = ?, supplier_id = ? WHERE pid = ?";
                    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                        stmt.setString(1, name);
                        stmt.setString(2, description);
                        stmt.setString(3, category);
                        stmt.setInt(4, quantity);
                        stmt.setDouble(5, price);
                        stmt.setInt(6, supplierId);
                        stmt.setInt(7, plantId);

                        stmt.executeUpdate();
                        loadPlants(); // Refresh the table
                        clearPlantInputFields();
                        JOptionPane.showMessageDialog(this, "Plant updated successfully.");
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(this, "Database Error: " + ex.getMessage());
                }

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid input. Please enter numbers for quantity and price.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "No plant selected.");
        }
    }

    private void deletePlant() {
        int selectedRow = plantsTable.getSelectedRow();
        if (selectedRow != -1) {
            int plantId = (int) plantsTableModel.getValueAt(selectedRow, 0);
            int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this plant?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                try (Connection conn = DBConnection.getConnection()) {
                    String sql = "DELETE FROM plants WHERE pid = ?";
                    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                        stmt.setInt(1, plantId);
                        stmt.executeUpdate();
                        loadPlants(); // Refresh the table
                        JOptionPane.showMessageDialog(this, "Plant deleted successfully.");
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(this, "Database Error: " + ex.getMessage());
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "No plant selected.");
        }
    }

    private void sendOrderRequest() {
        int selectedRow = plantsTable.getSelectedRow();
        if (selectedRow != -1) {
            try {
                int productId = (int) plantsTableModel.getValueAt(selectedRow, 0);
                int quantityRequested = Integer.parseInt(orderArea.getText());

                String supplierName = (String) supplierComboBox.getSelectedItem();
                int supplierId = getSupplierId(supplierName);

                if (supplierId != -1) {
                    try (Connection conn = DBConnection.getConnection()) {
                        String sql = "INSERT INTO oders (warehouse_manager_id, supplier_id, product_id, quantity_requested, order_status, order_date) VALUES (?, ?, ?, ?, ?, ?)";
                        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                            stmt.setInt(1, userId);
                            stmt.setInt(2, supplierId);
                            stmt.setInt(3, productId);
                            stmt.setInt(4, quantityRequested);
                            stmt.setString(5, "pending");
                            stmt.setString(6, LocalDate.now().toString());

                            stmt.executeUpdate();
                            JOptionPane.showMessageDialog(this, "Order request sent.");
                            orderArea.setText("");
                        }
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(this, "Database Error: " + ex.getMessage());
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Supplier not found. Please select a valid supplier.");
                }

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid quantity requested.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "No plant selected.");
        }
    }

    private void viewApprovedOrders() {
        JFrame approvedOrdersFrame = new JFrame("Approved Orders");
        approvedOrdersFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        DefaultTableModel approvedOrdersTableModel = new DefaultTableModel(
                new Object[]{"Order ID", "Supplier ID", "Product ID", "Quantity", "Order Date", "Status"}, 0);
        JTable approvedOrdersTable = new JTable(approvedOrdersTableModel);

        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT * FROM oders WHERE warehouse_manager_id = ? AND order_status = 'approved'";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, userId);
                try (ResultSet rs = stmt.executeQuery()) {
                    approvedOrdersTableModel.setRowCount(0);
                    while (rs.next()) {
                        approvedOrdersTableModel.addRow(new Object[]{
                                rs.getInt("oid"),
                                rs.getInt("supplier_id"),
                                rs.getInt("product_id"),
                                rs.getInt("quantity_requested"),
                                rs.getString("order_date"),
                                rs.getString("order_status")
                        });
                    }
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database Error: " + ex.getMessage());
        }

        approvedOrdersFrame.add(new JScrollPane(approvedOrdersTable));
        approvedOrdersFrame.pack();
        approvedOrdersFrame.setLocationRelativeTo(null);
        approvedOrdersFrame.setVisible(true);
    }

    private int getSupplierId(String supplierName) {
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT uid FROM users WHERE username = ?"; // Or id if it's an integer
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, supplierName);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        return rs.getInt("uid"); // Or rs.getInt("id")
                    } else {
                        return -1; // Return -1 if not found
                    }
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database Error: " + ex.getMessage());
            return -1; // Return -1 in case of exception
        }
    }

    private void clearPlantInputFields() {
        nameField.setText("");
        descriptionField.setText("");
        categoryField.setText("");
        quantityField.setText("");
        priceField.setText("");
        supplierComboBox.setSelectedIndex(0); // Reset to the first item
    }
}