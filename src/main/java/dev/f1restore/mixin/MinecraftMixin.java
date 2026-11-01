package dev.f1restore.mixin;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import dev.f1restore.State; import dev.f1restore.Feature;
import net.minecraft.client.Minecraft; import net.minecraft.unmapped.C_76792751; import net.minecraft.unmapped.C_23708450;

@Mixin(value=Minecraft.class, remap=false)
public abstract class MinecraftMixin {
    @Redirect(method="m_28306370", at=@At(value="FIELD", target="Lnet/minecraft/unmapped/C_76792751;f_06955029:Z"), require=1)
    private static boolean f1restore$names(C_76792751 options) { return State.hidden(options, Feature.NAMES); }
    @Redirect(method="m_97212553", at=@At(value="FIELD", target="Lnet/minecraft/unmapped/C_76792751;f_06955029:Z"), require=1)
    private boolean f1restore$debug(C_76792751 options) { return State.hidden(options, Feature.DEBUG); }
    @Redirect(method="m_67975642", at=@At(value="FIELD", target="Lnet/minecraft/unmapped/C_76792751;f_06955029:Z", opcode=181), require=1)
    private void f1restore$normalKey(C_76792751 options, boolean value) { dev.f1restore.ModeController.vanillaWrite(options, value); }
    @Inject(method="m_67975642", at=@At("RETURN"))
    private void f1restore$tick(CallbackInfo ci) { State.tick(); }
}
