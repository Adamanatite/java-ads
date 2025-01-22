package runnables;

import algorithms.SortingAlgs;
import helpers.DataGenerators;
import helpers.TestAndTime;

public class Main {

	public static void main(String[] args) {
		
		int[] A = DataGenerators.randomArray(1000, 0, 50000);

		SortingAlgs.BubbleSort(A);
				
	}
	

	
}
