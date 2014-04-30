package doc.mods.dynamictanks.helpers.grapher;

public interface LinkedListInterface<T> {

    public boolean isEmpty();

    public int size();

    public void addFirst(T newItem);

    public void addLast(T newItem);

    public T removeFirst();

    public String toString();
}
