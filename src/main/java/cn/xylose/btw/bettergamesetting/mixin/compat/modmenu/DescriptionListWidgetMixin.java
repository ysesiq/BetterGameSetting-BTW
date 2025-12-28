package cn.xylose.btw.bettergamesetting.mixin.compat.modmenu;

import cn.xylose.btw.bettergamesetting.config.BGSConfig;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.terraformersmc.modmenu.gui.widget.DescriptionListWidget;
import com.terraformersmc.modmenu.gui.widget.entries.EntryListWidget;
import net.minecraft.src.*;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value = DescriptionListWidget.class, priority = 1200)
public abstract class DescriptionListWidgetMixin extends EntryListWidget {
    @Shadow @Final private Minecraft minecraft;

    public DescriptionListWidgetMixin(Minecraft minecraft, int width, int height, int top, int bottom, int slotHeight) {
        super(minecraft, width, height, top, bottom, slotHeight);
    }

    @Redirect(method = "drawScreen", at = @At(value = "INVOKE", target = "Lnet/minecraft/src/TextureManager;bindTexture(Lnet/minecraft/src/ResourceLocation;)V"))
    private void transparentBackground(TextureManager instance, ResourceLocation resourceLocation) {
        if (this.minecraft.gameSettings.isTransparentBackground()) {
            Gui.drawRect(this.left, this.top, this.right, this.bottom, 0x66000000);//draw slot dark background
            //draw slot frame line
            Gui.drawRect(this.left, this.top, this.right, this.top - 1, 0xCC000000);
            Gui.drawRect(this.left, this.bottom, this.right, this.bottom + 1, 0xCC000000);
            Gui.drawRect(this.left, this.top - 1, this.right, this.top - 2, 0x66ADB1B1);
            Gui.drawRect(this.left, this.bottom + 1, this.right, this.bottom + 2, 0x66ADB1B1);
            ScaledResolution sr = new ScaledResolution(minecraft.gameSettings, minecraft.displayWidth, minecraft.displayHeight);
            GL11.glScissor((this.left * sr.getScaleFactor()), (minecraft.displayHeight - this.bottom * sr.getScaleFactor()), ((this.right - this.left) * sr.getScaleFactor()), ((this.bottom - this.top) * sr.getScaleFactor()));
            GL11.glEnable(GL11.GL_SCISSOR_TEST);
        } else {
            instance.bindTexture(resourceLocation);
        }
    }

    @Redirect(method = "drawScreen", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/vertex/BufferBuilder;start(I)V", ordinal = 0, remap = false))
    private void transparentBackgroundStart(BufferBuilder instance, int drawMode) {
        if (!this.minecraft.gameSettings.isTransparentBackground()) instance.start();
    }

    @Redirect(method = "drawScreen", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/vertex/BufferBuilder;end()I", ordinal = 0))
    private int transparentBackgroundEnd(BufferBuilder instance) {
        if (this.minecraft.gameSettings.isTransparentBackground())
            GL11.glDisable(GL11.GL_SCISSOR_TEST);
        else instance.end();
        return 0;
    }

    @Redirect(method = "drawScreen", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/vertex/BufferBuilder;start(I)V", ordinal = 1))
    private void delGradientMatteStart(BufferBuilder instance, int drawMode) {
        if (!this.minecraft.gameSettings.isTransparentBackground()) instance.start();
    }

    @Redirect(method = "drawScreen", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/vertex/BufferBuilder;end()I", ordinal = 1))
    private int delGradientMatteEnd(BufferBuilder instance) {
        if (!this.minecraft.gameSettings.isTransparentBackground()) instance.end();
        return 0;
    }

    @Redirect(method = "drawScreen", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/vertex/BufferBuilder;start(I)V", ordinal = 2))
    private void delGradientMatteStart1(BufferBuilder instance, int drawMode) {
        if (!this.minecraft.gameSettings.isTransparentBackground()) instance.start();
    }

    @Redirect(method = "drawScreen", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/vertex/BufferBuilder;end()I", ordinal = 2))
    private int delGradientMatteEnd1(BufferBuilder instance) {
        if (!this.minecraft.gameSettings.isTransparentBackground()) instance.end();
        return 0;
    }
}
