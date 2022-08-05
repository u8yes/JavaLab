package day26_1;

import java.util.Arrays;

public class Eratosthenes {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// 소수만 찾아내는 알고리즘
		// 101의 배열을 만들어 1로 초기화
		int[] arr = new int[101];
		
		int k = 2;
		
		while(k * k  < 100) {
			if(arr[k] == 0) {
				int i = k;
				System.out.println(i + "이 i 값");
				System.out.println(k + "이 k 값");

				while( i <= 100 / k) {
					arr[k * i] = 1;
					i++;
				}
			}
			k++;
		}
		
		int sum = 0;
		for(int j = 2; j < 101; j++) {
			if(arr[j] == 0) {
				System.out.print(j + " ");
			}
		}
	}
}