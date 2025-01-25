package algorithms;

public class SearchAlgs {

	//Returns index of key in sorted array, or -1 if it doesn't exist
	public static int BinarySearch(int[] A, int key){
		return BinarySearch(A, key, 0, A.length - 1);
	}
	
	
	private static int BinarySearch(int[] A, int key, int min, int max) {
		
		if(min > max) {
			return -1;
		}
		
		int mid = (max + min) / 2;
		
		if(A[mid] == key) {
			return mid;
		}
		if(A[mid] < key) {
			return BinarySearch(A, key, mid + 1, max);
		}
		return BinarySearch(A, key, min, mid - 1);
		
	}

}
