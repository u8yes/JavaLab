package Day6_1;

public class TvTest4 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// 길이가 3인 Tv를 객체 배열
		Tv[] tvArr = new Tv[3];
		
		for (int i = 0; i < tvArr.length; i++) {
			tvArr[i] = new Tv();
			tvArr[i].channel = i + 10;	// 각각 객체의 channel 에 다른 값을 저장. 
		}
		for (int i = 0; i < tvArr.length; i++) {
			tvArr[i].channel1Up();  // 객체마다 채널이 1이 증가
			System.out.printf("tvArr[%d].channel = %d%n", i, tvArr[i].channel);
		}
		
		
	}

}
