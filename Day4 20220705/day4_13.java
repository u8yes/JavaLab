import java.util.*;

public class java4_13 {
    public static void main(String[] args) {
        
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("합계를 구할 숫자 입력.(끝내려면 0 입력)");


        int num;

        int sum = 0;

        while (true) {
            System.out.print(">> ");
            num = scanner.nextInt();
            if(num != 0){
                sum += num;
            } else {    // 0을 입력한 경우
                break;
            }
                               
        }

        System.out.println(sum);
    }
}
