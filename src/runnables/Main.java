package runnables;

import algorithms.SortingAlgs;
import data_types.BTree;
import helpers.DataGenerators;

public class Main {

	public static void main(String[] args) {
		
		
		
		int[] A = DataGenerators.randomArray(1000, 0, 500);
		SortingAlgs.MergeSort(A);		
	
		
		
		BTree b = DataGenerators.getDeleteExampleTree();
		
		b.print();
		
		b.delete(21);
		System.out.print("\n\n");
		b.print();
	
		b.delete(30);
		System.out.print("\n\n");
		b.print();
		
		b.delete(27);
		System.out.print("\n\n");
		b.print();
		
		b.delete(22);
		System.out.print("\n\n");
		b.print();

		b.delete(17);
		System.out.print("\n\n");
		b.print();
			
		b.delete(9);
		System.out.print("\n\n");
		b.print();
		
	}
	

	
}
