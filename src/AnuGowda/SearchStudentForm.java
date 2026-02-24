package AnuGowda;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class SearchStudentForm extends JDialog {

    private JTextField txtId = new JTextField(10);
    private JTextField txtName = new JTextField(20);
    private JTextField txtEmail = new JTextField(20);
    private JTextField txtCourse = new JTextField(20);

    private JButton btnSearch = new JButton("Search");
    private JButton btnClose = new JButton("Close");

    public SearchStudentForm() {
        setTitle("Search Student");
        setSize(400, 300);
        setModal(true);
        setLocationRelativeTo(null);

        txtName.setEditable(false);
        txtEmail.setEditable(false);
        txtCourse.setEditable(false);

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
        gbc.gridx = 1; panel.add(btnClose, gbc);

        add(panel);

        btnSearch.addActionListener(e -> searchStudent());
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
            } else {
                JOptionPane.showMessageDialog(this, "Student not found.");
                txtName.setText("");
                txtEmail.setText("");
                txtCourse.setText("");
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }
}
