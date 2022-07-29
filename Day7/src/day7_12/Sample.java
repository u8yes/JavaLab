package day7_12;

public class Sample {
	
	int a;	// 객체 변수 a
	
	void varTest(Sample sample) {
		sample.a++;
	}
	
	public static void main(String[] args) {
        Sample sample = new Sample();
        sample.a = 1;
        sample.varTest(sample);
        System.out.println(sample.a);
    }
}
