package example;

import container.context.JIOCApplicationContext;
import container.context.XMLConfigApplicationContext;
import example.customer.Customer;
import example.customer.ICustomer;
import example.dao.IProductDao;
import example.dao.ProductDaoImpl;
import example.products.Product;
import example.products.SmartPhone;
import example.products.TV;

import java.io.File;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void dependencyInjection_StaticInstantiation(List<Product> productList){
        ProductDaoImpl dao = new ProductDaoImpl();
        Customer customer = new Customer();
        customer.setDao(dao);
        customer.checkout(productList);
    }

    public static void dependencyInjection_DynamicInstantiation(List<Product> productList) throws Exception{
        Scanner scanner = new Scanner(new File("config.txt"));

        String daoClassName = scanner.nextLine();
        Class daoClass = Class.forName(daoClassName);
        IProductDao dao = (IProductDao) daoClass.getDeclaredConstructor().newInstance();

        String customerClassName = scanner.nextLine();
        Class customerClass = Class.forName(customerClassName);
        ICustomer customer = (ICustomer) customerClass.getDeclaredConstructor().newInstance();

        Method method = customerClass.getMethod("setDao", IProductDao.class);
        method.invoke(customer, dao);

        customer.checkout(productList);

        scanner.close();
    }

    public static void dependencyInjection_JIOC(List<Product> productList) throws Exception {
        JIOCApplicationContext ctx = new XMLConfigApplicationContext("config.xml");

        // IProductDao dao = ctx.getChickpea(IProductDao.class);
        // ICustomer customer = ctx.getChickpea("ICustomer");
    }

    public static void main(String[] args) throws Exception {
        List<Product> productList = new ArrayList<Product>();
        Product tv = new TV("Samsung", 1212);
        Product smartphone = new SmartPhone("Huawei", 730);
        productList.add(tv);
        productList.add(smartphone);

        // Dependency Injection using static instantiation

        // Dependency Injection using dynamic instantiation

        // JIOC Dependency Injection
        dependencyInjection_JIOC(productList);
    }
}
