package Day8_7;



class Car{
    public String name;
    public int number; // private은 안 됨, protected와 public, default는 됨.
    
    public Car(String name, int number){
        this.name = name;
        this.number = number;
       
    }


}

public class CarExam{
    public static void main(String[] args) {
        Car car = new Car("포니", 1234);

        System.out.println("name: " + car.name);
        System.out.println("number: " + car.number);
    }
}


