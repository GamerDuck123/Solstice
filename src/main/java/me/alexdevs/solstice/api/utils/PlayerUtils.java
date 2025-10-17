package me.alexdevs.solstice.api.utils;

import com.mojang.authlib.GameProfile;
import me.alexdevs.solstice.Solstice;
import me.alexdevs.solstice.modules.spawn.SpawnModule;
import net.minecraft.server.level.ClientInformation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.NameAndId;

import java.util.UUID;

public class PlayerUtils {
    public static boolean isOnline(UUID uuid) {
        return Solstice.server.getPlayerList().getPlayer(uuid) != null;
    }

    public static ServerPlayer loadOfflinePlayer(GameProfile profile) {
        if (isOnline(profile.id())) {
            return null;
        }

        return new ServerPlayer(Solstice.server, Solstice.modules.getModule(SpawnModule.class).get().getGlobalSpawnWorld().getLevel(), profile, null);
    }

    public static void saveOfflinePlayer(ServerPlayer player) {
        if (isOnline(player.getUUID())) {
            Solstice.LOGGER.warn("Tried to save offline player data for a player that is online.");
            return;
        }
        var saveHandler = Solstice.server.playerDataStorage;
        saveHandler.save(player);
        Solstice.server.getPlayerList().remove(player);
    }
}
