package data_types;

/**
 * Provides the functionality of a priority queue abstract data structure, using a linked list
 * */
public class PriorityQueue<Item> {

	/**Linked list node containing a value and a priority*/
	private class Node implements Comparable<Node> {
		private Item key; 
		private int priority;
		private Node next;

		public Node(Item key, int priority) {
			this.key = key;
			this.priority = priority;
			this.next = null;
		}

		@Override
		public int compareTo(PriorityQueue<Item>.Node o) {

			if (this.priority > o.priority) {
				return 1;
			}
			if (this.priority < o.priority) {
				return -1;
			}
			return 0;
		}
	}
	
	private Node head;
				
		//METHODS
		
		/**Add a new node to the queue*/
		private void push(Node x) {
			
			if(this.head == null) {
				this.head = x;
				return;
			}
			
			Node m = this.head;
			Node n = this.head;
			
			while(n.next != null && n.compareTo(x) >= 0) {
				m = n;
				n = n.next;
			}
			
			m.next = x;
			x.next = n;

		}	
		
		/**Add a new item to the queue*/
		public void push(Item x, int priority) {
			//create node and add
			Node n = new Node(x, priority);
			this.push(n);
		}
		
		/**Check the front of the queue*/
		public Item peek() {
			return this.head.key;
		}
		
		/**Returns if the queue is empty*/
		public boolean isEmpty() {
			return (this.head == null);
		}
		
		/**Removes the next node from the queue and returns it*/
		public Item pop() {

			if(this.isEmpty()) {
				return null;
			}
			
			Item i = this.head.key;
			this.head = this.head.next;
			return i;
			
		}
		
		/**Prints a readable representation of the queue*/
		public void print() {
			if (this.head == null) {
				System.out.println("<Empty Queue>");
				return;
			}
			
			Node n = this.head;
			System.out.print(n.key + "[" + n.priority + "]");
			
			while (n.next != null) {
				n = n.next;
				System.out.println(" -> " + n.key + "[" + n.priority + "]");
			}
				
			System.out.print("\n");
		}
		
		
	}
