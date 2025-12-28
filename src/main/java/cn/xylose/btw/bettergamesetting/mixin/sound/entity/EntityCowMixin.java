package cn.xylose.btw.bettergamesetting.mixin.sound.entity;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.src.EntityCow;
import net.minecraft.src.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(EntityCow.class)
public class EntityCowMixin {

    @ModifyReturnValue(method = "getSoundVolume", at = @At("TAIL"))
    protected float cowVolume(float original) {
        return Minecraft.getMinecraft().gameSettings.getNeutralVolume() * 0.4f;
    }
}
