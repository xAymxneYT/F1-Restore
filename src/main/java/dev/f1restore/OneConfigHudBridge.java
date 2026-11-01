package dev.f1restore;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
public final class OneConfigHudBridge {
    private static boolean checked;
    private static MethodHandle render;
    public static boolean available() {
        if (checked) return render != null;
        checked = true;
        try {
            Class<?> type = Class.forName("org.polyfrost.oneconfig.internal.OneConfig", false, OneConfigHudBridge.class.getClassLoader());
            render = MethodHandles.publicLookup().unreflect(type.getMethod("render"));
        } catch (ReflectiveOperationException | LinkageError ex) {
            System.err.println("[F1 Restore] Add-on HUD renderer unavailable: " + ex);
        }
        return render != null;
    }
    public static void draw() {
        if (!available()) return;
        try { render.invokeExact(); }
        catch (VirtualMachineError | ThreadDeath ex) { throw ex; }
        catch (Throwable ex) {
            render = null;
            System.err.println("[F1 Restore] Add-on HUD rendering disabled after error: " + ex);
        }
    }
}
