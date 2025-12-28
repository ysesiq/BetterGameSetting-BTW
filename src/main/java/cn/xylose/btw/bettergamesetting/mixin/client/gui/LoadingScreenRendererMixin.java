package cn.xylose.btw.bettergamesetting.mixin.client.gui;

import cn.xylose.btw.bettergamesetting.config.BGSConfig;
import cn.xylose.btw.bettergamesetting.util.GuiScreenPanoramaHelp;
import net.minecraft.src.*;
import net.minecraft.src.LoadingScreenRenderer;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value = LoadingScreenRenderer.class, priority = 1200)
public class LoadingScreenRendererMixin {
    @Shadow private Minecraft mc;

    @Redirect(method = "setLoadingProgress", at = @At(value = "INVOKE", target = "Lorg/lwjgl/opengl/GL11;glClear(I)V", ordinal = 0, remap = false))
    private void transparentBackgroundGLClear(int mask) {
        if (!this.mc.gameSettings.isTransparentBackground()) GL11.glClear(mask);
    }

    @Redirect(method = "setLoadingProgress", at = @At(value = "INVOKE", target = "Lorg/lwjgl/opengl/GL11;glClear(I)V", ordinal = 1, remap = false))
    private void transparentBackgroundGLClear_1(int mask) {
        if (mc.currentScreen == null) return;
        if (this.mc.gameSettings.isTransparentBackground()) {
            GL11.glClear(GL11.GL_DEPTH_BUFFER_BIT);
            GuiScreenPanoramaHelp.drawPanorama(mc.currentScreen);
            Gui.drawRect(0, 0, mc.currentScreen.width, mc.currentScreen.height, 0x44000000);
        }
    }

    @Redirect(method = "setLoadingProgress", at = @At(value = "INVOKE", target = "Lnet/minecraft/src/TextureManager;bindTexture(Lnet/minecraft/src/ResourceLocation;)V"))
    private void transparentBackgroundBindTexture(TextureManager instance, ResourceLocation resourceLocation) {
        if (!this.mc.gameSettings.isTransparentBackground()) instance.bindTexture(resourceLocation);
    }

    @Redirect(method = "setLoadingProgress", at = @At(value = "INVOKE", target = "Lnet/minecraft/src/Tessellator;startDrawingQuads()V"))
    private void transparentBackgroundStart(Tessellator instance) {
        if (!this.mc.gameSettings.isTransparentBackground()) instance.startDrawingQuads();
    }

    @Redirect(method = "setLoadingProgress", at = @At(value = "INVOKE", target = "Lnet/minecraft/src/Tessellator;draw()I"))
    private int transparentBackgroundEnd(Tessellator instance) {
        if (!this.mc.gameSettings.isTransparentBackground()) instance.draw();
        return 0;
    }
}