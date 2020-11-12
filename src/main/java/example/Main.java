package example;

import container.context.JIOCApplicationContext;
import container.context.XMLConfigApplicationContext;
import example.products.Product;
import example.products.SmartPhone;
import example.products.TV;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws Exception {
        List<Product> productList = new ArrayList<Product>();
        Product tv = new TV("Samsung", 1212);
        Product smartphone = new SmartPhone("Huawei", 730);
        productList.add(tv);
        productList.add(smartphone);

        // Loose coupling using static instantiation
        /*ProductDaoImpl dao = new ProductDaoImpl();
        Customer customer = new Customer();
        customer.setDao(dao);
        customer.checkout(productList);*/

        // Loose coupling using dynamic instantiation
        /*Scanner scanner = new Scanner(new File("config.txt"));

        String daoClassName = scanner.nextLine();
        Class daoClass = Class.forName(daoClassName);
        IProductDao dao = (IProductDao) daoClass.getDeclaredConstructor().newInstance();

        String customerClassName = scanner.nextLine();
        Class customerClass = Class.forName(customerClassName);
        ICustomer customer = (ICustomer) customerClass.getDeclaredConstructor().newInstance();

        Method method = customerClass.getMethod("setDao", IProductDao.class);
        method.invoke(customer, dao);

        customer.checkout(productList);*/

        // JIOC Dependency Injection
        JIOCApplicationContext ctx = new XMLConfigApplicationContext("config.xml");

        //IProductDao dao = ctx.getChickpea(IProductDao.class);
        //ICustomer customer = ctx.getChickpea("ICustomer");

    }
}
