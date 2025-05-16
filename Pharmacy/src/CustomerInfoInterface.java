import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

class CustomerInfoInterface {
    // Database connection variables
    private static final String DB_URL = "jdbc:mysql://localhost:3306/your_database_name";
    private static final String USER = "your_username";
    private static final String PASSWORD = "your_password";

    // Main method
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new CustomerInfoInterface().createAndShowGUI());
    }

    public void createAndShowGUI() {
        // Frame setup
        JFrame frame = new JFrame("Customer Information Management");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);

        // Panel setup
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(7, 2, 10, 10));

        // Input fields
        JLabel lblOrderId = new JLabel("Order ID:");
        JTextField txtOrderId = new JTextField();

        JLabel lblCustomerId = new JLabel("Customer ID:");
        JTextField txtCustomerId = new JTextField();

        JLabel lblName = new JLabel("Name:");
        JTextField txtName = new JTextField();

        JLabel lblAddress = new JLabel("Address:");
        JTextField txtAddress = new JTextField();

        JLabel lblPhoneNo = new JLabel("Phone No:");
        JTextField txtPhoneNo = new JTextField();

        JLabel lblPhysicianId = new JLabel("Physician ID:");
        JTextField txtPhysicianId = new JTextField();

        // Buttons
        JButton btnAdd = new JButton("Add");
        JButton btnUpdate = new JButton("Update");
        JButton btnDelete = new JButton("Delete");
        JButton btnView = new JButton("View");

        // Adding components to the panel
        panel.add(lblOrderId);
        panel.add(txtOrderId);
        panel.add(lblCustomerId);
        panel.add(txtCustomerId);
        panel.add(lblName);
        panel.add(txtName);
        panel.add(lblAddress);
        panel.add(txtAddress);
        panel.add(lblPhoneNo);
        panel.add(txtPhoneNo);
        panel.add(lblPhysicianId);
        panel.add(txtPhysicianId);
        panel.add(btnAdd);
        panel.add(btnUpdate);
        panel.add(btnDelete);
        panel.add(btnView);

        frame.add(panel);
        frame.setVisible(true);

        // Button actions
        btnAdd.addActionListener(e -> handleAdd(txtOrderId, txtCustomerId, txtName, txtAddress, txtPhoneNo, txtPhysicianId));
        btnUpdate.addActionListener(e -> handleUpdate(txtOrderId, txtCustomerId, txtName, txtAddress, txtPhoneNo, txtPhysicianId));
        btnDelete.addActionListener(e -> handleDelete(txtCustomerId));
        btnView.addActionListener(e -> handleView());
    }

    private void handleAdd(JTextField txtOrderId, JTextField txtCustomerId, JTextField txtName, JTextField txtAddress, JTextField txtPhoneNo, JTextField txtPhysicianId) {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement("INSERT INTO customer_informations (order_id, customer_id, name, address, phone_no, physician_id) VALUES (?, ?, ?, ?, ?, ?)");) {
            stmt.setInt(1, Integer.parseInt(txtOrderId.getText()));
            stmt.setInt(2, Integer.parseInt(txtCustomerId.getText()));
            stmt.setString(3, txtName.getText());
            stmt.setString(4, txtAddress.getText());
            stmt.setString(5, txtPhoneNo.getText());
            stmt.setInt(6, Integer.parseInt(txtPhysicianId.getText()));
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Record added successfully!");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
        }
    }

    private void handleUpdate(JTextField txtOrderId, JTextField txtCustomerId, JTextField txtName, JTextField txtAddress, JTextField txtPhoneNo, JTextField txtPhysicianId) {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement("UPDATE customer_informations SET order_id = ?, name = ?, address = ?, phone_no = ?, physician_id = ? WHERE customer_id = ?");) {
            stmt.setInt(1, Integer.parseInt(txtOrderId.getText()));
            stmt.setString(2, txtName.getText());
            stmt.setString(3, txtAddress.getText());
            stmt.setString(4, txtPhoneNo.getText());
            stmt.setInt(5, Integer.parseInt(txtPhysicianId.getText()));
            stmt.setInt(6, Integer.parseInt(txtCustomerId.getText()));
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Record updated successfully!");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
        }
    }

    private void handleDelete(JTextField txtCustomerId) {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement("DELETE FROM customer_informations WHERE customer_id = ?");) {
            stmt.setInt(1, Integer.parseInt(txtCustomerId.getText()));
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Record deleted successfully!");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
        }
    }

    private void handleView() {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM customer_informations");) {
            StringBuilder result = new StringBuilder("Customer Information:\n");
            while (rs.next()) {
                result.append("Order ID: ").append(rs.getInt("order_id"))
                        .append(", Customer ID: ").append(rs.getInt("customer_id"))
                        .append(", Name: ").append(rs.getString("name"))
                        .append(", Address: ").append(rs.getString("address"))
                        .append(", Phone No: ").append(rs.getString("phone_no"))
                        .append(", Physician ID: ").append(rs.getInt("physician_id"))
                        .append("\n");
            }
            JOptionPane.showMessageDialog(null, result.toString());
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
        }
    }
}
