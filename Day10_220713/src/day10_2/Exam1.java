package day10_2;

public class Exam1 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int c;
		try {
			int[] a = {1,2,3};	// 
			System.out.println(a[4]);
		    
		} catch(ArrayIndexOutOfBoundsException e) {
			System.out.println("에러가 발생했습니다.");
		} finally {
			System.out.println("에러가 생기던 안 생기던 항상 추가로 처리된다.");
		}
		
		
		
		
		
	}

}
