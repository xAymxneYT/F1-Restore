package dev.f1restore;
import net.minecraft.client.Minecraft;
import net.minecraft.unmapped.*;

public final class ScreenMetrics {
    private static Minecraft owner;
    private static C_43718703 window;
    private static int width, height, scale;
    private static boolean unicode;
    public static C_43718703 get(Minecraft mc) {
        int w = mc.f_31800787, h = mc.f_74192110, s = mc.f_58088711.f_16434998;
        boolean u = mc.m_51603172();
        if (!State.reuseMetrics || window == null || owner != mc || width != w || height != h || scale != s || unicode != u) {
            owner = mc; width = w; height = h; scale = s; unicode = u;
            window = new C_43718703(mc);
        }
        return window;
    }
}
