package day10_4;

public class Sample {
    public void sayNick(String nick) {
        if("fool".equals(nick)) {
            return;
        }
        System.out.println("당신의 별명은 "+nick+" 입니다.");
    }

    public static void main(String[] args) {
        Sample sample = new Sample();
        sample.sayNick("fool");		// 여기에서 if문으로 들어감. return받고 다음 차례로 들어감.
        sample.sayNick("genious");	// 다음차례로 시작하고 if문으로 들어감. return 받고 출력!
    }
}

