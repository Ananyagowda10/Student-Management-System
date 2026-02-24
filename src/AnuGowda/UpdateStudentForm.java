package AnuGowda;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class UpdateStudentForm extends JDialog {

    private JTextField txtId = new JTextField(10);
    private JTextField txtName = new JTextField(20);
    private JTextField txtEmail = new JTextField(20);
    private JTextField txtCourse = new JTextField(20);

    private JButton btnSearch = new JButton("Search");
    private JButton btnUpdate = new JButton("Update");
    private JButton btnClose = new JButton("Close");

    public UpdateStudentForm() {
        setTitle("Update Student");
        setSize(400, 320);
        setModal(true);
        setLocationRelativeTo(null);

        txtName.setEditable(false);
        txtEmail.setEditable(false);
        txtCourse.setEditable(false);
        btnUpdate.setEnabled(false);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);

        gbc.gridx = 0; gbc.gridy = 0; panel.add(new JLabel("Student ID:"), gbc);
        gbc.gridx = 1; panel.add(txtId, gbc);

        gbc.gridx = 0; gbc.gridy = 1; panel.add(new JLabel("Name:"), gbc);
        gbc.gridx = 1; panel.add(txtName, gbc);

        gbc.gridx = 0; gbc.gridy = 2; panel.add(new JLabel("Email:"), gbc);
        gbc.gridx = 1; panel.add(txtEmail, gbc);

        gbc.gridx = 0; gbc.gridy = 3; panel.add(new JLabel("Course:"), gbc);
        gbc.gridx = 1; panel.add(txtCourse, gbc);

        gbc.gridx = 0; gbc.gridy = 4; panel.add(btnSearch, gbc);
        gbc.gridx = 1; panel.add(btnUpdate, gbc);

        gbc.gridx = 1; gbc.gridy = 5; panel.add(btnClose, gbc);

        add(panel);

        btnSearch.addActionListener(e -> searchStudent());
        btnUpdate.addActionListener(e -> updateStudent());
        btnClose.addActionListener(e -> dispose());

        setVisible(true);
    }

    private void searchStudent() {
        String idText = txtId.getText().trim();

        if (!idText.matches("\\d+")) {
            JOptionPane.showMessageDialog(this, "ID must be a number!");
            return;
        }

        int id = Integer.parseInt(idText);

        String sql = "SELECT * FROM student6 WHERE id=?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setInt(1, id);

            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                txtName.setText(rs.getString("name"));
                txtEmail.setText(rs.getString("email"));
                txtCourse.setText(rs.getString("course"));

                txtName.setEditable(true);
                txtEmail.setEditable(true);
                txtCourse.setEditable(true);
                btnUpdate.setEnabled(true);
            } else {
                JOptionPane.showMessageDialog(this, "Student not found.");
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    private void updateStudent() {
        String name = txtName.getText().trim();
        String email = txtEmail.getText().trim();
        String course = txtCourse.getText().trim();

        if (!name.matches("[a-zA-Z ]+")) {
            JOptionPane.showMessageDialog(this, "Name must contain only letters.");
            return;
        }

        if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            JOptionPane.showMessageDialog(this, "Invalid email format.");
            return;
        }

        if (!course.matches("[a-zA-Z ]+")) {
            JOptionPane.showMessageDialog(this, "Course must contain only letters.");
            return;
        }

        String sql = "UPDATE student6 SET name=?, email=?, course=? WHERE id=?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setString(1, name);
            pst.setString(2, email);
            pst.setString(3, course);
            pst.setInt(4, Integer.parseInt(txtId.getText()));

            pst.executeUpdate();

            JOptionPane.showMessageDialog(this, "Student updated successfully!");
            dispose();

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }
}
