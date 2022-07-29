package Day8_1;

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


class FireEngine extends Car{		// 소방차
	void water() {			// 물을 뿌리는 기능
		System.out.println("water!!!");
	}
}