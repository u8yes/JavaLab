package day17_5;

public class Burger {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int head = 0;
		int tail = 17;
		int center = 0;
		int ans = 17;
		int[] arr = {};
		
		
		while(head < tail){
			
			System.out.println("head값은 = " + head);
			System.out.println("tail값은 = " + tail);
			
			center = (head + tail) / 2;
			if(center < ans) {
				head = center + 1;
			}else if(center > ans) {
				tail = center - 1;
			}else if(center == ans){
				System.out.println(center + "번째 요소가 일치");
				break;
			}
		}
		System.out.println("최종 head값은 = " + head);
		System.out.println("최종 tail값은 = " + tail);
		System.out.println("더이상 찾는 값이 존재하지 않습니다.");
	}

}

