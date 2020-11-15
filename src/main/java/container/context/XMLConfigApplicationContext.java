package container.context;

import container.chickpeas.XMLChickpea;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicReference;

public class XMLConfigApplicationContext<T> implements JIOCApplicationContext {
    private File config;
    private Document document;
    private HashMap<String, XMLChickpea<T>> chickpeas;

    public XMLConfigApplicationContext(String s) throws Exception {
        this.config = new File(s);
        this.document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(this.config);
        this.init();
    }

    private void init() throws Exception{
        this.chickpeas = new HashMap<String, XMLChickpea<T>>();
        NodeList nodes = this.document.getElementsByTagName("chickpea");

        for(int i=0; i<nodes.getLength(); i++){
            Node node = nodes.item(i);

            if(node.getNodeType() == Node.ELEMENT_NODE){
                Element nElement = (Element) node;

                String id = nElement.getAttribute("id");
                String className = nElement.getAttribute("class");
                Class c = Class.forName(className);
                XMLChickpea chickpea = new XMLChickpea(id, c);

                NodeList fields = nElement.getElementsByTagName("field");
                for(int j=0; j<fields.getLength(); j++){
                    Node nField = fields.item(j);

                    if(nField.getNodeType() == Node.ELEMENT_NODE){
                        Element eField = (Element) nField;
                        String fieldName = eField.getAttribute("name");
                        String ref = eField.getAttribute("ref");

                        chickpea.addField(fieldName, ref);
                    }
                }

                this.chickpeas.put(id, chickpea);
            }
        }
    }

    public <T> T instantiateChickpea(XMLChickpea<T> chickpea) throws Exception{
        T obj = (T) chickpea.getC().getDeclaredConstructor().newInstance();
        chickpea.getFields().forEach((k,v) -> {
            XMLChickpea field = this.chickpeas.get(v);
            try {
                Object fObj = instantiateChickpea(field);
                Field f = chickpea.getC().getDeclaredField(k);
                String setterName = "set" + k.substring(0,1).toUpperCase() + k.substring(1);
                System.out.println(field.getC().getInterfaces()[0]);
                Method setterMethod = chickpea.getC().getMethod(setterName, field.getC().getInterfaces()[0]);
                setterMethod.invoke(obj, field.getC().getInterfaces()[0].cast(fObj));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        return obj;
    }

    @Override
    public <T> T getChickpea(Class<T> classObject) throws Exception {
        AtomicReference<T> out = new AtomicReference<>();

        this.chickpeas.forEach((k,v) -> {
            if(classObject.isAssignableFrom(v.getC())){
                try {
                    out.set((T) instantiateChickpea(v));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        return classObject.cast(out.get());
    }
}
