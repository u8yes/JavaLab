package day7_16_quiz1;

class Calculator {
	int value;
	
	Calculator(){
		this.value = 0;
	}
	
	void add(int val) {
		this.value += val;
	}
	
	int getValue() {
		return this.value;
	}
	
}


class UpgradeCalculator extends Calculator{
	void minus(int val) {
		this.value -= val;
	}
}

class MaxLimitCalculator extends Calculator {
	void add(int val) {
		this.value += val;
		if (this.value > 100) {
			this.value = 100;
		}
	}
	
}

public class Sample {

	public static void main(String[] args) {
		MaxLimitCalculator cal = new MaxLimitCalculator();
		cal.add(50);
		cal.add(60);
		// cal.minus(3);
		System.out.println(cal.getValue());
	}
}




/*
 * class Calculator {
    int value;

    Calculator() {
        this.value = 0;
    }

    void add(int val) {
        this.value += val;
    }

    int getValue() {
        return this.value;
    }
}

class UpgradeCalculator extends Calculator {
    void minus(int val) {
        this.value -= val;
    }
}

public class Sample {
    public static void main(String[] args) {
        UpgradeCalculator cal = new UpgradeCalculator();
        cal.add(10);
        cal.minus(3);
        System.out.println(cal.getValue());  // 10에서 7을 뺀 3을 출력
    }
}
*/
