import java.util.Scanner;

public class day4_7 {
    public static void main(String[] args) {
        System.out.println("1부터 n까지의 합을 구합니다. n의 값 : ");
        Scanner scanner = new Scanner(System.in);

        int n;

        do {

            System.out.print("n의 값:");

            n = stdIn.nextInt();

            } while (n ≤ 0);

        int sum = 0;

        for (int i = 1; i < n; i++) {

            System.out.print(i + " + ");

            sum += i;    // sum에 i를 더한다.

        }

        System.out.print(i + " = ");

        sum += n;

        System.out.println(sum);
        



    }
}

