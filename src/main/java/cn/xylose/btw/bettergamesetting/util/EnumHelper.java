package cn.xylose.btw.bettergamesetting.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Arrays;

public class EnumHelper {
    public static <T extends Enum<?>> T addEnum(Class<T> enumType, String name, Class<?>[] paramTypes, Object[] paramValues) {
        try {
            Constructor<T> constructor = enumType.getDeclaredConstructor(paramTypes);
            constructor.setAccessible(true);
            T newEnum = constructor.newInstance(paramValues);
            Field valuesField = enumType.getDeclaredField("$VALUES");
            valuesField.setAccessible(true);
            T[] values = (T[]) valuesField.get(null);
            T[] newValues = Arrays.copyOf(values, values.length + 1);
            newValues[values.length] = newEnum;
            valuesField.set(null, newValues);
            return newEnum;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
