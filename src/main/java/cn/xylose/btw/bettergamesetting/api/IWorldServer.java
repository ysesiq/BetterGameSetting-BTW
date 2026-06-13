package cn.xylose.btw.bettergamesetting.api;

import net.minecraft.src.PlayerManager;

public interface IWorldServer {
	default PlayerManager getPlayerManager() {
		return null;
	}
}
