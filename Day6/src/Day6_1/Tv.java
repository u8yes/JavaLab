package Day6_1;

public class Tv {
	// TV의 속성 설정
	String color;	// 색상
	boolean power;	// 전원
	int channel;	// 채널
	
	// TV 메서드 (기능, 함수)
	void power()	{
		power = !power;
	}
	
	void channel1Up() {
		++channel;
	}
	void channel1Down() {
		--channel;
	}
	
	
	
	
}
