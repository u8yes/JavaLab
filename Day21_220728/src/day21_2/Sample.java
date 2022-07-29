package day21_2;

import java.util.Arrays;

/**
 * @author tjouen-jr
 *
 */
public class Sample {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[] arr = {5,3,4,1,2};
		
		
		for(int i = 1; i < 5; i++) {
			int k = i - 1;
			int temp = arr[i];
			
			
			while(k >= 0 && temp < arr[k]) {
				arr[k+1] = arr[k];
				k--;
				System.out.println("의 값은 "+k);
			}
		
			arr[k+1] = temp;
		}
		
		
		System.out.println(Arrays.toString(arr));
	}

}
