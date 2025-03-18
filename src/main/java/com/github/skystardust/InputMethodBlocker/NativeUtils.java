package com.github.skystardust.InputMethodBlocker;


import cn.xylose.btw.bettergamesetting.config.BGSConfig;

public class NativeUtils {
    public static boolean available = false;

    private static boolean shouldPerform() {
        return available && BGSConfig.ENABLE_IMBLOCKER.getValue();
    }

    public static void forceInactive(String windowName) {
        if (available) {
            inactiveInputMethod(windowName);
        }
    }

    public static void forceActive(String windowName) {
        if (available) activeInputMethod(windowName);
    }

    public static void inactive(String windowName) {
        if (shouldPerform()) inactiveInputMethod(windowName);
    }

    public static void active(String windowName) {
        if (shouldPerform()) activeInputMethod(windowName);
    }

    private static native void inactiveInputMethod(String windowName);

    private static native void activeInputMethod(String windowName);
}
