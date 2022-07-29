package javaStudy;

public interface TV {
	
	public int MIN_VOLUME = 0;			// final 생략해도 상수 형태로 사용 가능
	public int MAX_VOLUME = 100;
	
	public void turnOn();	// 전원을 키는 기능
	public void turnOff();	// 전원을 끄는 기능
	public void changeVolume(int volume);	// 음량을 조절
	public void changeChannel(int channel);	// 채널 변경 가능
	
	
	
}
