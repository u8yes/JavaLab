
import java.util.*;

public class day4_18 {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        
        
        while (true) {
        System.out.println("(1) square");
        System.out.println("(2) square root");
        System.out.println("(3) log");

        System.out.print("원하는 메뉴 (1~3)를 선택하세요. choice Number ? (Done: 0) >");
        
        int num = scanner.nextInt();

        
            if (num == 0) {
                break;
            } else if (1 <= num && num <= 3) {
                System.out.printf("Y choose Number %d\n", num);
            } else {
                System.out.println("U god the wrong number");
            }

            


        } 
        System.out.println("Done0");
    }
}
