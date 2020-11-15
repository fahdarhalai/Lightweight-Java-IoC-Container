package container.chickpeas;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class XMLChickpea<T> implements IXMLChickpea{
    private String id;
    private Class<T> c;
    private HashMap<String, String> fields = new HashMap<String, String>(); // <FieldName, Reference>

    public XMLChickpea() { }

    public XMLChickpea(String id, Class c) {
        this.id = id;
        this.c = c;
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

    public void setFields(HashMap<String, String> fields) {
        this.fields = fields;
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
