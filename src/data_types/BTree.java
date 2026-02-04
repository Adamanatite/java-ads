package data_types;


/**
 * Provides the functionality of a B-Tree
 */
public class BTree {
	
	//Default value for the size of nodes within the tree. Nodes may contain between (t-1 and 2t-1) elements
	int DEFAULT_T = 2;
	private int t;
	
	private Node root;


	
	/**
	 * Represents a node of a b-tree, including the values and pointers to child nodes
	 */
	public class Node {
		
		// How many keys are currently stored in the node
		private int k = 0;
		
		private int[] keys;
		private Node[] children;
		
		public Node parent;

		
		/**
		 * Constructs a parentless node
		 */
		public Node() {
			this.children = new Node[2 * t];
			this.keys = new int[(2 * t) -1];
			this.parent = null;
		}
		
		
		/**
		 * Constructs a node with a given parent
		 * 
		 * @param parent The parent node of the new node
		 */
		public Node(Node parent) {
			this.children = new Node[2 * t];
			this.keys = new int[(2 * t) - 1];
			this.parent = parent;
		}

		
		/**Prints a readable version of a subtree
		 * 
		 * @param d The depth of the current iteration
		 * @param b The breadth of the current iteration
		 * */
		private void print(int d, int b) {
			
			System.out.print(d + "-" + b + "[");
			
			// [ a | b | c | - | - ]
			for(int i = 0; i < this.keys.length; i++) {
				
				if (i < this.k) {
					System.out.print(" " + this.keys[i] + " ");					
				}
				else {
					System.out.print(" - ");						
				}
				
				if(i == this.keys.length - 1) {
					break;
				}
				System.out.print("|");
			}
			
			System.out.print("] ");
			
			if(!this.isLeaf()) {System.out.println();}
			
			for(int i = 0; i < this.children.length; i++) {
				if(this.children[i] != null) {
					this.children[i].print(d + 1, i);
				}
			}
		}

		
		/**
		 * Prints a representation of the node and its children, including values contained within
		 */
		public void print() {
			this.print(0, 0);
		}
		
		
		/**
		 * True if this node is a leaf node (it has no children)
		 */
		public boolean isLeaf() {
			
			for(Node n: this.children) {
				if(n != null) {
					return false;
				}
			}
			return true;
		}	
		
		
		public void setK(int newK) {
			this.k = newK;
		}
		
		
		/**
		 * Inserts a value into the node in the correct order
		 * 
		 * @param v The value to insert 
		 * */
		public void insertNonFull(int v) {
			
			int i = this.k - 1;
			// If we're in a leaf, simply insert the key in the right place
			if(this.isLeaf()) {
				while( i >= 0 && v < this.keys[i]) {
					this.keys[i+1] = this.keys[i];
					i -= 1;
				}
				this.keys[i + 1] = v;
				this.k++;
			}

			else {
				// Get correct child index
				while(i >= 0 && v < this.keys[i]) {
					i--;
				}
				i++;
				
				// Split child index if it is full
				if(isFull(this.children[i])) {
					splitChild(this, i);
					
					// Decide if value belongs in new left or right child after node split
					if(v > this.keys[i]) {
						i++;
					}
				}
				
				//Continue recursion into child node
				this.children[i].insertNonFull(v);
			}
		}
		
		public Node getChild(int i) {
			return this.children[i];
		}
	}
	
	
	public BTree(){
		this.t = DEFAULT_T;
		root = new Node();
	}
	
	
	public BTree(int n){
		this.t = n;
		root = new Node();
		
	}	
	
	
	public Node getRoot() {
		return this.root;
	}
	
	
	private boolean isFull(Node n) {
		return (n.k == (2 * t) - 1);
	}
	
	
	private Node binaryTreeSearch(Node n, int key, int min, int max) {
		
		//Key not in this node (search appropriate child node)
		if(min > max) {
			n = n.children[min];
			//Termination condition: Reached empty node (key not in tree)
			if (n == null) {
				return null;
			}
			return binaryTreeSearch(n, key, 0, n.k - 1);
		}
		
		// Binary search this node for key
		int mid = (max + min) / 2;
		
		if(n.keys[mid] == key) {
			return n;
		}
		
		if(n.keys[mid] < key) {
			return binaryTreeSearch(n, key, mid + 1, max);
		}
		return binaryTreeSearch(n, key, min, mid - 1);
		
	}
	
	
	/** Returns the node containing the search value, or null if it is not in the database
	 * 	@param v The value to search the tree for
	 *  @return the node containing v, or null if there is no such node
	 *  */
	public Node search(int v) {
		Node n = this.root;
		if(n == null) {
			return null;
		}
		return this.binaryTreeSearch(n, v, 0, n.k - 1);
	}
	
	
	/** Prints the tree contents in a readable form */
	public void print() {
		//Print if empty
		Node n = this.root;
		if(n.k == 0) {
			System.out.println("Empty tree");
			return;
		}
		n.print();
		System.out.println();
	}
	
	
	private void splitChild (Node n, int i) {
		
		// Create new child node of the parent
		Node l = n.children[i];
		Node r = new Node(n);
		for(int j = n.k + 1; j > i + 1; j--) {
			n.children[j] = n.children[j-1];
		}
		n.children[i+1] = r;
		
		//Move median up to parent node (in correct place)
		for(int j = n.k; j > i; j--) {
			n.keys[j] = n.keys[j-1];
		}
		n.keys[i] = l.keys[t-1];
		n.k++;
		
		//Remove median from child
		l.keys[t-1] = 0;
		l.k--;
		
		//Split original child nodes and values between new nodes
		for(int j = t; j < (2 * t) - 1; j++) {
			r.keys[j - t] = l.keys[j];
			r.k++;
			l.keys[j] = 0;
			l.k--;
			
			if(!(l.isLeaf())) {
				r.children[j - t] = l.children[j];
				l.children[j] = null;
			}
		}

		//Fix off by one error (there's one more child than key
		r.children[t-1] = l.children[(2 * t) - 1];
		l.children[(2 * t) - 1] = null;
		
		
	}
	
	
	// based on https://www.youtube.com/watch?v=tT2DT9Z4H-
	
