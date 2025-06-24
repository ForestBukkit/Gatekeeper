package com.forestbukkit.gatekeeper.whitelist;

import com.forestbukkit.gatekeeper.config.ConfigManager;
import org.bukkit.entity.Player;

import java.util.UUID;

public class WhitelistManager {

    public static void init() {

    }

    public static Boolean isWhitelisted(Player player) {
        return ConfigManager.whitelistedPlayers.contains(player.getUniqueId());
    }

    public static Boolean isWhitelisted(UUID uuid) {
        return ConfigManager.whitelistedPlayers.contains(uuid);
    }

    public static Boolean addPlayer(Player player) {
        if(isWhitelisted(player)) return false;

        ConfigManager.whitelistedPlayers.add(player.getUniqueId());
        return true;
    }

    public static Boolean addUUID(UUID uuid) {
        if(isWhitelisted(uuid)) return false;

        ConfigManager.whitelistedPlayers.add(uuid);
        return true;
    }

    public static Boolean removePlayer(Player player) {
        if(!isWhitelisted(player)) return false;

        ConfigManager.whitelistedPlayers.remove(player.getUniqueId());
        return true;
    }

    public static Boolean removeUUID(UUID uuid) {
        if(!isWhitelisted(uuid)) return false;

        ConfigManager.whitelistedPlayers.remove(uuid);
        return true;
    }
}
