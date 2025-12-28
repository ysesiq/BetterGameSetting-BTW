package cn.xylose.btw.bettergamesetting.mixin.common;

import cn.xylose.btw.bettergamesetting.init.BGSClient;
import net.minecraft.src.GameRules;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameRules.class)
public class GameRulesMixin {

    @Inject(method = "addGameRule", at = @At("TAIL"))
    private void saveDefaultValue(String key, String value, CallbackInfo ci) {
        BGSClient.DEFAULT_GAMERULE_VALUE.put(key, value);
    }
}
