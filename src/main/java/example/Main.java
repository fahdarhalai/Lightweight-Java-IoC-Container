package example;

import example.customer.Customer;
import example.customer.ICustomer;
import example.dao.IProductDao;
import example.dao.ProductDaoImpl;
import example.products.Product;
import example.products.SmartPhone;
import example.products.TV;
import framework.context.JIOCApplicationContext;
import framework.context.XMLConfigApplicationContext;

import java.io.File;
import java.lang.reflect.Method;
import java.util.Scanner;

public class Main {

    public static ICustomer dependencyInjection_StaticInstantiation(){
        IProductDao dao = new ProductDaoImpl();
        Customer customer = new Customer();
        customer.setDao(dao);

        return customer;
    }

    public static ICustomer dependencyInjection_DynamicInstantiation() throws Exception{
        Scanner scanner = new Scanner(new File("config.txt"));

        String daoClassName = scanner.nextLine(); // "example.dao.ProductDaoImpl"
        Class daoClass = Class.forName(daoClassName);
        IProductDao dao = (IProductDao) daoClass.getDeclaredConstructor().newInstance();

        String customerClassName = scanner.nextLine(); // example.customer.Customer
        Class customerClass = Class.forName(customerClassName);
        ICustomer customer = (ICustomer) customerClass.getDeclaredConstructor().newInstance();

        // customer.setDao(dao);

        Method method = customerClass.getMethod("setDao", IProductDao.class);
        method.invoke(customer, dao);

        scanner.close();

        return customer;
    }

    public static ICustomer dependencyInjection_JIOC() throws Exception {
        JIOCApplicationContext ctx = new XMLConfigApplicationContext("config.xml");
        ICustomer customer = ctx.getChickpea(ICustomer.class);

        return customer;
    }

    public static void main(String[] args) throws Exception {
        // CDI
        JIOCApplicationContext ctx = new XMLConfigApplicationContext("config.xml");
        ICustomer customer = ctx.getChickpea(Customer.class);

        // Products
        Product tv = new TV("Samsung", 1212);
        Product smartphone = new SmartPhone("Huawei", 730);

        // Add products to basket
        customer.addToBasket(tv);
        customer.addToBasket(smartphone);

        // Checkout
        customer.checkout();
    }
}
