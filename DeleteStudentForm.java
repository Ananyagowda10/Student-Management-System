package AnuGowda;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class DeleteStudentForm extends JDialog {

    private JTextField txtId = new JTextField(10);
    private JButton btnDelete = new JButton("Delete");
    private JButton btnClose = new JButton("Close");

    public DeleteStudentForm() {
        setTitle("Delete Student");
        setModal(true);
        setupUI();
        setVisible(true);
    }

    private void setupUI() {

       
        JPanel header = new JPanel();
        JLabel title = new JLabel("Delete Student");
        title.setFont(new Font("Arial", Font.BOLD, 20));
        header.add(title);

        
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;

        txtId.setPreferredSize(new Dimension(120, 28)); // small size

 
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Student ID:"), gbc);

        gbc.gridx = 1;
        panel.add(txtId, gbc);

       
        JPanel btnPanel = new JPanel();
        btnPanel.add(btnDelete);
        btnPanel.add(btnClose);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(btnPanel, gbc);

       
        setLayout(new BorderLayout());
        add(header, BorderLayout.NORTH);
        add(panel, BorderLayout.CENTER);

        setSize(320, 220);
        setLocationRelativeTo(null);

        
        btnDelete.addActionListener(e -> deleteStudent());
        btnClose.addActionListener(e -> dispose());
    }

    private void deleteStudent() {
        String idText = txtId.getText().trim();

        if (!idText.matches("\\d+")) {
            JOptionPane.showMessageDialog(this, "ID must be a number!");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to delete?",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION
        );

        if (confirm != JOptionPane.YES_OPTION) return;

        int id = Integer.parseInt(idText);

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement("DELETE FROM student6 WHERE id=?")) {

            pst.setInt(1, id);

            int rows = pst.executeUpdate();

            if (rows > 0) {
                JOptionPane.showMessageDialog(this, "Student deleted successfully!");
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Student not found.");
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }
}
