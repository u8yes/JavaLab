package javaStudy_Day6_12;

import javaStudy_Day6_14.Car;

public class PersonExam {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// Person 클래스에서 String과 int를 매개변수로 받는 생성자를 호출합니다.
		Person person = new Person("사람", 25);
		System.out.println(person.name);
		System.out.println(person.age);
	}

}

