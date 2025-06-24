package com.forestbukkit.gatekeeper.config;

import com.forestbukkit.gatekeeper.GateKeeper;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class ConfigManager {

    // Motd Config

    public static List<String> normalLines;
    public static List<String> whitelistLines;

    // Whitelist Config

    public static Boolean whitelistEnabled;
    public static List<String> whitelistKickMessage;
    public static List<UUID> whitelistedPlayers = new ArrayList<>();

    public static void init() {
        FileConfiguration config = GateKeeper.instance.getConfig();

        normalLines = config.getStringList("motd.normal");
        whitelistLines = config.getStringList("motd.whitelist");

        whitelistEnabled = config.getBoolean("whitelist.enabled");
        whitelistKickMessage = config.getStringList("whitelist.kick-message");

        config.getStringList("whitelist.uuids").forEach(s -> {
            try {
                whitelistedPlayers.add(UUID.fromString(s));
                Bukkit.getLogger().info("Added " + s + " to the whitelist.");
            } catch (IllegalArgumentException e) {
                Bukkit.getLogger().severe("Failed to add " + s + " to the whitelist. (Invalid UUID)");
            }
        });

    }

    public static void save() {
        FileConfiguration config = GateKeeper.instance.getConfig();

        config.set("motd.normal", normalLines);
        config.set("motd.whitelist", whitelistLines);

        config.set("whitelist.enabled", whitelistEnabled);
        config.set("whitelist.kick-message", whitelistKickMessage);

        List<String> uuidStrings = whitelistedPlayers.stream()
                .map(UUID::toString)
                .collect(Collectors.toList());
        config.set("whitelist.uuids", uuidStrings);

        GateKeeper.instance.saveConfig();

    }

}
