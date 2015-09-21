package main.java.vlashchevskyi.exam.concurrency;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 *{@code TypeBuilder} class provides methods to define object constructor and create required instance.
 *
 * @author Valentyn Lashchevskyi
 * @version 1.0, 9/10/2015
 */
public class TypeBuilder {

    private Constructor generalConstructor = null;
    private Boolean oneForManyMode = true;

    /**
     * Constructs {@code TypeBuilder} instance.
     */
    public TypeBuilder() {

    }

    /**
     * Finds a constructor by instance class and its {@code arguments} class recursively.
     *
     * @param originType    the first {@code Class} is going to be instantiated.
     * @param argument  the second {@code Class} is constructor argument class.
     * @return  the {@code Constructor} of {@code originType}.
     */
    public Constructor findConstructor(Class originType, Class argument) {
        Constructor constructor = null;

        do {
            Map<Class, Class[]> classMix = getClassLevel(argument);
            Iterator<Class> superClassIt = classMix.keySet().iterator();

            while (superClassIt.hasNext()) {
                Class superClass = superClassIt.next();
                constructor = getConstructor(originType, superClass);
                constructor = (null == constructor)
                        ? findConstructorByInterface(originType, classMix.get(superClass)) : constructor;
                constructor = (null == constructor)
                        ? findConstructor(originType, superClass) : constructor;
            }
        } while (constructor == null);

        return constructor;
    }

    private Constructor findConstructorByInterface(Class originType, Class[] interfaces) {
        Constructor constructor = null;
        for (int i = 0; i < interfaces.length; i++) {
            constructor = getConstructor(originType, interfaces[i]);
            if (constructor != null) {
                break;
            }
        }
        return constructor;
    }

    private Constructor initConstructor(Class type, Class argument) {
        Constructor constructor = null;


        constructor = (oneForManyMode)
                ? generalConstructor : null;
        constructor = (constructor == null)
                ? getConstructor(type, argument) : constructor;
        constructor = (constructor == null)
                ? findConstructor(type, argument) : constructor;

        generalConstructor = (oneForManyMode)
                ? constructor : null;

        return constructor;
    }


    /**
     * Gets a constructor by instance class and its argument class.
     *
     * @param targetType    the first {@code Class} is going to be instantiated.
     * @param targetArgument  the second {@code Class} is constructor argument class.
     * @return  the {@code Constructor} of {@code targetType}.
     */
    public Constructor getConstructor(Class targetType, Class targetArgument) {
        Constructor constructor = null;
        try {
            constructor = targetType.getConstructor(targetArgument);
        } catch (NoSuchMethodException e) {
            //TODO: replace it by Logger
            // e.getStackTrace();
        }

        return constructor;
    }


    private Map<Class, Class[]> getClassLevel(Class targetArgument) {
        Map<Class, Class[]> superClasses = new HashMap<>();
        superClasses.put(targetArgument.getSuperclass(), targetArgument.getInterfaces());

        return superClasses;
    }

    /**
     * Creates an instance by reflection.
     *
     * @param type  the {@code Class} is going to be instantiated.
     * @param argument  the {@code argument} is used by constructor for initialization.
     * @param <T>   the type of instance.
     * @param <U>   the type of argument is used for initialization of {@code <T>} instance.
     * @return  the instance of {@code Class<T> type}.
     */
    public <T, U> T build(Class<T> type, U argument) {
        Constructor constructor = initConstructor(type, argument.getClass());

        T instance = null;
        try {
            instance = (T) constructor.newInstance(argument);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return instance;
    }

    private void setOneForManyMode(Boolean oneForManyMode) {
        this.oneForManyMode = oneForManyMode;
    }
}
