import java.util.Scanner;

public class day4_14 {
    public static void main(String[] args) {
        int input = 0, answer = 0;

        answer = (int) (Math.random() * 100) + 1;   // 1~100사이의 임의의 수를 저장
        Scanner scanner = new Scanner(System.in);

        do {
            System.out.print("1과 100사이의 정수를 입력하세요.>");
            input = scanner.nextInt();

            if (input > answer) {
                System.out.println("lower");
            } else if (input < answer) {
                System.out.println("more");
            }
        } while (input != answer);  // true면 Do로 들어간다.
        System.out.println("Good Job");
    }
}
