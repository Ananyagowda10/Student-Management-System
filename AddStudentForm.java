package AnuGowda;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class AddStudentForm extends JDialog {

    private JTextField txtName = new JTextField(20);
    private JTextField txtEmail = new JTextField(20);
    private JTextField txtCourse = new JTextField(20);
    private JButton btnSave = new JButton("Save");

    public AddStudentForm() {
        setTitle("Add Student");
        setModal(true);
        setupUI();
        setVisible(true);
    }

    private void setupUI() {

        
        JPanel header = new JPanel();
        header.setBackground(new Color(180, 210, 255));
        header.setPreferredSize(new Dimension(500, 55));

        JLabel title = new JLabel("➕ Add Student");
        title.setFont(new Font("Arial", Font.BOLD, 20));
        title.setForeground(new Color(0, 60, 140));
        header.add(title);

       
        JPanel form = new JPanel(new GridBagLayout());
        form.setBackground(new Color(203, 228, 255));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(12, 12, 12, 12);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;

        styleText(txtName);
        styleText(txtEmail);
        styleText(txtCourse);
        styleButton(btnSave);

        
        gbc.gridx = 0; gbc.gridy = 0;
        form.add(new JLabel("Name:"), gbc);

        gbc.gridx = 1;
        form.add(txtName, gbc);

        
        gbc.gridx = 0; gbc.gridy = 1;
        form.add(new JLabel("Email:"), gbc);

        gbc.gridx = 1;
        form.add(txtEmail, gbc);

       
        gbc.gridx = 0; gbc.gridy = 2;
        form.add(new JLabel("Course:"), gbc);

        gbc.gridx = 1;
        form.add(txtCourse, gbc);

        
        gbc.gridx = 0; gbc.gridy = 3; 
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.NONE;
        form.add(btnSave, gbc);

        btnSave.addActionListener(e -> saveStudent());

       
        setLayout(new BorderLayout());
        add(header, BorderLayout.NORTH);
        add(form, BorderLayout.CENTER);

        setSize(500, 350);
        setLocationRelativeTo(null);
    }

    
    private void styleText(JTextField txt) {
        txt.setPreferredSize(new Dimension(230, 30));
        txt.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        txt.setFont(new Font("Arial", Font.PLAIN, 14));
    }

    private void styleButton(JButton btn) {
        btn.setPreferredSize(new Dimension(150, 40));
        btn.setBackground(new Color(0, 120, 215));
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Arial", Font.BOLD, 16));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

   
    private void saveStudent() {
        String name = txtName.getText().trim();
        String email = txtEmail.getText().trim();
        String course = txtCourse.getText().trim();

        
        if (name.isEmpty() || email.isEmpty() || course.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields are required!");
            return;
        }

       
        if (!name.matches("^[A-Za-z ]{3,}$")) {
            JOptionPane.showMessageDialog(this,
                    "Invalid Name! Name must contain only alphabets and minimum 3 characters.");
            return;
        }

       
        if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            JOptionPane.showMessageDialog(this, 
                    "Invalid Email Format! Example: abc@gmail.com");
            return;
        }

        
        if (course.length() < 2) {
            JOptionPane.showMessageDialog(this,
                    "Course must have at least 2 characters.");
            return;
        }

        try (Connection conn = DBConnection.getConnection()) {

           
            String checkSql = "SELECT * FROM student6 WHERE email = ?";
            PreparedStatement checkPst = conn.prepareStatement(checkSql);
            checkPst.setString(1, email);
            ResultSet rs = checkPst.executeQuery();

            if (rs.next()) {
                JOptionPane.showMessageDialog(this,
                        "Email already exists! Please use another email.");
                return; 
            }

           
            String sql = "INSERT INTO student6 (name, email, course) VALUES (?, ?, ?)";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, name);
            pst.setString(2, email);
            pst.setString(3, course);

            pst.executeUpdate();

            JOptionPane.showMessageDialog(this, "Student Added Successfully!");
            dispose();

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    }


