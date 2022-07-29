package Day6_1;

public class TvTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		// TV 객체 생성, Tv클래스로 Tv, t1 인스턴스 생성
		
		Tv t1 = new Tv();	// t1 객체 생성
		Tv t2 = new Tv();	// t2 객체 생성
		
		t1.channel = 7;	// channel 번호 7로 초기화
		t1.channel1Down();	// channel 변경 하니 내림 7 -> 6
		
		t2.channel = 11;
		t2.channel1Up();
		
		System.out.println("현재 채널" + t1.channel);
		System.out.println("현재 채널" + t2.channel);
		
		
		
	}

}
