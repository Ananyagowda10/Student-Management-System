package AnuGowda;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class ViewStudentsForm extends JDialog {

    private JTable table;
    private DefaultTableModel model;

    public ViewStudentsForm() {
        setTitle("All Students");
        setSize(600, 400);
        setModal(true);
        setLocationRelativeTo(null);

        model = new DefaultTableModel(new Object[]{"ID", "Name", "Email", "Course"}, 0);
        table = new JTable(model);

        add(new JScrollPane(table));

        loadStudents();

        setVisible(true);
    }

    private void loadStudents() {
        model.setRowCount(0);

        String sql = "SELECT * FROM student6 ORDER BY id";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("course")
                });
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }
}
