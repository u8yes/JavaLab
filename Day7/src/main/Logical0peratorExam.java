package main;

public class Logical0peratorExam {
	
	public boolean isAgeDiscountable(int age) {
		boolean isDiscount = false;
		// 이 아랫줄을 수정하세요.
		if(age <= 19 || age >= 60) {
			isDiscount = true;
		}else {
			isDiscount = false;
		}
		
		
		
		return isDiscount;
	}
	
	
	
	
	
	
	
	
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Logical0peratorExam exam = new Logical0peratorExam();
		System.out.println(exam.isAgeDiscountable(15));
		System.out.println(exam.isAgeDiscountable(27));
		System.out.println(exam.isAgeDiscountable(61));
	}

}
