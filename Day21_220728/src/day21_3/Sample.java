package day21_3;

import java.util.Arrays;

public class Sample {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[] arr = {5,3,4,1,2};
		
		
		for(int i = 1; i < 5; i++) {
			int j = i - 1;
			int temp = arr[i];
			
			
			while(j >= 0 && temp < arr[j]) {
				arr[j+1] = arr[j];
				j--;
				System.out.println("j의 값은 "+j);
			}
		
			arr[j+1] = temp;
		}
		
		
		System.out.println(Arrays.toString(arr));
	}

}
