package helpers;

public class TestAndTime {
	
	/**Checks if a given array is sorted
	 * 
	 * @param a The array to check
	 * @return is_sorted True if the array is sorted
	 * */
	public static boolean IsSorted(int[] a) {
		
		int n = a.length;
		//loop through array
		for(int i = 0; i < n - 1; i++) {
			//if an element is larger than its successor, return false
			if(a[i] > a[i+1]) {
				return false;
			}
		}	
		//if all elements checked, return true
		return true;
	}
	
}
