
public class Car{
    public void run(){
        System.out.println("차가 달립니다.");
    }
    public void stop(){
        System.out.println("차가 멈춥니다.");
    }
}




public class CarExam {
    public static void main(String[] args) {
        Car car = new Car();
        car.run();
        car.stop();
    }
}
