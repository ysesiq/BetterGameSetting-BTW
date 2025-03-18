package com.github.skystardust.InputMethodBlocker.compat;

import net.minecraft.src.GuiScreen;

public class InputMethodHandler {
    private static final InputMethodHandler Instance = new InputMethodHandler();

    public static InputMethodHandler getInstance() {
        return Instance;
    }

    public boolean shouldActive(GuiScreen gui) {
        return gui != null;
    }
}
