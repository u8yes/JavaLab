package day19_2;

public class HashInput {
	
	public static void main(String[] args) {
		
		int[] arrD = {12, 25, 36, 20, 30, 8, 42};
		int[] arrH = new int[11];
		
		int k = 0;
		
		for(int i=0;i<arrD.length;i++) {
			k = arrD[i] % 11;

			if(arrH[k] == 0) {
				arrH[k] = arrD[i];

			}else if(arrH[k] != 0){
				while(arrH[k]!=0) {
					k = (k + 1) % 11;

				}
				arrH[k] = arrD[i];

			}
		}
		for(int j : arrH) {
			System.out.println(j);
		}
	}

}