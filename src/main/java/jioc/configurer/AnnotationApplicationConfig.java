package jioc.configurer;

import jioc.annotation.AnnotatedPea;
import jioc.annotation.AutoInject;
import jioc.annotation.Pea;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

/**
 * Implementation of {@link jioc.configurer.ApplicationConfig} interface providing annotation-based configuration.
 * It allows for automatic scan of a given array of packages for any classes with the @{@link jioc.annotation.Pea} annotation, as well as dependency injection in case it encounters @{@link jioc.annotation.AutoInject} annotation.
 *
 * @Author: Fahd Arhalai
 * @see jioc.annotation.AnnotatedPea
 */
public class AnnotationApplicationConfig<T> implements ApplicationConfig {
    List<String> packages;
    private HashMap<String, AnnotatedPea<T>> peas;

    /**
     * Create a new {@link jioc.configurer.AnnotationApplicationConfig} which takes an array of package names to be scanned for components that will be managed by the framework.
     *
     * @param packages packages to be scanned by the framework.
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public AnnotationApplicationConfig(String[] packages) throws IOException, ClassNotFoundException {
        this.packages = new ArrayList<String>();

        for(String p:packages){
            this.packages.add(p);
        }

        this.init();
    }

    private void init() throws IOException, ClassNotFoundException {
        this.peas = new HashMap<String, AnnotatedPea<T>>();
        List<Class> classes = new ArrayList<Class>();

        ClassLoader cl = AnnotationApplicationConfig.class.getClassLoader();

        for(String pack:this.packages) {
            classes.addAll(getClasses(cl, pack));
        }

        for(Class c:classes){
            if(c.isAnnotationPresent(Pea.class)){
                Annotation an = c.getAnnotation(Pea.class);
                Pea cp = (Pea) an;
                AnnotatedPea pea = new AnnotatedPea(c, cp.initMethod());
                this.peas.put(c.getInterfaces()[0].getName(), pea);
            }
        }
    }

    private static List<Class> getClasses(ClassLoader cl, String pack) throws IOException, ClassNotFoundException {
        List<Class> classes = new ArrayList<Class>();

        String dottedPackage = pack.replaceAll("[/]", ".");
        URL upackage = cl.getResource(pack);

        DataInputStream dis = new DataInputStream((InputStream) upackage.getContent());
        String line = null;

        while ((line = dis.readLine()) != null) {
            if (line.endsWith(".class")) {
                classes.add(Class.forName(dottedPackage + "." + line.substring(0, line.lastIndexOf('.'))));
            } else {
                classes.addAll(getClasses(cl, pack + "/" + line));
            }
        }

        return classes;
    }

    private <T> T instantiatePea(AnnotatedPea<T> pea) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        T obj = pea.getC().getDeclaredConstructor().newInstance();

        Field[] fields = pea.getC().getDeclaredFields();
        List<Field> listFields = Arrays.stream(fields).filter(field -> field.isAnnotationPresent(AutoInject.class)).collect(Collectors.toList());

        for(Field field:listFields){
            AnnotatedPea fieldPea = this.peas.get(field.getType().getName());
            Object fieldObj = instantiatePea(fieldPea);

            String setterName = "set" + field.getName().substring(0,1).toUpperCase() + field.getName().substring(1);
            Method setterMethod = pea.getC().getMethod(setterName, field.getType());
            setterMethod.invoke(obj, field.getType().cast(fieldObj));

            String initName = pea.getInitMethod();
            if(initName != null && !initName.equals("")) {
                Method initMethod = pea.getC().getMethod(initName);
                initMethod.invoke(obj);
            }
        }

        return obj;
    }

    /**
     * Return an instance of the class that implements the interface.
     * There should be no ambiguity when the JIOC scanning mechanism looks for an implementation registered using @{@link jioc.annotation.Pea} annotation.
     *
     * @param classObject interface type the pea must implement.
     * @return an instance of the pea.
     */
    @Override
    public <T> T getPea(Class<T> classObject) {
        AtomicReference<T> out = new AtomicReference<>();

        this.peas.forEach((k, v) -> {
            if(classObject.isAssignableFrom(v.getC())){
                try {
                    out.set((T) instantiatePea(v));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        return classObject.cast(out.get());
    }

}
