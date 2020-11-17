package jioc.configurer;

/**
 * Main interface to provide configuration for an application.
 * It provides a simple factory for components called Peas to be managed by the JIOC framework.
 *
 * @Author: Fahd Arhalai
 * @see jioc.configurer.XMLApplicationConfig
 * @see jioc.configurer.AnnotationApplicationConfig
 */
public interface ApplicationConfig {

    /**
     * Return an instance of the class that implements the interface.
     * There should be no ambiguity when the JIOC scanning mechanism looks for an implementation registred using either XML-based or annotation-based configuration
     *
     * @param classObject interface type the pea must match
     * @return an instance of the pea
     * @see jioc.configurer.AnnotationApplicationConfig#getPea(Class)
     * @see jioc.configurer.XMLApplicationConfig#getPea(Class) 
     */
    public <T> T getPea(Class<T> classObject);
}
