import java.util.*;

public class day4_25 {
    public static void main(String[] args) {
        Scanner stdIn = new Scanner(System.in);

        System.out.println("How many stars do you want? : ");
        int stars = stdIn.nextInt();
        int i = 1;
        while( i <= stars) {
            System.out.print("*");
            i++;
        }


    }
}
