package jioc.annotation;

/**
 * The main data structure for storing a component annotated by @{@link jioc.annotation.Pea}.
 *
 * @Author: Fahd Arhalai
 * @see jioc.configurer.AnnotationApplicationConfig
 */
public class AnnotatedPea<T> {
    private Class<T> c;
    private String initMethod;

    /**
     * Default constructor for the {@link jioc.annotation.AnnotatedPea} data structure.
     *
     * @see jioc.configurer.AnnotationApplicationConfig
     */
    public AnnotatedPea() { }

    /**
     * Constructor with arguments for the {@link jioc.annotation.AnnotatedPea} data structure.
     *
     * @param c Class object representing the class of the pea in a running Java application.
     * @param initMethod the name of the method to be executed right after constructor call.
     * @see jioc.configurer.AnnotationApplicationConfig
     */
    public AnnotatedPea(Class<T> c, String initMethod) {
        this.c = c;
        this.initMethod = initMethod;
    }

    public Class<T> getC() {
        return c;
    }

    public void setC(Class<T> c) {
        this.c = c;
    }

    public String getInitMethod() {
        return initMethod;
    }

    public void setInitMethod(String initMethod) {
        this.initMethod = initMethod;
    }
}
