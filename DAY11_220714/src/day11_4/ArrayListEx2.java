package day11_4;


import java.util.*;

public class ArrayListEx2 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		final int LIMIT = 10;			// 자르고자 하는 글자의 개수를 지정한다.
		String source = "0123456789abcdefghijABCDEFGHIJ!@#()$%^&*ZZZ";
		int length = source.length();	// length의 개수는 43개
		
		List list = new ArrayList(length/LIMIT + 10);		//	크기를 약간 여유 있게 잡는다.
		
		for(int i=0; i < length; i+=LIMIT) {
			if(i+LIMIT < length) {
				list.add(source.substring(i, i+LIMIT));
				System.out.println(i+"은 i의 값, "+(i+LIMIT)+"은 i+LIMIT 값");
			}else {
				list.add(source.substring(i));
				System.out.println(i);
			}
		}	
		for(int i=0; i < list.size(); i++) {
				System.out.println(list.get(i));
		}
		
	}

}


