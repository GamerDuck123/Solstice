package me.alexdevs.solstice.mixin.modules.admin;

import com.mojang.authlib.GameProfile;
import me.alexdevs.solstice.Solstice;
import me.alexdevs.solstice.api.events.PlayerConnectionEvents;
import net.minecraft.server.dedicated.DedicatedPlayerList;
import net.minecraft.server.players.NameAndId;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(DedicatedPlayerList.class)
public abstract class ConnectionBypassMixin {
    @Inject(method = "isWhiteListed", at = @At("HEAD"), cancellable = true)
    public void solstice$bypassWhitelist(NameAndId nameAndId, CallbackInfoReturnable<Boolean> cir) {
        try {
            if (PlayerConnectionEvents.WHITELIST_BYPASS.invoker().bypassWhitelist(nameAndId.id()))
                cir.setReturnValue(true);
        } catch (Exception e) {
            Solstice.LOGGER.error("Error checking whitelist bypass for profile {}", nameAndId.id(), e);
        }
    }

    @Inject(method = "canBypassPlayerLimit", at = @At("HEAD"), cancellable = true)
    public void solstice$bypassPlayerLimit(NameAndId nameAndId, CallbackInfoReturnable<Boolean> cir) {
        try {
            if (PlayerConnectionEvents.FULL_SERVER_BYPASS.invoker().bypassFullServer(nameAndId.id()))
                cir.setReturnValue(true);
        } catch (Exception e) {
            Solstice.LOGGER.error("Error checking full server bypass for profile {}", nameAndId.id(), e);
        }
    }
}
