package Day6_10;

public class CarTest2 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Car c1 = new Car();
		Car c2 = new Car("blue");
		
		System.out.println("c1의 color=" + c1.color + ", gearType = "
				+ c1.gearType + ", door="+ c1.door);
		System.out.println("c2의 color=" + c2.color + ", gearType = "
				+ c2.gearType + ", door="+ c2.door);
		
	}

}
