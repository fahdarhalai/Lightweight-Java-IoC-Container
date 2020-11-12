package example.customer;

import example.dao.IProductDao;
import example.products.Product;

import java.util.ArrayList;
import java.util.List;

public class Customer implements ICustomer {
    private IProductDao dao;

    public void setDao(IProductDao dao) {
        this.dao = dao;
    }

    public void checkout(List<Product> items) {
        List<Integer> ids = new ArrayList<Integer>();
        for (Product p:items) {
            ids.add(p.getId());
        }
        dao.buyListProducts(ids);
    }
}
