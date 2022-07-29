public class PointTest2 {
    public static void main(String[] args) {
        Point3D p3 = new Point3D();
        System.out.println("p3.x =" + p3.x);
        System.out.println("p3.y =" + p3.y);
        System.out.println("p3.z =" + p3.z);

    }
}

class Point {
    int x = 10;
    int y = 20;

    Point(int x, int y){
        // super(); // 자동이다.
        this.x = x;     // 이 안에 Point3D() x = 100값이 들어옴.
        this.y = y;     // 이 안에 Point3D() y = 200값이 들어옴.
    }
}

class Point3D extends Point{
    int z = 30;

    Point3D(){
        this(100, 200, 300);    // Point3D(intx, int y, int z)를 호출한다.
    }
    Point3D(int x, int y, int z){
        super(x, y);        // Point(int x, int y)를 호출한다.
        this.z = z;
    }
}