package example.products;

public class Product {
    private static int counter = 372;
    private int id;
    private String title;
    private float price;

    public Product(String title, float price) {
        this(counter++, title, price);
    }

    public Product(int id, String title, float price){
        this.id = id;
        this.title = title;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }
}
