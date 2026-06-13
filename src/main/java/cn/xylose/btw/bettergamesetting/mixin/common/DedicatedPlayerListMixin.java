package cn.xylose.btw.bettergamesetting.mixin.common;

import cn.xylose.btw.bettergamesetting.api.IServerConfigurationManager;
import net.minecraft.src.DedicatedPlayerList;
import net.minecraft.src.DedicatedServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(DedicatedPlayerList.class)
public class DedicatedPlayerListMixin {
	@Inject(method = "<init>", at = @At("TAIL"))
	private void onInitTail(DedicatedServer par1DedicatedServer, CallbackInfo ci) {
		((IServerConfigurationManager) this).parseDistance(par1DedicatedServer.getIntProperty("view-distance", 10));
	}
}
