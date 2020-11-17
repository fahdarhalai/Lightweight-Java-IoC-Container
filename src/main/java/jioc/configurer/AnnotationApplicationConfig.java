package jioc.annotation.configurer;

import jioc.ApplicationConfig;
import jioc.xml.chickpeas.XMLChickpea;

import java.util.HashMap;

public class AnnotationApplicationConfig<T> implements ApplicationConfig {
    private HashMap<String, XMLChickpea<T>> chickpeas;

    public void init() {

    }



    @Override
    public <T> T getChickpea(Class<T> classObject) {
        return null;
    }
}
