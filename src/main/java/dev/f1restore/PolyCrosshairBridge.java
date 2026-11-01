package dev.f1restore;
import net.minecraft.client.Minecraft;
import net.minecraft.unmapped.*;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
public final class PolyCrosshairBridge {
    private static boolean resolved;
    private static MethodHandle renderer;
    private static String status = "Not checked";
    public static boolean available() { resolve(); return renderer != null; }
    public static String status() { resolve(); return status; }
    private static void resolve() {
        if (resolved) return;
        resolved = true;
        try {
            Class<?> type = Class.forName("org.polyfrost.crosshair.render.CrosshairRenderer", true, PolyCrosshairBridge.class.getClassLoader());
            Object instance = type.getField("INSTANCE").get(null);
            renderer = MethodHandles.publicLookup().unreflect(type.getMethod("render", C_43718703.class, boolean.class)).bindTo(instance);
            status = "Connected";
        } catch (ClassNotFoundException ex) { status = "Not installed"; }
        catch (ReflectiveOperationException | LinkageError ex) { fail(ex); }
    }
    public static boolean render(C_43718703 window, boolean vanillaVisible) {
        resolve();
        if (renderer == null) return false;
        try { return (boolean)renderer.invokeExact(window, vanillaVisible); }
        catch (VirtualMachineError | ThreadDeath ex) { throw ex; }
        catch (Throwable ex) { fail(ex); return false; }
    }
    private static void fail(Throwable ex) {
        renderer = null;
        status = "Unavailable; vanilla fallback";
        System.err.println("[F1 Restore] PolyCrosshair bridge unavailable: " + ex);
    }
}
