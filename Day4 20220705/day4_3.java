public class day4_3 {
    public static void main(String[] args) {
        
        
        
        int[] arr = {10,20,30,40,50};
        int sum = 0;

        for(int i = 0; i < arr.length; i++){
        System.out.printf("%d ", arr);  
        }
        for( int i : arr) {
            System.out.printf("%d ", i);
            sum += i;
        
        System.out.println();
        System.out.println("sum = " + sum);

        }
    }
}
