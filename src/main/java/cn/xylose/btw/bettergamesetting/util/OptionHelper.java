package cn.xylose.btw.bettergamesetting.util;

import com.google.gson.Gson;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Unique;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class OptionHelper {
    public static final Gson gson = new Gson();
    public List<String> resourcePacks = new ArrayList<>();
    public static final ParameterizedType typeListString = new ParameterizedType() {
        public Type[] getActualTypeArguments() {
            return new Type[]{String.class};
        }

        public Type getRawType() {
            return List.class;
        }

        public Type getOwnerType() {
            return null;
        }
    };

    public static float normalizeValue(float value, float min, float max, float step) {
        float v = snapToStepClamp(value, min, max, step);
        return clamp((v - min) / (max - min), 0.0f, 1.0f);
    }

    public static float denormalizeValue(float value, float min, float max, float step) {
        float v = min + ((max - min) * clamp(value, 0.0f, 1.0f));
        return snapToStepClamp(v, min, max, step);
    }

    public static float snapToStepClamp(float value, float min, float max, float step) {
        return clamp(snapToStep(value, step), min, max);
    }

    public static float snapToStep(float value, float step) {
        if (step > 0.0f) {
            return step * Math.round(value / step);
        }
        return value;
    }

    public static float clamp(float num, float min, float max) {
        if (num < min) {
            return min;
        }
        return Math.min(num, max);
    }
}
