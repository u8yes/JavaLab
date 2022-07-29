package day7_8;


class Animal {
	String name;
	
	public void setName(String name) {
		this.name = name;
	}
}





public class Sample {
	public static void main(String[] args) {
		Animal cat = new Animal();
		cat.setName("body");
		
		Animal dog = new Animal();
		dog.setName("happy");
		
		System.out.println(cat.name);
		System.out.println(dog.name);
	}
}






