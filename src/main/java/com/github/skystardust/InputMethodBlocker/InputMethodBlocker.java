package com.github.skystardust.InputMethodBlocker;

import net.fabricmc.api.ClientModInitializer;

import java.util.logging.Logger;


public class InputMethodBlocker implements ClientModInitializer {
    public static final Logger logger = Logger.getLogger("IMBlocker");

    @Override
    public void onInitializeClient() {
        NativeUtils.inactive("");
    }
}
