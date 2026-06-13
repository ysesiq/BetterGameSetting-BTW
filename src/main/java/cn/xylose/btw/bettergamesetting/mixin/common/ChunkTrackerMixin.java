package cn.xylose.btw.bettergamesetting.mixin.common;

import api.world.ChunkTracker;
import api.world.ChunkTrackerEntry;
import cn.xylose.btw.bettergamesetting.api.IChunkTracker;
import net.minecraft.src.EntityPlayerMP;
import net.minecraft.src.MathHelper;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

import java.util.LinkedList;

@Mixin(value = ChunkTracker.class, remap = false)
public abstract class ChunkTrackerMixin implements IChunkTracker {
	@Shadow @Mutable @Final private int chunkViewDistance;
	@Shadow @Final private LinkedList<EntityPlayerMP> playersTracked;
	@Shadow protected abstract boolean areWithinAxisDistance(int iX1, int iZ1, int iX2, int iZ2, int iAxisDist);
	@Shadow protected abstract ChunkTrackerEntry getOrCreateTrackerEntry(int iChunkX, int iChunkZ);
	
	@ModifyConstant(method = "<init>", constant = @Constant(intValue = 15))
	private int modifyMaxRadius(int original) {
		return 32;
	}
	
	public void resetViewRadius(int viewDistance) {
		viewDistance = MathHelper.clamp_int(viewDistance, 3, 32);
		if (viewDistance != this.chunkViewDistance) {
			int j = viewDistance - this.chunkViewDistance;
			for (EntityPlayerMP player : this.playersTracked) {
				int k = (int) player.posX >> 4;
				int l = (int) player.posZ >> 4;
				int i1;
				int j1;
				if (j > 0) {
					for (i1 = k - viewDistance; i1 <= k + viewDistance; ++i1) {
						for (j1 = l - viewDistance; j1 <= l + viewDistance; ++j1) {
							ChunkTrackerEntry playerinstance = this.getOrCreateTrackerEntry(i1, j1);
							if (!playerinstance.getPlayersInChunk().contains(player)) {
								playerinstance.addPlayerWatching(player);
							}
						}
					}
				} else {
					for (i1 = k - this.chunkViewDistance; i1 <= k + this.chunkViewDistance; ++i1) {
						for (j1 = l - this.chunkViewDistance; j1 <= l + this.chunkViewDistance; ++j1) {
							if (!this.areWithinAxisDistance(i1, j1, k, l, viewDistance)) {
								this.getOrCreateTrackerEntry(i1, j1).removePlayerWatching(player);
							}
						}
					}
				}
			}
			
			this.chunkViewDistance = viewDistance;
		}
	}
}
