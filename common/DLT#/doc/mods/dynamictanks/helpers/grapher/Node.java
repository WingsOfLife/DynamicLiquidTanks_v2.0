package doc.mods.dynamictanks.helpers.grapher;

public class Node<T> {

	protected T       item;
	protected Node<T> next;

	Node (T itemArg) {
		
		item = itemArg;
		next = null;
	} 

	Node (T itemArg, Node<T> nextArg) {
		
		item = itemArg;
		next = nextArg;
	} 

	public String toString() {
		
		return item.toString();
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void main(String [] args) {

		Node firstNode = new Node(new String("last"));
		firstNode = new Node(new String("middle"),firstNode);
		firstNode = new Node(new String("first"),firstNode);

		for (Node current = firstNode; current != null; current = current.next)
			System.out.println((String)current.item + ' ');
	}
}
