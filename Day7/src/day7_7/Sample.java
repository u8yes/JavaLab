package day7_7;





class Calculator {
    static int result = 0;

    static int add(int num) {
        result += num;
        return result;
    }
    
    static int sub(int num) {
    	result -= num;
    	return result;
    }

}


class Calculator1 {
    static int result = 0;

    static int add(int num) {
        result += num;
        return result;
    }
}

class Calculator2 {
    static int result = 0;

    static int add(int num) {
        result += num;
        return result;
    }
}


public class Sample {
    public static void main(String[] args) {
    	
    	
    	
        System.out.println(Calculator1.add(3));
        System.out.println(Calculator1.add(4));

        System.out.println(Calculator2.add(3));
        System.out.println(Calculator2.add(7));
    }
}



// 객체 지향 프로그래밍이란? 책 리스트에서 확인할 것. 
