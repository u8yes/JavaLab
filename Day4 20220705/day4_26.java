import java.util.*;

public class day4_26 {
    public static void main(String[] args) {
        Scanner stdIn = new Scanner(System.in);

        System.out.println("How many stars do you want? : ");
        int n = stdIn.nextInt();
        

        if( n > 0) {
            int i = 0;
            while(i < n) {
                if ( i % 2 == 0) {
                    System.out.print("*");
                } else {
                    System.out.print("+");
                    
                }
                i++;
                
                
                
            }

            System.out.println();

            
        }


    }
}
