package com.forestbukkit.gatekeeper.motd.listener;

import com.forestbukkit.gatekeeper.config.ConfigManager;
import com.forestbukkit.gatekeeper.motd.MotdManager;
import com.forestbukkit.gatekeeper.motd.construct.MotdType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;

import java.util.Set;

public class MotdListener implements Listener {

    @EventHandler
    public void onRetrieveMotd(ServerListPingEvent event) {
        String motd;

        if (ConfigManager.whitelistEnabled) {
            motd = MotdManager.getMotdString(MotdType.WHITELIST);
        } else {
            motd = MotdManager.getMotdString(MotdType.NORMAL);
        }

        event.setMotd(motd);
    }
}
