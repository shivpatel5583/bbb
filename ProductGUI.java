import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
public class ProductGUI {

    public static void displayGUI (Seller currentSeller, Store selectedStore) {
        JFrame frame = new JFrame("Products");
        frame.setSize(700, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(0, 2, 10, 10));
        panel.setBorder(new EmptyBorder(10, 30, 10, 30));
        frame.add(panel);
        placeComponents(panel, frame, currentSeller, selectedStore);

        frame.setVisible(true);
    }

    private static void placeComponents(JPanel panel, JFrame frame, Seller currentSeller, Store selectedStore) {
        panel.setLayout(new BorderLayout(10, 0));

        JPanel labelsPanel = new JPanel(new GridLayout(5, 1, 10, 10));

        JLabel productName = new JLabel("Product Name:");
        JLabel description = new JLabel("Description:");
        JLabel quantity = new JLabel("Quantity:");
        JLabel price = new JLabel("Price:");

        labelsPanel.add(productName);
        labelsPanel.add(description);
        labelsPanel.add(quantity);
        labelsPanel.add(price);

        panel.add(labelsPanel, BorderLayout.WEST);

        JPanel textBoxPanel = new JPanel(new GridLayout(5, 1, 10, 10));

        JTextField productNameText = new JTextField("");
        JTextField descriptionText = new JTextField("");
        JTextField quantityText = new JTextField("");
        JTextField priceText = new JTextField("");

        textBoxPanel.add(productNameText);
        textBoxPanel.add(descriptionText);
        textBoxPanel.add(quantityText);
        textBoxPanel.add(priceText);

        panel.add(textBoxPanel, BorderLayout.CENTER);

        JPanel buttonsPanel = new JPanel(new GridLayout(6, 1, 10, 10));

        JLabel selectedStoreName = new JLabel("Store: " + selectedStore.getStoreName());
        JButton addProduct = new JButton("Add Product");
        JButton editProduct = new JButton("Edit Selected Product");
        JButton deleteProduct = new JButton("Delete Selected Product");
        JButton returnButton = new JButton("Return to Store Manager");

        buttonsPanel.add(selectedStoreName);
        buttonsPanel.add(addProduct);
        buttonsPanel.add(editProduct);
        buttonsPanel.add(deleteProduct);
        buttonsPanel.add(returnButton);

        panel.add(buttonsPanel, BorderLayout.NORTH);

        JPanel comboBoxPanel = new JPanel(new FlowLayout());
        JComboBox<String> productSelection = new JComboBox<>();
        productSelection.setPreferredSize(new Dimension(150, 25));

        productSelection.removeAllItems();
        for (Product product : selectedStore.getProductList()) {
            productSelection.addItem(product.getName());
        }

        comboBoxPanel.add(new JLabel("Your Products:"));
        comboBoxPanel.add(productSelection);

        panel.add(comboBoxPanel, BorderLayout.EAST);

        addProduct.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = productNameText.getText();
                String description = descriptionText.getText();
                int quantity;
                double price;

                try {
                    quantity = Integer.parseInt(quantityText.getText());
                    price = Double.parseDouble(priceText.getText());

                    currentSeller.createProduct(name, selectedStore, description, quantity, price);
                    JOptionPane.showMessageDialog(panel, "Product added successfully!");
                    updateProductList(productSelection, selectedStore);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(panel, "Invalid input for quantity or price.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        //populates the text fields with the item you pick in order to edit or delete
        productSelection.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    String selectedProductName = (String) e.getItem();
                    Product selectedProduct = findProduct(selectedStore, selectedProductName);
                    if (selectedProduct != null) {
                        productNameText.setText(selectedProduct.getName());
                        descriptionText.setText(selectedProduct.getDescription());
                        quantityText.setText(String.valueOf(selectedProduct.getQuantity()));
                        priceText.setText(String.valueOf(selectedProduct.getPrice()));
                    }
                }
            }
        });
        editProduct.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedProductName = (String) productSelection.getSelectedItem();
                Product selectedProduct = findProduct(selectedStore, selectedProductName);

                if (selectedProduct != null) {
                    try {
                        String name = productNameText.getText();
                        String description = descriptionText.getText();
                        int quantity = Integer.parseInt(quantityText.getText());
                        double price = Double.parseDouble(priceText.getText());

                        currentSeller.editProduct(selectedProduct, name, description, quantity, price);
                        JOptionPane.showMessageDialog(panel, "Product updated successfully!");
                        updateProductList(productSelection, selectedStore);
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(panel, "Invalid input for quantity or price.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(panel, "No product selected.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        returnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose(); // close current frame
                StoreManagementGUI.displayGUI(currentSeller, selectedStore);
            }
        });
        deleteProduct.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedProductName = (String) productSelection.getSelectedItem();
                Product selectedProduct = findProduct(selectedStore, selectedProductName);

                if (selectedProduct != null) {
                    currentSeller.deleteProduct(selectedStore, selectedProduct);
                    JOptionPane.showMessageDialog(panel, "Product deleted successfully!");
                    updateProductList(productSelection, selectedStore);
                } else {
                    JOptionPane.showMessageDialog(panel, "No product selected.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }
    private static void updateProductList(JComboBox<String> productSelection, Store selectedStore) {
        productSelection.removeAllItems();
        for (Product product : selectedStore.getProductList()) {
            productSelection.addItem(product.getName());
        }
    }
    private static Product findProduct(Store store, String productName) {
        for (Product product : store.getProductList()) {
            if (product.getName().equals(productName)) {
                return product;
            }
        }
        return null;
    }
}
