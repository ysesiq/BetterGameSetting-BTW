package cn.xylose.btw.bettergamesetting.mixin.sound.entity;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.src.EntityDragon;
import net.minecraft.src.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(EntityDragon.class)
public class EntityDragonMixin {
    @ModifyReturnValue(method = "getSoundVolume", at = @At("TAIL"))
    protected float dragonVolume(float original) {
        return Minecraft.getMinecraft().gameSettings.getHostileVolume() * original;
    }
}
