package ForBeansUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class BeansUtils {
    /**
     * Scans object "from" for all getters. If object "to"
     * contains correspondent setter, it will invoke it
     * to set property value for "to" which equals to the property
     * of "from".
     * <p/>
     * The type in setter should be compatible to the value returned
     * by getter (if not, no invocation performed).
     * Compatible means that parameter type in setter should
     * be the same or be superclass of the return type of the getter.
     * <p/>
     * The method takes care only about public methods.
     *
     * @param to   Object which properties will be set.
     * @param from Object which properties will be used to get values.
     */
    public static void assign(Object to, Object from) throws InvocationTargetException, IllegalAccessException, NoSuchFieldException {
        Method[] methodsFrom = from.getClass().getMethods();
        Method[] methodsTo = to.getClass().getMethods();

        // Определяем имена геттеров из "from"
        Map<String, Method> getterMethodsFrom = new HashMap<>();
        for (int i = 0; i < methodsFrom.length; i++) { // Проходим по всем методам из "from"
            Method methodFrom = methodsFrom[i];
            String methodFromName= methodFrom.getName();
            if (methodFromName.startsWith("get")) { // и определям геттеры
                methodFromName = methodFromName.substring(3);
                getterMethodsFrom.put(methodFromName, methodFrom); // добавляем название геттера в мапу
            } else if (methodFromName.startsWith("is")) {
                methodFromName = methodFromName.substring(2);
                getterMethodsFrom.put(methodFromName, methodFrom); // добавляем название геттера в мапу
            }
        }

        // Определяем имена сеттеров из "to"
        Map<String, Method> setterMethodsTo = new HashMap<>();
        for (int i = 0; i < methodsTo.length; i++) { // Проходим по всем методам из "to"
            Method methodTo = methodsTo[i];
            String methodToName = methodTo.getName();
            if (methodToName.startsWith("set")) { // и определяем сеттеры
                String methodName = methodToName.substring(3);
                setterMethodsTo.put(methodName, methodTo); // добавляем название сеттера в мапу
            }
        }
        Field[] fieldsFrom = from.getClass().getDeclaredFields();
        Field[] fieldsTo = to.getClass().getDeclaredFields();
        System.out.println("Fields before set");
        System.out.println("------------------");
        System.out.println("FROM");
        for (Field field : fieldsFrom) {
            System.out.println(field.getName() + " = " + field.get(from));
        }
        System.out.println();
        System.out.println("TO");
        for (Field field : fieldsTo) {
            System.out.println(field.getName() + " = " + field.get(to));
        }

        // Находим совпадения между геттерами из "from" и сеттерами из "to"
        for (String getterMethodFromName : getterMethodsFrom.keySet()) {
            if (setterMethodsTo.containsKey(getterMethodFromName)) {
                Method getterMethod = getterMethodsFrom.get(getterMethodFromName);
                getterMethod.setAccessible(true);
                Object getterValue = getterMethod.invoke(from);
                Method setterMethod = setterMethodsTo.get(getterMethodFromName);
                setterMethod.setAccessible(true);
                setterMethod.invoke(to, getterValue);
            }
        }
        System.out.println();
        System.out.println("Fields after set");
        System.out.println("------------------");
        System.out.println("FROM");
        for (Field field : fieldsFrom) {
            System.out.println(field.getName() + " = " + field.get(from));
        }
        System.out.println();
        System.out.println("TO");
        for (Field field : fieldsTo) {
            System.out.println(field.getName() + " = " + field.get(to));
        }
    }
}

