package cn.xylose.btw.bettergamesetting.mixin.common;

import cn.xylose.btw.bettergamesetting.api.IServerConfigurationManager;
import net.minecraft.server.MinecraftServer;
import net.minecraft.src.IntegratedServer;
import net.minecraft.src.Minecraft;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.File;

@Mixin(IntegratedServer.class)
public abstract class IntegratedServerMixin extends MinecraftServer {
	@Shadow @Final private Minecraft mc;

	public IntegratedServerMixin(File par1File) {
		super(par1File);
	}
	
	@Inject(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/MinecraftServer;tick()V", shift = At.Shift.AFTER))
	private void onTick(CallbackInfo ci) {
		if (this.mc.gameSettings.renderDistance != this.getConfigurationManager().getViewDistance()) {
			((IServerConfigurationManager) this.getConfigurationManager()).parseDistance(this.mc.gameSettings.renderDistance);
		}
	}
}
