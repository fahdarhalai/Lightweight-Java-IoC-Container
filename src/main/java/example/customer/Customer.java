package example.customer;

import example.dao.IProductDao;
import example.products.Product;

import java.util.ArrayList;
import java.util.List;

public class Customer implements ICustomer {
    private IProductDao dao;
    List<Product> basket;

    public void init(){
        this.basket = new ArrayList<>();
    }

    public void setDao(IProductDao dao) {
        this.dao = dao;
    }

    @Override
    public void addToBasket(Product p) {
        this.basket.add(p);
    }

    @Override
    public void addToBasket(List<Product> productList) {
        productList.stream().forEach((p) -> this.basket.add(p));
    }

    @Override
    public void checkout() {
        List<Integer> ids = new ArrayList<Integer>();
        this.basket.stream().forEach((p) -> ids.add(p.getId()) );
        dao.buyListProducts(ids);
    }
}
