package cn.xylose.btw.bettergamesetting.mixin.compat.emi;

import cn.xylose.btw.bettergamesetting.config.BGSConfig;
import cn.xylose.btw.bettergamesetting.util.GuiScreenPanoramaHelp;
import emi.dev.emi.emi.screen.widget.config.ListWidget;
import emi.shims.java.net.minecraft.client.gui.AbstractParentElement;
import net.minecraft.src.*;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import emi.shims.java.net.minecraft.client.gui.DrawContext;

@Mixin(value = ListWidget.class)
public abstract class ListWidgetMixin extends AbstractParentElement {
    @Shadow(remap = false) protected int left;
    @Shadow(remap = false) protected int top;
    @Shadow(remap = false) protected int right;
    @Shadow(remap = false) protected int bottom;
    @Shadow(remap = false) protected int width;
    @Shadow(remap = false) protected int height;
    @Shadow(remap = false) @Final protected Minecraft client;

    @Shadow(remap = false) public abstract int getRowLeft();
    @Shadow(remap = false) public abstract double getScrollAmount();
    @Shadow(remap = false) protected abstract void renderList(DrawContext draw, int x, int y, int mouseX, int mouseY, float delta);

    @Redirect(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/src/TextureManager;bindTexture(Lnet/minecraft/src/ResourceLocation;)V"))
    private void transparentBackgroundBindTexture(TextureManager instance, ResourceLocation resourceLocation) {
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

    @Redirect(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/src/Tessellator;startDrawingQuads()V", ordinal = 0))
    private void transparentBackgroundStart(Tessellator instance) {
        if (!BGSConfig.TRANSPARENT_BACKGROUND.getValue()) instance.startDrawingQuads();
    }

    @Redirect(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/src/Tessellator;draw()I", ordinal = 0))
    private int transparentBackgroundEnd(Tessellator instance) {
        if (BGSConfig.TRANSPARENT_BACKGROUND.getValue())
            GL11.glDisable(GL11.GL_SCISSOR_TEST);
        if (!BGSConfig.TRANSPARENT_BACKGROUND.getValue()) instance.draw();
        return 0;
    }

    @Redirect(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/src/Tessellator;startDrawingQuads()V", ordinal = 1))
    private void delGradientMatteStart(Tessellator instance) {
        if (!BGSConfig.TRANSPARENT_BACKGROUND.getValue()) instance.startDrawingQuads();
    }

    @Redirect(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/src/Tessellator;draw()I", ordinal = 1))
    private int delGradientMatteEnd(Tessellator instance) {
        if (!BGSConfig.TRANSPARENT_BACKGROUND.getValue()) instance.draw();
        return 0;
    }

    @Redirect(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/src/Tessellator;startDrawingQuads()V", ordinal = 2))
    private void delGradientMatteStart1(Tessellator instance) {
        if (!BGSConfig.TRANSPARENT_BACKGROUND.getValue()) instance.startDrawingQuads();
    }

    @Redirect(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/src/Tessellator;draw()I", ordinal = 2))
    private int delGradientMatteEnd1(Tessellator instance) {
        if (!BGSConfig.TRANSPARENT_BACKGROUND.getValue()) instance.draw();
        return 0;
    }

    @Redirect(method = "render", at = @At(value = "INVOKE", target = "Lemi/dev/emi/emi/screen/widget/config/ListWidget;renderList(Lemi/shims/java/net/minecraft/client/gui/DrawContext;IIIIF)V"))
    private void delRenderList(ListWidget instance, DrawContext context, int p, int o, int k, int l, float m) {
        if (!BGSConfig.TRANSPARENT_BACKGROUND.getValue()) this.renderList(context, p, o, k, l, m);
    }

    @Inject(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/src/Tessellator;draw()I", ordinal = 1, shift = At.Shift.AFTER))
    private void addRenderList(DrawContext draw, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        if (BGSConfig.TRANSPARENT_BACKGROUND.getValue()) {
            int k = this.getRowLeft();
            int l = this.top + 4 - (int) this.getScrollAmount();
            ScaledResolution sr = new ScaledResolution(client.gameSettings, client.displayWidth, client.displayHeight);
            GL11.glScissor((this.left * sr.getScaleFactor()), (client.displayHeight - this.bottom * sr.getScaleFactor()), ((this.right - this.left) * sr.getScaleFactor()), ((this.bottom - this.top) * sr.getScaleFactor()));
            GL11.glEnable(GL11.GL_SCISSOR_TEST);
            this.renderList(draw, k, l, mouseX, mouseY, delta);
            GL11.glDisable(GL11.GL_SCISSOR_TEST);
        }
    }
}