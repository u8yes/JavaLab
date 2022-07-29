package javaStudy;

public class LedTV implements TV {

	@Override
	public void turnOn() {
		// TODO Auto-generated method stub
		System.out.println("키다");
	}

	@Override
	public void turnOff() {
		// TODO Auto-generated method stub
		System.out.println("끄다");

	}

	@Override
	public void changeVolume(int volume) {
		// TODO Auto-generated method stub
		System.out.println("볼륨 조절하다");

	}

	@Override
	public void changeChannel(int channel) {
		// TODO Auto-generated method stub
		System.out.println("채널을 변경하다");

	}

}
