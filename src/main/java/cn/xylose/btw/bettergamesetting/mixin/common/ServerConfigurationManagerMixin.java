package cn.xylose.btw.bettergamesetting.mixin.common;

import cn.xylose.btw.bettergamesetting.api.IChunkTracker;
import cn.xylose.btw.bettergamesetting.api.IServerConfigurationManager;
import net.minecraft.server.MinecraftServer;
import net.minecraft.src.ServerConfigurationManager;
import net.minecraft.src.WorldServer;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

@Mixin(ServerConfigurationManager.class)
public class ServerConfigurationManagerMixin implements IServerConfigurationManager {
	@Shadow @Final private MinecraftServer mcServer;
	@Shadow protected int viewDistance;
	
	/**
	 * {@link IServerConfigurationManager#parseDistance(int)}
	 */
	@Unique
	public void parseDistance(int distance) {
		this.viewDistance = distance;
		if (this.mcServer.worldServers != null) {
			WorldServer[] aworldserver = this.mcServer.worldServers;
			for (WorldServer worldserver : aworldserver) {
				if (worldserver != null) {
					((IChunkTracker) worldserver.getChunkTracker()).resetViewRadius(distance);
				}
			}
		}
	}
}
