package jioc.annotation;

/**
 * @param <T>
 */
public class AnnotatedChickpea<T> {
    /**
     *
     */
    private Class<T> c;
    /**
     *
     */
    private String initMethod;

    /**
     *
     */
    public AnnotatedChickpea() { }

    /**
     * @param c
     * @param initMethod
     */
    public AnnotatedChickpea(Class<T> c, String initMethod) {
        this.c = c;
        this.initMethod = initMethod;
    }

    /**
     * @return
     */
    public Class<T> getC() {
        return c;
    }

    /**
     * @param c
     */
    public void setC(Class<T> c) {
        this.c = c;
    }

    /**
     * @return
     */
    public String getInitMethod() {
        return initMethod;
    }

    /**
     * @param initMethod
     */
    public void setInitMethod(String initMethod) {
        this.initMethod = initMethod;
    }
}
