package runnables;

import algorithms.SearchAlgs;
import algorithms.SortingAlgs;
import data_types.BTree;
import helpers.DataGenerators;

public class Main {

	public static void main(String[] args) {
		
		
//		int[] test = new int[5];
		
//		int[] A = DataGenerators.randomArray(100, 0, 50);
//
//		System.out.println(TestAndTime.IsSorted(A));
//		SortingAlgs.HeapSort(A);
//		System.out.println(TestAndTime.IsSorted(A));
		
		
		
		int[] A = DataGenerators.randomArray(1000, 0, 500);
		
		SortingAlgs.MergeSort(A);
//		
		int one = SearchAlgs.BinarySearch(A, 5);
//		if(one >= 0) {
//			System.out.println(one);
//			System.out.println(A[one]);
//		} else {
//			System.out.println("Not found");
//		}
//		
//		int two = SearchAlgs.BinarySearch(A, 250);
//		if(two >= 0) {
//			System.out.println(two);
//			System.out.println(A[two]);
//		} else {
//			System.out.println("Not found");
//		}
//		
//		int three = SearchAlgs.BinarySearch(A, 400);
//		if(three >= 0) {
//			System.out.println(three);
//			System.out.println(A[three]);
//		} else {
//			System.out.println("Not found");
//		}
		
//		BTree b = DataGenerators.getDeleteExampleTree();
//		
//		b.print();
//		
//		b.delete(21);
//		System.out.print("\n\n");
//		b.print();
//	
//		b.delete(30);
//		System.out.print("\n\n");
//		b.print();
//		
//		b.delete(27);
//		System.out.print("\n\n");
//		b.print();
//		
//		b.delete(22);
//		System.out.print("\n\n");
//		b.print();
//
//		b.delete(17);
//		System.out.print("\n\n");
//		b.print();
//			
//		b.delete(9);
//		System.out.print("\n\n");
//		b.print();
		
	}
	

	
}
