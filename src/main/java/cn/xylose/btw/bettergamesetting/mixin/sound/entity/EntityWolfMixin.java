package cn.xylose.btw.bettergamesetting.mixin.sound.entity;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.src.EntityLivingBase;
import net.minecraft.src.EntityWolf;
import net.minecraft.src.Minecraft;
import net.minecraft.src.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(EntityWolf.class)
public abstract class EntityWolfMixin extends EntityLivingBase {
    public EntityWolfMixin(World par1World) {
        super(par1World);
    }

    @ModifyReturnValue(method = "getSoundVolume", at = @At("TAIL"))
    protected float wolfVolume(float original) {
        return Minecraft.getMinecraft().gameSettings.getNeutralVolume() * original;
    }
}
