package cn.xylose.btw.bettergamesetting.mixin.client.gui;

import cn.xylose.btw.bettergamesetting.util.GuiScreenPanoramaHelp;
import net.minecraft.src.Gui;
import net.minecraft.src.GuiScreen;
import net.minecraft.src.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiScreen.class)
public class GuiScreenMixin {
    @Shadow public int width;
    @Shadow public int height;
    @Shadow public Minecraft mc;

    @Inject(method = "drawBackground", at = @At("HEAD"), cancellable = true)
    private void transparentBackground(int par1, CallbackInfo ci) {
        if (mc.currentScreen == null) return;
        if (this.mc.gameSettings.isTransparentBackground()) {
            ci.cancel();
            if (mc.theWorld == null) {
                GuiScreenPanoramaHelp.drawPanorama((GuiScreen) (Object) this);
                Gui.drawRect(0, 0, this.width, this.height, 0x44000000);
            } else {
                Gui.drawRect(0, 0, this.width, this.height, 0xAA000000);
            }
        }
    }
}