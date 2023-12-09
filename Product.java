public class Product {

    private String name;

    private Store owner;

    private String description;

    private int quantity;

    private double price;

    public Product(String name, Store owner, String description, int quantity, double price) {
        this.name = name;
        this.owner = owner;
        this.description = description;
        this.quantity = quantity;
        this.price = price;
        this.owner.getProductList().add(this);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Store getOwner() {
        return owner;
    }

    public void setOwner(Store owner) {
        this.owner = owner;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
