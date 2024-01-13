import ForBeansUtils.BeansUtils;
import ForBeansUtils.FromExample;
import ForBeansUtils.ToExample;
import proxy.CachedInvocationHandler;
import proxy.PerformanceProxy;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class Main {
    public static void main(String[] args) throws IllegalAccessException, InvocationTargetException, NoSuchFieldException {
        CalculatorImpl calculator = new CalculatorImpl();
        // Первое задание
        System.out.println("Первое задание");
        System.out.println("-----------------------");
        System.out.println(calculator.calc(4));
        // Второе задание
        System.out.println("Второе задание");
        System.out.println("-----------------------");
        Method[] methods = CalculatorImpl.class.getDeclaredMethods();
        for (Method method : methods) {
            System.out.println(method);
        }
        // Третье задание
        System.out.println("Третье задание");
        System.out.println("-----------------------");
        ThereIsGetters thereIsGetters = new ThereIsGetters();
        Method[] methods2 = ThereIsGetters.class.getDeclaredMethods();
        for (Method method : methods2) {
            if (method.getName().startsWith("get") || method.getName().startsWith("is")) {
                System.out.println(method);
            }
        }
        // Четвертое задание
        System.out.println("Четвертое задание");
        System.out.println("-----------------------");
        ForStrings forStrings = new ForStrings();
        Field[] fields = ForStrings.class.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            String fieldName = field.getName();
            String fieldValue = (String) field.get(field);
            if (fieldName.equals(fieldValue)) {
                System.out.println("Field Name: " + fieldName + " equals Field Value: " + fieldValue);
            }
        }
        // Пятое задание
        System.out.println("Пятое задание");
        System.out.println("-----------------------");
        Calculator delegate = new CalculatorImpl();
        Calculator proxyCalculator = (Calculator) Proxy.newProxyInstance(ClassLoader.getSystemClassLoader(),
                delegate.getClass().getInterfaces(),
                new CachedInvocationHandler(delegate));
        run(proxyCalculator);

        // Шестое задание
        System.out.println("Шестое задание");
        System.out.println("-----------------------");
        Calculator perfProxyCalculator = (Calculator) Proxy.newProxyInstance(ClassLoader.getSystemClassLoader(),
                delegate.getClass().getInterfaces(),
                new PerformanceProxy(delegate));
        run(perfProxyCalculator);

        // Седьмое задание
        System.out.println("Седьмое задание");
        System.out.println("-----------------------");
        BeansUtils.assign(new ToExample(), new FromExample());
    }

    private static void run(Calculator calculator) {
        System.out.println(calculator.calc(1));
        System.out.println(calculator.calc(5));
        System.out.println(calculator.calc(6));
    }

}
