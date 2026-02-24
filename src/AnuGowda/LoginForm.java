package AnuGowda;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class LoginForm extends JDialog {

    private JTextField txtEmail = new JTextField(20);
    private JPasswordField txtPassword = new JPasswordField(20);
    private JButton btnLogin = new JButton("Login");
    private JButton btnRegister = new JButton("Register");

    public LoginForm(Frame parent) {
        super(parent, "Student Login", true);
        initUI();
    }

    private void initUI() {

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(203, 228, 255)); // soft blue

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(12, 12, 12, 12);

       
        JLabel title = new JLabel("Student Login");
        title.setFont(new Font("Arial", Font.BOLD, 22));
        title.setForeground(new Color(0, 70, 160));

        gbc.gridx = 0; 
        gbc.gridy = 0; 
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(title, gbc);

        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;

       
        styleTextField(txtEmail);
        styleTextField(txtPassword);

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;

        
        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(new JLabel("Email:"), gbc);

        gbc.gridx = 1;
        panel.add(txtEmail, gbc);

       
        gbc.gridx = 0; gbc.gridy = 2;
        panel.add(new JLabel("Password:"), gbc);

        gbc.gridx = 1;
        panel.add(txtPassword, gbc);

       
        styleButton(btnLogin);
        styleButton(btnRegister);

        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;

        gbc.gridx = 0; gbc.gridy = 3;
        panel.add(btnLogin, gbc);

        gbc.gridx = 1;
        panel.add(btnRegister, gbc);

        add(panel);

       
        btnLogin.addActionListener(e -> login());
        btnRegister.addActionListener(e -> openRegistration());

        setSize(420, 330);
        setLocationRelativeTo(null);
    }

   
    private void styleTextField(JTextField txt) {
        txt.setPreferredSize(new Dimension(220, 30));
        txt.setFont(new Font("Arial", Font.PLAIN, 14));
        txt.setBackground(Color.WHITE);
        txt.setForeground(Color.BLACK);
        txt.setCaretColor(Color.BLACK);
        txt.setBorder(BorderFactory.createLineBorder(new Color(40, 40, 40), 2)); // Visible border
    }

  
    private void styleButton(JButton btn) {
        btn.setBackground(new Color(0, 120, 215));
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Arial", Font.BOLD, 14));
        btn.setFocusPainted(false);
        btn.setPreferredSize(new Dimension(120, 35));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    
    private void login() {

        String email = txtEmail.getText().trim();
        String password = new String(txtPassword.getPassword()).trim();

        if (email.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Email and Password cannot be empty!");
            return;
        }

        try (Connection conn = DBConnection.getConnection()) {

            // 1️⃣ CHECK IF EMAIL EXISTS
            PreparedStatement emailCheck = conn.prepareStatement(
                    "SELECT * FROM project WHERE email=?");
            emailCheck.setString(1, email);
            ResultSet rsEmail = emailCheck.executeQuery();

            if (!rsEmail.next()) {
                JOptionPane.showMessageDialog(this,
                        "No account found!\nPlease register first.");
                dispose();
                new RegistrationForm(null).setVisible(true);
                return;
            }

           
            PreparedStatement pst = conn.prepareStatement(
                    "SELECT id, name FROM project WHERE email=? AND password=?");

            pst.setString(1, email);
            pst.setString(2, password);

            ResultSet rs = pst.executeQuery();

            if (rs.next()) {

                int id = rs.getInt("id");
                String name = rs.getString("name");

                JOptionPane.showMessageDialog(this, "Welcome, " + name + "!");

                dispose();
                new MainDashboard();

            } else {
                JOptionPane.showMessageDialog(this, "Incorrect Password!");
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "DB Error: " + ex.getMessage());
        }
    }

    private void openRegistration() {
        dispose();
        new RegistrationForm(null).setVisible(true);
    }
}
