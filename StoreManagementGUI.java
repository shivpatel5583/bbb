
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class StoreManagementGUI {

    public static void displayGUI(Seller currentSeller, Store selectedStore) {
        JFrame frame = new JFrame("Store Management");
        frame.setSize(600, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new GridLayout(0, 2, 10, 10));
        mainPanel.setBorder(new EmptyBorder(10, 30, 10, 30));
        frame.add(mainPanel);
        placeComponents(mainPanel, frame, currentSeller, selectedStore);

        frame.setVisible(true);
    }

    private static void placeComponents(JPanel mainPanel, JFrame frame, Seller currentSeller, Store selectedStore) {
        mainPanel.setLayout(new BorderLayout(10, 0)); // Horizontal gap of 10

        // panel for buttons
        JPanel buttonPanel = new JPanel(new GridLayout(5, 1, 10, 10));

        JButton salesListButton = new JButton("Show Sales List");
        JButton statisticsButton = new JButton("Statistics of Store");
        JButton modifyProductsButton = new JButton("Modify Products");
        JButton deleteAccountButton = new JButton("Delete Account");
        JButton returnButton = new JButton("Return to Dashboard");

        buttonPanel.add(salesListButton);
        buttonPanel.add(statisticsButton);
        buttonPanel.add(modifyProductsButton);
        buttonPanel.add(deleteAccountButton);
        buttonPanel.add(returnButton);

        // line to separate dropdown
        JSeparator separator = new JSeparator(JSeparator.VERTICAL);

        // panel for the combo box
        JPanel comboBoxPanel = new JPanel(new FlowLayout());
        JComboBox<String> storeSelection = new JComboBox<>();
        storeSelection.setPreferredSize(new Dimension(150, 25));

        comboBoxPanel.add(new JLabel("Your stores:"));
        comboBoxPanel.add(storeSelection);
        // clears the combo box everytime, so there are no duplicates, there might be an easier way to do this so i will look again
        storeSelection.removeAllItems();
        for (Store store : currentSeller.getStoreLists()) {
            boolean exists = false;
            for (int i = 0; i < storeSelection.getItemCount(); i++) {
                if (storeSelection.getItemAt(i).equals(store.getStoreName())) {
                    exists = true;
                    break;
                }
            }
            if (!exists) {
                storeSelection.addItem(store.getStoreName());
            }
        }

        mainPanel.add(buttonPanel, BorderLayout.WEST);
        mainPanel.add(separator, BorderLayout.CENTER);
        mainPanel.add(comboBoxPanel, BorderLayout.EAST);

        salesListButton.addActionListener(new ActionListener() { //have to test if this works with the customer gui
            @Override
            public void actionPerformed(ActionEvent e) {
                List<Customer> customers = Customer.getAllCustomers();

                String salesDetails = currentSeller.viewSalesByStore(customers);

                JTextArea textArea = new JTextArea(salesDetails);
                textArea.setEditable(false);
                JScrollPane scrollPane = new JScrollPane(textArea);
                scrollPane.setPreferredSize(new Dimension(400, 200));
                JOptionPane.showMessageDialog(frame, scrollPane, "Sales List", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        modifyProductsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedStoreName = (String) storeSelection.getSelectedItem();
                Store selectedStore = null;
                for (Store store : currentSeller.getStoreLists()) {
                    if (store.getStoreName().equals(selectedStoreName)) {
                        selectedStore = store;
                        break;
                    }
                }
                if (selectedStore != null) {
                    frame.dispose();
                    ProductGUI.displayGUI(currentSeller, selectedStore);
                } else {
                    JOptionPane.showMessageDialog(mainPanel,
                            "Please select a store.",
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        statisticsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // gets customer list
                List<Customer> customers = Customer.getAllCustomers(); //have to test if this works with the customer gui

                String statistics = currentSeller.viewStatisticsDashboard(customers);

                JTextArea textArea = new JTextArea(statistics);
                textArea.setEditable(false);
                JScrollPane scrollPane = new JScrollPane(textArea);
                scrollPane.setPreferredSize(new Dimension(400, 200));
                JOptionPane.showMessageDialog(frame, scrollPane,
                        "Store Statistics", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        returnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose(); // close the current frame
                SellerDashboardGUI.displayGUI(currentSeller, selectedStore);
            }
        });
    }
}
