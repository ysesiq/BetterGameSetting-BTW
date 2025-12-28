package cn.xylose.btw.bettergamesetting.mixin.sound.entity;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.src.EntitySquid;
import net.minecraft.src.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(EntitySquid.class)
public class EntitySquidMixin {
    @ModifyReturnValue(method = "getSoundVolume", at = @At("TAIL"))
    protected float squidVolume(float original) {
        return Minecraft.getMinecraft().gameSettings.getNeutralVolume() * original;
    }
}
