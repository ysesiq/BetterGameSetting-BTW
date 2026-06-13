package cn.xylose.btw.bettergamesetting.api;

public interface IKeyBinding {

    default int getKeyCode() {
        return 0;
    }

    default void setKeyCode(int keyCode) {}

    default int getDefaultKeyCode(String keyDescription) {
        return 0;
    }
}
