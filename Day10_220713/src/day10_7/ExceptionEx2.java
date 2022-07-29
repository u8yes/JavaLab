package day10_7;

public class ExceptionEx2 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int number = 100;
		int result = 0;
		
		for(int i=0; i<10; i++) {
			result = number / (int)(Math.random() * 10);	// 7번째 라인
			System.out.println(result);
		}
	}

}

// 당연히 나오는 에러. 수업 내용입니다.