
import java.util.*;

public class day4_21 {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        
        int retry;  
        
        do {
        
            System.out.print("int gab : ");
            
            
            int n = scanner.nextInt();

            if ( n > 0) {
                System.out.println("this is + ");

            } else if (n < 0) {
                System.out.println("this is - ");
            } else {
                System.out.println("this is 0 ");
            }
            System.out.print("Try again? 1-yes / 0 - No: ");
            retry = scanner.nextInt();
        }   while (retry == 1);


        
            
    }
}
