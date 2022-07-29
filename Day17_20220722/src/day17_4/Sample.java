package day17_4;

//import java.util.ArrayList;

//public class Sample extends Thread {
//    int seq;
//    public Sample(int seq) {
//        this.seq = seq;
//    }
//
//    public void run() {
//        System.out.println(this.seq+" thread start.");
//        try {
//            Thread.sleep(1000);
//        }catch(Exception e) {
//        }
//        System.out.println(this.seq+" thread end.");
//    }
//
//    public static void main(String[] args) {
//        ArrayList<Thread> threads = new ArrayList<>();
//        for(int i=0; i<10; i++) {
//            Thread t = new Sample(i);
//            t.start();
//            threads.add(t);
//        }
//
//        for(int i=0; i<threads.size(); i++) {
//            Thread t = threads.get(i);
//            try {
//                t.join(); // t 쓰레드가 종료할 때까지 기다린다.
//            }catch(Exception e) {
//            }
//        }
//        System.out.println("main end.");
//    }
//}

import java.util.ArrayList;

public class Sample extends Thread {
	int seq; 
	public Sample(int seq) {
		this.seq = seq;
	}
	
	public void run() {
		System.out.println(this.seq+" thread start.");
		try {
			Thread.sleep(1000);
		}catch(Exception e) {
			
		}
		System.out.println(this.seq+"thread end.");
	}
	
	public static void main(String[] args) {
		ArrayList<Thread> threads = new ArrayList<>();
		for(int i = 0; i < 10; i++) {
			Thread t = new Sample(i);
			t.start();
			threads.add(t);
			
		}
		for(int i = 0; i < threads.size(); i++) {
			Thread t = threads.get(i);
		try {
			t.join();
		}catch(Exception e) {
		}	
		}
		System.out.println("main end");
	}
}

