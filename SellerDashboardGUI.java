import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SellerDashboardGUI {
    // Method to create and show the Seller Dashboard GUI
    public static void displayGUI(Seller currentSeller, Store selectedStore) {
        JFrame frame = new JFrame("Seller Dashboard");
        frame.setSize(400, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(0, 2, 10, 10));
        panel.setBorder(new EmptyBorder(10, 30, 10, 30));
        frame.add(panel);
        placeComponents(panel, frame, currentSeller, selectedStore);

        frame.setVisible(true);
    }
    public static void placeComponents(JPanel panel, JFrame frame, Seller currentSeller, Store selectedStore) {
        panel.setLayout(new BorderLayout()); // borderLayout for the main panel
        // Panel for the dashboard label
        JPanel labelPanel = new JPanel(new BorderLayout());

        JLabel dashboardLabel = new JLabel("Create and Manage Stores", SwingConstants.CENTER);
        dashboardLabel.setFont(new Font(dashboardLabel.getFont().getName(), Font.BOLD, 20));
        dashboardLabel.setPreferredSize(new Dimension(100,30));

        labelPanel.add(dashboardLabel, BorderLayout.CENTER);

        // Panel for the rest of the components
        JPanel componentsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        JLabel storeNameLabel = new JLabel("Store Name:");
        JTextField storeNameText = new JTextField(10); // Set the desired size
        JButton createStoreButton = new JButton("Create Store");
        JLabel manageStoresLabel = new JLabel("Manage your stores here:");
        JButton manageStoresButton = new JButton("Manage Stores");
        JButton logoutButton = new JButton("Log Out");

        componentsPanel.add(storeNameLabel);
        componentsPanel.add(storeNameText);
        componentsPanel.add(createStoreButton);
        componentsPanel.add(manageStoresLabel);
        componentsPanel.add(manageStoresButton);
        componentsPanel.add(logoutButton);

        panel.add(labelPanel, BorderLayout.NORTH);
        panel.add(componentsPanel, BorderLayout.CENTER);



        manageStoresButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose(); // closes current frame
                StoreManagementGUI.displayGUI(currentSeller, selectedStore);
            }
        });
        createStoreButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String storeName = storeNameText.getText();
                if (!storeName.isEmpty()) {
                    currentSeller.createStore(storeName, currentSeller); // Create store
                    JOptionPane.showMessageDialog(panel, "Store created successfully!");
                } else {
                    JOptionPane.showMessageDialog(panel,
                            "Please enter a store name.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                LoginRegisterGUI.displayGUI(currentSeller, selectedStore);
            }
        });
    }
}
