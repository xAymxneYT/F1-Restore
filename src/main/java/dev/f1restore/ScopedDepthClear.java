package dev.f1restore;
import net.minecraft.client.Minecraft;
import net.minecraft.unmapped.*;

import org.lwjgl.opengl.GL11;
public final class ScopedDepthClear {
    public static int left(int guiWidth, int scale) { return Math.max(0, (guiWidth / 2 - 128) * scale); }
    public static int width(int screenWidth, int left, int scale) { return Math.max(0, Math.min(screenWidth - left, 256 * scale)); }
    public static int height(int screenHeight, int scale) { return Math.max(0, Math.min(screenHeight, 64 * scale)); }
    public static void clear(C_43718703 window, Minecraft mc) {
        if (!State.scopedDepth) { C_37925375.m_10221654(256); return; }
        int scale = Math.max(1, window.m_51467571());
        int x = left(window.m_39119333(), scale);
        GL11.glPushAttrib(GL11.GL_SCISSOR_BIT);
        try {
            GL11.glEnable(GL11.GL_SCISSOR_TEST);
            GL11.glScissor(x, 0, width(mc.f_31800787, x, scale), height(mc.f_74192110, scale));
            C_37925375.m_10221654(256);
        } finally { GL11.glPopAttrib(); }
    }
}
