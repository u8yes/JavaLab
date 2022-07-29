package Day6_3;

public class MyMathTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MyMath mm = new MyMath();
		long result1 = mm.add(5L,  3L);
		long result2 = mm.subtract(5L,  3L);
		long result3 = mm.multyply(5L,  3L);
		double result4 = mm.divide(5L,  3L);
		// double 대신 long값으로 호출하였다. 이 값은 double로 자동형변환된다.
		
		System.out.println("add(5L, 3L) = " + result1);
		System.out.println("subtract(5L, 3L) = " + result2);
		System.out.println("multyply(5L, 3L) = " + result3);
		System.out.println("divide(5L, 3L) = " + result4);
	}

}
