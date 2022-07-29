package day10_5;

class FoolException extends RuntimeException {
}

public class Sample {
    public void sayNick(String nick) {
        if("fool".equals(nick)) {
            throw new FoolException();
        }
        System.out.println("당신의 별명은 "+nick+" 입니다.");
    }

    public static void main(String[] args) {
        Sample sample = new Sample();
        sample.sayNick("fool");
        sample.sayNick("genious");
    }
}