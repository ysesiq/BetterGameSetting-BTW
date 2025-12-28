package cn.xylose.btw.bettergamesetting.mixin.sound.entity;

import cn.xylose.btw.bettergamesetting.api.IGameSetting;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.src.EntityHorse;
import net.minecraft.src.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(EntityHorse.class)
public class EntityHorseMixin {
    @ModifyReturnValue(method = "getSoundVolume", at = @At("TAIL"))
    protected float horseVolume(float original) {
        return ((IGameSetting) Minecraft.getMinecraft().gameSettings).getNeutralVolume() * original;
    }
}
