package javaStudy_Day6_14;

public class Car {
	String name;
	int number;
	
	Car() {
		this("이름없음", 0);
	}
	
	Car(String name){
		this(name, 0);
	
	}
	Car(String name, int number){
		this.name = name;
		this.number = number;
	}
}


