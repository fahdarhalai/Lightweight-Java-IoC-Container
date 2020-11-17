package jioc.xml;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @param <T>
 */
public class XMLChickpea<T> {
    /**
     *
     */
    private String id;
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
    private HashMap<String, String> fields;

    /**
     *
     */
    public XMLChickpea() {
        this.fields = new HashMap<String, String>();
    }

    /**
     * @param id
     * @param c
     */
    public XMLChickpea(String id, Class c) {
        this.id = id;
        this.c = c;
        this.fields = new HashMap<String, String>();
    }

    /**
     * @return
     */
    public String getId() {
        return id;
    }

    /**
     * @return
     */
    public Class<T> getC() {
        return c;
    }

    /**
     * @return
     */
    public HashMap<String, String> getFields() {
        return fields;
    }

    /**
     * @param id
     */
    public void setId(String id) {
        this.id = id;
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

    /**
     * @param name
     * @param ref
     */
    public void addField(String name, String ref){
        this.fields.put(name, ref);
    }

    /**
     * @param name
     * @return
     */
    public String getReferenceByField(String name){
        if(this.fields == null || this.fields.isEmpty()) return null;
        return this.fields.get(name);
    }

    /**
     * @param ref
     * @return
     */
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
