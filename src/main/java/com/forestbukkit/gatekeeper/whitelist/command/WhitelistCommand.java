package com.forestbukkit.gatekeeper.whitelist.command;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.CommandHelp;
import co.aikar.commands.annotation.*;
import com.forestbukkit.gatekeeper.config.ConfigManager;
import com.forestbukkit.gatekeeper.whitelist.WhitelistManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;

import java.util.UUID;
import java.util.stream.Collectors;

@CommandAlias("whitelist|wl")
@CommandPermission("basic.admin")
@Description("Manages the server's whitelist.")
public class WhitelistCommand extends BaseCommand {

    @HelpCommand
    @Default
    public void doHelp(CommandSender sender, CommandHelp help) {
        help.showHelp();
    }

    @Subcommand("status")
    @Description("Show the current whitelist status and all whitelisted players")
    public void onStatus(CommandSender sender) {
        sender.sendMessage(ChatColor.YELLOW + "Whitelist is currently " + (ConfigManager.whitelistEnabled ? ChatColor.GREEN + "enabled" : ChatColor.RED + "disabled") + ChatColor.YELLOW + ".");
    }

    @Subcommand("list")
    @Description("Get the list of whitelisted players")
    public void onList(CommandSender sender) {
        sender.sendMessage(ChatColor.YELLOW + "Whitelisted players:");
        if (ConfigManager.whitelistedPlayers.isEmpty()) {
            sender.sendMessage(ChatColor.RED + "No players are whitelisted.");
            return;
        }
        for (UUID uuid : ConfigManager.whitelistedPlayers) {
            OfflinePlayer p = Bukkit.getOfflinePlayer(uuid);
            sender.sendMessage(ChatColor.GRAY + " - " + ChatColor.WHITE + (p.getName() != null ? p.getName() : uuid));
        }
    }

    @Subcommand("on")
    @Description("Enable the whitelist")
    public void onEnable(CommandSender sender) {
        if (ConfigManager.whitelistEnabled) {
            sender.sendMessage(ChatColor.RED + "Whitelist is already enabled.");
            return;
        }
        ConfigManager.whitelistEnabled = true;
        ConfigManager.save();
        sender.sendMessage(ChatColor.YELLOW + "Whitelist enabled.");
    }

    @Subcommand("off")
    @Description("Disable the whitelist")
    public void onDisable(CommandSender sender) {
        if (!ConfigManager.whitelistEnabled) {
            sender.sendMessage(ChatColor.RED + "Whitelist is already disabled.");
            return;
        }
        ConfigManager.whitelistEnabled = false;
        ConfigManager.save();
        sender.sendMessage(ChatColor.YELLOW + "Whitelist disabled.");
    }

    @Subcommand("add")
    @CommandCompletion("@players @nothing")
    @Syntax("<player>")
    @Description("Add a player to the whitelist")
    public void onAdd(CommandSender sender, String playerName) {
        OfflinePlayer target = Bukkit.getOfflinePlayer(playerName);
        UUID uuid = target.getUniqueId();
        if (uuid == null) {
            sender.sendMessage(ChatColor.RED + "Could not find player: " + playerName);
            return;
        }
        if (WhitelistManager.isWhitelisted(uuid)) {
            sender.sendMessage(ChatColor.RED + target.getName() + " is already whitelisted.");
            return;
        }
        WhitelistManager.addUUID(uuid);
        ConfigManager.save();
        sender.sendMessage(ChatColor.YELLOW + "Added " + ChatColor.WHITE + target.getName() + ChatColor.YELLOW + " to the whitelist.");
    }

    @Subcommand("remove")
    @CommandCompletion("@players @nothing")
    @Syntax("<player>")
    @Description("Remove a player from the whitelist")
    public void onRemove(CommandSender sender, String playerName) {
        OfflinePlayer target = Bukkit.getOfflinePlayer(playerName);
        UUID uuid = target.getUniqueId();
        if (uuid == null) {
            sender.sendMessage(ChatColor.RED + "Could not find player: " + playerName);
            return;
        }
        if (!WhitelistManager.isWhitelisted(uuid)) {
            sender.sendMessage(ChatColor.RED + target.getName() + " is not whitelisted.");
            return;
        }
        WhitelistManager.removeUUID(uuid);
        ConfigManager.save();
        sender.sendMessage(ChatColor.YELLOW + "Removed " + ChatColor.WHITE + target.getName() + ChatColor.YELLOW + " from the whitelist.");
    }
}