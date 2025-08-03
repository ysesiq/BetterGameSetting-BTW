package cn.xylose.btw.bettergamesetting.mixin.common.client.gui;

import cn.xylose.btw.bettergamesetting.config.BGSConfig;
import net.minecraft.src.*;
import net.minecraft.src.GuiMultiplayer;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiMultiplayer.class)
public abstract class GuiMultiplayerMixin extends GuiScreen {

    @Inject(method = "drawScreen", at = @At(value = "INVOKE", target = "Lnet/minecraft/src/GuiSlotServer;drawScreen(IIF)V"))
    private void disableServerSlotDepthTest(int par1, int par2, float par3, CallbackInfo ci) {
        if (BGSConfig.TRANSPARENT_BACKGROUND.getValue())
            GL11.glDisable(GL11.GL_DEPTH_TEST);
    }
}