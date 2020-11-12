package example.dao;

import example.products.Product;

import java.util.List;

public interface IProductDao {
    public Product getProduct(int id);
    public void buyProduct(int id);
    public void buyListProducts(List<Integer> ids);
}
