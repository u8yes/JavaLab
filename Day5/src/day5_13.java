
public class day5_13 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[][] score = {
				{ 100, 100, 100 }
				,{20, 20, 20}
				,{30, 30, 30}
				,{40, 40, 40}
		};
		int sum = 0;
		
		for(int i = 0; i < score.length; i++) {
			for(int j=0; j < score[i].length; j++) {
				System.out.printf("score[%d][%d]=%d%n", i, j, score[i][j]);
				
			}
		}
		
		for(int[] tmp : score) {
			for (int i : tmp) {
				sum += i;
			}
		}
		
		System.out.println("sum=" + sum);
	}

}
