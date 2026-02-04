package helpers;

import java.util.Random;

import data_types.BTree;
import data_types.BTree.Node;

public class DataGenerators {
	
	/**Generates a pathological array (worst case for sorting algorithms)
	 * 
	 * @param n The size of the array
	 * @return A The generated array
	 * */
	public static int[] pathologicalArray(int n) {
		
		//Create array of size n
		int[] A = new int[n];
		int l = n - 1;
		
		//Add to the tail, then the head, decrementing n and moving inwards
		for(int i = 0; i < (l+1)/2; i++) {
			A[i] = --n;	
			A[l - i] = --n;
		}
		
		//Return generated array
		return A;
	}

	/**Generates a random integer array of a given size with values between a minimum and maximum value
	 * 
	 * @param size The size of the array
	 * @param min The minimum value any index can take
	 * @param max The maximum value any index can take
	 * @return A The generated array
	 * */
	public static int[] randomArray(int size, int min, int max) {

		int[] A = new int[size];
		
		Random rand = new Random();
		for(int i = 0; i < size; i++) {
			A[i] = rand.nextInt((max - min) + 1) + min;
		}

		return A;
	}
	
	//Generates an example tree to test the delete methods
	public static BTree getDeleteExampleTree() {
		
		//Example tree taken from https://github.com/msambol/dsa/blob/master/trees/b_tree.py
		
		BTree b = new BTree(3);
		
		//Add nodes in specific order to construct tree
		b.insert(1);
		b.insert(90);
		b.insert(9);
		b.insert(72);
		b.insert(40);
		b.insert(22);
		b.insert(55);
		b.insert(39);
		b.insert(31);
		b.insert(15);
		b.insert(21);
		b.insert(19);
		b.insert(17);
		b.insert(41);
		b.insert(47);
		b.insert(50);
		b.insert(56);
		b.insert(60);
		b.insert(63);
		b.insert(100);
		b.insert(30);
		b.insert(23);
		b.insert(25);
		b.insert(27);
		b.insert(32);
		
		//Remove the extra 100 added to split nodes properly
		b.getRoot().getChild(1).getChild(2).setK(2);

		return b;
	}
	
	
}
