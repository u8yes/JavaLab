package day27_2;

public class Output {
// HashSearch 발표 : 고의석
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[] arrS = {15,13,1050,60,80,8,78};
		int[] arrH = new int[11];
		
		Input input = new Input();
		int[] arr = input.Input(arrS, arrH);
		
		int x = 1050;
		int k = x;
		k = k % 11;
		
		while(arrH[k] != 0) {
			if(arrH[k] == x) {
				System.out.println(x + "의 저장 위치는 " + k +"번째 입니다.");
				break;
			}else {
				k = (k + 1) % 11;
			}
		}
		if(arrH[k] == 0) {
			System.out.println("없습니다.");
		}
	}
}