	/** Adds a value v into the tree at the correct place 
	 * 
	 * @param v The value to insert into the tree
	 * */
	public void insert(int v) {
		
		if(isFull(this.root)) {
			Node newRoot = new Node();
			newRoot.children[0] = this.root;
			this.root = newRoot;
			splitChild(newRoot, 0);
			newRoot.insertNonFull(v);
		} else {
			this.root.insertNonFull(v);
		}
		
		return;
	}
	
	
	/** Adds a list of values into the tree one by one
	 * 
	 * @param vs The list of values to insert
	 * */
	public void insertAll(int[] vs) {
		for(int v : vs) {
			this.insert(v);
		}
	}
	
	
	//Delete based on https://www.youtube.com/watch?v=pN4C8cLVc7I
	
	/**Removes a given value from the tree if it exists
	 * 
	 * @param v The value to remove
	 * */
	public void delete(int v) {
		delete(v, this.root);
	}
	
	private void delete(int v, Node n) {
		
		//Find correct place for value in node
		int i = 0;
		while(i < n.k && v > n.keys[i]) {i++;}
		
		//Leaf case - delete value if it exists
		if(n.isLeaf()) {
			if(i < n.k && n.keys[i] == v) {
				//Move all subsequent values down
				for(int j = i; j < n.k - 1; j++) {
					n.keys[j] = n.keys[j+1];
				}
				
				//Delete last value
				n.keys[n.k-1] = 0;
				n.k--;
				
			}
			return;
		}
		
		//If we find the value in the internal node, delete it and rebalance tree
		if(i < n.k && n.keys[i] == v) {
			deleteInternalNode(v, n, i);
			return;
		}
		
		//If there's a child node with space for a node to be deleted, check it for the value
		if(n.children[i].k >= this.t) {
			delete(v, n.children[i]);
			return;
		}
		
		//Child node splitting cases (figure out what they're doing)
		
		//Non edge node (not first or last child)
		if( i != 0 && i < n.children.length - 2) {
			if(n.children[i-1].k >= t) {
				deleteSibling(n, i, i-1);
			}
			else if(n.children[i+1].k >= t) {
				deleteSibling(n, i, i+1);
			}
			else {
				deleteMerge(n, i, i+1);
			}
			
		}
		//First child
		else if(i == 0) {
			if(n.children[i+1].k >= t) {
				deleteSibling(n, i, i+1);
			}
			else {
				deleteMerge(n, i, i+1);
			}
		}
		
		//Last child
		else {
			if(n.children[i-1].k >= t) {
				deleteSibling(n, i, i-1);
			}
			else {
				deleteMerge(n, i, i-1);
			}
		}
		
		//Continue into child
		delete(v, n.children[i]);
	};
	
	
	private void deleteInternalNode(int v, Node n, int i) {
		
		if(n.isLeaf()) {
			if(n.keys[i] == v) {
				//Move all subsequent values down
				for(int j = i; j < n.k - 1; j++) {
					n.keys[j] = n.keys[j+1];
				}
				
				//Delete last value
				n.keys[n.k-1] = 0;
				n.k--;
			}
			return;
		}
		
		if(n.children[i].k >= t) {
			n.keys[i] = deletePredecessor(n.children[i]);
		}
		else if(n.children[i+1].k >= t) {
			n.keys[i] = deleteSuccessor(n.children[i+1]);
		}
		else {
			deleteMerge(n, i , i+1);
			deleteInternalNode(v, n.children[i], this.t-1);
		}
	};
	
	
	private int deletePredecessor(Node n) {
		
		int x = n.k - 1;
		if(n.isLeaf()) {
			//pop largest value and return
			int v = n.keys[x];
			n.keys[x] = 0;
			n.k--;
			return v;
			
		}
		
		if(n.children[x].k >= this.t) {
			deleteSibling(n, x+1, x);
		}
		else {
			deleteMerge(n, x, x + 1);
		}
		return deletePredecessor(n.children[x]);
	};
	
	
	private int deleteSuccessor(Node n) {
		if(n.isLeaf()) {
			//pop smallest value and return
			int v = n.keys[0];
			for(int i = 0; i < n.k-1; i++) {
				n.keys[i] = n.keys[i+1];
			}
			n.keys[n.k-1] = 0;
			n.k--;
			return v;
			
		}
		
		if(n.children[1].k >= this.t) {
			deleteSibling(n, 0, 1);
		}
		else {
			deleteMerge(n, 0, 1);
		}
		return deleteSuccessor(n.children[0]);
	};
	
	
	private void deleteMerge(Node n, int i, int j) {
				
		Node cnode = n.children[i];
		
		Node finalNode = new Node();
		
		if(j > i) {
			Node rsnode = n.children[j];
			cnode.keys[cnode.k] = n.keys[i];
			cnode.k++;
			
			for (int x = 0; x < rsnode.k; x++) {
				//This might not work because of position 0?
				cnode.keys[cnode.k] = rsnode.keys[x];
				if(!rsnode.isLeaf()) {
					System.out.println(cnode.k);
					cnode.children[cnode.k] = rsnode.children[x];
				}
				cnode.k++;

			}
			if (!rsnode.isLeaf()) {
				cnode.children[cnode.k] = rsnode.children[rsnode.k];
				rsnode.children[rsnode.k] = null;
			}
			finalNode = cnode;
			
			//Remove the ith key of n
			for(int x = i; x < n.k - 1; x++) {
				n.keys[x] = n.keys[x+1];
			}
			n.keys[n.k-1] = 0;
			
			//Remove the jth child of n
			for(int x = j; x < n.k; x++) {
				n.children[x] = n.children[x+1];
			}
			n.children[n.k] = null;
			
			n.k--;
		}
		else {
			Node lsnode = n.children[j];
			lsnode.keys[lsnode.k] = n.keys[j];
			lsnode.k++;
			
			for (int x = 0; x < cnode.k; x++) {
				//This might not work because of position 0?
				lsnode.keys[cnode.k] = cnode.keys[x];
				
				if(!lsnode.isLeaf()) {
					lsnode.children[cnode.k] = cnode.children[x];
				}
				lsnode.k++;
			}
			if (!lsnode.isLeaf()) {
				lsnode.children[lsnode.k] = cnode.children[cnode.k];
				cnode.children[cnode.k] = null;
			}
			finalNode = lsnode;
			
			//Remove the jth key of n
			for(int x = j; x < n.k - 1; x++) {
				n.keys[x] = n.keys[x+1];
			}
			n.keys[n.k-1] = 0;
			
			
			//Remove the ith child of n
			for(int x = i; x < n.k; x++) {
				n.children[x] = n.children[x+1];
			}
			n.children[n.k] = null;
			
			n.k--;
		}
		
		//Set the new root if appropriate
		if(this.root == n && n.k == 0) {
			this.root = finalNode;
		}
		
	};
	
	
	private void deleteSibling(Node n, int i, int j) {
		
		Node cnode = n.children[i];
		
		// Left sibling
		if(i < j) {
			Node rsnode = n.children[j];
			cnode.keys[cnode.k] = n.keys[i];
			cnode.k++;
			n.keys[i] = rsnode.keys[0];
			
			if(!rsnode.isLeaf()) {
				cnode.children[cnode.k] = rsnode.children[0];
			}
			//Move all subsequent values down
			for(int x = 0; x < rsnode.k - 1; x++) {
				rsnode.keys[x] = rsnode.keys[x+1];
			}
			
			//Delete last value
			rsnode.keys[rsnode.k-1] = 0;
			rsnode.k--;
		}
		//Right sibling
		else {
			Node lsnode = n.children[j];
			//Insert previous key at position 0 of cnode
			for(int y = cnode.k; y > 0; y--) {
				cnode.keys[y] = cnode.keys[y-1];
			}
			cnode.keys[0] = n.keys[i-1];
			cnode.k++;
			
			// Move predecessor up a level
			n.keys[i-1] = lsnode.keys[lsnode.k-1];
			lsnode.keys[lsnode.k-1] = 0;
			lsnode.k--;
			
			if(!lsnode.isLeaf()) {
				//insert lsnode last child at cnode position 0
				for(int y = cnode.k; y > 0; y--) {
					cnode.children[y] = cnode.children[y-1];
				}
				cnode.children[0] = lsnode.children[lsnode.k];
			}
			
		}
	};
	
}
