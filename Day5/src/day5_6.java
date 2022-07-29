import java.util.*;


public class day5_6 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int sum = 0;	// 총점을 저장하기 위한 변수
		float average = 0f;	// 평균을 저장하기 위한 변수, f안넣으면 double로 인식함
		
		int[] score = {100, 88, 100, 100, 90};
		
		
		for(int i=0; i < score.length; i++) {
			sum += score[i];
			
			
		}
		System.out.println("총점 : " + sum);
		System.out.println("평균 : " + (sum / (float)score.length));
	}

}
