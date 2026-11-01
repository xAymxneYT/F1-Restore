package dev.f1restore.mixin;
import net.minecraft.client.Minecraft;
import net.minecraft.unmapped.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import dev.f1restore.*;
@Mixin(targets="net.minecraft.unmapped.C_97866173", remap=false)
public abstract class HudMixin {
    @Inject(method="m_17482805", at=@At("HEAD"), cancellable=true)
    private void f1restore$hotbar(CallbackInfo ci) { if (State.block(Feature.HOTBAR)) ci.cancel(); }
    @Inject(method="m_24393454", at=@At("HEAD"), cancellable=true)
    private void f1restore$status(CallbackInfo ci) { if (State.block(Feature.STATUS)) ci.cancel(); }
    @Inject(method="m_33306183", at=@At("HEAD"), cancellable=true)
    private void f1restore$xp(CallbackInfo ci) { if (State.block(Feature.XP)) ci.cancel(); }
    @Inject(method="m_99486747", at=@At("HEAD"), cancellable=true)
    private void f1restore$jump(CallbackInfo ci) { if (State.block(Feature.JUMP)) ci.cancel(); }
    @Inject(method="m_68540463", at=@At("HEAD"), cancellable=true)
    private void f1restore$scoreboard(CallbackInfo ci) { if (State.block(Feature.SCOREBOARD)) ci.cancel(); }
    @Inject(method="m_40186702", at=@At("HEAD"), cancellable=true)
    private void f1restore$item_name(CallbackInfo ci) { if (State.block(Feature.ITEM_NAME)) ci.cancel(); }
    @Inject(method="m_19807489", at=@At("HEAD"), cancellable=true)
    private void f1restore$boss(CallbackInfo ci) { if (State.block(Feature.BOSS)) ci.cancel(); }
    @Inject(method="m_15423002", at=@At("HEAD"), cancellable=true)
    private void f1restore$pumpkin(CallbackInfo ci) { if (State.block(Feature.PUMPKIN)) ci.cancel(); }
    @Inject(method="m_77930904", at=@At("HEAD"), cancellable=true)
    private void f1restore$vignette(CallbackInfo ci) { if (State.block(Feature.VIGNETTE)) ci.cancel(); }
    @Inject(method="m_56920776", at=@At("HEAD"), cancellable=true)
    private void f1restore$nausea(CallbackInfo ci) { if (State.block(Feature.NAUSEA)) ci.cancel(); }
    @Inject(method="m_54933694", at=@At("HEAD"), cancellable=true)
    private void f1restore$crosshair(CallbackInfoReturnable<Boolean> cir) { if (State.block(Feature.CROSSHAIR)) cir.setReturnValue(false); }
    @Shadow private int f_12119563;
    @Redirect(method="m_94668477", at=@At(value="FIELD", target="Lnet/minecraft/unmapped/C_97866173;f_12119563:I"), require=1)
    private int f1restore$titles(C_97866173 hud) { return State.block(Feature.TITLES) ? 0 : this.f_12119563; }
    @Shadow private int f_86854635;
    @Redirect(method="m_94668477", at=@At(value="FIELD", target="Lnet/minecraft/unmapped/C_97866173;f_86854635:I"), require=1)
    private int f1restore$notices(C_97866173 hud) { return State.block(Feature.NOTICES) ? 0 : this.f_86854635; }
}
