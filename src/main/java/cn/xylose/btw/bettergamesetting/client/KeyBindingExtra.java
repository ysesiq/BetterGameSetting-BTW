package cn.xylose.btw.bettergamesetting.client;

import net.minecraft.src.I18n;
import net.minecraft.src.KeyBinding;

public class KeyBindingExtra extends KeyBinding {
    private final String keyCategory;
    private int keyCategoryCount = 0;

    public KeyBindingExtra(String keyCategory, String description, int keyCode) {
        super(description, keyCode);
        this.keyCategory = keyCategory;
        this.keyCategoryCount++;
    }

    public String getKeyCategory(String keyDescription) {
        if (this.keyCategory != null) {
            return this.keyCategory;
        }
        return switch (keyDescription) {
            case "key.forward", "key.jump", "key.right", "key.back", "key.left", "key.sneak" ->
                    I18n.getString("key.categories.movement");
            case "key.inventory" -> I18n.getString("key.categories.inventory");
            case "key.drop", "key.attack", "key.use", "key.pickItem", "key.special" ->
                    I18n.getString("key.categories.gameplay");
            case "key.chat", "key.command", "key.playerlist" -> I18n.getString("key.categories.multiplayer");
            case "key.achievements" -> I18n.getString("key.categories.misc");
            default -> I18n.getString("key.categories.uncategorized");
        };
    }

    public int getKeyCategoryCount() {
        return keyCategoryCount;
    }
}
