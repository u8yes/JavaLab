package day7_11;

public class Sample {
	int varTest(int a) {
		a++;
		return a;
	}
	
	
	 public static void main(String[] args) {
	        int a = 1;
	        Sample sample = new Sample();
	        a = sample.varTest(a);
	        System.out.println(a);
	    }
	
	
	
}


