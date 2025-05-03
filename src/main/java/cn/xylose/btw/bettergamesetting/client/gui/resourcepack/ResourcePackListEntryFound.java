package cn.xylose.btw.bettergamesetting.client.gui.resourcepack;

import cn.xylose.btw.bettergamesetting.api.IResourcePackRepository;
import net.minecraft.src.ResourcePackRepositoryEntry;

public class ResourcePackListEntryFound extends ResourcePackListEntry {
    private final ResourcePackRepositoryEntry resourcePackRepositoryEntry;

    public ResourcePackListEntryFound(GuiScreenResourcePacks guiScreenResourcePacks, ResourcePackRepositoryEntry resourcePackRepositoryEntry) {
        super(guiScreenResourcePacks);
        this.resourcePackRepositoryEntry = resourcePackRepositoryEntry;
    }

    protected void getPackIcon() {
        this.resourcePackRepositoryEntry.bindTexturePackIcon(this.mc.getTextureManager());
    }

    protected int getPackFormat() {
        return ((IResourcePackRepository) this.resourcePackRepositoryEntry).getPackFormat();
    }

    protected String getPackDescription() {
        return this.resourcePackRepositoryEntry.getTexturePackDescription();
    }

    protected String getPackName() {
        return this.resourcePackRepositoryEntry.getResourcePackName();
    }

    public ResourcePackRepositoryEntry func_148318_i() {
        return this.resourcePackRepositoryEntry;
    }
}