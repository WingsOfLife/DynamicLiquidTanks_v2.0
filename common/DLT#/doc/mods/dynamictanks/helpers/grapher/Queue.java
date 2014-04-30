package doc.mods.dynamictanks.helpers.grapher;


public class Queue<T> implements StackInterface<T> {

	LinkedList<T> queueElements = new LinkedList<T>();

	@Override
	public boolean isEmpty() {

		return queueElements.head == null;
	}

	@Override
	public void put(T newItem) {

		queueElements.addLast(newItem);
	}

	@Override
	public T get() {

		if (isEmpty())
			return null;

		try {
			return queueElements.removeFirst();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public int size() {

		return queueElements.size();
	}
}

