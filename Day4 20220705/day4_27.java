public class day4_27 {
    public static void main(String[] args) {
        int[] arr = {10,20,30,40,50};
        int sum = 0;
        
        for(int tmp : arr){
            System.out.println("%d ", tmp);
            sum += tmp;
            
        }
        System.out.println();
        System.out.println("sum="+sum);
    }
}
