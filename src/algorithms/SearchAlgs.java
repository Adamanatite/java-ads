package algorithms;

public class SearchAlgs {

	/**
	 * Returns the index of a key in sorted array, or -1 if it doesn't exist
	 *
	 * @param A The array to search
	 * @param key The value to find in the array
	 * @return index The index of the key in the array or -1 if it isn't contained
	 */
	public static int BinarySearch(int[] A, int key){
		return BinarySearch(A, key, 0, A.length - 1);
	}
	
	/**
	 * Searches the given array for a specified value between a minimum and maximum index
	 *
	 * @param A The array to search
	 * @param key The value to find in the array
	 * @param min The minimum index of the current search (initially 0)
	 * @param max The maximum index of the current search (initially the last index)
	 * @return index The index of the key in the array or -1 if it isn't contained
	 */
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
