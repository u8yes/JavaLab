package day20_1;

public class SelectionSort {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[] arr = {12,13,11,14,10};
		int min = 0;
		int temp = 0;
		
		for(int i = 0; i < 4; i++) {
			min = arr[i];
			
			for(int k = 1; k < arr.length; k++) {
				if(arr[k] < min) {
					min = arr[k];
				}else {
					temp = min;
					min = arr[i];
					arr[i] = temp;
				}
			}
		}
		
		for(int num: arr) {
			System.out.println(num);
		}
		
		
	}

}