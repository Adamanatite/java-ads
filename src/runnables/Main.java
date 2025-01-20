package runnables;

import algorithms.SortingAlgs;
import helpers.DataGenerators;
import helpers.TestAndTime;

public class Main {

	public static void main(String[] args) {
		
		int[] A = DataGenerators.randomArray(50, 0, 10);
		int[] B = A.clone();
		
		SortingAlgs.QuickSort3Way(A, 0, A.length - 1);
		SortingAlgs.QuickSort3WayTest(B, 0, B.length - 1);
		
		System.out.println(TestAndTime.IsSorted(A));
		System.out.println(TestAndTime.IsSorted(B));
		
		
	}
	

	
}
