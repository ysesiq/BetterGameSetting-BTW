package cn.xylose.btw.bettergamesetting.mixin.sound.entity;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.src.EntityGhast;
import net.minecraft.src.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(EntityGhast.class)
public class EntityGhastMixin {
    @ModifyReturnValue(method = "getSoundVolume", at = @At("TAIL"))
    protected float ghastVolume(float original) {
        return Minecraft.getMinecraft().gameSettings.getHostileVolume() * original;
    }
}
