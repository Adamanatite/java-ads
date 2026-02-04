package data_types.sets;

import java.util.Arrays;

public class TreeSet<Item extends Comparable<Item>> implements AbstractSet<Item>{

	private Node root; // root of BST
	private int size; //size of BST
	
	
	private class Node{
		private Item key; // value held by node
		private Node left, right, p; //left and right subtree and parent node

		public Node(Item key) {
			this.key = key;
			this.left = null;
			this.right = null;
			this.p = null;
		}
	}
	
	
	public TreeSet(){
		root = null;
		size = 0;
	}
	
	
	//METHODS
	
	
	/**
	 * Converts this set to a ListSet
	 *
	 * @return l This set as a ListSet
	 */
	public ListSet<Item> asList(){
		//start recursion at root
		ListSet<Item> l = new ListSet<Item>();
		this.asList(l, this.root);
		return l;
	}
	
	
	/**
	 * Converts a subtree with root x to a ListSet
	 *
	 * @param x The root of the subtree
	 * @return l This set as a ListSet
	 */
	private void asList(ListSet<Item> L, Node x){
		//traverse tree in order and add each element to list
		if(x != null) {
			asList(L, x.left);
			L.insert_tail(x.key);
			asList(L, x.right);	
		}
	}
	
	
	/**
	 * Gets the node with minimum value in the subtree with root x
	 *
	 * @param x The root of the subtree
	 * @return min The minimum node
	 */
	public Node min(Node x) {
		//Go to end of leftmost branch
		while(x.left != null) {
			x = x.left;
		}
		return x;
	}
	
	
	/**
	 * Gets the minimum value in the subtree with root x
	 *
	 * @param x The root of the subtree
	 * @return k The minimum value
	 */
	public Item min_value(Node x) {
		return min(x).key;
	}
	
	
	/**
	 * Gets the node with maximum value in the subtree with root x
	 *
	 * @param x The root of the subtree
	 * @return max The maximum node
	 */
	public Node max(Node x) {
		//Go to end of rightmost branch
		while(x.right != null) {
			x = x.right;
		}
		return x;
	}
	
	
	/**
	 * Gets the maximum value in the subtree with root x
	 *
	 * @param x The root of the subtree
	 * @return k The maximum value
	 */
	public Item max_value(Node x) {
		return max(x).key;
	}
	
	
	/**
	 * Adds a node with a given value to the set
	 *
	 * @param z The node to add
	 */
	private void add (Node z) {
		Node y = null;
		Node x = this.root;
		
		//Traverse tree to find place for node value
		while(x != null) {
			y = x;
			//Do not add duplicates
			if(z.key.compareTo(x.key) == 0) {		
				return;
			}
			else if(z.key.compareTo(x.key) < 0) {
				x = x.left;
			}
			else {x = x.right;}
		}
		//Set nodes parent to node before
		z.p = y;
		//Set new node as left or right child of parent node (or as root if no parent)
		if(y == null) {
			this.root = z;
		}
		else if(z.key.compareTo(y.key) < 0) {
			y.left = z;
		}
		else {
			y.right = z;
		}
		this.size++;
	}
	
	
	@Override
	/**
	 * Adds an item to the set
	 *
	 * @param k The item to add
	 */
	public void add(Item k) {
		//Create node to add
		Node n = new Node(k);
		this.add(n);
	}
	
	
	/**
	 * Searches the subtree with root x for a given value
	 *
	 * @param x The root of the subtree
	 * @param k The value to search for
	 * @param n The node with given value, if it exists
	 */
	private Node Search(Node x, Item k) {
		//Base case if current node is empty or equal to item
		if(x == null || x.key.compareTo(k) == 0) {
			return x;
		}
		//Traverse right or left subbranch
		if(x.key.compareTo(k) > 0) {
			return Search(x.left, k);
		}
		return Search(x.right, k);
	}
	
	
	/**
	 * Searches the tree for a given value
	 *
	 * @param k The value to search for
	 * @return n The node with given value, if it exists
	 */
	public Node Search(Item k) {
		//Start recursion at root
		return Search(this.root, k);
	}
	
	
	/**
	 * Removes a node with a value from the set, if it exists
	 *
	 * @param z The node to remove
	 */
	private void remove(Node z) {
		//If no left branch, cut element from right branch
		if(z.left == null) {
			transplant(z, z.right);
			this.size--;
			return;
		}
		//If no right branch, cut element from left branch
		if(z.right == null) {
			transplant(z, z.left);
			this.size--;
			return;
		}
		
		//Find smallest element in right subtree
		Node y = this.min(z.right);
		
		if(y.p != z) {
			//Remove z and move its right subtree to y
			this.transplant(y, y.right);
			y.right = z.right;
			y.right.p = y;
		}
		//Remove z and move its left subtree to y
		this.transplant(z, y);
		y.left = z.left;
		y.left.p = y;
		
		this.size--;
		
	}
	
	
	/**
	 * Replace a node u with a different node v in the tree
	 *
	 * @param u The node to replace
	 * @param v The new node to be inserted
	 */
	private void transplant(Node u, Node v) {
		if(u.p == null) {
			this.root = v;
		}
		//If u is a left subtree, replace the parents left subtree with v
		else if(u == u.p.left) {
			u.p.left = v;
		}
		//Otherwise replace right subtree
		else {u.p.right = v;}
		//Give v its new parent (u's previous parent)
		if(v != null) {
			v.p = u.p;
		}
	}
	
	
	@Override
	/**
	 * Removes a value from the set, if it exists
	 *
	 * @param k The value to remove
	 */
	public void remove(Item k) {
		//Remove node if found
		Node n = this.Search(k);
		if(n != null) {
			this.remove(n);
		}
	}

	
	/**
	 * Returns true if the value is in the set, false otherwise
	 *
	 * @param k The value to search for
	 * @return is_element Whether the element is in the list or not
	 */
	@Override
	public boolean is_element(Item k) {
		//return if element can be found
		return this.Search(k) != null;
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
	public TreeSet<Item> union(AbstractSet<Item> T) {
		ListSet<Item> u;
		// Convert trees to lists, union lists, convert to array and create balanced tree
		if(T instanceof TreeSet<?>) {
			u = this.asList().union(((TreeSet<Item>) T).asList());
		} else {
			u = this.asList().union((ListSet<Item>) T);
		}
		
		return createBalancedTree(u.toArray());
		
		
	}

	
	@Override
	/**
	 * Performs the intersection operation with this set and another set (only keeps values present in both sets)
	 *
	 * @param T The set to intersect with this set
	 * @return x The intersected set containing only elements present in both this set and T
	 */
	public TreeSet<Item> intersection(AbstractSet<Item> T) {
		ListSet<Item> x;
		// Convert trees to lists, intersect lists, convert to array and create balanced tree
		if(T instanceof TreeSet<?>) {
			x = this.asList().intersection(((TreeSet<Item>) T).asList());
		} else {
			x = this.asList().intersection((ListSet<Item>) T);
		}
		
		return createBalancedTree(x.toArray());
	}

	
	@Override
	/**
	 * Performs the difference operation with this set and another set (only keeps values present in this set and not the other)
	 *
	 * @param T The set to difference with this set
	 * @return d The set containing only elements present in this set and not T
	 */
	public TreeSet<Item> difference(AbstractSet<Item> T) {
		ListSet<Item> d;
		// Convert trees to lists, difference lists, convert to array and create balanced tree
		if(T instanceof TreeSet<?>) {
			d = this.asList().difference(((TreeSet<Item>) T).asList());
		} else {
			d = this.asList().difference((ListSet<Item>) T);
		}
		
		return createBalancedTree(d.toArray());
	}

	
	/**
	 * Creates a balanced tree from a subarray of items
	 *
	 * @param A The array containing the items
	 * @param l The left index of the subarray
	 * @param r The right index of the subarray
	 * @return n The root of the balanced tree created
	 */
	private Node createBalancedTree(Item[] A, int l, int r){
		//If left and right indices overlap, return an empty node
		if(l > r) {
			return null;
			}
		//If pointers are the same, return single node
		if(l == r) {
			return new Node(A[r]);
		}
		//Create node from midpoint
		int mid = (l + r) / 2;
		Node n = new Node(A[mid]);
		//Create left and right subtrees from left and right subarrays
		n.left = createBalancedTree(A,l, mid-1);
		n.right = createBalancedTree(A,mid+1, r);
		return n;
	}
	
	
	/**
	 * Creates a balanced tree from an array of items
	 *
	 * @param A The array containing the items
	 * @return n The root of the balanced tree created
	 */
	private TreeSet<Item> createBalancedTree(Item[] A) {
		System.out.println("Array: " + Arrays.toString(A));
		//If array is empty, return empty tree
		if(A == null) {
			return new TreeSet<Item>();
		}
		
		//Recursively create balanced tree
		Node n = createBalancedTree(A, 0, A.length - 1);
		//Create tree object to return
		TreeSet<Item> T = new TreeSet<Item>();
		T.root = n;
		T.size = A.length;
		return T;
	}
	
	
	/**
	 * Returns the height of the subtree with root x
	 *
	 * @param x The root of the subtree
	 * @return h The height of the subtree
	 */
	private int height(Node x) {
		//base case for leaf element
		if(x == null) {
			return 0;
		}
		//Recursively find longest branch and add up each node along it
		return 1 + Math.max(height(x.left), height(x.right));
	}
	
	
	/**
	 * Returns the height of the tree
	 *
	 * @return h The height of the tree
	 */
	public int height() {
		//start recursion at root
		return height(this.root);
	}
	
	
	/**
	 * Performs in-order traversal of the subtree with root x, printing the results
	 *
	 * @return x The root of the subtree
	 */
	private void inOrder(Node x) {
		//Print elements in order
		if(x != null) {
			inOrder(x.left);
			System.out.print(x.key + " ");
			inOrder(x.right);	
		}
	}
	
	
	/**
	 * Performs in-order traversal of the tree, printing the results
	 */
	@Override
	public void print() {
		//Print if empty
		if(this.set_empty()) {
			System.out.println("Empty set");
			return;
		}
		//Recursively print tree
		inOrder(this.root);
		System.out.println();
	}
	
	
}
