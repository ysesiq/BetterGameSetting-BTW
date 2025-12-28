package cn.xylose.btw.bettergamesetting.mixin.client.compat;

import com.github.skystardust.InputMethodBlocker.NativeUtils;
import emi.shims.java.net.minecraft.client.gui.widget.TextFieldWidget;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = TextFieldWidget.class, remap = false)
public class EMICompat {
    @Inject(method = "setFocused", at = @At("HEAD"))
    private void activateIM(boolean focused, CallbackInfo ci) {
        if (focused) {
            NativeUtils.active("");
        } else {
            NativeUtils.inactive("");
        }
    }
}
