package day17_8;

import java.util.*;

public class Array3 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner sc = new Scanner(System.in);
		System.out.println("요소수 : ");
		int no = sc.nextInt();
		int[] arr = new int[no]; 
		
		
		for(int i = 0; i < no; i++) {
			
			System.out.println("a[" + i + "] = ");
			int arrno = sc.nextInt();
			
			arr[i] = arrno;
			
		}
		
		System.out.print("a = {");
		for(int j = 0; j < arr.length - 1; j++) {
			System.out.print(arr[j] + ",");
		}
		System.out.println(arr[arr.length-1] + "}");
		
	}

}
