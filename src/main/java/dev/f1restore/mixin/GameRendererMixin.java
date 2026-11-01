package dev.f1restore.mixin;
import net.minecraft.client.Minecraft;
import net.minecraft.unmapped.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import dev.f1restore.*;

@Mixin(targets="net.minecraft.unmapped.C_03140863", remap=false)
public abstract class GameRendererMixin {
    @Shadow private Minecraft f_61271335;
    @Inject(method="m_08357947", at=@At(value="FIELD", target="Lnet/minecraft/unmapped/C_76792751;f_06955029:Z"), require=1)
    private void f1restore$isolated(float delta, long start, CallbackInfo ci) {
        if (State.inF1() && this.f_61271335.f_70816363 == null && State.enabled) {
            long stamp = FrameProfiler.start();
            try { ((HudBridge)this.f_61271335.f_28235293).f1restore$renderIsolated(delta); }
            finally { FrameProfiler.frame(stamp); }
        }
    }
    @Redirect(method="m_08357947", at=@At(value="INVOKE", target="Lnet/minecraft/unmapped/C_97866173;m_94668477(F)V"), require=1)
    private void f1restore$skipShared(C_97866173 hud, float delta) {
        if (!ModeController.guiHidden() || this.f_61271335.f_70816363 != null) hud.m_94668477(delta);
    }
    @Redirect(method="m_02087150", at=@At(value="FIELD", target="Lnet/minecraft/unmapped/C_76792751;f_06955029:Z"), require=1)
    private boolean f1restore$outline(C_76792751 options) { return State.hidden(options, Feature.OUTLINE); }
    @Redirect(method="m_71403968", at=@At(value="FIELD", target="Lnet/minecraft/unmapped/C_76792751;f_06955029:Z"), require=1)
    private boolean f1restore$axis(C_76792751 options) { return State.hidden(options, Feature.DEBUG); }
}
