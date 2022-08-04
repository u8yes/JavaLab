package day26_2;

import java.util.Scanner;

public class GreatestCommonDivisor {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner stdIn = new Scanner(System.in);
		System.out.println("숫자를 입력하세요.");
		int a = stdIn.nextInt();
		int aa = a;
		int b = stdIn.nextInt();
		int bb = b;
		int r = a % b;
		
		if( r == 0) {
			System.out.printf("%d와 %d의 최대 공약수는 %d입니다.", aa, bb, b);
			
		}else {
			while(r != 0) {
				r = a % b;
				a = b;
				b = r;
			}
			System.out.printf("%d와 %d의 최대 공약수는 %d입니다.", aa, bb, a);
		}
	}
}
