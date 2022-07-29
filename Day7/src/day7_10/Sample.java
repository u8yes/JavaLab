package day7_10;

public class Sample {
	
	void sayNick(String nick) {
		if("fool".equals(nick)) {
			return;
		}
		System.out.println("나의 별명은 " + nick + " 입니다.");
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Sample sample = new Sample();
		sample.sayNick("angel");
		sample.sayNick("fool");	// 출력되지 않는다.
		

	}

}
