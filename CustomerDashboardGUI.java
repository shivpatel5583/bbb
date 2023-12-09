
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Map;

public class CustomerDashboardGUI {
    // Method to create and show the Seller Dashboard GUI
    public static void displayGUI(Customer currentCustomer) {
        JFrame frame = new JFrame("Customer Dashboard");
        frame.setSize(400, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(0, 2, 10, 10));
        panel.setBorder(new EmptyBorder(10, 30, 10, 30));
        frame.add(panel);
        placeComponents(panel, frame, currentCustomer);

        frame.setVisible(true);
    }
    public static void placeComponents(JPanel panel, JFrame frame, Customer currentCustomer) {
        panel.setLayout(new BorderLayout()); // borderLayout for the main panel
        // Panel for the dashboard label
        JPanel labelPanel = new JPanel(new BorderLayout());

        JLabel dashboardLabel = new JLabel("Customer Dashboard", SwingConstants.CENTER);
        dashboardLabel.setFont(new Font(dashboardLabel.getFont().getName(), Font.BOLD, 20));
        dashboardLabel.setPreferredSize(new Dimension(100,30));

        labelPanel.add(dashboardLabel, BorderLayout.CENTER);

        // Panel for the rest of the components
        JPanel componentsPanel = new JPanel(new GridLayout(2, 2, 10, 10));


        JButton viewShoppingCart = new JButton("View Shopping Cart");
        JButton addProductsToShoppingCart = new JButton("Add products to Shopping Cart");
        JButton removeProductShoppingCart = new JButton("Remove Product Shopping Cart");
        JButton checkoutButton = new JButton("Checkout");
        JButton logoutButton = new JButton("Log Out");

        componentsPanel.add(viewShoppingCart);
        componentsPanel.add(addProductsToShoppingCart);
        componentsPanel.add(removeProductShoppingCart);
        componentsPanel.add(checkoutButton);
        componentsPanel.add(logoutButton);

        panel.add(labelPanel, BorderLayout.NORTH);
        panel.add(componentsPanel, BorderLayout.CENTER);



        viewShoppingCart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose(); // closes current frame
                Map<Product, Integer> shoppingCart = currentCustomer.getShoppingCart();

                if (shoppingCart.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Your shopping cart is empty.",
                            "Costumer", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    String output = "Shopping Cart: \n";
                    for (Map.Entry<Product, Integer> entry : shoppingCart.entrySet()) {
                        Product product = entry.getKey();
                        int quantity = entry.getValue();

                        output += ("Product: " + product.getName() +
                                ", Quantity: " + quantity +
                                ", Price per unit: $" + product.getPrice() +
                                ", Total Price: $" + (quantity * product.getPrice()) + "\n");
                    }
                    JOptionPane.showMessageDialog(null, output);
                }
                displayGUI(currentCustomer);
            }
        });
        checkoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Map<Product, Integer> shoppingCart = currentCustomer.getShoppingCart();

                if (shoppingCart.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Your shopping cart is empty. Nothing to checkout.",
                            "Costumer", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "Checking out...",
                            "Costumer", JOptionPane.INFORMATION_MESSAGE);

                    // Assuming you have a payment process or other checkout logic
                    // You can perform additional actions here

                    // Clear the shopping cart after successful checkout
                    currentCustomer.checkout();

                    JOptionPane.showMessageDialog(null, "Checkout successful!",
                            "Costumer", JOptionPane.INFORMATION_MESSAGE);
                }
                displayGUI(currentCustomer);
            }
        });
        removeProductShoppingCart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Map<Product, Integer> shoppingCart = currentCustomer.getShoppingCart();

                if (shoppingCart.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Your shopping cart is empty.",
                            "Costumer", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    String output = "Shopping Cart: \n";
                    for (Map.Entry<Product, Integer> entry : shoppingCart.entrySet()) {
                        Product product = entry.getKey();
                        int quantity = entry.getValue();

                        output += ("Product: " + product.getName() +
                                ", Quantity: " + quantity +
                                ", Price per unit: $" + product.getPrice() +
                                ", Total Price: $" + (quantity * product.getPrice()) + "\n");
                    }
                    JOptionPane.showMessageDialog(null, output);
                }
                // Assuming storeLists is a protected or package-private field in the Customer class
                List<Store> storeLists = currentCustomer.getStoreLists();

                // Assuming you have a list of products in the shopping cart, prompt the user to select a product
                String[] optionsArray;
                String options = "";
                String output = "Select a product to remove:" + "\n";
                int index = 1;
                for (Product product : currentCustomer.getShoppingCart().keySet()) {
                    options += (index + ",");
                    output += (index + ". " + product.getName() + "\n");
                    index++;
                }
                optionsArray = options.split(",");
                String choiceString = (String) JOptionPane.showInputDialog(null,output, "Costumer",
                        JOptionPane.QUESTION_MESSAGE, null, optionsArray, optionsArray[0]);

                int productChoice = Integer.parseInt(choiceString);

                // Find the selected product
                Product selectedProduct = null;
                index = 1;
                for (Product product : currentCustomer.getShoppingCart().keySet()) {
                    if (index == productChoice) {
                        selectedProduct = product;
                        break;
                    }
                    index++;
                }

                String[] optionsArray2;
                String options2 = "";
                String output2 = "Quantity to remove" + "\n";

                // Prompt the user for the quantity to remove from the shopping cart
                int qInCart = currentCustomer.getShoppingCart().get(selectedProduct);
                for (int i = 0; i < qInCart; i++) {
                    options2 += ((i + 1) + ",");
                }
                optionsArray2 = options2.split(",");
                String choiceString2 = (String) JOptionPane.showInputDialog(null,output2, "Costumer",
                        JOptionPane.QUESTION_MESSAGE, null, optionsArray2, optionsArray2[0]);
                int quantityToRemove = Integer.parseInt(choiceString2);

                // Validate quantity to remove
                int currentQuantity = currentCustomer.getShoppingCart().get(selectedProduct);
                if (quantityToRemove > 0 && quantityToRemove <= currentQuantity) {
                    // Remove the selected product from the shopping cart
                    currentCustomer.removeFromCart(selectedProduct, quantityToRemove);
                    JOptionPane.showMessageDialog(null, "Product removed from your shopping cart.",
                            "Costumer", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid quantity. Please enter a valid quantity.",
                            "Costumer", JOptionPane.INFORMATION_MESSAGE);
                }
                displayGUI(currentCustomer);
            }
        });
        addProductsToShoppingCart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Placeholder for adding a product to the shopping cart

                // Assuming you have a list of stores, prompt the user to select a store
                String output = "";
                String[] optionsArray = {};
                String options = "";

                for (int i = 0; i < currentCustomer.getStoreLists().size(); i++) {
                    options += (i+1) + ",";
                    output += ((i + 1) + ". " + currentCustomer.getStoreLists().get(i).getStoreName() + "\n");
                }
                optionsArray = options.split(",");
                String choiceString = (String) JOptionPane.showInputDialog(null,output, "Costumer",
                        JOptionPane.QUESTION_MESSAGE, null, optionsArray, optionsArray[0]);
                int storeChoice = Integer.parseInt(choiceString);
                Store selectedStore = currentCustomer.getStoreLists().get(storeChoice - 1);

                // Assuming you have a list of products in the selected store, prompt the user to select a product
                String output2 = "Select a product: " + "\n";
                String[] optionsArray2 = {};
                String options2 = "";
                for (int i = 0; i < selectedStore.getProductList().size(); i++) {
                    Product product = selectedStore.getProductList().get(i);
                    options2 += (i+1) + ",";
                    output2 += ((i + 1) + ". " + product.getName() +
                            ", Price: $" + product.getPrice() +
                            ", Quantity Available: " + product.getQuantity() + "\n");
                }
                optionsArray2 = options2.split(",");
                String choiceString2 = (String) JOptionPane.showInputDialog(null,output2, "Costumer",
                        JOptionPane.QUESTION_MESSAGE, null, optionsArray2, optionsArray2[0]);
                int productChoice = Integer.parseInt(choiceString2);
                Product selectedProduct = selectedStore.getProductList().get(productChoice - 1);

                int quantityAvailable = selectedProduct.getQuantity();
                String[] qAvailableArray;
                String qAvailableString = "";
                for(int i = 0; i < quantityAvailable; i++) {
                    qAvailableString += ((i+1) + ",");
                }
                qAvailableArray = qAvailableString.split(",");

                String choiceString3 = (String) JOptionPane.showInputDialog(null,"Enter the quantity:", "Costumer",
                        JOptionPane.QUESTION_MESSAGE, null, qAvailableArray, qAvailableArray[0]);
                int quantity = Integer.parseInt(choiceString3);
                // Add the selected product to the shopping cart
                currentCustomer.addToCart(selectedProduct, quantity);
                JOptionPane.showMessageDialog(null, "Product added to your shopping cart.",
                        "Costumer", JOptionPane.INFORMATION_MESSAGE);
                displayGUI(currentCustomer);
            }
        });

        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                LoginRegisterGUI.displayGUI();
            }
        });
    }
}
