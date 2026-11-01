package dev.f1restore;
import net.minecraft.client.Minecraft;
import net.minecraft.unmapped.*;

import java.util.Arrays;
import java.util.Properties;
import java.io.*;
public final class State {
    public static boolean enabled = true;
    public static boolean fastDefaults141;
    private static boolean anyHud = true;
    public static boolean leanState = true, reuseMetrics = true, scopedDepth = false;
    public static final boolean[] visible = new boolean[Feature.values().length];
    private static File config;
    static { Arrays.fill(visible, true); visible[Feature.ADDON_HUDS.ordinal()] = false; refreshVisibility(); }
    public static boolean inF1() { return ModeController.active(); }
    public static void refreshVisibility() {
        anyHud = false;
        for (Feature feature : Feature.values()) {
            if (feature != Feature.HAND && feature != Feature.NAMES && feature != Feature.OUTLINE && visible[feature.ordinal()]) { anyHud = true; break; }
        }
    }
    public static boolean hasHud() { return enabled && anyHud; }
    public static boolean selected(Feature feature) { return enabled && visible[feature.ordinal()]; }
    public static boolean block(Feature feature) { return inF1() && !selected(feature); }
    public static boolean hidden(C_76792751 options, Feature feature) { return options.f_06955029 && !(inF1() && selected(feature)); }
    public static void setAll(boolean value) { Arrays.fill(visible, value); save(); }
    public static void flip(Feature feature) { visible[feature.ordinal()] = !visible[feature.ordinal()]; save(); }
    public static void register(C_76792751 options) {
        config = new File(Minecraft.m_20213497().f_25803373, "config/f1restore.properties");
        load();
        enabled = true;
    }
    public static void load() {
        if (config == null || !config.isFile()) { refreshVisibility(); return; }
        Properties p = new Properties();
        try (InputStream in = new FileInputStream(config)) {
            p.load(in);
            fastDefaults141 = Boolean.parseBoolean(p.getProperty("fastDefaults141", "false"));
            enabled = Boolean.parseBoolean(p.getProperty("enabled", "true"));
            leanState = Boolean.parseBoolean(p.getProperty("leanState", "true"));
            reuseMetrics = Boolean.parseBoolean(p.getProperty("reuseMetrics", "true"));
            scopedDepth = Boolean.parseBoolean(p.getProperty("scopedDepth", "false"));
            for (Feature feature : Feature.values()) visible[feature.ordinal()] = Boolean.parseBoolean(p.getProperty(feature.name(), feature == Feature.ADDON_HUDS ? "false" : "true"));
        } catch (IOException ex) { System.err.println("[F1 Restore] Could not load settings: " + ex.getMessage()); }
        refreshVisibility();
    }
    public static void save() {
        refreshVisibility();
        if (config == null) return;
        Properties p = new Properties();
        p.setProperty("fastDefaults141", Boolean.toString(fastDefaults141));
        p.setProperty("enabled", Boolean.toString(enabled));
        p.setProperty("leanState", Boolean.toString(leanState));
        p.setProperty("reuseMetrics", Boolean.toString(reuseMetrics));
        p.setProperty("scopedDepth", Boolean.toString(scopedDepth));
        for (Feature feature : Feature.values()) p.setProperty(feature.name(), Boolean.toString(visible[feature.ordinal()]));
        try {
            File dir = config.getParentFile();
            if (!dir.isDirectory() && !dir.mkdirs()) throw new IOException("Could not create config directory");
            try (OutputStream out = new FileOutputStream(config)) { p.store(out, "F1 Restore - visibility while F1 is active"); }
        } catch (IOException ex) { System.err.println("[F1 Restore] Could not save settings: " + ex.getMessage()); }
    }
    public static void tick() {
        FrameProfiler.tick();
        NativeSettings.tick();
        ModeController.tick();
    }
}
