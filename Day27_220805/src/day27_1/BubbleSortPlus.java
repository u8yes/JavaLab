package day27_1;


import java.util.Arrays;

public class BubbleSortPlus {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[] arr = {5,3,4,1,2};
		System.out.println("정렬 전");
		System.out.println(Arrays.toString(arr));
		System.out.println();
		boolean car = true; // while문을 돌리기 위한 차키
		int j = 0;
		int tmp;
		while (car) {
			car = false;
			j++;
			for (int i = 0; i < arr.length - j; i++) {                                       
				if (arr[i] > arr[i + 1]) {                          

					tmp = arr[i];
					arr[i] = arr[i + 1];
					arr[i + 1] = tmp;
					car = true;
					System.out.println(Arrays.toString(arr));
				}
			}                
		}
		System.out.println();
		System.out.println("정렬 후");
		System.out.println(Arrays.toString(arr));
	}
}