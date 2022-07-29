

public class CastingTest1 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Car car = null;
		FireEngine fe = new FireEngine();
		FireEngine fe2 = null;
		
		fe.water();
		car = fe;		// car = (Car) fe; 에서 형병환이 생략된 형태
		// car.water();
		fe2 = (FireEngine)car;	// 자손타입 <- 조상타입
		fe2.water();
	}

}


class Car{
	String color;
	int door;
	
	void drive() {		// 운전하는 기능
		System.out.println("drive, brrrr~");
	}
	
	void stop() {		// 멈추는 기능
		System.out.println("stop!!!!");
	}
}


class FireEngine exteds Car{		// 소방차
	void water() {			// 물을 뿌리는 기능
		System.out.println("water!!!");
	}
}

// 안 됨