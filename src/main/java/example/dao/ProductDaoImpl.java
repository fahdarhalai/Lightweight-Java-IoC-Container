package example.dao;

import example.products.Product;
import jioc.annotation.Pea;

import java.util.List;

@Pea
public class ProductDaoImpl implements IProductDao {
    public Product getProduct(int id) {
        return new Product(id, "Sample Product", 999);
    }

    public void buyProduct(int id) {
        System.out.println(String.format("- Item %d bought.", id));
    }

    public void buyListProducts(List<Integer> ids) {
        System.out.println("Checkout List:");
        for(int id:ids){
            System.out.print("\t");
            this.buyProduct(id);
        }
        System.out.println("Happy shopping :)");
    }
}
