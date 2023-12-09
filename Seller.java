import java.util.*;
import java.util.stream.Collectors;

public class Seller extends Account {
    public ArrayList<Store> storeLists;

    public Seller(String username, String password) {
        super(username, password);
        this.storeLists = new ArrayList<>();
    }

    public void createStore(String storeName, Seller owner) {
        Store newStore = new Store(storeName, owner);
        storeLists.add(newStore);
    }

    public String viewStatisticsDashboard(List<Customer> customers) {
        StringBuilder stats = new StringBuilder();
        stats.append("Statistics Dashboard for Seller: ").append(getUsername()).append("\n");

        for (Store store : storeLists) {
            stats.append("Store: ").append(store.getStoreName()).append("\n");

            List<Customer> customersSortedByItemsPurchased = customers.stream()
                    .sorted(Comparator.comparingInt(c -> c.getShoppingCart().values().stream().mapToInt(Integer::intValue).sum()))
                    .collect(Collectors.toList());

            stats.append("Customers by Items Purchased:\n");
            for (Customer customer : customersSortedByItemsPurchased) {
                int itemsPurchased = customer.getShoppingCart().values().stream().mapToInt(Integer::intValue).sum();
                stats.append("Customer: ").append(customer.getUsername()).append(", Items Purchased: ").append(itemsPurchased).append("\n");
            }

            List<Product> productsSortedBySales = store.getProductList().stream()
                    .sorted(Comparator.comparingInt(p -> p.getQuantity()))
                    .collect(Collectors.toList());

            stats.append("Products by Sales:\n");
            for (Product product : productsSortedBySales) {
                int sales = store.getProductList().size() - product.getQuantity();
                stats.append("Product: ").append(product.getName()).append(", Sales: ").append(sales).append("\n");
            }

            stats.append("----------------------------\n");
        }

        return stats.toString();
    }

    public String viewSalesByStore(List<Customer> customers) {
        StringBuilder salesInfo = new StringBuilder();

        for (Store store : storeLists) {
            salesInfo.append("Store: ").append(store.getStoreName()).append("\n");

            for (Product product : store.getProductList()) {
                if (product.getQuantity() < store.getProductList().size()) {
                    int quantitySold = store.getProductList().size() - product.getQuantity();
                    salesInfo.append("Product: ").append(product.getName())
                            .append(", Quantity Sold: ").append(quantitySold).append("\n");

                    for (Customer customer : customers) {
                        Map<Product, Integer> customerShoppingCart = customer.getShoppingCart();
                        if (customerShoppingCart.containsKey(product)) {
                            int quantityPurchased = customerShoppingCart.get(product);
                            double revenue = quantityPurchased * product.getPrice();

                            salesInfo.append("Customer: ").append(customer.getUsername())
                                    .append(", Quantity Purchased: ").append(quantityPurchased)
                                    .append(", Revenue: $").append(revenue).append("\n");
                        }
                    }
                }
            }
            salesInfo.append("\n"); // Add a new line for spacing between stores
        }
        return null;
    }

    public void viewCustomerShoppingCarts(List<Customer> customers) {
        // Map to keep track of the total quantity of products in all customer shopping carts
        Map<Product, Integer> totalQuantityMap = new HashMap<>();

        // Iterate over all customers
        for (Customer customer : customers) { // Assuming you have a list of customers
            // Iterate over the products in the customer's shopping cart
            for (Map.Entry<Product, Integer> entry : customer.getShoppingCart().entrySet()) {
                Product product = entry.getKey();
                int quantity = entry.getValue();

                // Update the total quantity map
                totalQuantityMap.put(product, totalQuantityMap.getOrDefault(product, 0) + quantity);

                // Print details (you can modify this as needed)
                System.out.println("Customer: " + customer.getUsername() +
                        ", Product: " + product.getName() +
                        ", Quantity: " + quantity +
                        ", Store: " + product.getOwner().getStoreName());
            }
        }

        // Print total quantities (you can modify this as needed)
        System.out.println("Total quantities in all shopping carts:");
        for (Map.Entry<Product, Integer> entry : totalQuantityMap.entrySet()) {
            Product product = entry.getKey();
            int totalQuantity = entry.getValue();
            System.out.println("Product: " + product.getName() +
                    ", Total Quantity: " + totalQuantity);
        }
    }

    public void createProduct(String name, Store owner, String description, int quantity, double price) {
        if (storeLists.contains(owner)) {
            Product newProduct = new Product(name, owner, description, quantity, price);
        } else {
            System.out.println("Invalid Owner");
        }
    }

    public void deleteProduct(Store store, Product product) {
        store.getProductList().remove(product);
    }

    public void editProduct(Product product, String name, String description, int quantity, double price) {
        // Check if the product belongs to any of the seller's stores
        boolean productBelongsToSeller = false;
        for (Store store : storeLists) {
            if (store.getProductList().contains(product)) {
                productBelongsToSeller = true;
                break;
            }
        }

        if (productBelongsToSeller) {
            // Update the product details
            product.setName(name);
            product.setDescription(description);
            product.setQuantity(quantity);
            product.setPrice(price);
        } else {
            System.out.println("You don't have permission to edit this product.");
        }
    }

    public ArrayList<Store> getStoreLists() {
        return storeLists;
    }

    public void setStoreLists(ArrayList<Store> storeLists) {
        this.storeLists = storeLists;
    }
}
