package day17_9;

import java.util.*;

public class Array4 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner sc = new Scanner(System.in);
		System.out.println("사람 수 : ");
		int no = sc.nextInt();
		int sum = 0;
		
		
		int[] arr = new int[no]; 
		
		System.out.println("점수를 입력하세요.");
		for(int i = 0; i < no; i++) {
			
			System.out.println((i+1) + "번의 점수 : ");
			int arrno = sc.nextInt();
			
			arr[i] = arrno;
			
		}
		
		for(int j = 0; j < arr.length; j++) {
			sum += arr[j];
		}
		System.out.println("합계는 " + sum + "점 입니다.");
		
		System.out.println("평균은 " + ((double)sum / arr.length)+ "점 입니다.");
		
		int max = arr[0];
		for(int k = 0; k < arr.length; k++) {
			if(arr[k] > max) {
				max = arr[k];
				
			}
		}
		System.out.println("최고점은 "+ max + "점 입니다.");
		
		int min = arr[0];
		for(int l = 0; l < arr.length; l++) {
			if(arr[l] < min) {
				min = arr[l];
			}
		}
		System.out.println("최저점은 "+ min + "점 입니다.");
	}

}
