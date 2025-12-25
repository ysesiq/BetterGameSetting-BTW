package cn.xylose.btw.bettergamesetting.util;

import com.chocohead.mm.api.ClassTinkerers;
import com.chocohead.mm.api.EnumAdder;

public class EnumExtends {
    public static final EnumAdder OPTIONS = ClassTinkerers.enumBuilder("net.minecraft.src.EnumOptions", String.class, Boolean.TYPE, Boolean.TYPE);

    public EnumExtends() {
    }

    public static void buildEnumExtending() {
        OPTIONS.build();
    }
}
