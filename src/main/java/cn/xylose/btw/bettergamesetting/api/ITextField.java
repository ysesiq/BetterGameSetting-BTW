package cn.xylose.btw.bettergamesetting.api;

import net.minecraft.src.Minecraft;

public interface ITextField {
    default void setPosition(int x, int y) {
    }

    default void setSize(int width, int height) {
    }

    default void setHint(String hint) {
    }

    default boolean mousePressed(Minecraft client, int x, int y) {
        return false;
    }
}
