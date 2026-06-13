package cn.xylose.btw.bettergamesetting.mixin.client;

import cn.xylose.btw.bettergamesetting.api.IServerConfigurationManager;
import net.minecraft.src.IntegratedPlayerList;
import net.minecraft.src.IntegratedServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(IntegratedPlayerList.class)
public class IntegratedPlayerListMixin {
	@Inject(method = "<init>", at = @At("TAIL"))
	private void onInitTail(IntegratedServer par1, CallbackInfo ci) {
		((IServerConfigurationManager) this).parseDistance(10);
	}
}
