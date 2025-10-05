package cn.xylose.btw.bettergamesetting.mixin.common.client;

import cn.xylose.btw.bettergamesetting.api.IKeyBinding;
import net.minecraft.src.KeyBinding;
import net.minecraft.src.I18n;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.input.Keyboard;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.HashMap;
import java.util.Map;

@Mixin(KeyBinding.class)
public class KeyBindingMixin implements IKeyBinding, Comparable<KeyBinding> {
    @Shadow public String keyDescription;
    @Shadow public int keyCode;

    @Unique private String keyCategory;
    @Unique private static final Map<String, Integer> DEFAULT_KEYCODES = new HashMap<>();

    @Inject(method = "<init>", at = @At("RETURN"))
    private void onInit(String keyDescription, int keyCode, CallbackInfo ci) {
        this.keyCategory = keyDescription;
        if (keyDescription != null && !keyDescription.isEmpty()) {
            DEFAULT_KEYCODES.put(keyDescription, keyCode);
        }
    }

    @Override
    public int getDefaultKeyCode(String keyDescription) {
        if (keyDescription == null || keyDescription.isEmpty()) {
            return Keyboard.KEY_NONE;
        }
        Integer value = DEFAULT_KEYCODES.get(keyDescription);
        return value != null ? value : Keyboard.KEY_NONE;
    }

    @Override
    public String getKeyCategory(String keyDescription) {
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

    @Override
    public int compareTo(@NotNull KeyBinding key) {
        int i = I18n.getString(this.keyCategory).compareTo(I18n.getString(key.getKeyCategory(key.keyDescription)));

        if (i == 0) {
            i = I18n.getString(this.keyDescription).compareTo(I18n.getString(key.keyDescription));
        }

        return i;
    }

    @Override
    public int getKeyCode() {
        return this.keyCode;
    }

    @Override
    public void setKeyCode(int keyCode) {
        this.keyCode = keyCode;
    }
}