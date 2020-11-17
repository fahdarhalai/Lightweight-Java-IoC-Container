package jioc.configurer;

import jioc.xml.XMLPea;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Implementation of {@link jioc.configurer.ApplicationConfig} interface providing XML-based configuration.
 * Uses a custom XML file which declares the peas to be managed by the JIOC framework.
 *
 * Example:
 * <pre>
 * {@code
 * <pea id="instance_of_B" class="com.example.B" init-method="initB"></pea>
 * <pea id="instance_of_A" class="com.example.A" init-method="initA">
 *      <field name="field_B" ref="instance_of_B"></field>
 * </pea>
 * }
 * </pre>
 *
 * NOTE: 'id' and 'class' attributes are required while 'init-method' is optional and depends on the business logic.
 */
public class XMLApplicationConfig<T> implements ApplicationConfig {
    private File config;
    private Document document;
    private HashMap<String, XMLPea<T>> peas;

    /**
     * Create a new {@link jioc.configurer.XMLApplicationConfig} which takes a string denoting the path to the XML configuration file to be scanned for components to be managed by the framework.
     *
     * @param s path to XML configuration file.
     * @throws ParserConfigurationException
     * @throws ClassNotFoundException
     * @throws IOException
     * @throws SAXException
     */
    public XMLApplicationConfig(String s) throws ParserConfigurationException, ClassNotFoundException, IOException, SAXException {
        this.config = new File(s);
        this.document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(this.config);
        this.init();
    }

    private void init() throws ClassNotFoundException {
        this.peas = new HashMap<String, XMLPea<T>>();
        NodeList nodes = this.document.getElementsByTagName("pea");

        for(int i=0; i<nodes.getLength(); i++){
            Node node = nodes.item(i);

            if(node.getNodeType() == Node.ELEMENT_NODE){
                Element nElement = (Element) node;

                String id = nElement.getAttribute("id");
                String className = nElement.getAttribute("class");
                Class c = Class.forName(className);
                XMLPea pea = new XMLPea(id, c);

                String initMethod = nElement.getAttribute("init-method");
                if(initMethod != null && !initMethod.equals("")){
                    pea.setInitMethod(initMethod);
                }

                NodeList fields = nElement.getElementsByTagName("field");
                for(int j=0; j<fields.getLength(); j++){
                    Node nField = fields.item(j);

                    if(nField.getNodeType() == Node.ELEMENT_NODE){
                        Element eField = (Element) nField;
                        String fieldName = eField.getAttribute("name");
                        String ref = eField.getAttribute("ref");

                        pea.addField(fieldName, ref);
                    }
                }

                this.peas.put(id, pea);
            }
        }
    }

    private <T> T instantiatePea(XMLPea<T> pea) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        T obj = pea.getC().getDeclaredConstructor().newInstance();

        pea.getFields().forEach((k,v) -> {
            XMLPea field = this.peas.get(v);
            try {
                Object fieldObj = instantiatePea(field);

                String setterName = "set" + k.substring(0,1).toUpperCase() + k.substring(1);
                Method setterMethod = pea.getC().getMethod(setterName, field.getC().getInterfaces()[0]);
                setterMethod.invoke(obj, field.getC().getInterfaces()[0].cast(fieldObj));

                String initName = pea.getInitMethod();
                if(initName != null && initName != "") {
                    Method initMethod = pea.getC().getMethod(initName);
                    initMethod.invoke(obj);
                }
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException e) {
                e.printStackTrace();
            }
        });

        return obj;
    }

    /**
     * Return an instance of the class that implements the interface.
     * There should be no ambiguity when the JIOC scanning mechanism looks for an implementation registered using XML pea tags.
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
