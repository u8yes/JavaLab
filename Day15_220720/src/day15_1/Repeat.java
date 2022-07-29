package day15_1;

public class Repeat {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int sum = 0;
		int[] array = {12, 13, 11, 14, 10};
		
		
		for(int i = 0; i< array.length; i++) {
			sum += array[i];
		}
		System.out.println(sum);
	}

}
