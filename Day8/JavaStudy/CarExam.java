package javaStudy;


public class Machine{
    public void turnOn(){
        System.out.println("켰습니다.");
    }
    public void turnOff(){
        System.out.println("껐습니다.");
    }
}

class Car extends Machine{

}

public class CarExam {
    
    public static void main(String[] args) {
        Car car = new Car();
        car.turnOn();
        car.turnOff();
    }


}

// 아직안됨