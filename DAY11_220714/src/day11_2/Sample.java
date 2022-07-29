package day11_2;

import java.util.HashMap;

public class Sample {
    public static void main(String[] args) {
        HashMap<String, String> map = new HashMap<>();
        map.put("people", "사람");
        map.put("baseball", "야구");
        
        System.out.println(map.get("people"));
        
        System.out.println(map.get("baseball"));	// 출력할 때는 get.
    }
}