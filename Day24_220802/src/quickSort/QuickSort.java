package quickSort;

import java.util.Arrays;

public class QuickSort {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[] arr = {5,4,7,6,8,3,1,2,9};
		arr = quickSort(arr, 0, arr.length-1);	// 어레이, 시작, 끝
		System.out.println(Arrays.toString(arr));
	}
	
	static int[] quickSort(int[] arr, int start, int end) {
		int p = partition(arr, start, end);

		if(start < p-1) {
			quickSort(arr, start, p-1);
		}
		if(p < end) {
			quickSort(arr, p, end);
		}
		return arr;
	}
	
	static int partition(int[] arr, int start, int end) {
		int pivot = arr[(start + end) / 2];
		while(start <= end) {
			while(arr[start] < pivot) start++;
			while(arr[end] > pivot) end--;
			if(start <= end) {
				int tmp = arr[start];
				arr[start] = arr[end];
				arr[end] = tmp;
				start++;
				end--;
			}
		}
		return start;
	}

}
