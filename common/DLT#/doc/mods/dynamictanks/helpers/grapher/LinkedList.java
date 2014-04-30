package doc.mods.dynamictanks.helpers.grapher;

class LinkedList<T> implements LinkedListInterface<T> {

	Node<T> head 		 = null;
	private int size     = 0;

	public LinkedList() {}

	public void insert(int index, T element) {
		
		if (index == 0)
			if (head == null)
				head = new Node<T>(element);
			else {
				Node<T> tempNode = head;
				head = new Node<T>(element, tempNode);
			}
		else {
			Node<T> tempNode   = null;
			Node<T> beforeNode = null;

			tempNode   = getNode(index);
			beforeNode = getNode(index - 1);

			beforeNode.next = new Node<T>(element, tempNode);
		}
		size++;
	}

	public String get(int index) {
		
		return get(index, 0, head).toString();
	}

	public Node<T> getNode(int index) {
		
		return get(index, 0, head);
	}

	public Node<T> get(int index, int count, Node<T> current) {			

		if (index == count)
			return current;

		return get(index, count + 1, current.next);
	}

	public void remove(int index) {
		
		if (index == 0)
			head = head.next;
		else {
			Node<T> tempNode   = null;
			Node<T> beforeNode = null;

			beforeNode   = getNode(index - 1);
			tempNode     = index + 1 > size ? null : getNode(index + 1);

			beforeNode.next = tempNode;
		}

		size--;
	}

	public int size() {
		
		return size;
	}

	public String toString() {
		
		for (int i = 0; i < size; i++)
			System.out.println(get(i));

		return "";
	}

	public static void main(String[] args) {

		LinkedList<String> list = new LinkedList<String>();
		list.insert(0, new String("middle"));
		list.insert(0, new String("first"));
		String s = list.get(1);
		System.out.println("Item at index 1 is \"" + s + "\"");
		list.insert(2, new String("last"));
		System.out.println(list);

		LinkedList<Double> listd = new LinkedList<Double>();
		listd.insert(0, new Double(3.14));
		listd.insert(0, new Double(23.123));
		listd.insert(0, new Double(-0.32));
		System.out.println(listd);
		listd.remove(1);
		System.out.println(listd.get(1));
		System.out.println("Size of listd is " + listd.size());
		System.out.println(listd);
	}

	@Override
	public boolean isEmpty() {
		
		return head == null;
	}

	@Override
	public void addFirst(T newItem) {
		
		insert(0, newItem);
	}

	@Override
	public void addLast(T newItem) {

		insert(size(), newItem);
	}

	@Override
	public T removeFirst() {

		if (isEmpty())
			return null;

		T toReturn = getNode(0).item;
		remove(0);

		return toReturn;
	}
}

