package jioc.xml;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * The main data structure for storing the XML pea with its corresponding fields and attributes.
 *
 * @see jioc.configurer.XMLApplicationConfig
 */
public class XMLPea<T> {
    private String id;
    private Class<T> c;
    private String initMethod;
    private HashMap<String, String> fields;

    /**
     * Default constructor for the XMLPea data structure.
     *
     * @see jioc.configurer.XMLApplicationConfig
     */
    public XMLPea() {
        this.fields = new HashMap<String, String>();
    }

    /**
     * Constructor with arguments for the XMLPea data structure.
     *
     * @param id unique identifier attribute specified in the XML pea tag.
     * @param c Class object representing the class of the pea in a running Java application.
     * @see jioc.configurer.XMLApplicationConfig
     * @see java.lang.Class#forName(String)
     */
    public XMLPea(String id, Class c) {
        this.id = id;
        this.c = c;
        this.fields = new HashMap<String, String>();
    }

    public String getId() {
        return id;
    }

    public Class<T> getC() {
        return c;
    }

    public HashMap<String, String> getFields() {
        return fields;
    }

    public void setId(String id) {
        this.id = id;
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

    public void addField(String name, String ref){
        this.fields.put(name, ref);
    }

    public String getReferenceByField(String name){
        if(this.fields == null || this.fields.isEmpty()) return null;
        return this.fields.get(name);
    }

    public List<String> getFieldsByReference(String ref){
        if(this.fields == null || this.fields.isEmpty()) return null;

        List<String> names = new ArrayList<>();
        this.fields.forEach((k,v) -> {
            if(v.equals(ref)){
                names.add(k);
            }
        });

        return names;
    }
}
