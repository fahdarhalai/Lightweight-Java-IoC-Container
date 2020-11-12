package example.customer;

import example.products.Product;

import java.util.List;

public interface ICustomer {
    public void checkout(List<Product> items);
}
