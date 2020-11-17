# Lightweight Java IoC Container (JIOC)
[![Project Status: Active – The project has reached a stable, usable state and is being actively developed.](https://www.repostatus.org/badges/latest/active.svg)](https://www.repostatus.org/#active)

JIOC or Java IoC, is a lightweight Inversion of Control framework for dependency injection using either XML-based configuration or annotation-based configuration.

You can browse the [javadoc](https://fahdarhalai.github.io/Lightweight-Java-IoC-Container/) for more information.

## How it works
### Test Application
#### customer/ICustomer.java
```java
public interface ICustomer {
    // Methods
}
```

#### customer/Customer.java
```java
public class Customer implements ICustomer {
    private IProductDao dao;
    // ...
    
    public void init(){ 
        // Optional: to be executed after constructor and setter call 
    }
    
    public void setDao(IProductDao dao) { this.dao = dao; }
    
    // Override methods
```

#### products/Product.java
```java
public class Product {
    // Fields
    // Constructors
    // Getters & Setters
    // ...
}
```
#### dao/IProductDao.java
```java
public interface IProductDao {
    // Methods
}
```

#### dao/ProductDaoImpl.java
```java
public class ProductDaoImpl implements IProductDao {
    // Override methods ...
}
```

### XML-based configuration
#### File config.xml
```xml
<?xml version="1.0" encoding="UTF-8" ?>
<peas>
    <pea id="d" class="example.dao.ProductDaoImpl"></pea>

    <pea id="c" class="example.customer.Customer" init-method="init">
        <field name="dao" ref="d"></field>
    </pea>
</peas>
```

#### Main.java
```java
public class Main() {
    public static void main(String[] args) throws Exception {
        ApplicationConfig ctx = new XMLApplicationConfig("config.xml");
        ICustomer customer = ctx.getPea(ICustomer.class);
    }
}
```

### Annotation-based configuration
#### Configuration using annotations
```java
@Pea(initMethod = "init")
public class Customer implements ICustomer {
    @AutoInject
    private IProductDao dao;
    ...
}
```
```java
@Pea
public class ProductDaoImpl implements IProductDao {
    ...
}
```
#### Main.java
```java
public class Main() {
    public static void main(String[] args) throws Exception {
        String[] packages = {"example"}; // packages containing the peas to be managed by JIOC
        ApplicationConfig config = new AnnotationApplicationConfig(packages);
        ICustomer customer = config.getPea(ICustomer.class);
    }
}
```

### Requirements
Java 8 or above.

### Stability
Note that the API is not stable yet and its design could be subject to changes in the future.

