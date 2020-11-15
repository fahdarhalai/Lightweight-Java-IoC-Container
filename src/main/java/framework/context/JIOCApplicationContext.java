package container.context;

import javax.xml.xpath.XPathExpressionException;

public interface JIOCApplicationContext {

    public <T> T getChickpea(Class<T> classObject) throws XPathExpressionException, Exception;
}
