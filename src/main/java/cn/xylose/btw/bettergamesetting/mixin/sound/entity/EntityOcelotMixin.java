package cn.xylose.btw.bettergamesetting.mixin.sound.entity;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.src.EntityOcelot;
import net.minecraft.src.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(EntityOcelot.class)
public class EntityOcelotMixin {
    @ModifyReturnValue(method = "getSoundVolume", at = @At("TAIL"))
    protected float ocelotVolume(float original) {
        return Minecraft.getMinecraft().gameSettings.getNeutralVolume() * original;
    }
}
