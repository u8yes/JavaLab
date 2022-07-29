package Day6_2;

public class CardTest {

	public static void main(String[] args) {	// 동작을 할 것이기 때문에 main을 만들었음.
		// 클래스 변수는 객체 생성 없이도 바로 호출 가능하다.
		System.out.println("Card width = " + Card.width);
		System.out.println("Card height = " + Card.height);
		
		// 인스턴스 변수는 객체를 생성해야만 호출 가능하다.
		Card c1 = new Card();
		c1.kind = "Heart";
		c1.number = 7;
		
		Card c2 = new Card();
		c2.kind = "Spade";
		c2.number = 4;
		
		System.out.println("C1은 " + c1.kind + ", " + c1.number + 
				"이며 크기는 (" + c1.width + ", " + c1.height + ")");

		System.out.println("C2는 " + c2.kind + ", " + c2.number + 
				"이며 크기는 (" + c2.width + ", " + c2.height + ")");
		
		System.out.println("C1의 width와 height을 50, 80으로 변경");
		c1.width = 50;
		c1.height = 80;
		
		System.out.println("C1은 " + c1.kind + ", " + c1.number + 
				"이며 크기는 (" + c1.width + ", " + c1.height + ")");

		System.out.println("C2는 " + c2.kind + ", " + c2.number + 
				"이며 크기는 (" + c2.width + ", " + c2.height + ")");
		

	}

}
