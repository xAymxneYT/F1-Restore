package dev.f1restore;
import net.minecraft.client.Minecraft;
import net.minecraft.unmapped.*;

import java.io.*;
import java.util.*;
public final class FrameProfiler {
    private static boolean active;
    private static long deadline, frames, frameNanos;
    private static final Feature[] FEATURES = Feature.values();
    private static final long[] nanos = new long[FEATURES.length], calls = new long[FEATURES.length];
    public static String result = "No measurement yet";
    public static String second = "Start a 10-second sample from Performance settings";
    public static boolean active() { return active; }
    public static long start() { return active ? System.nanoTime() : 0L; }
    public static void record(Feature feature, long started) {
        if (started == 0 || !active) return;
        int i = feature.ordinal(); nanos[i] += System.nanoTime() - started; calls[i]++;
    }
    public static void frame(long started) { if (started != 0 && active) { frameNanos += System.nanoTime() - started; frames++; } }
    public static void toggle() {
        if (active) { finish(); return; }
        if (!State.inF1()) { result = "Enter optimized F4 mode before starting a measurement"; return; }
        Arrays.fill(nanos, 0L); Arrays.fill(calls, 0L); frames = 0; frameNanos = 0;
        deadline = System.nanoTime() + 10_000_000_000L; active = true;
        result = "Measuring for 10 seconds...";
        second = "Keep the same view; results appear here and in logs";
    }
    public static void tick() { if (active && System.nanoTime() >= deadline) finish(); }
    private static void finish() {
        active = false;
        List<Integer> order = new ArrayList<>();
        for (int i = 0; i < nanos.length; i++) if (calls[i] > 0) order.add(i);
        order.sort((a, b) -> Long.compare(nanos[b], nanos[a]));
        StringBuilder report = new StringBuilder("F1 Restore CPU submission timings (not GPU time)\n");
        report.append("Isolated HUD frames: ").append(frames).append('\n');
        report.append(String.format(Locale.ROOT, "HUD routing + drawing: %.2f us/frame%n", frameNanos / (1000.0 * Math.max(1, frames))));
        for (int i : order) report.append(String.format(Locale.ROOT, "%s: %.2f us/draw; %d draws%n", FEATURES[i].label, nanos[i] / (1000.0 * calls[i]), calls[i]));
        result = order.isEmpty() ? "No visible elements were measured" : format(order.get(0));
        second = order.size() > 1 ? format(order.get(1)) : "Full report: logs/f1restore-profile.txt";
        System.out.print("[F1 Restore] " + report);
        try {
            File file = new File(Minecraft.m_20213497().f_25803373, "logs/f1restore-profile.txt");
            File dir = file.getParentFile();
            if (!dir.isDirectory() && !dir.mkdirs()) throw new IOException("Could not create logs directory");
            try (Writer writer = new OutputStreamWriter(new FileOutputStream(file), "UTF-8")) { writer.write(report.toString()); }
        } catch (IOException ex) { System.err.println("[F1 Restore] Could not save timings: " + ex.getMessage()); }
    }
    private static String format(int i) { return String.format(Locale.ROOT, "%s: %.2f us/draw", FEATURES[i].label, nanos[i] / (1000.0 * Math.max(1, calls[i]))); }
}
