package day10_17;

class ExceptionExam{
	public int get50thItem(int [] array) throws IllegalArgumentException {
		if(array.length < 50) {
			throw new IllegalArgumentException();
		}
		
		return array[49];
	}
}



public class ExamExam {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ExceptionExam ex = new ExceptionExam();
		// Test 를 위한 코드입니다.
		int[] array = null;
		array = new int[50];
		array[49] = 100;
		System.out.println("array배열의 50번째 요소의 값 : " + ex.get50thItem(array));
		
		array = new int[49];
		array[48] = 100;
		System.out.println("array배열의 50번째 요소의 값 : " + ex.get50thItem(array));
	}

}
