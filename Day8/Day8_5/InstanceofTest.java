

public class InstanceofTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		FireEngine fe = new FireEngine();
		
		if(fe instanceof FireEngine) {
			System.out.println("This is a FireEngine instance.");
		}
		
		if(fe instanceof Car) {
			System.out.println("This is a Car instance.");
		}

		if(fe instanceof Object) {
			System.out.println("This is a Object instance.");
		}
		
		System.out.println(fe.getClass().getName());	// 클래스의 이름을 출력
	}

}
class Car{}
class FireEngine extends Car{}
