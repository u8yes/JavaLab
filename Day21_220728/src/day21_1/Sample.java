package day21_1;

public class Sample {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
	
	int[] arr = {5,3,4,1,2};
	
	System.out.println("정렬 전");
	for(int num2 : arr) {
		System.out.print(num2 + " ");
	}
	System.out.println();
	System.out.println();

	
	for(int k = 0; k < arr.length-1; k++ ) {
		
		for(int i = arr.length-1; i > 0; i--) {
			if(i > k) {
				if(arr[i-1] > arr[i]) {
					int temp = 0;
					temp = arr[i-1];
					arr[i-1] = arr[i];
					arr[i] = temp;
					
					for(int num2 : arr) {
						System.out.print(num2 + " ");
					}
					System.out.println();

				}else {
					
				}
			}
		}
	}
	System.out.println();
	System.out.println("정렬 후");

	for(int num3 : arr) {
		System.out.print(num3 + " ");
	}
	
	
	
	
	
	
	
	}

}
