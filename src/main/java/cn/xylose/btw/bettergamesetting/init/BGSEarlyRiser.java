package cn.xylose.btw.bettergamesetting.init;

import cn.xylose.btw.bettergamesetting.util.EnumExtends;
import net.fabricmc.loader.api.entrypoint.PreLaunchEntrypoint;

public class BGSEarlyRiser implements PreLaunchEntrypoint {
    public void onPreLaunch() {
        EnumExtends.OPTIONS.addEnum("RECORDS", "options.record", true, false);
        EnumExtends.OPTIONS.addEnum("WEATHER", "options.weather", true, false);
        EnumExtends.OPTIONS.addEnum("BLOCKS", "options.block", true, false);
        EnumExtends.OPTIONS.addEnum("MOBS", "options.hostile", true, false);
        EnumExtends.OPTIONS.addEnum("ANIMALS", "options.neutral", true, false);
        EnumExtends.OPTIONS.addEnum("PLAYERS", "options.player", true, false);
        EnumExtends.OPTIONS.addEnum("AMBIENT", "options.ambient", true, false);
        System.out.println("Better Game Setting Early Riser");
    }
}
