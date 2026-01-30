package data_types;

/**
 * Provides functionality for the stack abstract data structure using a linked list implementation
 * */
public class Stack<Item> {

	private Node head; // head of stack

	/*Linked list node containing a value*/
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
	
	/*Adds a new node to the stack*/
	private void push(Node x) {
		
		x.next = this.head;
		this.head = x;

	}	
	
	/*Adds a new item to the stack*/
	public void push(Item x) {
		//create node and add
		Node n = new Node(x);
		this.push(n);
	}
	
	/*Returns the next value on the stack*/
	public Item peek() {
		return this.head.key;
	}
	
	/*Returns if the stack is empty*/
	public boolean isEmpty() {
		return (this.head == null);
	}
	
	/*Removes the next node from the stack and returns its value*/
	public Item pop() {

		if(this.isEmpty()) {
			return null;
		}
		
		Item i = this.head.key;
		this.head = this.head.next;
		return i;
		
	}
	
	/*Prints a readable representation of the stack*/
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
