package day7_14;

//class Animal {
//    String name;
//
//    void setName(String name) {
//        this.name = name;
//    }
//}
//
//class Dog extends Animal {
//    void sleep() {
//        System.out.println(this.name + " zzz");
//    }
//}
//
//class HouseDog extends Dog {
//    void sleep() {
//        System.out.println(this.name + " zzz in house");
//    }
//
//    void sleep(int hour) {
//        System.out.println(this.name + " zzz in house for " + hour + " hours");
//    }
//}
//
//public class Sample {
//    public static void main(String[] args) {
//        HouseDog houseDog = new HouseDog();
//        houseDog.setName("happy");
//        houseDog.sleep();
//        houseDog.sleep(3);
//    }
//}

class Animal {
	String name;
	
	void setName(String name) {
		this.name = name;
	}
}

class Dog extends Animal {
	void sleep() {
		System.out.println(this.name + " zzz");
	}
}


class HouseDog extends Dog {
	void sleep() {
		System.out.println(this.name + " zzz in house");
	}
	
	void sleep(int hour) { // 메소드 오버로딩, 상속받은 자손이 가장 우선시된다.
		System.out.println(this.name + " zzz in house for "+ hour + " hours");
	}
}

public class Sample {
	public static void main(String[] args) {
		HouseDog houseDog = new HouseDog();
		houseDog.setName("happy");
		houseDog.sleep();
		houseDog.sleep(3);
	}
}




















