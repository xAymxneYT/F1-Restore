package dev.f1restore.mixin;
import net.minecraft.client.Minecraft;
import net.minecraft.unmapped.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import dev.f1restore.*;

@Mixin(targets="net.minecraft.unmapped.C_97866173", remap=false)
public abstract class IsolatedHudMixin extends C_29058632 implements HudBridge {
    @Shadow @Final private Minecraft f_18124716;
    @Shadow @Final private C_51094326 f_31347527;
    @Shadow @Final private C_53551716 f_26183868;
    @Shadow @Final private C_05206503 f_80841473;
    @Shadow @Final private C_16311865 f_87268340;
    @Shadow private int f_86797423;
    @Shadow private int f_18761244;
    @Shadow private int f_86854635;
    @Shadow private int f_12119563;
    @Shadow private int f_13455300;
    @Shadow private int f_07589675;
    @Shadow private int f_22489246;
    @Shadow private C_88708284 f_98474179;
    @Shadow private String f_53236833;
    @Shadow private String f_62781793;
    @Shadow private String f_67587688;
    @Shadow private boolean f_46262816;
    @Shadow protected abstract boolean m_54933694();
    @Shadow protected abstract void m_17482805(C_43718703 window, float delta);
    @Shadow private void m_24393454(C_43718703 window) { throw new AssertionError(); }
    @Shadow public abstract void m_33306183(C_43718703 window, int x);
    @Shadow public abstract void m_99486747(C_43718703 window, int x);
    @Shadow public abstract void m_40186702(C_43718703 window);
    @Shadow private void m_68540463(C_30593188 objective, C_43718703 window) { throw new AssertionError(); }
    @Shadow private void m_19807489() { throw new AssertionError(); }
    @Shadow private void m_15423002(C_43718703 window) { throw new AssertionError(); }
    @Shadow private void m_77930904(float brightness, C_43718703 window) { throw new AssertionError(); }
    @Shadow private void m_56920776(float strength, C_43718703 window) { throw new AssertionError(); }
    @Override public void f1restore$renderIsolated(float delta) {
        Minecraft mc = this.f_18124716;
        if (!State.hasHud() || mc.f_28396371 == null || mc.f_26407825 == null) return;
        boolean spectator = mc.f_17639899.m_52384723();
        boolean horse = mc.f_28396371.m_82873565();
        boolean hotbar = State.selected(Feature.HOTBAR) && (!spectator || this.f_80841473.m_76386387());
        boolean vanillaCrosshair = State.selected(Feature.CROSSHAIR) && this.m_54933694();
        boolean crosshair = State.selected(Feature.CROSSHAIR) && (vanillaCrosshair || PolyCrosshairBridge.available());
        boolean status = State.selected(Feature.STATUS) && mc.f_17639899.m_20190380();
        boolean xp = State.selected(Feature.XP) && !horse && mc.f_17639899.m_65722964();
        boolean jump = State.selected(Feature.JUMP) && horse;
        boolean chat = State.selected(Feature.CHAT) && mc.f_58088711.f_29432737 != net.minecraft.unmapped.C_40996800.C_42934102.f_03841634
            && ((ChatBridge)this.f_31347527).f1restore$hasVisibleChat(this.f_86797423);
        boolean debug = State.selected(Feature.DEBUG) && mc.f_58088711.f_63869242;
        boolean itemName = State.selected(Feature.ITEM_NAME) && (spectator ? this.f_80841473.m_76386387()
            : mc.f_58088711.f_04525784 && this.f_18761244 > 0 && this.f_98474179 != null);
        boolean notice = State.selected(Feature.NOTICES) && this.f_86854635 > 0;
        boolean titles = State.selected(Feature.TITLES) && this.f_12119563 > 0;
        boolean boss = State.selected(Feature.BOSS) && C_93010634.f_91711528 > 0 && C_93010634.f_04281774 != null;
        boolean vignette = State.selected(Feature.VIGNETTE) && Minecraft.m_14282231();
        float portalStrength = State.selected(Feature.NAUSEA) ? mc.f_28396371.f_52763846 + (mc.f_28396371.f_12371388 - mc.f_28396371.f_52763846) * delta : 0.0F;
        boolean portal = portalStrength > 0.0F && !mc.f_28396371.m_27258118(C_26934808.f_90408006);
        boolean pumpkin = false;
        if (State.selected(Feature.PUMPKIN) && mc.f_58088711.f_33865429 == 0) {
            C_88708284 helmet = mc.f_28396371.f_29439708.m_34290324(3);
            pumpkin = helmet != null && helmet.m_23235460() == C_98888256.m_11873116(C_53524147.f_51274940);
        }
        C_73166169 scores = null;
        C_30593188 sidebar = null, tabObjective = null;
        boolean tab = State.selected(Feature.TAB) && mc.f_58088711.f_34423269.m_70603120();
        if (State.selected(Feature.SCOREBOARD) || tab) {
            scores = mc.f_26407825.m_63054104();
            if (State.selected(Feature.SCOREBOARD)) {
                C_97595263 team = scores.m_42132668(mc.f_28396371.m_98997855());
                if (team != null) { int color = team.m_88062935().m_13275291(); if (color >= 0) sidebar = scores.m_63794212(3 + color); }
                if (sidebar == null) sidebar = scores.m_63794212(1);
            }
            if (tab) tabObjective = scores.m_63794212(0);
        }
        this.f_87268340.m_60433573(tab);
        boolean addons = State.selected(Feature.ADDON_HUDS) && OneConfigHudBridge.available();
        if (!(addons || hotbar || crosshair || status || xp || jump || chat || debug || itemName || notice || titles || boss || vignette || portal || pumpkin || sidebar != null || tab)) return;
        C_43718703 window = ScreenMetrics.get(mc);
        int width = window.m_39119333(), height = window.m_16103615();
        f1restore$begin(window);
        try {
            if (vignette) { long stamp = FrameProfiler.start();  this.m_77930904(mc.f_28396371.m_95186532(delta), window); f1restore$flat();  FrameProfiler.record(Feature.VIGNETTE, stamp); }
            if (pumpkin) { long stamp = FrameProfiler.start();  this.m_15423002(window); f1restore$flat();  FrameProfiler.record(Feature.PUMPKIN, stamp); }
            if (portal) { long stamp = FrameProfiler.start();  this.m_56920776(portalStrength, window); f1restore$flat();  FrameProfiler.record(Feature.NAUSEA, stamp); }
            if (hotbar) { long stamp = FrameProfiler.start(); 
                boolean needsDepth = spectator;
                if (!needsDepth) for (int slot = 0; slot < 9; slot++) if (mc.f_28396371.f_29439708.f_35249023[slot] != null) { needsDepth = true; break; }
                if (needsDepth) { C_37925375.m_94091499(true); ScopedDepthClear.clear(window, mc); C_37925375.m_62426402(); }
                if (spectator) this.f_80841473.m_18022406(window, delta);
                else this.m_17482805(window, delta);
                f1restore$flat();
             FrameProfiler.record(Feature.HOTBAR, stamp); }
            if (crosshair) {
                long stamp = FrameProfiler.start();
                boolean custom = PolyCrosshairBridge.render(window, vanillaCrosshair);
                if (!custom && vanillaCrosshair) {
                    mc.m_65924382().m_69671809(this.f_01232700);
                    C_37925375.m_55656894(775, 769, 1, 0);
                    this.m_66359748(width / 2 - 7, height / 2 - 7, 0, 0, 16, 16);
                    C_37925375.m_55656894(770, 771, 1, 0);
                    if (!State.leanState) f1restore$flat();
                } else f1restore$flat();
                FrameProfiler.record(Feature.CROSSHAIR, stamp);
            }
            if (boss) { long stamp = FrameProfiler.start();  mc.m_65924382().m_69671809(this.f_01232700); this.m_19807489(); f1restore$flat();  FrameProfiler.record(Feature.BOSS, stamp); }
            if (status) { long stamp = FrameProfiler.start();  mc.m_65924382().m_69671809(this.f_01232700); this.m_24393454(window); if (!State.leanState) f1restore$flat();  FrameProfiler.record(Feature.STATUS, stamp); }
            if (jump) { long stamp = FrameProfiler.start();  this.m_99486747(window, width / 2 - 91); if (!State.leanState) f1restore$flat();  FrameProfiler.record(Feature.JUMP, stamp); }
            if (xp) { long stamp = FrameProfiler.start();  this.m_33306183(window, width / 2 - 91); f1restore$flat();  FrameProfiler.record(Feature.XP, stamp); }
            if (itemName) { long stamp = FrameProfiler.start();  if (spectator) this.f_80841473.m_92645776(window); else this.m_40186702(window); f1restore$flat();  FrameProfiler.record(Feature.ITEM_NAME, stamp); }
            if (debug) { long stamp = FrameProfiler.start();  this.f_26183868.m_58915502(window); f1restore$flat();  FrameProfiler.record(Feature.DEBUG, stamp); }
            if (notice) { long stamp = FrameProfiler.start();  f1restore$notice(delta, width, height); f1restore$flat();  FrameProfiler.record(Feature.NOTICES, stamp); }
            if (titles) { long stamp = FrameProfiler.start();  f1restore$titles(delta, width, height); f1restore$flat();  FrameProfiler.record(Feature.TITLES, stamp); }
            if (sidebar != null) { long stamp = FrameProfiler.start();  this.m_68540463(sidebar, window); f1restore$flat();  FrameProfiler.record(Feature.SCOREBOARD, stamp); }
            if (chat) { long stamp = FrameProfiler.start(); 
                C_37925375.m_55305366();
                C_37925375.m_36201038(0.0F, (float)(height - 48), 0.0F);
                this.f_31347527.m_16106529(this.f_86797423);
                C_37925375.m_23590488();
                f1restore$flat();
             FrameProfiler.record(Feature.CHAT, stamp); }
            if (tab) { long stamp = FrameProfiler.start();  this.f_87268340.m_35336664(width, scores, tabObjective);  FrameProfiler.record(Feature.TAB, stamp); }
            if (addons) {
                long stamp = FrameProfiler.start();
                f1restore$flat();
                OneConfigHudBridge.draw();
                FrameProfiler.record(Feature.ADDON_HUDS, stamp);
            }
        } finally { f1restore$end(); }
    }
    @Unique private static void f1restore$begin(C_43718703 window) {
        C_37925375.m_76931806(5889); C_37925375.m_55305366(); C_37925375.m_89802155();
        C_37925375.m_80062613(0.0D, window.m_31221441(), window.m_98453558(), 0.0D, 1000.0D, 3000.0D);
        C_37925375.m_76931806(5888); C_37925375.m_55305366(); C_37925375.m_89802155();
        C_37925375.m_36201038(0.0F, 0.0F, -2000.0F);
        f1restore$flat();
    }
    @Unique private static void f1restore$flat() {
        C_37925375.m_21251773(); C_37925375.m_94091499(false); C_37925375.m_04437956();
        C_37925375.m_65692258(); C_37925375.m_65380947(516, 0.1F);
        C_37925375.m_33736929(); C_37925375.m_94088189(); C_37925375.m_55656894(770, 771, 1, 0);
        C_37925375.m_64365553(1.0F, 1.0F, 1.0F, 1.0F);
    }
    @Unique private static void f1restore$end() {
        C_37925375.m_94091499(true); C_37925375.m_62426402(); C_37925375.m_65692258(); C_37925375.m_72824863();
        C_37925375.m_04437956(); C_37925375.m_64365553(1.0F, 1.0F, 1.0F, 1.0F);
        C_37925375.m_76931806(5888); C_37925375.m_23590488();
        C_37925375.m_76931806(5889); C_37925375.m_23590488(); C_37925375.m_76931806(5888);
    }
    @Unique private void f1restore$notice(float delta, int width, int height) {
        float remaining = this.f_86854635 - delta;
        int alpha = Math.min(255, (int)(remaining * 255.0F / 20.0F));
        if (alpha <= 8) return;
        Minecraft mc = this.f_18124716;
        int color = this.f_46262816 ? C_45510667.m_71777013(remaining / 50.0F, 0.7F, 0.6F) & 0xFFFFFF : 0xFFFFFF;
        C_37925375.m_55305366(); C_37925375.m_36201038(width / 2.0F, height - 68.0F, 0.0F);
        String text = this.f_67587688;
        mc.f_21497584.m_72964882(text, -mc.f_21497584.m_09235706(text) / 2, -4, color | alpha << 24);
        C_37925375.m_23590488();
    }
    @Unique private void f1restore$titles(float delta, int width, int height) {
        float remaining = this.f_12119563 - delta;
        int alpha = 255;
        if (this.f_12119563 > this.f_22489246 + this.f_07589675 && this.f_13455300 > 0)
            alpha = (int)((this.f_13455300 + this.f_07589675 + this.f_22489246 - remaining) * 255.0F / this.f_13455300);
        if (this.f_12119563 <= this.f_22489246 && this.f_22489246 > 0)
            alpha = (int)(remaining * 255.0F / this.f_22489246);
        alpha = Math.max(0, Math.min(255, alpha));
        if (alpha <= 8) return;
        Minecraft mc = this.f_18124716;
        C_37925375.m_55305366(); C_37925375.m_36201038(width / 2.0F, height / 2.0F, 0.0F);
        f1restore$titleLine(this.f_53236833, 4.0F, -10.0F, alpha, mc.f_21497584);
        f1restore$titleLine(this.f_62781793, 2.0F, 5.0F, alpha, mc.f_21497584);
        C_37925375.m_23590488();
    }
    @Unique private static void f1restore$titleLine(String text, float scale, float y, int alpha, C_23014920 font) {
        if (text == null || text.isEmpty()) return;
        C_37925375.m_55305366(); C_37925375.m_77345436(scale, scale, scale);
        font.m_11445413(text, -font.m_09235706(text) / 2.0F, y, 0xFFFFFF | alpha << 24, true);
        C_37925375.m_23590488();
    }
}
