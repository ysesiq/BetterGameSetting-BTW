package cn.xylose.btw.bettergamesetting.client.gui.resourcepack;

import net.minecraft.src.I18n;
import net.minecraft.src.Minecraft;

import java.util.List;

public class GuiResourcePackAvailable extends GuiResourcePackList {

    public GuiResourcePackAvailable(Minecraft mcIn, int widthIn, int heightIn, List<ResourcePackListEntry> resourcePackList) {
        super(mcIn, widthIn, heightIn, resourcePackList);
    }

    protected String getListHeader() {
        return I18n.getString("resourcePack.available.title");
    }

    @Override
    protected void elementClicked(int i, boolean bl) {}
}
