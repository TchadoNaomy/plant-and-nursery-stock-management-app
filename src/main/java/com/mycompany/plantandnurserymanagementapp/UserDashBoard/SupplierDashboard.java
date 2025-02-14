/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.plantandnurserymanagementapp;

import javax.swing.JFrame;

/**
 *
 * @author Kapnang
 */
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SupplierDashboard extends JFrame {

    private int userId;
    private JTable ordersTable;
    private DefaultTableModel ordersTableModel;
    private JButton approveButton;
    private JButton rejectButton;

    public SupplierDashboard(int userId) {
        this.userId = userId;
        setTitle("Supplier Dashboard");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Or DISPOSE_ON_CLOSE if you don't want to exit the app

        // Table Model for Orders
        ordersTableModel = new DefaultTableModel(
                new Object[]{"Order ID", "Warehouse Manager ID", "Product ID", "Quantity", "Order Date", "Status"}, 0);
        ordersTable = new JTable(ordersTableModel);
        JScrollPane ordersScrollPane = new JScrollPane(ordersTable);

        // Buttons
        approveButton = new JButton("Approve");
        rejectButton = new JButton("Reject");

        // Button Actions
        approveButton.addActionListener(e -> approveOrder());
        rejectButton.addActionListener(e -> rejectOrder());


        // Layout (BorderLayout)
        setLayout(new BorderLayout());
        add(ordersScrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout()); // Buttons at the bottom
        buttonPanel.add(approveButton);
        buttonPanel.add(rejectButton);
        add(buttonPanel, BorderLayout.SOUTH);

        loadOrders(); // Load orders on startup

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void loadOrders() {
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT * FROM oders WHERE supplier_id = ?"; // Orders for this supplier
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, userId);
                try (ResultSet rs = stmt.executeQuery()) {
                    ordersTableModel.setRowCount(0); // Clear existing data
                    while (rs.next()) {
                        ordersTableModel.addRow(new Object[]{
                                rs.getInt("oid"),
                                rs.getInt("warehouse_manager_id"),
                                rs.getInt("product_id"),
                                rs.getInt("quantity_requested"),
                                rs.getString("order_date"),
                                rs.getString("order_status")
                        });
                    }
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace(); // REMOVE IN PRODUCTION
            JOptionPane.showMessageDialog(this, "Database Error: " + ex.getMessage());
        }
    }

    private void approveOrder() {
        int selectedRow = ordersTable.getSelectedRow();
        if (selectedRow != -1) {
            int orderId = (int) ordersTableModel.getValueAt(selectedRow, 0);
            updateOrderStatus(orderId, "approved");
        } else {
            JOptionPane.showMessageDialog(this, "No order selected.");
        }
    }

    private void rejectOrder() {
        int selectedRow = ordersTable.getSelectedRow();
        if (selectedRow != -1) {
            int orderId = (int) ordersTableModel.getValueAt(selectedRow, 0);
            updateOrderStatus(orderId, "rejected");
        } else {
            JOptionPane.showMessageDialog(this, "No order selected.");
        }
    }


    private void updateOrderStatus(int orderId, String status) {
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "UPDATE oders SET order_status = ? WHERE oid = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, status);
                stmt.setInt(2, orderId);
                stmt.executeUpdate();
                loadOrders(); // Refresh the table
                JOptionPane.showMessageDialog(this, "Order " + status + ".");
            }
        } catch (SQLException ex) {
            ex.printStackTrace(); // REMOVE IN PRODUCTION
            JOptionPane.showMessageDialog(this, "Database Error: " + ex.getMessage());
        }
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new SupplierDashboard(1).setVisible(true)); // Replace 1 with actual user ID
    }
}