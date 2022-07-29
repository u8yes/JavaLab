package javaStudy_Day6_14;

public class CarExam {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Car car1 = new Car();
		Car car2 = new Car("자동차");
		Car car3 = new Car("자동차", 1234);
		
	System.out.println(car1.name + " , " + car1.number);
	System.out.println(car2.name + " , " + car2.number);
	System.out.println(car3.name + " , " + car3.number);
	}

}



//public class Car {
//	String name;
//	int number;
//	
//	Car() {
//		this("이름없음", 0);
//	}
//	
//	Car(String name){
//		this(name, 0);
//	
//	}
//	Car(String name, int number){
//		this.name = name;
//		this.number = number;
//	}
//}


