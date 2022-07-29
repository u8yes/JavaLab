package day10_3;

public class Sample {
    public void shouldBeRun() {
        System.out.println("ok thanks.");
    }

    public static void main(String[] args) {
        Sample sample = new Sample();
        int c;
        try {
            c = 4 / 0;
            sample.shouldBeRun();  // 이 코드는 실행되지 않는다.
        } catch (ArithmeticException e) {
            c = -1;
        } finally {
        	sample.shouldBeRun(); 	// 에러와 상관없이 무조건 수행한다.
        }
    }
}