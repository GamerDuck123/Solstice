package me.alexdevs.solstice.mixin.modules.spawn;

import com.llamalad7.mixinextras.sugar.Local;
import me.alexdevs.solstice.modules.ModuleProvider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.NameAndId;
import net.minecraft.server.players.PlayerList;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.LevelData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.Optional;

@Mixin(PlayerList.class)
public abstract class OverrideNewPlayerSpawnPointMixin {
    // Lnet/minecraft/server/MinecraftServer;getLevel(Lnet/minecraft/resources/ResourceKey;)Lnet/minecraft/server/level/ServerLevel;
    @Redirect(
            method = "placeNewPlayer",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/server/level/ServerPlayer;level()Lnet/minecraft/server/level/ServerLevel;"
            )
    )
    public ServerLevel solstice$overrideWorld(ServerPlayer player, @Local Optional<NameAndId> optional) {
//        player.level().getServer().setRespawnData(new LevelData.RespawnData());
        if (optional.isEmpty()) {
            var spawn = ModuleProvider.SPAWN;
            var firstSpawn = spawn.getFirstSpawn();
            if (firstSpawn != null) {
                return firstSpawn.getWorld(player.level().getServer());
            }
            return spawn.getGlobalSpawnWorld();
        }
        // Temporarily replaced to get the mod running
        return player.level().getServer().getLevel(Level.OVERWORLD);

    }
}
