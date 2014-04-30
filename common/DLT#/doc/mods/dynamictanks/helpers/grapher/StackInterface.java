package doc.mods.dynamictanks.helpers.grapher;

public interface StackInterface<T> {

    public boolean isEmpty();

    public void put(T newItem);

    public T get();

    public int size();

    public String toString();
}
