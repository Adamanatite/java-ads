package data_types.sets;

public class ListSet<Item extends Comparable<Item>> implements AbstractSet<Item>{

	private Node head, tail; // head and tail of list
	private int size; // size of set
	
	
	private class Node{
		private Item key; // sorted by key
		private Node prev, next; //next and previous elements in list

		public Node(Item key) {
			this.key = key;
			this.next = null;
			this.prev = null;
		}
	}
	
	public ListSet(){
		head = null;
		tail = null;
		size = 0;
	}
	
	
	//METHODS
	
	/**
	 * Adds a node with a given value to the set
	 *
	 * @param x The node to add
	 */
	private void add(Node x) {
		
		//If x is the first element
		if(this.head == null) {
			this.head = x;
			this.tail = x;
			this.size++;
			return;
		}
		Node y = this.head;
		
		//Skip if duplicate value
		if(y.key.compareTo(x.key) == 0) {
			return;
		}
		
		//If x is the new smallest element
		if(y.key.compareTo(x.key) > 0) {
			this.head = x;
			y.prev = x;
			x.next = y;
			this.size++;
			return;
		}
		
		//Find first element larger than key (or until end of list
		while(y.next != null && y.next.key.compareTo(x.key) < 0) {
			y = y.next;
		}
		
		if(y.next != null) {
			//skip duplicates
			if(y.next.key.compareTo(x.key) == 0) return;
			y.next.prev = x;
		}
		else {
			//set to tail if end of list
			this.tail = x;
		}
		//insert x after y
		x.next = y.next;
		y.next = x;
		x.prev = y;
		//increase list size
		this.size++;
	}	
	
	
	@Override
	/**
	 * Adds an item to the set
	 *
	 * @param k The item to add
	 */
	public void add(Item k) {
		//create node and add
		Node n = new Node(k);
		this.add(n);
	}
	
	
	/**
	 * Searches the set for a given value
	 *
	 * @param k The value to search for
	 */
	private Node search(Item k) {
		Node x = this.head;
		//loop until end of list or until an equal or larger element is found
		while(x != null && x.key.compareTo(k) < 0) {
			x = x.next;
		}
		//if element is equal, we have found it, otherwise it is not in list
		if(x != null && x.key.compareTo(k) == 0) {
			return x;
		}
		return null;
	}
	
	
	/**
	 * Removes a node with a value from the set, if it exists
	 *
	 * @param x The node to remove
	 */
	private void remove(Node x) {
		//connect node before x to node after x, or make x.next the new head
		if(x.prev != null) {
			x.prev.next = x.next;
		} else {
			this.head = x.next;
		}
		
		//connect node after x to node before x, or make x.prev the new tail
		if(x.next != null) {
			x.next.prev = x.prev;
		} else {
			this.tail = x.prev;
		}
		//decrease set size
		this.size--;
	}
	
	
	@Override
	/**
	 * Removes a value from the set, if it exists
	 *
	 * @param k The value to remove
	 */
	public void remove(Item k) {
		//remove node if found
		Node n = this.search(k);
		if(n != null) {
			this.remove(n);
		}		
	}

	
	@Override
	/**
	 * Returns true if the value is in the set, false otherwise
	 *
	 * @param k The value to search for
	 * @return is_element Whether the element is in the list or not
	 */
	public boolean is_element(Item k) {
		//return if node can be found
		return this.search(k) != null;
	}

	
	@Override
	/**
	 * Returns the current size of the set
	 *
	 * @return size The current set size
	 */
	public int set_size() {
		return this.size;
	}

	
	@Override
	/**
	 * Performs the union operation with this set and another set (combines their values)
	 *
	 * @param T The set to merge with this set
	 * @return u The united set containing all the elements from this set and T
	 */
	public ListSet<Item> union(AbstractSet<Item> T) {
		
		//If tree, convert to list
		ListSet<Item> L;
		if(T instanceof TreeSet<?>) {
			L = ((TreeSet<Item>) T).asList();
		} else {
			L = (ListSet<Item>) T;
		}
		
		//Create new list for union
		ListSet<Item> u = new ListSet<Item>();
		
		//Initialise 2 pointers to heads of lists
		Node n1 = this.head; Node n2 = L.head;
		
		while(n1 != null && n2 != null) {
			int comparison = n1.key.compareTo(n2.key);
			if(comparison <= 0) {
				//Insert smaller value and move its lists pointer on. Move both pointers on if values are equal (to avoid duplicates)
				u.insert_tail(n1.key);
				n1 = n1.next;
				if(comparison == 0) {
					n2 = n2.next;
				}
			}		
			else {
				u.insert_tail(n2.key);
				n2 = n2.next;
			}
		}
		
		//If one of the lists have not been exhausted, add the rest of its elements
		if(n1 == null) {
			while(n2 != null) {
				u.insert_tail(n2.key);
				n2 = n2.next;
			}
		}
		else{
			while(n1 != null) {
				u.insert_tail(n1.key);
				n1 = n1.next;
			}
		}
		return u;
	}

	
	@Override
	/**
	 * Performs the intersection operation with this set and another set (only keeps values present in both sets)
	 *
	 * @param T The set to intersect with this set
	 * @return x The intersected set containing only elements present in both this set and T
	 */
	public ListSet<Item> intersection(AbstractSet<Item> T) {
		
		//If tree, convert to list
		ListSet<Item> L;
		if(T instanceof TreeSet<?>) {
			L = ((TreeSet<Item>) T).asList();
		} else {
			L = (ListSet<Item>) T;
		}
		
		//Create new list to store intersection
		ListSet<Item> x = new ListSet<Item>();
		
		
		Node n1 = this.head; Node n2 = L.head;
		while(n1 != null && n2 != null) {
			int comparison = n1.key.compareTo(n2.key);
			//Move smaller values pointer on, if equal then add the element and move both pointers on
			if(comparison <= 0) {
				n1 = n1.next;			
				if(comparison == 0) {
					x.insert_tail(n2.key);
					n2 = n2.next;
				}
			}
			
			else {
				n2 = n2.next;
			}
		}
		return x;
	}

	
	@Override
	/**
	 * Performs the difference operation with this set and another set (only keeps values present in this set and not the other)
	 *
	 * @param T The set to difference with this set
	 * @return d The set containing only elements present in this set and not T
	 */
	public ListSet<Item> difference(AbstractSet<Item> T) {
		
		//If tree, convert to list
		ListSet<Item> L;
		if(T instanceof TreeSet<?>) {
			L = ((TreeSet<Item>) T).asList();
		} else {
			L = (ListSet<Item>) T;
		}
		
		//Create new list to store difference
		ListSet<Item> d = new ListSet<Item>();
		
		Node n1 = this.head; Node n2 = L.head;
		while(n1 != null && n2 != null) {
			int comparison = n1.key.compareTo(n2.key);
			//If first list value is smaller, it is not in second list so add to difference
			if(comparison < 0) {
				d.insert_tail(n1.key);
				n1 = n1.next;
			}
			else {
				//Move both pointers on or second pointer on if greater
				if(comparison == 0) n1 = n1.next;
				n2 = n2.next;
			}
		}
		
		//If we have exhausted second list, add rest of first list (as none of its last elements are in list 2)
		if(n2 == null) {
			while(n1 != null) {
				d.insert_tail(n1.key);
				n1 = n1.next;
			}
		}
		
		return d;	
	}

	
	/**
	 * Inserts a node at the tail of the set
	 *
	 * @param x The node to insert
	 */
	private void insert_tail(Node x) {
		//If no elements, make x the head
		if(this.tail == null) {
			this.head = x;
		}
		else {
			//Add x after previous tail
			this.tail.next = x;
		}
		//Connect x to previous tail
		x.prev = this.tail;

		//Make x the tail and increase size
		this.tail = x;
		x.next = null;
		this.size++;
	}
	
	
	/**
	 * Inserts a value at the tail of the set
	 *
	 * @param k The value to insert
	 */
	public void insert_tail (Item k) {
		//Create node and insert to tail
		Node n = new Node(k);
		this.insert_tail(n);
	}
	
	
	/**
	 * Converts this set to an array
	 *
	 * @return a The array containing all of the set values
	 */
	public Item[] toArray() {
		
		if(this.set_empty()) {
			return null;
		}
		
		Node n = this.head;
		Item[] a = (Item[]) new Comparable[this.size];
		int i = 0;
		//Loop through list and add to corresponding index of array
		while(i < this.size && n != null) {
			a[i] = n.key;
			i++;
			n = n.next;
		}
		return a;
		
	}
	
	
	@Override
	/**
	 * Prints a readable representation of this set
	 */
	public void print() {
		
		//Print if empty
		if(this.set_empty()) {
			System.out.println("Empty set");
		}
		
		//Loop through list and add every element to print line
		Node n = this.head;
		while(n != null) {
			System.out.print(n.key + " ");
			n = n.next;
		}
		System.out.println();
	}
	
}
