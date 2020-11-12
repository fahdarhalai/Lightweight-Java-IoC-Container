package container.context;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.List;

public class XMLConfigApplicationContext implements JIOCApplicationContext {
    private File config;
    private Document document;
    private List<Class> chickpeas;

    public XMLConfigApplicationContext(String s) throws Exception {
        this.config = new File(s);
        this.document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(this.config);
        this.init();
    }

    private void init() throws Exception{
        this.document.getDocumentElement().normalize();
        NodeList nList = this.document.getElementsByTagName("chickpea");

        for(int i=0; i<nList.getLength(); i++){
            Node nNode = nList.item(i);

            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) nNode;
                this.chickpeas.add(Class.forName(eElement.getAttribute("class")));
            }
        }
    }

    public <E,T> T getChickpea(Class<E> classObject) {
        return null;
    }

    public <E,T> T getChickpea(String classString) {
        return null;
    }
}
