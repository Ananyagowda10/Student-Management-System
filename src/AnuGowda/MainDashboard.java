package AnuGowda;

import javax.swing.*;
import java.awt.*;

public class MainDashboard extends JFrame {

    private JButton btnAdd = new JButton("➕ Add Student");
    private JButton btnSearch = new JButton("🔍 Search Student");
    private JButton btnUpdate = new JButton("✏ Update Student");
    private JButton btnDelete = new JButton("🗑 Delete Student");
    private JButton btnView = new JButton("📄 View All Students");
    private JButton btnLogout = new JButton("🚪 Logout");

 
    public MainDashboard() {
        setTitle("Student Management Dashboard");
        setSize(450, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(6, 1, 12, 12));

        panel.add(btnAdd);
        panel.add(btnSearch);
        panel.add(btnUpdate);
        panel.add(btnDelete);
        panel.add(btnView);
        panel.add(btnLogout);

        add(panel);
        setVisible(true);

       
        btnAdd.addActionListener(e -> new AddStudentForm());
        btnSearch.addActionListener(e -> new SearchStudentForm());
        btnUpdate.addActionListener(e -> new UpdateStudentForm());
        btnDelete.addActionListener(e -> new DeleteStudentForm());
        btnView.addActionListener(e -> new ViewStudentsForm());

       
        btnLogout.addActionListener(e -> logout());
    }

   
    private void logout() {
        dispose(); 
        new LoginForm(null).setVisible(true); 
    }

    
    public static void main(String[] args) {
        JOptionPane.showMessageDialog(null,
                "Access Denied!\nPlease login first.",
                "Error",
                JOptionPane.ERROR_MESSAGE);
        System.exit(0);
    }
}
