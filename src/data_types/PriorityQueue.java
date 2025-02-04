package data_types;

public class PriorityQueue<Item> {

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
		
		
		private void push(Node x) {
			
			if(this.head == null) {
				this.head = x;				
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
		
		
		public void push(Item x, int priority) {
			//create node and add
			Node n = new Node(x, priority);
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
