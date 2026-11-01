package dev.f1restore;
import net.minecraft.client.Minecraft;
import net.minecraft.unmapped.*;

public final class ModeController {
    private static boolean optimized;
    private static boolean fallbackHeld;
    public static boolean active() { return optimized && guiHidden(); }
    public static boolean guiHidden() {
        Minecraft mc = Minecraft.m_20213497();
        return mc != null && mc.f_58088711 != null && mc.f_58088711.f_06955029;
    }
    private static boolean canSwitch() {
        Minecraft mc = Minecraft.m_20213497();
        return mc != null && mc.f_58088711 != null && mc.f_26407825 != null && mc.f_70816363 == null;
    }
    public static void normal() {
        if (!canSwitch()) return;
        Minecraft mc = Minecraft.m_20213497();
        boolean wasOptimized = active(); optimized = false;
        mc.f_58088711.f_06955029 = wasOptimized || !mc.f_58088711.f_06955029;
    }
    public static void optimized() {
        if (!canSwitch()) return;
        Minecraft mc = Minecraft.m_20213497();
        boolean turnOn = !active(); optimized = turnOn; State.enabled = true;
        mc.f_58088711.f_06955029 = turnOn;
    }
    public static void vanillaWrite(C_76792751 options, boolean value) {
        if (NativeSettings.ready) return;
        boolean wasOptimized = active(); optimized = false;
        options.f_06955029 = wasOptimized || value;
    }
    public static void tick() {
        if (!guiHidden()) optimized = false;
        if (NativeSettings.ready) return;
        boolean pressed = org.lwjgl.input.Keyboard.isKeyDown(62);
        if (pressed && !fallbackHeld) optimized();
        fallbackHeld = pressed;
    }
    public static boolean hideHand() { return guiHidden() && (!active() || !State.selected(Feature.HAND)); }
}
