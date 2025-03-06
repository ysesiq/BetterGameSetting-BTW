package cn.xylose.btw.bettergamesetting.api;

import net.minecraft.src.KeyBinding;

public interface IKeyBinding {
    default String getKeyCategory() {
        return "";
    }

    default int compareTo(KeyBinding p_compareTo_1_) {
        return 0;
    }

    default int getKeyCode() {
        return 0;
    }

    default void setKeyCode(int keyCode) {
        return;
    }

    default int getDefaultKeyCode(String keyDescription, int keyCode) {
        return 0;
    }
}
