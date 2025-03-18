package cn.xylose.btw.bettergamesetting.config;

import com.terraformersmc.modmenu.config.option.BooleanConfigOption;
import com.terraformersmc.modmenu.config.option.ConfigOption;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;

public class BGSConfig {
    public static final BooleanConfigOption ENABLE_IMBLOCKER = new BooleanConfigOption("enable_imblocker", true);
    public static final BooleanConfigOption FORCE_UNICODE_FONT = new BooleanConfigOption("force_unicode_font", false);
    public static ConfigOption[] asOptions() {
        ArrayList<ConfigOption> options = new ArrayList<>();
        for (Field field : BGSConfig.class.getDeclaredFields()) {
            if (Modifier.isStatic(field.getModifiers())
                    && Modifier.isFinal(field.getModifiers())
                    && ConfigOption.class.isAssignableFrom(field.getType())) {
                try {
                    options.add(((ConfigOption) field.get(null)));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        return options.stream().toArray(ConfigOption[]::new);
    }
}
