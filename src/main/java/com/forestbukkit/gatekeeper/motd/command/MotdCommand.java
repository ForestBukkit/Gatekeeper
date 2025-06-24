package com.forestbukkit.gatekeeper.motd.command;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.CommandHelp;
import co.aikar.commands.annotation.*;
import com.forestbukkit.gatekeeper.config.ConfigManager;
import com.forestbukkit.gatekeeper.motd.MotdManager;
import com.forestbukkit.gatekeeper.motd.construct.MotdType;
import net.minebo.cobalt.util.ColorUtil;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

@CommandAlias("motd")
@CommandPermission("basic.admin")
public class MotdCommand extends BaseCommand {

    @HelpCommand
    public void doHelp(CommandSender sender, CommandHelp help) {
        help.showHelp();
    }

    @Subcommand("view")
    @CommandCompletion("@motdTypes @nothing")
    @Syntax("<motdType>")
    public void onView(CommandSender sender, String motdType) {
        MotdType type = MotdType.valueOf(motdType.toUpperCase());
        String text = MotdManager.getMotdString(type);

        sender.sendMessage(motdType + ChatColor.YELLOW + "'s motd is set to: " + ChatColor.WHITE + "\"" + ChatColor.translateAlternateColorCodes('&', ColorUtil.translateHexColors(text)) + ChatColor.WHITE + "\"");
    }

    @Subcommand("setLine")
    @CommandCompletion("@motdTypes 1|2 @nothing")
    @Syntax("<motdType> <line> <text>")
    public void onSetLine(CommandSender sender, String motdType, int line, String text) {
        sender.sendMessage(ChatColor.YELLOW + "Set line " + ChatColor.WHITE + line + ChatColor.YELLOW + " of " + ChatColor.WHITE + motdType + ChatColor.YELLOW + " to: " + ChatColor.WHITE + "\"" + ChatColor.translateAlternateColorCodes('&', ColorUtil.translateHexColors(text)) + ChatColor.WHITE + "\"");

        MotdType type = MotdType.valueOf(motdType.toUpperCase());

        switch (type) {
            case NORMAL -> ConfigManager.normalLines.set(line-1, text);
            case WHITELISTED -> ConfigManager.whitelistLines.set(line-1, text);
        }

        ConfigManager.save();
    }
}