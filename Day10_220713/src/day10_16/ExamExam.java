package day10_16;

class ExceptionExam{
	public int get50thItem(int[] array) throws ArrayIndexOutOfBoundsException {	// 
		return array[49];
	}
}

// 문제1)
//public class ExamExam {
//
//	public static void main(String[] args) {
//		// TODO Auto-generated method stub
//		
//		ExceptionExam ex = new ExceptionExam();
//		int num = ex.get50thItem(new int[100]);
//		System.out.println("array 배열의 50번째 요소의 값 : " + num);
//	}
//
//}

// 문제2)
public class ExamExam {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ExceptionExam ex = new ExceptionExam();
		int num = ex.get50thItem(new int[30]);
		System.out.println("array 배열의 50번째 요소의 값 : " + num);
	}

}