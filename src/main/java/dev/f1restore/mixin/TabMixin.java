package dev.f1restore.mixin;
import net.minecraft.client.Minecraft;
import net.minecraft.unmapped.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import dev.f1restore.*;

@Mixin(targets="net.minecraft.unmapped.C_16311865", remap=false)
public abstract class TabMixin {
    @Inject(method="m_35336664", at=@At("HEAD"), cancellable=true)
    private void f1restore$hide(CallbackInfo ci) { if (State.block(Feature.TAB)) ci.cancel(); }
}
