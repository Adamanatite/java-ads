package runnables;

import algorithms.SortingAlgs;
import helpers.DataGenerators;
import helpers.TestAndTime;

public class Main {

	public static void main(String[] args) {
		
		int[] A = DataGenerators.randomArray(500, 0, 10);
		
		SortingAlgs.QuickSort3Way(A, 0, A.length - 1);
		
		System.out.println(TestAndTime.IsSorted(A));
		
		
	}
	

	
}
