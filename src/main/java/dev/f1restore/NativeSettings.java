package dev.f1restore;
public final class NativeSettings {
    public static boolean ready;
    private static boolean failed;
    private static int delay = 10;
    public static void tick() {
        if (ready || failed || delay-- > 0) return;
        try {
            OneClientConfig.install();
            ready = true;
        } catch (VirtualMachineError | ThreadDeath ex) { throw ex; }
        catch (Throwable ex) {
            failed = true;
            System.err.println("[F1 Restore] OneConfig settings unavailable; vanilla F1 and fallback F4 remain available: " + ex);
        }
    }
}
