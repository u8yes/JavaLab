package main;

import java.util.Scanner;

public class DoWhileExam {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner scan = new Scanner(System.in);
		
		int value = 0;
		int sum = 0;
		
		System.out.println("숫자를 입력해주세요.");
		
		
		do {
			value = scan.nextInt();
			sum += value;
		}while(value < 100);
		
		
		System.out.println("합계는 "+sum+"입니다");
		
		
		
		
	}

}
