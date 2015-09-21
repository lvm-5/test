package main.java.vlashchevskyi.exam.tool;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

/**
 * {@code Tool} is static class provides service methods to perform jUnit testing.
 *
 * @author Valentyn Lashchevskyi
 * @version 1.0, 9/4/2015
 */
public class Tool {

    private Tool() {

    }

    public static List<String> getInputWithConsole(String welcomeMessage) {
        Scanner scanner = new Scanner(System.in);
        System.out.println(welcomeMessage);

        String argument = null;
        List<String> arguments = new ArrayList();
        do {
            argument = scanner.nextLine();
        } while (!argument.isEmpty() && arguments.add(argument));

        return arguments;
    }

    public static ByteArrayOutputStream print(Tool.SimpleAction action) {
        PrintStream console = System.out;
        ByteArrayOutputStream messageContainer = new ByteArrayOutputStream();
        PrintStream myPrinter = new PrintStream(messageContainer);

        System.setOut(myPrinter);
        action.doIt();
        System.out.flush();
        System.setOut(console);

        return messageContainer;
    }

    public static <T, U> T doReflection(U instance, String methodName, Object[] args) {
        T result = null;
        try {
            Method method = searchMethod(instance, methodName, buildParameterTypes(args));
            method.setAccessible(true);
            result = (T) method.invoke(instance, args);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static <T, U> T doReflection(U instance, String methodName) {
        T result = null;
        try {
            Method method = null;
            method = (instance instanceof Class) ?
                    ((Class) instance).getDeclaredMethod(methodName) :
                    instance.getClass().getDeclaredMethod(methodName);
            method.setAccessible(true);
            result = (T) method.invoke(instance);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static <T, U> T doFieldReflection(U instance, String fieldName) {
        T result = null;
        try {

            Field field = (instance instanceof Class) ?
                    ((Class) instance).getDeclaredField(fieldName) :
                    instance.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            result = (T) field.get(instance);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static <T, U> void doFieldReflection(U instance, String fieldName, T fieldValue) {
        try {
            Field field = (instance instanceof Class) ?
                    ((Class) instance).getDeclaredField(fieldName) :
                    instance.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(instance, fieldValue);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static <T> List<Method> searchMethod(T target, String methodName) {
        List<Method> expectedMethod = new ArrayList<>();
        Method[] methods = (target instanceof Class) ?
                ((Class) target).getDeclaredMethods() :
                target.getClass().getDeclaredMethods();

        for (int i = 0; i < methods.length; i++) {
            if (methods[i].getName().equals(methodName)) {
                expectedMethod.add(methods[i]);
            }
        }
        return expectedMethod;
    }

    private static <T> Method searchMethod(T target, String methodName, Class<?>[] parameterTypes) {
        Method method = null;
        List<Method> methods = searchMethod(target, methodName);
        method = filterMethods(methods, parameterTypes);
        return method;
    }

    private static Method filterMethods(List<Method> methods, Class<?>[] parameterTypes) {
        Method resultMethod = null;
        Iterator<Method> it = methods.iterator();
        while (it.hasNext()) {
            Method method = it.next();
            if (isMatched(parameterTypes, method.getParameterTypes())) {
                resultMethod = method;
                break;
            }
        }
        return resultMethod;
    }

    private static Boolean isMatched(Class<?> actualType, Class<?> declaredType) {
        Boolean matchedStatus = null;

        Integer sum = toNumber(actualType) + toNumber(declaredType);
        switch (sum) {
            case 0:
                matchedStatus = actualType == declaredType;
                break;
            case 1:
                matchedStatus = (getWrapper(actualType, declaredType) != null) ? true : false;
                break;
            case 2:
            default:
                matchedStatus = declaredType.isAssignableFrom(actualType);
        }

        return matchedStatus;
    }

    private static Integer toNumber(Class<?> one) {
        return (one.isPrimitive()) ? 0 : 1;
    }

    private static Constructor<?> getWrapper(Class<?> actualType, Class<?> declaredType) {
        Constructor<?> wrapper = null;

        try {
            wrapper = (actualType.isPrimitive() && !declaredType.isPrimitive()) ?
                    declaredType.getConstructor(actualType) :
                    actualType.getConstructor(declaredType);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

        return wrapper;
    }

    private static Boolean isMatched(Class<?>[] actualTypes, Class<?>[] declaredTypes) {
        Boolean matchingResult = false;

        if (actualTypes.length != declaredTypes.length) {
            return false;
        }

        int i = 0;
        do {
            matchingResult = isMatched(actualTypes[i], declaredTypes[i]);
            i++;
        } while (i < declaredTypes.length && matchingResult);

        return matchingResult;
    }

    public static <T> Class<?>[] buildParameterTypes(T[] args) {
        Class<?>[] result = new Class[args.length];
        for (int i = 0; i < args.length; i++) {
            if (args[i] != null) {
                result[i] = args[i].getClass();
            }
        }
        return result;

    }

    /**
     * {@code SimpleAction} is functional interface to perform concrete code without arguments and any results.
     */
    public interface SimpleAction {
        /**
         * Runs code defined by lambda expression.
         */
        void doIt();
    }

    /**
     * {@code ResultAction} is functional interface allows to take argument, perform concrete code and get an result.
     *
     * @param <T> the type of result based on method realization.
     * @param <U> the type of argument.
     */
    public interface ResultAction<T, U> {
        /**
         * Runs code defined by lambda expression and gets result.
         *
         * @param input the argument to be handled by lambda code.
         * @return  any result based on code implementation.
         */
        T doIt(U input);
    }
}
