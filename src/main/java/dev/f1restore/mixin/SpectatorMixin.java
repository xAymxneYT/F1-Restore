package dev.f1restore.mixin;
import net.minecraft.client.Minecraft;
import net.minecraft.unmapped.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import dev.f1restore.*;
@Mixin(targets="net.minecraft.unmapped.C_05206503", remap=false)
public abstract class SpectatorMixin {
    @Inject(method="m_18022406", at=@At("HEAD"), cancellable=true)
    private void f1restore$hotbar(CallbackInfo ci) { if (State.block(Feature.HOTBAR)) ci.cancel(); }
    @Inject(method="m_92645776", at=@At("HEAD"), cancellable=true)
    private void f1restore$item_name(CallbackInfo ci) { if (State.block(Feature.ITEM_NAME)) ci.cancel(); }
}
