package day24_3;

import java.util.*;

public class Debug_err {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner sc = new Scanner(System.in);
		int testcase = sc.nextInt();
		int answer = 0;
		int a[] = new int[100001];
		int S[] = new int[100001];
		for (int i = 1; i < 10000; i++) {
			a[i] = (int) (Math.random() * Integer.MAX_VALUE);
			S[i] = S[i-1] + a[i];
		}
		for(int t = 1; t < testcase; t++) {
			int query = sc.nextInt();
			for (int i = 0; i < query; i++) {
				int start = sc.nextInt();
				int end = sc.nextInt();
				answer += S[end] - S[start - 1];
				System.out.println(testcase + " " + answer);
			}
		}
	}

}
