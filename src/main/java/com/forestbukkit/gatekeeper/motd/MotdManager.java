package com.forestbukkit.gatekeeper.motd;

import com.forestbukkit.gatekeeper.GateKeeper;
import com.forestbukkit.gatekeeper.config.ConfigManager;
import com.forestbukkit.gatekeeper.motd.construct.MotdType;
import com.forestbukkit.gatekeeper.motd.listener.MotdListener;
import net.minebo.cobalt.util.ColorUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.util.List;
import java.util.stream.Collectors;

public class MotdManager {

    public static void init() {
        Bukkit.getPluginManager().registerEvents(new MotdListener(), GateKeeper.instance);
    }

    public static String getMotdString(MotdType motdType) {
        List<String> lines = switch (motdType) {
            case NORMAL -> ConfigManager.normalLines;
            case WHITELISTED -> ConfigManager.whitelistLines;
        };

        // Apply color translation to each line
        return lines.stream()
                .map(line -> ChatColor.translateAlternateColorCodes('&', ColorUtil.translateHexColors(line)))
                .collect(Collectors.joining("\n"));
    }

}
