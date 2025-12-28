package cn.xylose.btw.bettergamesetting.mixin.sound.entity;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.src.EntityBat;
import net.minecraft.src.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(EntityBat.class)
public abstract class EntityBatMixin {
    @ModifyReturnValue(method = "getSoundVolume", at = @At("TAIL"))
    protected float batVolume(float original) {
        return Minecraft.getMinecraft().gameSettings.getNeutralVolume() * original;
    }
}
