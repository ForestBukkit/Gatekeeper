package com.forestbukkit.gatekeeper.whitelist.listener;

import com.forestbukkit.gatekeeper.config.ConfigManager;
import com.forestbukkit.gatekeeper.whitelist.WhitelistManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;

public class WhitelistListener implements Listener {

    @EventHandler
    public void onPlayerJoin(AsyncPlayerPreLoginEvent event) {
        if(!ConfigManager.whitelistEnabled) return;

        if(!WhitelistManager.isWhitelisted(event.getUniqueId())) {
            event.disallow(AsyncPlayerPreLoginEvent.Result.KICK_WHITELIST, String.join("\n", ConfigManager.whitelistKickMessage));
        }

    }
}
