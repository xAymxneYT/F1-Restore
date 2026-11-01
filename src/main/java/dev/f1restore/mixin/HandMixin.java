package dev.f1restore.mixin;
import net.minecraft.client.Minecraft;
import net.minecraft.unmapped.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import dev.f1restore.*;

@Mixin(targets="net.minecraft.unmapped.C_03140863", remap=false)
public abstract class HandMixin {
    @Shadow private Minecraft f_61271335;
    @Shadow @Final public C_35412243 f_75697535;
    @Shadow public abstract void m_72834990();
    @Shadow public abstract void m_43380338();
    @Unique private boolean f1restore$handDrawn;
    @Inject(method="m_38540407", at=@At("HEAD"))
    private void f1restore$begin(float delta, int pass, CallbackInfo ci) { this.f1restore$handDrawn = false; }
    @Redirect(method="m_38540407", at=@At(value="INVOKE", target="Lnet/minecraft/unmapped/C_35412243;m_03415899(F)V"), require=1)
    private void f1restore$draw(C_35412243 renderer, float delta) {
        if (!ModeController.hideHand()) { long stamp = State.inF1() ? FrameProfiler.start() : 0L; renderer.m_03415899(delta); FrameProfiler.record(Feature.HAND, stamp); this.f1restore$handDrawn = true; }
    }
    @Inject(method="m_38540407", at=@At(value="INVOKE", target="Lnet/minecraft/unmapped/C_37925375;m_23590488()V", ordinal=0), require=1)
    private void f1restore$restore(float delta, int pass, CallbackInfo ci) {
        if (!State.inF1() || !State.selected(Feature.HAND) || this.f1restore$handDrawn) return;
        Minecraft mc = this.f_61271335;
        C_42232651 camera = mc.m_67314413();
        if (mc.f_58088711.f_33865429 != 0 || mc.f_17639899.m_52384723()) return;
        if (camera instanceof C_74425583 && ((C_74425583)camera).m_18110524()) return;
        this.m_72834990();
        long stamp = FrameProfiler.start();
        this.f_75697535.m_03415899(delta);
        FrameProfiler.record(Feature.HAND, stamp);
        this.m_43380338();
        this.f1restore$handDrawn = true;
    }
}
