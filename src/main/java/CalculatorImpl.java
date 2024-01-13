import proxy.Cache;
import proxy.Metric;

public class CalculatorImpl implements Calculator {

    @Override
    public int calc(int number) {
        if (number <= 0) {
            System.out.println("Provide only a positive number!");
            return 0;
        } else if (number == 1) {
            return 1;
        } else {
            return number * calc(number - 1);
        }
    }
}
