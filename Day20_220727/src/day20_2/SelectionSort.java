package day20_2;

public class SelectionSort {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[] arr = {12,13,11,14,10};	// 정렬할 배열
		System.out.print("정렬전 ");
		
		for(int each : arr) {
			System.out.print(each + " ");
		}
		System.out.println();
		System.out.println();

		
		for(int i = 0; i < 4; i++) {
			int min = i;	// 첫번째 값을 임시 최소값으로 가정
			for(int k = i+1; k < arr.length; k++) {
				if(arr[k] < arr[min]) {
					min = k;
				}
			}
			int temp = arr[min];
			arr[min] = arr[i];
			arr[i] = temp;
			
			for(int each : arr) {
				System.out.print(each + " ");
				
			}
			System.out.println();
		}
		System.out.println();
		System.out.print("정렬 후 ");

		//
		for(int j : arr) {
			System.out.print(j + " ");
		}
	}

}
