import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginRegisterGUI {

    public static void displayGUI() {
        JFrame frame = new JFrame("Login or Register");
        frame.setSize(400, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(0,2,10,10));
        panel.setBorder(new EmptyBorder(10, 30, 10, 30));
        frame.add(panel);
        placeComponents(panel, frame);

        frame.setVisible(true);
    }

    private static void placeComponents(JPanel panel, JFrame frame) {

        panel.add(new JLabel("Email"));
        JTextField userText = new JTextField(20);
        panel.add(userText);

        panel.add(new JLabel("Password"));
        JPasswordField passwordText = new JPasswordField(20);
        panel.add(passwordText);

        JRadioButton customerButton = new JRadioButton("Customer");
        panel.add(customerButton);

        JRadioButton sellerButton = new JRadioButton("Seller");
        panel.add(sellerButton);

        ButtonGroup group = new ButtonGroup();
        group.add(customerButton);
        group.add(sellerButton);

        JButton loginButton = new JButton("Login");
        panel.add(loginButton);

        JButton registerButton = new JButton("Register");
        panel.add(registerButton);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = userText.getText();
                String password = new String(passwordText.getPassword());
                if (sellerButton.isSelected()) {
                    frame.dispose();

                    Seller currentSeller = new Seller(username, password);
                    Store selectedStore = null;
                    SellerDashboardGUI.displayGUI(currentSeller, selectedStore);
                } else if (customerButton.isSelected()) {
                    frame.dispose();

                    Customer currentCustomer = new Customer(username, password);
                    CustomerDashboardGUI.displayGUI(currentCustomer);
                }
            }
        });

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = userText.getText();
                String password = new String(passwordText.getPassword());
                if (sellerButton.isSelected()) {
                    frame.dispose();
                    // Create a Seller object for the new registration
                    Seller currentSeller = new Seller(username, password);
                    Store selectedStore = null;
                    SellerDashboardGUI.displayGUI(currentSeller, selectedStore); // Pass the Seller object
                } else if (customerButton.isSelected()) {
                    frame.dispose();

                    Customer currentCustomer = new Customer(username, password);
                    CustomerDashboardGUI.displayGUI(currentCustomer);
                }
            }
        });
    }
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                Seller currentSeller = new Seller("sellerUsername", "sellerPassword");
                Store selectedStore = null;
                displayGUI();
            }
        });
    }
}
