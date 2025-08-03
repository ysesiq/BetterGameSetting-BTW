package cn.xylose.btw.bettergamesetting.mixin.compat.modmenu;

import cn.xylose.btw.bettergamesetting.config.BGSConfig;
import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.terraformersmc.modmenu.gui.widget.entries.EntryListWidget;
import net.minecraft.src.*;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntryListWidget.class)
public abstract class EntryListWidgetMixin extends GuiSlot {
    @Shadow(remap = false) protected abstract void renderList(int x, int y, int mouseX, int mouseY);

    @Unique private Minecraft client;

    public EntryListWidgetMixin(Minecraft par1Minecraft, int par2, int par3, int par4, int par5, int par6) {
        super(par1Minecraft, par2, par3, par4, par5, par6);
    }

    @Inject(method = "<init>", at = @At("TAIL"))
    private void addClientField(Minecraft minecraft, int width, int height, int top, int bottom, int slotHeight, CallbackInfo ci) {
        client = minecraft;
    }

    @Redirect(method = "drawScreen", at = @At(value = "INVOKE", target = "Lnet/minecraft/src/TextureManager;bindTexture(Lnet/minecraft/src/ResourceLocation;)V"))
    private void transparentBackground(TextureManager instance, ResourceLocation resourceLocation) {
        if (!BGSConfig.TRANSPARENT_BACKGROUND.getValue()) instance.bindTexture(resourceLocation);
    }

    @Redirect(method = "drawScreen", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/vertex/BufferBuilder;start()V", ordinal = 0, remap = false))
    private void transparentBackgroundStart(BufferBuilder instance) {
        if (!BGSConfig.TRANSPARENT_BACKGROUND.getValue()) instance.start();
    }

    @Redirect(method = "drawScreen", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/vertex/BufferBuilder;end()I", ordinal = 0, remap = false))
    private int transparentBackgroundEnd(BufferBuilder instance) {
        if (!BGSConfig.TRANSPARENT_BACKGROUND.getValue()) instance.end();
        return 0;
    }

    @Redirect(method = "drawScreen", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/vertex/BufferBuilder;start()V", ordinal = 1, remap = false))
    private void delGradientMatteStart_1(BufferBuilder instance) {
        if (!BGSConfig.TRANSPARENT_BACKGROUND.getValue()) instance.start();
    }

    @Redirect(method = "drawScreen", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/vertex/BufferBuilder;end()I", ordinal = 1, remap = false))
    private int delGradientMatteEnd_1(BufferBuilder instance) {
        if (!BGSConfig.TRANSPARENT_BACKGROUND.getValue()) instance.end();
        return 0;
    }

    @Redirect(method = "drawScreen", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/vertex/BufferBuilder;start()V", ordinal = 2, remap = false))
    private void delGradientMatteStart_2(BufferBuilder instance) {
        if (!BGSConfig.TRANSPARENT_BACKGROUND.getValue()) instance.start();
    }

    @Redirect(method = "drawScreen", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/vertex/BufferBuilder;end()I", ordinal = 2, remap = false))
    private int delGradientMatteEnd_2(BufferBuilder instance) {
        if (!BGSConfig.TRANSPARENT_BACKGROUND.getValue()) instance.end();
        return 0;
    }

    @Redirect(method = "drawScreen", at = @At(value = "INVOKE", target = "Lcom/terraformersmc/modmenu/gui/widget/entries/EntryListWidget;renderList(IIII)V"))
    private void delRenderList(EntryListWidget instance, int x, int y, int mouseX, int mouseY) {
        if (!BGSConfig.TRANSPARENT_BACKGROUND.getValue()) this.renderList(x, y, mouseX, mouseY);
    }

    @Inject(method = "drawScreen", at = @At(value = "INVOKE", target = "Lcom/terraformersmc/modmenu/gui/widget/entries/EntryListWidget;renderHoleBackground(IIII)V", ordinal = 1, shift = At.Shift.AFTER))
    private void addRenderList(int mouseX, int mouseY, float tickDelta, CallbackInfo ci, @Local(name = "n4") int n4, @Local(name = "n5") int n5) {
        if (BGSConfig.TRANSPARENT_BACKGROUND.getValue()) {
            ScaledResolution sr = new ScaledResolution(client.gameSettings, client.displayWidth, client.displayHeight);
            GL11.glScissor(this.left * sr.getScaleFactor(), client.displayHeight - this.bottom * sr.getScaleFactor(), (this.right - this.left) * sr.getScaleFactor(), (this.bottom - this.top) * sr.getScaleFactor());
            GL11.glEnable(GL11.GL_SCISSOR_TEST);
            this.renderList(n5, n4, mouseX, mouseY);
            GL11.glDisable(GL11.GL_SCISSOR_TEST);
        }
    }

    @Redirect(method = "renderHoleBackground", at = @At(value = "INVOKE", target = "Lnet/minecraft/src/TextureManager;bindTexture(Lnet/minecraft/src/ResourceLocation;)V"))
    private void transparentHoleBackground(TextureManager instance, ResourceLocation resourceLocation) {
        if (BGSConfig.TRANSPARENT_BACKGROUND.getValue()) {
            Gui.drawRect(this.left, this.top, this.right, this.bottom, 0x66000000);//draw slot dark background
            //draw slot frame line
            Gui.drawRect(this.left, this.top, this.right, this.top - 1, 0xCC000000);
            Gui.drawRect(this.left, this.bottom, this.right, this.bottom + 1, 0xCC000000);
            Gui.drawRect(this.left, this.top - 1, this.right, this.top - 2, 0x66ADB1B1);
            Gui.drawRect(this.left, this.bottom + 1, this.right, this.bottom + 2, 0x66ADB1B1);
            ScaledResolution sr = new ScaledResolution(client.gameSettings, client.displayWidth, client.displayHeight);
            GL11.glScissor((this.left * sr.getScaleFactor()), (client.displayHeight - this.bottom * sr.getScaleFactor()), ((this.right - this.left) * sr.getScaleFactor()), ((this.bottom - this.top) * sr.getScaleFactor()));
            GL11.glEnable(GL11.GL_SCISSOR_TEST);
        } else {
            instance.bindTexture(resourceLocation);
        }
    }

    @Redirect(method = "renderHoleBackground", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/vertex/BufferBuilder;start()V", remap = false), remap = false)
    private void transparentHoleBackgroundStart(BufferBuilder instance) {
        if (!BGSConfig.TRANSPARENT_BACKGROUND.getValue()) instance.start();
    }

    @Redirect(method = "renderHoleBackground", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/vertex/BufferBuilder;end()I", remap = false), remap = false)
    private int transparentHoleBackgroundEnd(BufferBuilder instance) {
        if (BGSConfig.TRANSPARENT_BACKGROUND.getValue())
            GL11.glDisable(GL11.GL_SCISSOR_TEST);
        else instance.end();
        return 0;
    }
}
