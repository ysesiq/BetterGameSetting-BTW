package cn.xylose.btw.bettergamesetting.client;

import net.minecraft.src.I18n;
import net.minecraft.src.KeyBinding;

public class KeyBindingExtra extends KeyBinding {
    public final int defaultKeyCode;
    public static KeyBindingExtra instance;

    public KeyBindingExtra(String description, int keyCode, int keyCodeDefault) {
        super(description, keyCode);
        instance = this;
        this.defaultKeyCode = keyCodeDefault;
    }

    public static int getKeyCodeDefault(String keyDescription) {
            return switch (keyDescription) {
                case "key.forward" -> 17;
                case "key.left" -> 30;
                case "key.back" -> 31;
                case "key.right" -> 32;
                case "key.jump" -> 57;
                case "key.inventory" -> 18;
                case "key.drop" -> 16;
                case "key.chat" -> 20;
                case "key.sneak" -> 42;
                case "key.attack" -> -100;
                case "key.use" -> -99;
                case "key.playerlist" -> 15;
                case "key.pickItem" -> -98;
                case "key.command" -> 53;
                case "key.special" -> 29;
                default -> getDefaultKeyCode();
            };
    }

    public static String getKeyCategory(String keyDescription) {
        return switch (keyDescription) {
            case "key.forward", "key.jump", "key.right", "key.back", "key.left", "key.sneak" ->
                    I18n.getString("key.categories.movement");
            case "key.inventory" -> I18n.getString("key.categories.inventory");
            case "key.drop", "key.attack", "key.use", "key.pickItem", "key.special" ->
                    I18n.getString("key.categories.gameplay");
            case "key.chat", "key.command", "key.playerlist" -> I18n.getString("key.categories.multiplayer");
//            case "key." -> I18n.getString("key.categories.misc");
            default -> I18n.getString("key.categories.uncategorized");
        };
    }

    public int compareTo(KeyBinding p_compareTo_1_) {
        int i = I18n.getString(getKeyCategory(p_compareTo_1_.keyDescription)).compareTo(I18n.getString(getKeyCategory(p_compareTo_1_.keyDescription)));

        if (i == 0) {
            i = I18n.getString(getKeyCategory(p_compareTo_1_.keyDescription)).compareTo(I18n.getString(p_compareTo_1_.keyDescription));
        }

        return i;
    }

    public static int getDefaultKeyCode() {
        return 0;
    }

    public static KeyBindingExtra getInstance() {
        return instance;
    }
}
