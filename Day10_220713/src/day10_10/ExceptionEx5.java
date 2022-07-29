package day10_10;

public class ExceptionEx5 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println(1);
		System.out.println(2);
		
		try {
			System.out.println(3);
			System.out.println(0/0);	// 오류 발생됨. catch로 넘어감.
			System.out.println(4);  // 실행되지 않는다.
		}catch(ArithmeticException ae) {
			System.out.println(5);
		}
		System.out.println(6);
	}

}
