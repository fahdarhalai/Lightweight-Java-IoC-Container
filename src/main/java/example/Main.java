package example;

import example.customer.Customer;
import example.customer.ICustomer;
import example.dao.IProductDao;
import example.dao.ProductDaoImpl;
import example.products.SmartPhone;
import jioc.configurer.AnnotationApplicationConfig;
import jioc.configurer.ApplicationConfig;
import jioc.configurer.XMLApplicationConfig;

import java.io.File;
import java.lang.reflect.Method;
import java.util.Scanner;

public class Main {

    public static ICustomer static_dependencyInjection(){
        IProductDao dao = new ProductDaoImpl();
        Customer customer = new Customer();
        customer.setDao(dao);
        customer.init();

        return customer;
    }

    public static ICustomer dynamic_dependencyInjection() throws Exception{
        Scanner scanner = new Scanner(new File("config.txt"));

        // "example.dao.ProductDaoImpl"
        String daoClassName = scanner.nextLine();
        Class daoClass = Class.forName(daoClassName);
        IProductDao dao = (IProductDao) daoClass.getDeclaredConstructor().newInstance();

        // "example.customer.Customer"
        String customerClassName = scanner.nextLine();
        Class customerClass = Class.forName(customerClassName);
        ICustomer customer = (ICustomer) customerClass.getDeclaredConstructor().newInstance();

        // customer.setDao(dao);
        Method setterMethod = customerClass.getMethod("setDao", IProductDao.class);
        setterMethod.invoke(customer, dao);

        // customer.init();
        Method initMethod = customerClass.getMethod("init");
        initMethod.invoke(customer);

        scanner.close();
        return customer;
    }

    public static ICustomer JIOC_XML_dependencyInjection() throws Exception {
        ApplicationConfig ctx = new XMLApplicationConfig("config.xml");
        ICustomer customer = ctx.getPea(ICustomer.class);

        return customer;
    }

    public static ICustomer JIOC_Annotation_dependencyInjection() throws Exception {
        String[] packages = {"example"};
        ApplicationConfig config = new AnnotationApplicationConfig(packages);
        ICustomer customer = config.getPea(ICustomer.class);

        return customer;
    }

    public static void main(String[] args) throws Exception {
        // ICustomer customer = static_dependencyInjection();
        // ICustomer customer = dynamic_dependencyInjection();
        // ICustomer customer = JIOC_XML_dependencyInjection();
        ICustomer customer = JIOC_Annotation_dependencyInjection();

        SmartPhone smartPhone = new SmartPhone("Samsung", 123);
        SmartPhone tv = new SmartPhone("LG", 1234);

        customer.addToBasket(smartPhone);
        customer.addToBasket(tv);

        customer.checkout();
    }
}
