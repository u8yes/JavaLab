import java.util.Random;
import java.util.Scanner;


public class day4_23 {

    public static void main(String[] args) {
        Random rand = new Random();
        Scanner stdIn = new Scanner(System.in);
        int no = 10 + rand.nextInt(90);  //
        System.out.println("number game, start!");
        System.out.println("to 10 from 99, quiz");
        int x;
            do {
                System.out.print("what number");
                x = stdIn.nextInt();

                if (x > no) {
                    System.out.println("lower");
                }
                    System.out.println("more");
            } while (x != no);
            System.out.println("answer!!");
    }
}
