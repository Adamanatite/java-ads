package helpers;

import java.util.Random;

public class DataGenerators {

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
	
	public static int[] randomArray(int size, int min, int max) {

		int[] A = new int[size];
		
		Random rand = new Random();
		for(int i = 0; i < size; i++) {
			A[i] = rand.nextInt((max - min) + 1) + min;
		}

		return A;
	}
	
	
}
