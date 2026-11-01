package dev.f1restore.mixin;
import dev.f1restore.*;
import net.minecraft.unmapped.C_76792751;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
@Pseudo
@Mixin(targets="org.polyfrost.oneconfig.internal.OneConfig", remap=false)
public abstract class OneConfigHudVisibilityMixin {
    @Redirect(method="*", at=@At(value="FIELD", target="Lnet/minecraft/unmapped/C_76792751;f_06955029:Z", opcode=180), require=1)
    private static boolean f1restore$widgets(C_76792751 options) {
        return options.f_06955029 && !(State.inF1() && State.selected(Feature.ADDON_HUDS));
    }
}
