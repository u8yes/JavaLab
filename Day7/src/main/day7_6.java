package main;

public class day7_6 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//while문을 사용해 1부터 1000까지의 자연수 중 3의 배수의 합을 구해 보자.
		int sum = 0;
		while(true) {
			for(int i = 0; i < 1000; i++) {
				if(i % 3 == 0) {
					sum += i;
					System.out.println(i);
				}
			}
			System.out.println(sum);
			break;
		}

	}

}
