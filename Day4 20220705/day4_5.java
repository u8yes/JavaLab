
import java.util.Scanner;

public class day4_5 {
    public static void main(String[] args) {
        
        System.out.println("Count down..");
        System.out.print("Number: ");

        Scanner stdIn = new Scanner(System.in);
        int num = stdIn.nextInt();
        for(int i = num; i >= 0 ; i--){
            
            System.out.println(i);
        }




    }
}
