package cn.xylose.btw.bettergamesetting.client;

import com.google.common.collect.Lists;
import net.minecraft.src.EnumOptions;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

public class EnumOptionsExtra {
    private static Constructor<EnumOptions> enumConstructor;
    private static List<EnumOptions> VALUES;

    static {
        try {
            Class<?>[] paramTypes = {String.class, boolean.class, boolean.class};
            enumConstructor = EnumOptions.class.getDeclaredConstructor(paramTypes);
            enumConstructor.setAccessible(true);

            initEnumSystem();
        } catch (Exception e) {
            throw new RuntimeException("初始化 EnumOptions 扩展失败", e);
        }
    }

    private static void initEnumSystem() throws Exception {
        Field valuesField = EnumOptions.class.getDeclaredField("$VALUES");
        valuesField.setAccessible(true);

        EnumOptions[] originalValues = (EnumOptions[]) valuesField.get(null);
        VALUES = Lists.newArrayList(originalValues);

        addOption("options.record", true, false);
        addOption("options.weather", true, false);
        addOption("options.block", true, false);
        addOption("options.hostile", true, false);
        addOption("options.neutral", true, false);
        addOption("options.player", true, false);
        addOption("options.ambient", true, false);
        addOption("options.forceUnicodeFont", false, true);

        updateEnumValues(valuesField);
    }

    private static void addOption(String name, boolean param1, boolean param2) {
        try {
            EnumOptions instance = enumConstructor.newInstance(name, param1, param2);
            VALUES.add(instance);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException("创建枚举失败: " + name, e);
        }
    }

    private static void updateEnumValues(Field valuesField) throws Exception {
        EnumOptions[] newValues = VALUES.toArray(new EnumOptions[0]);

        valuesField.set(null, newValues);

        for (int i = 0; i < newValues.length; i++) {
            setOrdinal(newValues[i], i);
        }
    }

    private static void setOrdinal(EnumOptions enumObj, int ordinal) {
        try {
            Field ordinalField = Enum.class.getDeclaredField("ordinal");
            ordinalField.setAccessible(true);
            ordinalField.set(enumObj, ordinal);
        } catch (Exception e) {
            throw new RuntimeException("设置 ordinal 失败", e);
        }
    }

    public static List<EnumOptions> getValues() {
        return VALUES;
    }

    public static final EnumOptions RECORDS = findByName("options.record");
    public static final EnumOptions WEATHER = findByName("options.weather");
    public static final EnumOptions BLOCKS = findByName("options.block");
    public static final EnumOptions MOBS = findByName("options.hostile");
    public static final EnumOptions ANIMALS = findByName("options.neutral");
    public static final EnumOptions PLAYERS = findByName("options.player");
    public static final EnumOptions AMBIENT = findByName("options.ambient");
    public static final EnumOptions FORCE_UNICODE_FONT = findByName("options.forceUnicodeFont");

    private static EnumOptions findByName(String name) {
        return VALUES.stream()
                .filter(e -> e.name().equals(name))
                .findFirst()
                .orElseThrow();
    }
}