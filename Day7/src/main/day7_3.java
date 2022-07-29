package main;

import java.util.*;

public class day7_3 {
    public static void main(String[] args) {
    	
    	
    	
    	
    	Scanner stdIn = new Scanner(System.in);
    	System.out.print("월을 입력하세요 : ");
    	
    	
    	while (true) {
    		
    	int month = stdIn.nextInt();
    	
    	String monthString = "";
    	
    	switch (month) {
    	case 1: monthString = "January";
    			break;
    	case 2: monthString = "February";
    			break;
    	case 3: monthString = "March";
    			break;
    	case 4: monthString = "April";
    			break;
    	case 5: monthString = "May";
    			break;
    	case 6: monthString = "June";
    			break;
    	case 7: monthString = "July";
    			break;
    	case 8: monthString = "August";
    			break;
    	case 9: monthString = "September";
    			break;
    	case 10: monthString = "October";
    			break;
    	case 11: monthString = "November";
    			break;
    	case 12: monthString = "December";
    			break;
    	default : monthString = "Invalid month";
    			break;
    			
    	}		
    	System.out.println(monthString);
    	System.out.println("다시 입력해주세요.");
    	
    	
    	} 
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
//    	Scanner stdIn = new Scanner(System.in);
//    	System.out.print("월을 입력하세요 : ");
//    	int month = stdIn.nextInt();
//    	
//    	
//        String monthString = "";
//        switch (month) {
//            case 1:  monthString = "January";
//                     break;
//            case 2:  monthString = "February";
//                     break;
//            case 3:  monthString = "March";
//                     break;
//            case 4:  monthString = "April";
//                     break;
//            case 5:  monthString = "May";
//                     break;
//            case 6:  monthString = "June";
//                     break;
//            case 7:  monthString = "July";
//                     break;
//            case 8:  monthString = "August";
//                     break;
//            case 9:  monthString = "September";
//                     break;
//            case 10: monthString = "October";
//                     break;
//            case 11: monthString = "November";
//                     break;
//            case 12: monthString = "December";
//                     break;
//            default: monthString = "Invalid month";
//                     break;
//        }
//        System.out.println(monthString);
        
    }
}