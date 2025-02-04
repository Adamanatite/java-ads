package data_types;

public class Stack<Item> {

	private Node head; // head of stack
	
	
	private class Node{
		private Item key; 
		private Node next; 

		public Node(Item key) {
			this.key = key;
			this.next = null;
		}
	}
	
	
	public Stack(){
		head = null;
	}
	
	
	//METHODS
	
	
	private void push(Node x) {
		
		x.next = this.head;
		this.head = x;

	}	
	
	
	public void push(Item x) {
		//create node and add
		Node n = new Node(x);
		this.push(n);
	}
	
	
	public Item peek() {
		return this.head.key;
	}
	
	
	private boolean isEmpty() {
		return (this.head == null);
	}
	
	
	public Item pop() {

		if(this.isEmpty()) {
			return null;
		}
		
		Item i = this.head.key;
		this.head = this.head.next;
		return i;
		
	}
	
	public void print() {
		if (this.isEmpty()) {
			System.out.println("<Empty Stack>");
			return;
		}
		
		Node n = this.head;
		System.out.print(n.key);
		
		while (n.next != null) {
			n = n.next;
			System.out.println(" -> " + n.key);
		}
		
		System.out.print("\n");
		
	}
	
	
}
