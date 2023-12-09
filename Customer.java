import java.util.*;
import java.util.stream.Collectors;

public class Customer extends Account {
    private static List<Customer> allCustomers = new ArrayList<>();
    private Map<Product, Integer> shoppingCart;
    private ArrayList<Store> storeLists;// Use Map to keep track of quantity for each product

    public Customer(String username, String password) {
        super(username, password);
        this.shoppingCart = new HashMap<>();
        this.storeLists = new ArrayList<>();
        allCustomers.add(this);

    }
    public static List<Customer> getAllCustomers() {
        return allCustomers;
    }

    public ArrayList<Store> getStoreLists() {
        return storeLists;
    }

    public void viewStatisticsDashboard(List<Store> stores) {
        System.out.println("Statistics Dashboard for Customer: " + getUsername());

        // List of stores by the number of products sold
        List<Store> storesSortedByProductsSold = stores.stream()
                .sorted(Comparator.comparingInt(s -> s.getProductList().size()))
                .collect(Collectors.toList());

        System.out.println("Stores by Products Sold:");
        for (Store store : storesSortedByProductsSold) {
            int productsSold = store.getProductList().size() - store.getProductList().stream().mapToInt(Product::getQuantity).sum();
            System.out.println("Store: " + store.getStoreName() + ", Products Sold: " + productsSold);
        }

        // List of stores by the products purchased by the customer
        List<Store> storesSortedByProductsPurchased = stores.stream()
                .sorted(Comparator.comparingInt(s -> s.getProductList().size() - s.getProductList().stream()
                        .filter(p -> getShoppingCart().containsKey(p))
                        .mapToInt(p -> getShoppingCart().get(p))
                        .sum()))
                .collect(Collectors.toList());

        System.out.println("Stores by Products Purchased:");
        for (Store store : storesSortedByProductsPurchased) {
            int productsPurchased = store.getProductList().stream()
                    .filter(p -> getShoppingCart().containsKey(p))
                    .mapToInt(p -> getShoppingCart().get(p))
                    .sum();
            System.out.println("Store: " + store.getStoreName() + ", Products Purchased: " + productsPurchased);
        }
    }

    public void addToCart(Product product, int quantity) {
        // Check if the product is available in sufficient quantity
        if (product.getQuantity() > 0) {
            // Reduce the quantity by 1
            product.setQuantity(product.getQuantity() - 1);

            // Add the product to the customer's shopping cart or update quantity if already in the cart
            shoppingCart.put(product, shoppingCart.getOrDefault(product, 0) + 1);

            System.out.println("Product '" + product.getName() + "' added to your shopping cart.");
        } else {
            System.out.println("Sorry, the product '" + product.getName() + "' is out of stock.");
        }
    }

    public void removeFromCart(Product product, int quantityToRemove) {
        // Check if the product is in the shopping cart
        if (shoppingCart.containsKey(product)) {
            // Increase the quantity in the store and remove the product from the cart
            product.setQuantity(product.getQuantity() + shoppingCart.get(product));
            shoppingCart.remove(product);
            System.out.println("Product '" + product.getName() + "' removed from your shopping cart.");
        } else {
            System.out.println("Product '" + product.getName() + "' is not in your shopping cart.");
        }
    }

    public void checkout() {
        for (Map.Entry<Product, Integer> entry : shoppingCart.entrySet()) {
            Product product = entry.getKey();
            int quantity = entry.getValue();
            for (int i = 0; i < quantity; i++) {
                buyProduct(product);
            }
        }
        shoppingCart.clear(); // Clear the shopping cart after checkout
        System.out.println("Checkout successful!");
    }

    public Map<Product, Integer> getShoppingCart() {
        return shoppingCart;
    }

    public void buyProduct(Product product) {
        // Check if the product is available in sufficient quantity
        if (product.getQuantity() > 0) {
            // Reduce the quantity by 1
            product.setQuantity(product.getQuantity() - 1);

            // Add the product to the customer's shopping cart or update quantity if already in the cart
            shoppingCart.put(product, shoppingCart.getOrDefault(product, 0) + 1);

            System.out.println("Product '" + product.getName() + "' added to your shopping cart.");
        } else {
            System.out.println("Sorry, the product '" + product.getName() + "' is out of stock.");
        }
    }
}
