package day16_1;

public class Max {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[] arr = {12, 13, 11, 14, 10};
		int max = arr[0];
		
		for(int i=1; i < arr.length; i++) {
			if(arr[i] > max) {
				max = arr[i];
				
			}
		}
		System.out.println(max);
	}

}
