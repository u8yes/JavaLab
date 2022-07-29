package day17_1;





class Sample extends Thread {
    public void run() {  // Thread 를 상속하면 run 메서드를 구현해야 한다.
        System.out.println("thread run.");
    }

    public static void main(String[] args) {
        Sample sample = new Sample();
        sample.start();  // start()로 쓰레드를 실행한다.
    }
}