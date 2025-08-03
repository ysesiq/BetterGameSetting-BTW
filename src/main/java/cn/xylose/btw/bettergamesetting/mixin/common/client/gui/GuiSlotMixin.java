package cn.xylose.btw.bettergamesetting.mixin.common.client.gui;

import cn.xylose.btw.bettergamesetting.config.BGSConfig;
import net.minecraft.src.*;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = GuiSlot.class, priority = 1200)
public abstract class GuiSlotMixin {
    @Shadow public int width;
    @Shadow protected int top;
    @Shadow protected int bottom;
    @Shadow public int right;
    @Shadow public int left;
    @Shadow @Final private Minecraft mc;

    @Redirect(method = "drawScreen", at = @At(value = "INVOKE", target = "Lnet/minecraft/src/TextureManager;bindTexture(Lnet/minecraft/src/ResourceLocation;)V"))
    private void transparentBackground(TextureManager instance, ResourceLocation resourceLocation) {
        if (BGSConfig.TRANSPARENT_BACKGROUND.getValue()) {
            Gui.drawRect(this.left, this.top, this.right, this.bottom, 0x66000000);//draw slot dark background
            //draw slot frame line
            Gui.drawRect(this.left, this.top, this.right, this.top - 1, 0xCC000000);
            Gui.drawRect(this.left, this.bottom, this.right, this.bottom + 1, 0xCC000000);
            Gui.drawRect(this.left, this.top - 1, this.right, this.top - 2, 0x66ADB1B1);
            Gui.drawRect(this.left, this.bottom + 1, this.right, this.bottom + 2, 0x66ADB1B1);
        } else {
            instance.bindTexture(resourceLocation);
        }
    }

    @Redirect(method = "drawScreen", at = @At(value = "INVOKE", target = "Lnet/minecraft/src/Tessellator;startDrawingQuads()V", ordinal = 0))
    private void transparentBackgroundStart(Tessellator instance) {
        if (!BGSConfig.TRANSPARENT_BACKGROUND.getValue()) instance.startDrawingQuads();
    }

    @Redirect(method = "drawScreen", at = @At(value = "INVOKE", target = "Lnet/minecraft/src/Tessellator;draw()I", ordinal = 0))
    private int transparentBackgroundEnd(Tessellator instance) {
        if (!BGSConfig.TRANSPARENT_BACKGROUND.getValue()) instance.draw();
        return 0;
    }

    @Redirect(method = "drawScreen", at = @At(value = "INVOKE", target = "Lnet/minecraft/src/Tessellator;startDrawingQuads()V", ordinal = 2))
    private void delGradientMatteStart(Tessellator instance) {
        if (!BGSConfig.TRANSPARENT_BACKGROUND.getValue()) instance.startDrawingQuads();
    }

    @Redirect(method = "drawScreen", at = @At(value = "INVOKE", target = "Lnet/minecraft/src/Tessellator;draw()I", ordinal = 2))
    private int delGradientMatteEnd(Tessellator instance) {
        if (!BGSConfig.TRANSPARENT_BACKGROUND.getValue()) instance.draw();
        return 0;
    }

    @Redirect(method = "drawScreen", at = @At(value = "INVOKE", target = "Lnet/minecraft/src/Tessellator;startDrawingQuads()V", ordinal = 3))
    private void delGradientMatteStart1(Tessellator instance) {
        if (!BGSConfig.TRANSPARENT_BACKGROUND.getValue()) instance.startDrawingQuads();
    }

    @Redirect(method = "drawScreen", at = @At(value = "INVOKE", target = "Lnet/minecraft/src/Tessellator;draw()I", ordinal = 3))
    private int delGradientMatteEnd1(Tessellator instance) {
        if (!BGSConfig.TRANSPARENT_BACKGROUND.getValue()) instance.draw();
        return 0;
    }

    @Inject(method = "overlayBackground", at = @At("HEAD"), cancellable = true)
    private void transparentOverlayBackground(int j, int k, int l, int par4, CallbackInfo ci) {
        if (BGSConfig.TRANSPARENT_BACKGROUND.getValue()) ci.cancel();
    }

    @Inject(method = "drawScreen", at = @At(value = "INVOKE", target = "Lnet/minecraft/src/GuiSlot;drawSlot(IIIILnet/minecraft/src/Tessellator;)V"))
    private void scissorSlotStart(int j, int f, float par3, CallbackInfo ci) {
        ScaledResolution sr = new ScaledResolution(mc.gameSettings, mc.displayWidth, mc.displayHeight);
        GL11.glScissor((this.left * sr.getScaleFactor()), (mc.displayHeight - this.bottom * sr.getScaleFactor()), ((this.right - this.left) * sr.getScaleFactor()), ((this.bottom - this.top) * sr.getScaleFactor()));
        GL11.glEnable(GL11.GL_SCISSOR_TEST);
    }

    @Inject(method = "drawScreen", at = @At(value = "INVOKE", target = "Lnet/minecraft/src/GuiSlot;drawSlot(IIIILnet/minecraft/src/Tessellator;)V", shift = At.Shift.AFTER))
    private void scissorSlotEnd(int j, int f, float par3, CallbackInfo ci) {
        GL11.glDisable(GL11.GL_SCISSOR_TEST);
    }
}
