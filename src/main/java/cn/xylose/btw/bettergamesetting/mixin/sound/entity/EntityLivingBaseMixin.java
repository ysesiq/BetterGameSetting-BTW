package cn.xylose.btw.bettergamesetting.mixin.sound.entity;

import net.minecraft.src.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = EntityLivingBase.class, priority = 2000)
public class EntityLivingBaseMixin {
    @Redirect(method = "attackEntityFrom", at = @At(value = "INVOKE",
            target = "Lnet/minecraft/src/EntityLivingBase;playSound(Ljava/lang/String;FF)V"))
    private void modifyAttackHurtSound(EntityLivingBase instance, String sound, float volume, float pitch) {
        float adjustedVolume = volume * Minecraft.getMinecraft().gameSettings.getNeutralVolume();
        instance.playSound(sound, adjustedVolume, pitch);
    }

    @Redirect(method = "handleHealthUpdate", at = @At(value = "INVOKE",
            target = "Lnet/minecraft/src/EntityLivingBase;playSound(Ljava/lang/String;FF)V"))
    private void modifyHealthUpdateHurtSound(EntityLivingBase instance, String sound, float volume, float pitch) {
        float adjustedVolume = volume * Minecraft.getMinecraft().gameSettings.getNeutralVolume();
        instance.playSound(sound, adjustedVolume, pitch);
    }

//    @Redirect(method = "handleHealthUpdate", at = @At(value = "INVOKE",
//            target = "Lnet/minecraft/src/EntityLivingBase;playSound(Ljava/lang/String;FF)V", ordinal = 1))
//    private void modifyHealthUpdateDeathSound(EntityLivingBase instance, String sound, float volume, float pitch) {
//        float adjustedVolume = volume * Minecraft.getMinecraft().gameSettings.getNeutralVolume();
//        instance.playSound(sound, adjustedVolume, pitch);
//    }

    @Redirect(method = "entityLivingBaseFall", at = @At(value = "INVOKE",
            target = "Lnet/minecraft/src/EntityLivingBase;playSound(Ljava/lang/String;FF)V"))
    private void modifyFallSound(EntityLivingBase instance, String sound, float volume, float pitch) {
        float adjustedVolume = volume * Minecraft.getMinecraft().gameSettings.getBlockVolume();
        instance.playSound(sound, adjustedVolume, pitch);
    }

//    @Redirect(method = "entityLivingBaseFall", at = @At(value = "INVOKE",
//            target = "Lnet/minecraft/src/EntityLivingBase;playSound(Ljava/lang/String;FF)V", ordinal = 1))
//    private void modifyFallBlockSound(EntityLivingBase instance, String sound, float volume, float pitch) {
//        float adjustedVolume = volume * Minecraft.getMinecraft().gameSettings.getBlockVolume();
//        instance.playSound(sound, adjustedVolume, pitch);
//    }

    @Inject(method = "getSoundVolume", at = @At("HEAD"), cancellable = true)
    private void modifySoundVolume(CallbackInfoReturnable<Float> cir) {
        EntityLivingBase self = (EntityLivingBase) (Object) this;
        float baseVolume = 1.0F;

        if (self instanceof EntityPlayer) {
            baseVolume *= Minecraft.getMinecraft().gameSettings.getPlayerVolume();
        } else if (self instanceof EntityMob) {
            baseVolume *= Minecraft.getMinecraft().gameSettings.getHostileVolume();
        } else if (self instanceof EntityAnimal) {
            baseVolume *= Minecraft.getMinecraft().gameSettings.getNeutralVolume();
        } else {
            baseVolume *= Minecraft.getMinecraft().gameSettings.getAmbientVolume();
        }

        cir.setReturnValue(baseVolume);
    }

    @Redirect(method = "renderBrokenItemStack", at = @At(value = "INVOKE",
            target = "Lnet/minecraft/src/EntityLivingBase;playSound(Ljava/lang/String;FF)V"))
    private void modifyBrokenItemSound(EntityLivingBase instance, String sound, float volume, float pitch) {
        float adjustedVolume = volume * Minecraft.getMinecraft().gameSettings.getBlockVolume();
        instance.playSound(sound, adjustedVolume, pitch);
    }

//    @Redirect(method = "*", at = @At(value = "INVOKE",
//            target = "Lnet/minecraft/src/EntityLivingBase;playSound(Ljava/lang/String;FF)V"))
//    private void modifyAllPlaySoundCalls(EntityLivingBase instance, String sound, float volume, float pitch) {
//        float adjustedVolume = volume;
//
//        if (sound != null) {
//            if (sound.contains("damage") || sound.contains("hurt")) {
//                adjustedVolume *= getVolumeForDamageSound(instance);
//            } else if (sound.contains("step") || sound.contains("dig")) {
//                adjustedVolume *= Minecraft.getMinecraft().gameSettings.getBlockVolume();
//            } else if (sound.contains("random")) {
//                adjustedVolume *= Minecraft.getMinecraft().gameSettings.getAmbientVolume();
//            } else {
//                adjustedVolume *= getDefaultVolumeForEntity(instance);
//            }
//        }
//
//        instance.playSound(sound, adjustedVolume, pitch);
//    }

    private float getVolumeForDamageSound(EntityLivingBase entity) {
        if (entity instanceof EntityPlayer) {
            return Minecraft.getMinecraft().gameSettings.getPlayerVolume();
        } else if (entity instanceof EntityMob) {
            return Minecraft.getMinecraft().gameSettings.getHostileVolume();
        } else {
            return Minecraft.getMinecraft().gameSettings.getNeutralVolume();
        }
    }

    private float getDefaultVolumeForEntity(EntityLivingBase entity) {
        if (entity instanceof EntityPlayer) {
            return Minecraft.getMinecraft().gameSettings.getPlayerVolume();
        } else if (entity instanceof EntityMob) {
            return Minecraft.getMinecraft().gameSettings.getHostileVolume();
        } else if (entity instanceof EntityAnimal) {
            return Minecraft.getMinecraft().gameSettings.getNeutralVolume();
        } else {
            return Minecraft.getMinecraft().gameSettings.getAmbientVolume();
        }
    }
}
