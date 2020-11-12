package container.context;

public interface JIOCApplicationContext {

    public <E, T> T getChickpea(Class<E> classObject);
    public <E, T> T getChickpea(String classString);
}
