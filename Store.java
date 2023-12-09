import java.util.ArrayList;

public class Store {
    private String storeName;

    private Seller owner;
    private ArrayList<Product> productList;

    private ArrayList<String> sales;

    public Store(String storeName, Seller owner) {
        this.storeName = storeName;
        this.owner = owner;
        this.owner.getStoreLists().add(this);
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public Seller getOwner() {
        return owner;
    }

    public void setOwner(Seller owner) {
        this.owner = owner;
    }

    public ArrayList<Product> getProductList() {
        return productList;
    }

    public void setProductList(ArrayList<Product> products) {
        this.productList = products;
    }


}

