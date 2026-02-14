package AnuGowda;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class RegistrationForm extends JDialog {

    private JTextField txtName = new JTextField(20);
    private JTextField txtEmail = new JTextField(20);
    private JPasswordField txtPassword = new JPasswordField(20);
    private JButton btnRegister = new JButton("Register");
    private JButton btnClear = new JButton("Clear");

    public RegistrationForm(Frame parent) {
        super(parent, "Register", true);
        initUI();
    }

    private void initUI() {

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(203, 228, 255));  

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(12, 12, 12, 12);

        
        JLabel title = new JLabel("Student Registration");
        title.setFont(new Font("Arial", Font.BOLD, 22));
        title.setForeground(new Color(0, 70, 160));

        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(title, gbc);

        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;

       
        styleTextField(txtName);
        styleTextField(txtEmail);
        styleTextField(txtPassword);

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;

      
        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(new JLabel("Name:"), gbc);

        gbc.gridx = 1;
        panel.add(txtName, gbc);

      
        gbc.gridx = 0; gbc.gridy = 2;
        panel.add(new JLabel("Email:"), gbc);

        gbc.gridx = 1;
        panel.add(txtEmail, gbc);

        
        gbc.gridx = 0; gbc.gridy = 3;
        panel.add(new JLabel("Password:"), gbc);

        gbc.gridx = 1;
        panel.add(txtPassword, gbc);

     
        styleButton(btnRegister);
        styleButton(btnClear);

        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;

       
        gbc.gridx = 0; gbc.gridy = 4;
        panel.add(btnRegister, gbc);

        gbc.gridx = 1;
        panel.add(btnClear, gbc);

        add(panel);

        
        btnRegister.addActionListener(e -> register());
        btnClear.addActionListener(e -> clearFields());

        setSize(420, 360);
        setLocationRelativeTo(null);
    }

  
    private void styleTextField(JTextField txt) {
        txt.setPreferredSize(new Dimension(220, 30));
        txt.setFont(new Font("Arial", Font.PLAIN, 14));
        txt.setBackground(Color.WHITE);
        txt.setForeground(Color.BLACK);
        txt.setCaretColor(Color.BLACK);
        txt.setBorder(BorderFactory.createLineBorder(new Color(40, 40, 40), 2));
    }

    
    private void styleButton(JButton btn) {
        btn.setBackground(new Color(0, 120, 215));
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Arial", Font.BOLD, 14));
        btn.setFocusPainted(false);
        btn.setPreferredSize(new Dimension(120, 35));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    
    private void register() {

        String name = txtName.getText().trim();
        String email = txtEmail.getText().trim();
        String password = new String(txtPassword.getPassword()).trim();

       
        if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields are required!");
            return;
        }

        if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            JOptionPane.showMessageDialog(this, "Invalid email format!");
            return;
        }

        if (password.length() < 6) {
            JOptionPane.showMessageDialog(this, "Password must be at least 6 characters!");
            return;
        }

      
        String sql = "INSERT INTO project (name, email, password) VALUES (?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setString(1, name);
            pst.setString(2, email);
            pst.setString(3, password);

            pst.executeUpdate();

            JOptionPane.showMessageDialog(this, "Registration Successful!");

            dispose(); 
            new LoginForm(null).setVisible(true); 

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    private void clearFields() {
        txtName.setText("");
        txtEmail.setText("");
        txtPassword.setText("");
    }
}
