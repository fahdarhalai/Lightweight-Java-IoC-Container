package example.customer;

import example.products.Product;

import java.util.List;

public interface ICustomer {
    public void addToBasket(Product product);
    public void addToBasket(List<Product> productList);
    public void checkout();
}
