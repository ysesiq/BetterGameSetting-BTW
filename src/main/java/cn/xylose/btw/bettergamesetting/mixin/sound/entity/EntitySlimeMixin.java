package cn.xylose.btw.bettergamesetting.mixin.sound.entity;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.src.EntitySlime;
import net.minecraft.src.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(EntitySlime.class)
public abstract class EntitySlimeMixin {

    @ModifyReturnValue(method = "getSoundVolume", at = @At("TAIL"))
    protected float cubicVolume(float original) {
        return Minecraft.getMinecraft().gameSettings.getNeutralVolume() * original;
    }
}
