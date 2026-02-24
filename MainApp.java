package AnuGowda;

import javax.swing.*;

public class MainApp {
    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

           
            LoginForm login = new LoginForm(null);
            login.setVisible(true);

           
        });
    }
}
