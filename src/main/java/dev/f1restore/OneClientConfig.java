package dev.f1restore;
import org.polyfrost.oneconfig.api.config.v1.Config;
import org.polyfrost.oneconfig.api.config.v1.Tree;
import org.polyfrost.oneconfig.api.config.v1.annotations.Switch;
import org.polyfrost.oneconfig.api.config.v1.annotations.Button;
import org.polyfrost.oneconfig.api.config.v1.annotations.Keybind;
import org.polyfrost.oneconfig.api.ui.v1.keybind.OneConfigKeybind;
import org.polyfrost.oneconfig.api.ui.v1.keybind.KeybindManager;

public final class OneClientConfig extends Config {
    private static OneClientConfig instance;
    private OneConfigKeybind registeredNormal, registeredOptimized;
    private Tree boundTree;
    @Switch(title="Hand and held item", description="Hand, held item and animations.", category="HUD", subcategory="Main HUD")
    public boolean hand = State.visible[Feature.HAND.ordinal()];
    @Switch(title="Hotbar", description="Slots, items, counts and durability.", category="HUD", subcategory="Main HUD")
    public boolean hotbar = State.visible[Feature.HOTBAR.ordinal()];
    @Switch(title="Crosshair", description="PolyCrosshair supported.", category="HUD", subcategory="Main HUD")
    public boolean crosshair = State.visible[Feature.CROSSHAIR.ordinal()];
    @Switch(title="Survival bars", description="Health, hunger, armour, air and horse health.", category="HUD", subcategory="Main HUD")
    public boolean status = State.visible[Feature.STATUS.ordinal()];
    @Switch(title="Experience", description="Show the XP bar and your level.", category="HUD", subcategory="Main HUD")
    public boolean xp = State.visible[Feature.XP.ordinal()];
    @Switch(title="Horse jump bar", description="Show charging progress while riding a horse.", category="HUD", subcategory="Main HUD")
    public boolean jump = State.visible[Feature.JUMP.ordinal()];
    @Switch(title="Chat", description="Respects Minecraft chat visibility.", category="HUD", subcategory="Main HUD")
    public boolean chat = State.visible[Feature.CHAT.ordinal()];
    @Switch(title="Scoreboard sidebar", description="Show the server scoreboard when an objective exists.", category="HUD", subcategory="Main HUD")
    public boolean scoreboard = State.visible[Feature.SCOREBOARD.ordinal()];
    @Switch(title="Player list", description="Show the player list while holding Tab.", category="HUD", subcategory="Main HUD")
    public boolean tab = State.visible[Feature.TAB.ordinal()];
    @Switch(title="Selected item name", description="Show the temporary item-name message.", category="HUD", subcategory="Main HUD")
    public boolean item_name = State.visible[Feature.ITEM_NAME.ordinal()];
    @Switch(title="Action bar and music messages", description="Show active notifications above the hotbar.", category="HUD", subcategory="Main HUD")
    public boolean notices = State.visible[Feature.NOTICES.ordinal()];
    @Switch(title="Titles and subtitles", description="Show active server titles and subtitles.", category="HUD", subcategory="Main HUD")
    public boolean titles = State.visible[Feature.TITLES.ordinal()];
    @Switch(title="Boss bar", description="Show an active boss health bar.", category="HUD", subcategory="Main HUD")
    public boolean boss = State.visible[Feature.BOSS.ordinal()];
    @Switch(title="Debug screen", description="Allow the debug screen when F3 is open.", category="HUD", subcategory="Extra")
    public boolean debug = State.visible[Feature.DEBUG.ordinal()];
    @Switch(title="Player names", description="Allow names above players.", category="HUD", subcategory="Extra")
    public boolean names = State.visible[Feature.NAMES.ordinal()];
    @Switch(title="Block outline", description="Custom highlights use their own Show in F1 option.", category="HUD", subcategory="Extra")
    public boolean outline = State.visible[Feature.OUTLINE.ordinal()];
    @Switch(title="Pumpkin overlay", description="Show the overlay while wearing a pumpkin.", category="HUD", subcategory="Extra")
    public boolean pumpkin = State.visible[Feature.PUMPKIN.ordinal()];
    @Switch(title="Vignette", description="Show dark shading at the edges of the screen.", category="HUD", subcategory="Extra")
    public boolean vignette = State.visible[Feature.VIGNETTE.ordinal()];
    @Switch(title="Portal overlay", description="Show the overlay only while a portal effect is active.", category="HUD", subcategory="Extra")
    public boolean nausea = State.visible[Feature.NAUSEA.ordinal()];
    @Switch(title="Evergreen / OneConfig widgets", description="Optional: uses the full OneConfig HUD renderer and costs FPS.", category="HUD", subcategory="Extra")
    public boolean addon_huds = State.visible[Feature.ADDON_HUDS.ordinal()];
    @Keybind(title="Normal F1", description="Clean F1. Press again for normal view.", category="Controls", subcategory="Two display modes")
    public OneConfigKeybind normalKey = key(290, false);
    @Keybind(title="Optimized HUD", description="Your selected HUD, with the fast rendering path.", category="Controls", subcategory="Two display modes")
    public OneConfigKeybind optimizedKey = key(293, true);
    @Switch(title="Reduce repeated graphics setup", description="Less setup work. Disable if HUD graphics glitch.", category="Performance", subcategory="Rendering")
    public boolean leanState = State.leanState;
    @Switch(title="Reuse screen-size calculations", description="Reuse unchanged screen dimensions.", category="Performance", subcategory="Rendering")
    public boolean reuseMetrics = State.reuseMetrics;
    @Switch(title="Scoped depth clear - experimental", description="Experimental. Compare FPS; disable if items look wrong.", category="Performance", subcategory="Rendering")
    public boolean scopedDepth = State.scopedDepth;
    public OneClientConfig() {
        super("f1restore.json", "/assets/f1restore/icon.svg", "F1 Restore", Category.PERFORMANCE);
    }
    public static void install() {
        if (instance != null) return;
        instance = new OneClientConfig();
        instance.initialize(false);
        if (instance.getTree() == null || instance.boundTree == null) throw new IllegalStateException("OneConfig did not initialize the settings and bindings");
    }
    private static OneConfigKeybind key(int code, boolean optimized) {
        return new OneConfigKeybind(new int[]{code}, null, (byte)0, 0L, pressed -> {
            if (pressed) { if (optimized) ModeController.optimized(); else ModeController.normal(); }
            return true;
        });
    }
    @Override public void initialize(boolean byConfigManager) {
        super.initialize(byConfigManager);
        Tree tree = getTree();
        if (tree == null || tree == boundTree) return;
        boundTree = tree;
        if (!State.fastDefaults141) {
            if (normalKey != null) KeybindManager.unregister(normalKey);
            if (optimizedKey != null) KeybindManager.unregister(optimizedKey);
            getProperty("normalKey").setAs(key(290, false));
            getProperty("optimizedKey").setAs(key(293, true));
            getProperty("addon_huds").setAs(false);
            State.fastDefaults141 = true;
            save();
        }
        State.visible[Feature.HAND.ordinal()] = hand;
        getProperty("hand").addCallback(value -> { State.visible[Feature.HAND.ordinal()] = hand; State.save(); return false; });
        State.visible[Feature.HOTBAR.ordinal()] = hotbar;
        getProperty("hotbar").addCallback(value -> { State.visible[Feature.HOTBAR.ordinal()] = hotbar; State.save(); return false; });
        State.visible[Feature.CROSSHAIR.ordinal()] = crosshair;
        getProperty("crosshair").addCallback(value -> { State.visible[Feature.CROSSHAIR.ordinal()] = crosshair; State.save(); return false; });
        State.visible[Feature.STATUS.ordinal()] = status;
        getProperty("status").addCallback(value -> { State.visible[Feature.STATUS.ordinal()] = status; State.save(); return false; });
        State.visible[Feature.XP.ordinal()] = xp;
        getProperty("xp").addCallback(value -> { State.visible[Feature.XP.ordinal()] = xp; State.save(); return false; });
        State.visible[Feature.JUMP.ordinal()] = jump;
        getProperty("jump").addCallback(value -> { State.visible[Feature.JUMP.ordinal()] = jump; State.save(); return false; });
        State.visible[Feature.CHAT.ordinal()] = chat;
        getProperty("chat").addCallback(value -> { State.visible[Feature.CHAT.ordinal()] = chat; State.save(); return false; });
        State.visible[Feature.SCOREBOARD.ordinal()] = scoreboard;
        getProperty("scoreboard").addCallback(value -> { State.visible[Feature.SCOREBOARD.ordinal()] = scoreboard; State.save(); return false; });
        State.visible[Feature.TAB.ordinal()] = tab;
        getProperty("tab").addCallback(value -> { State.visible[Feature.TAB.ordinal()] = tab; State.save(); return false; });
        State.visible[Feature.ITEM_NAME.ordinal()] = item_name;
        getProperty("item_name").addCallback(value -> { State.visible[Feature.ITEM_NAME.ordinal()] = item_name; State.save(); return false; });
        State.visible[Feature.NOTICES.ordinal()] = notices;
        getProperty("notices").addCallback(value -> { State.visible[Feature.NOTICES.ordinal()] = notices; State.save(); return false; });
        State.visible[Feature.TITLES.ordinal()] = titles;
        getProperty("titles").addCallback(value -> { State.visible[Feature.TITLES.ordinal()] = titles; State.save(); return false; });
        State.visible[Feature.BOSS.ordinal()] = boss;
        getProperty("boss").addCallback(value -> { State.visible[Feature.BOSS.ordinal()] = boss; State.save(); return false; });
        State.visible[Feature.DEBUG.ordinal()] = debug;
        getProperty("debug").addCallback(value -> { State.visible[Feature.DEBUG.ordinal()] = debug; State.save(); return false; });
        State.visible[Feature.NAMES.ordinal()] = names;
        getProperty("names").addCallback(value -> { State.visible[Feature.NAMES.ordinal()] = names; State.save(); return false; });
        State.visible[Feature.OUTLINE.ordinal()] = outline;
        getProperty("outline").addCallback(value -> { State.visible[Feature.OUTLINE.ordinal()] = outline; State.save(); return false; });
        State.visible[Feature.PUMPKIN.ordinal()] = pumpkin;
        getProperty("pumpkin").addCallback(value -> { State.visible[Feature.PUMPKIN.ordinal()] = pumpkin; State.save(); return false; });
        State.visible[Feature.VIGNETTE.ordinal()] = vignette;
        getProperty("vignette").addCallback(value -> { State.visible[Feature.VIGNETTE.ordinal()] = vignette; State.save(); return false; });
        State.visible[Feature.NAUSEA.ordinal()] = nausea;
        getProperty("nausea").addCallback(value -> { State.visible[Feature.NAUSEA.ordinal()] = nausea; State.save(); return false; });
        State.visible[Feature.ADDON_HUDS.ordinal()] = addon_huds;
        getProperty("addon_huds").addCallback(value -> { State.visible[Feature.ADDON_HUDS.ordinal()] = addon_huds; State.save(); return false; });
        State.leanState = leanState;
        getProperty("leanState").addCallback(value -> { State.leanState = leanState; State.save(); return false; });
        State.reuseMetrics = reuseMetrics;
        getProperty("reuseMetrics").addCallback(value -> { State.reuseMetrics = reuseMetrics; State.save(); return false; });
        State.scopedDepth = scopedDepth;
        getProperty("scopedDepth").addCallback(value -> { State.scopedDepth = scopedDepth; State.save(); return false; });
        getProperty("normalKey").addCallback(value -> { refreshKeys(); return false; });
        getProperty("optimizedKey").addCallback(value -> { refreshKeys(); return false; });
        refreshKeys();
        State.save();
    }
    private void refreshKeys() {
        if (registeredNormal != null) KeybindManager.unregister(registeredNormal);
        if (registeredOptimized != null) KeybindManager.unregister(registeredOptimized);
        registeredNormal = register(normalKey, "Normal F1", false);
        registeredOptimized = register(optimizedKey, "Optimized HUD", true);
    }
    private static OneConfigKeybind register(OneConfigKeybind source, String name, boolean optimized) {
        if (source == null) return null;
        // Share the exact config-owned instance with OneConfig's automatic registrar.
        // A second copied binding produces another pair in Minecraft's Controls menu.
        source.setName(name);
        source.setCategory("F1 Restore (OneConfig)");
        source.setDefaultKeybind(key(optimized ? 293 : 290, optimized));
        if (!KeybindManager.isRegistered(source)) KeybindManager.register(source);
        return source;
    }
    private void all(boolean value) {
        getProperty("hand").setAs(value);
        getProperty("hotbar").setAs(value);
        getProperty("crosshair").setAs(value);
        getProperty("status").setAs(value);
        getProperty("xp").setAs(value);
        getProperty("jump").setAs(value);
        getProperty("chat").setAs(value);
        getProperty("scoreboard").setAs(value);
        getProperty("tab").setAs(value);
        getProperty("item_name").setAs(value);
        getProperty("notices").setAs(value);
        getProperty("titles").setAs(value);
        getProperty("boss").setAs(value);
        getProperty("debug").setAs(value);
        getProperty("names").setAs(value);
        getProperty("outline").setAs(value);
        getProperty("pumpkin").setAs(value);
        getProperty("vignette").setAs(value);
        getProperty("nausea").setAs(value);
        getProperty("addon_huds").setAs(false);
        save();
    }
    @Button(title="Show standard HUD", description="Enable vanilla elements; keep add-on rendering off.", text="Show all", category="HUD", subcategory="Main HUD")
    public void showAll() { all(true); }
    @Button(title="Hide all elements", description="Start with an empty optimized HUD, then enable only what you need.", text="Hide all", category="HUD", subcategory="Main HUD")
    public void hideAll() { all(false); }
    @Button(title="Measure rendering CPU time", description="While F4 mode is active, collect a 10-second report. Close this menu after starting. Results are written to logs/f1restore-profile.txt. This does not measure GPU time.", text="Start / stop", category="Performance", subcategory="Diagnostics")
    public void measure() { FrameProfiler.toggle(); }
}
