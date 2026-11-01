package dev.f1restore.mixin;
import net.minecraft.client.Minecraft;
import net.minecraft.unmapped.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import dev.f1restore.*;

@Mixin(targets="net.minecraft.unmapped.C_51094326", remap=false)
public abstract class ChatMixin implements ChatBridge {
    @org.spongepowered.asm.mixin.Shadow @org.spongepowered.asm.mixin.Final private java.util.List<C_46155730> f_88224172;
    @org.spongepowered.asm.mixin.Shadow public abstract boolean m_96530659();
    @Override public boolean f1restore$hasVisibleChat(int ticks) {
        return !this.f_88224172.isEmpty() && (this.m_96530659() || ticks - this.f_88224172.get(0).m_65631873() < 200);
    }
    @Inject(method="m_16106529", at=@At("HEAD"), cancellable=true)
    private void f1restore$hide(CallbackInfo ci) { if (State.block(Feature.CHAT) && Minecraft.m_20213497().f_70816363 == null) ci.cancel(); }
}
