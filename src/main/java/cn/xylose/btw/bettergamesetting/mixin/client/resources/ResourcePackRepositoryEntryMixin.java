package cn.xylose.btw.bettergamesetting.mixin.client.resources;

import cn.xylose.btw.bettergamesetting.api.IResourcePackRepository;
import net.minecraft.src.PackMetadataSection;
import net.minecraft.src.ResourcePackRepositoryEntry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(ResourcePackRepositoryEntry.class)
public class ResourcePackRepositoryEntryMixin implements IResourcePackRepository {
    @Shadow private PackMetadataSection rePackMetadataSection;

    @Override
    public int getPackFormat() {
        return this.rePackMetadataSection.getPackFormat();
    }
}
