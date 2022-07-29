package day10_15;

public class ExceptionExam {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			int[] array = new int[10];
			array[20] = 5;
			System.out.println("여기도 포함시키니???");
		}catch(Exception e) {
			System.out.println("배열의 범위를 지나쳤어요.");
		}
		System.out.println("성공");
	}

}
