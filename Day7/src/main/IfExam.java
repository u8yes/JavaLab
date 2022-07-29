package main;

public class IfExam {
	public int Iftest(int value) {
		// 변수 value가 선언됐다고 가정하고 코드를 작성하세요.
		int ret = 0;		
		
		if( value % 3 == 0) {
			ret = 3;
		} 
		else if ( value % 4 == 0 ) {	
			ret = 4;
			
		}
		return ret;	//  결과 체크를 위한 코드입니다.
	}
		
		// 아래는 실행을 위한 코드입니다. 수정하지 마세요.
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		IfExam exam = new IfExam();
		System.out.println(exam.Iftest(6));
		System.out.println(exam.Iftest(8));
	}

}





