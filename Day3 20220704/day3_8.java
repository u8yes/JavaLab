public class day3_8 {
    public static void main(String[] args) {
        
        // 구구단
        
        
        for (int i = 2 ; i <= 9 ; i++) {                // j = 1 2 3
            for (int j = 1; j <= 9; j++) {
                System.out.printf("%d x %d = %d%n", i, j, i*j);
            }
            System.out.println();
        }
    }
}
