package cn.xylose.btw.bettergamesetting.api;

import net.minecraft.src.FontRenderer;

public interface IButton {
    default void setPosition(int x, int y) {
    }

    default void setSize(int width, int height) {
    }

    /**
    * scrolling string
    */
    default void renderString(FontRenderer font, int color) {
    }
}
