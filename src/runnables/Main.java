package runnables;

import algorithms.SortingAlgs;
import helpers.DataGenerators;
import helpers.TestAndTime;

public class Main {

	public static void main(String[] args) {
		
		int[] A = DataGenerators.randomArray(100, 0, 50);

		System.out.println(TestAndTime.IsSorted(A));
		SortingAlgs.HeapSort(A);
		System.out.println(TestAndTime.IsSorted(A));
				
	}
	

	
}
