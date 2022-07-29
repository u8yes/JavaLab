package java_books_sample;

public class Lo {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[] lo = new int[6];
		
		int idx = 0;
		while (true)  {
			int number = (int)(Math.random()*45)+1;
			
			boolean insert = true;
			for(int i=0; i<=idx; i++) {
				if(lo[i] == number) {
					System.out.println("idx값 : " + idx);
					insert = false;
					break;

				}
			}
			
			if(insert == true) {
				lo[idx] = number;
				idx++;
			}
			
			if(idx ==6) break;
		}
		
		for (int i=0; i<lo.length; i++) {
			System.out.println(lo[i]);
		}
	}

}
