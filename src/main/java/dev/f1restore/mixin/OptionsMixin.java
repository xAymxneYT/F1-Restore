package dev.f1restore.mixin;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import dev.f1restore.State;
import net.minecraft.client.Minecraft; import net.minecraft.unmapped.C_76792751; import net.minecraft.unmapped.C_23708450;

@Mixin(value=C_76792751.class, remap=false)
public abstract class OptionsMixin {
    @Inject(method="<init>", at=@At(value="INVOKE", target="Lnet/minecraft/unmapped/C_76792751;m_70449379()V"), require=1)
    private void f1restore$register(CallbackInfo ci) { State.register((C_76792751)(Object)this); }
}
