package cn.xylose.btw.bettergamesetting.mixin.sound.network;

import net.minecraft.src.Entity;
import net.minecraft.src.Minecraft;
import net.minecraft.src.NetClientHandler;
import net.minecraft.src.WorldClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(NetClientHandler.class)
public class NetClientHandlerMixin {
    @Shadow private Minecraft mc;

    @Redirect(method = "handleCollect", at = @At(value = "INVOKE",
            target = "Lnet/minecraft/src/WorldClient;playSoundAtEntity(Lnet/minecraft/src/Entity;Ljava/lang/String;FF)V"))
    private void modifyCollectOrbSound(WorldClient instance, Entity entity, String sound, float volume, float pitch) {
        float adjustedVolume = volume * this.mc.gameSettings.getAmbientVolume();
        instance.playSoundAtEntity(entity, sound, adjustedVolume, pitch);
    }

//    @Redirect(method = "handleCollect", at = @At(value = "INVOKE",
//            target = "Lnet/minecraft/src/WorldClient;playSoundAtEntity(Lnet/minecraft/src/Entity;Ljava/lang/String;FF)V", ordinal = 1))
//    private void modifyCollectPopSound(WorldClient instance, Entity entity, String sound, float volume, float pitch) {
//        float adjustedVolume = volume * this.mc.gameSettings.getBlockVolume();
//        instance.playSoundAtEntity(entity, sound, adjustedVolume, pitch);
//    }

    @ModifyArg(method = "handleDoorChange", at = @At(value = "INVOKE",
            target = "Lnet/minecraft/src/WorldClient;playAuxSFX(IIIII)V"), index = 4)
    private int modifyDoorChangeSound(int auxData) {
        return auxData;
    }

    @ModifyArg(method = "handleLevelSound", at = @At(value = "INVOKE",
            target = "Lnet/minecraft/src/WorldClient;playSound(DDDLjava/lang/String;FFZ)V"), index = 5)
    private float modifyLevelSoundVolume(float originalVolume) {
        return originalVolume * this.mc.gameSettings.getBlockVolume();
    }

//    @Redirect(method = "*", at = @At(value = "INVOKE",
//            target = "Lnet/minecraft/src/WorldClient;playSound(DDDLjava/lang/String;FFZ)V"))
//    private void modifyAllWorldPlaySound(WorldClient instance, double x, double y, double z,
//                                         String soundName, float volume, float pitch, boolean distanceDelay) {
//        float adjustedVolume = adjustSoundVolume(soundName, volume);
//        instance.playSound(x, y, z, soundName, adjustedVolume, pitch, distanceDelay);
//    }
//    @Redirect(method = "*", at = @At(value = "INVOKE",
//            target = "Lnet/minecraft/src/WorldClient;playSoundAtEntity(Lnet/minecraft/src/Entity;Ljava/lang/String;FF)V"))
//    private void modifyAllEntityPlaySound(WorldClient instance, Entity entity, String sound,
//                                          float volume, float pitch) {
//        float adjustedVolume = adjustSoundVolume(sound, volume);
//        instance.playSoundAtEntity(entity, sound, adjustedVolume, pitch);
//    }
//
//    @Redirect(method = "*", at = @At(value = "INVOKE",
//            target = "Lnet/minecraft/src/WorldClient;playAuxSFX(IIIII)V"))
//    private void modifyAllAuxSFX(WorldClient instance, int sfxType, int x, int y,
//                                 int z, int auxData) {
//        float volumeMultiplier = getAuxSFXVolumeMultiplier(sfxType);
//        instance.playAuxSFX(sfxType, x, y, z, auxData);
//    }

    private float adjustSoundVolume(String soundName, float originalVolume) {
        float multiplier = 1.0f;

        if (soundName == null) {
            return originalVolume * this.mc.gameSettings.getAmbientVolume();
        }

        String lowerSound = soundName.toLowerCase();

        if (lowerSound.contains("player") || lowerSound.contains("damage") ||
                lowerSound.contains("hurt") || lowerSound.contains("death")) {
            multiplier = this.mc.gameSettings.getPlayerVolume();
        } else if (lowerSound.contains("mob") || lowerSound.contains("zombie") ||
                lowerSound.contains("skeleton") || lowerSound.contains("creeper") ||
                lowerSound.contains("spider") || lowerSound.contains("enderman") ||
                lowerSound.contains("ghast") || lowerSound.contains("slime") ||
                lowerSound.contains("witch") || lowerSound.contains("wither")) {
            multiplier = this.mc.gameSettings.getHostileVolume();
        } else if (lowerSound.contains("animal") || lowerSound.contains("cow") ||
                lowerSound.contains("pig") || lowerSound.contains("sheep") ||
                lowerSound.contains("chicken") || lowerSound.contains("wolf") ||
                lowerSound.contains("ocelot") || lowerSound.contains("horse") ||
                lowerSound.contains("villager") || lowerSound.contains("bat")) {
            multiplier = this.mc.gameSettings.getNeutralVolume();
        } else if (lowerSound.contains("step") || lowerSound.contains("dig") ||
                lowerSound.contains("place") || lowerSound.contains("break") ||
                lowerSound.contains("door") || lowerSound.contains("chest") ||
                lowerSound.contains("piston") || lowerSound.contains("lever") ||
                lowerSound.contains("button") || lowerSound.contains("random") ||
                lowerSound.contains("fire") || lowerSound.contains("explode") ||
                lowerSound.contains("fizz") || lowerSound.contains("pop") ||
                lowerSound.contains("orb")) {
            multiplier = this.mc.gameSettings.getBlockVolume();
        } else if (lowerSound.contains("ui") || lowerSound.contains("menu") ||
                lowerSound.contains("click") || lowerSound.contains("select")) {
            multiplier = this.mc.gameSettings.getUIVolume();
        } else if (lowerSound.contains("ambient") || lowerSound.contains("weather") ||
                lowerSound.contains("rain") || lowerSound.contains("thunder") ||
                lowerSound.contains("wind") || lowerSound.contains("water") ||
                lowerSound.contains("lava") || lowerSound.contains("portal")) {
            multiplier = this.mc.gameSettings.getAmbientVolume();
        } else if (lowerSound.contains("record") || lowerSound.contains("music") ||
                lowerSound.contains("note")) {
            multiplier = this.mc.gameSettings.getRecordVolume();
        } else {
            multiplier = this.mc.gameSettings.getAmbientVolume();
        }

        return originalVolume * multiplier;
    }

    private float getAuxSFXVolumeMultiplier(int sfxType) {
        return switch (sfxType) {
            case 1000, 1001 -> this.mc.gameSettings.getRecordVolume();
            case 1002, 1003, 1004 -> this.mc.gameSettings.getBlockVolume();
            case 1005, 1006 -> this.mc.gameSettings.getHostileVolume();
            default -> this.mc.gameSettings.getAmbientVolume();
        };
    }

    @Redirect(method = "handleGameEvent", at = @At(value = "INVOKE",
            target = "Lnet/minecraft/src/WorldClient;playSound(DDDLjava/lang/String;FFZ)V"))
    private void modifyGameEventHitSound(WorldClient instance, double x, double y, double z,
                                         String soundName, float volume, float pitch, boolean distanceDelay) {
        float adjustedVolume = volume * this.mc.gameSettings.getPlayerVolume();
        instance.playSound(x, y, z, soundName, adjustedVolume, pitch, distanceDelay);
    }
}
