package com.forestbukkit.gatekeeper;

import com.forestbukkit.gatekeeper.config.ConfigManager;
import com.forestbukkit.gatekeeper.motd.MotdManager;
import com.forestbukkit.gatekeeper.motd.construct.MotdType;
import com.forestbukkit.gatekeeper.whitelist.WhitelistManager;
import lombok.Getter;
import net.minebo.cobalt.acf.ACFCommandController;
import net.minebo.cobalt.acf.ACFManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;
import java.util.Collections;
import java.util.stream.Collectors;

public class GateKeeper extends JavaPlugin {

    public static GateKeeper instance;

    @Override
    public void onEnable() {
        instance = this;

        this.saveDefaultConfig();

        initCobalt();
        registerManagers();
    }

    @Override
    public void onDisable() {
        ConfigManager.save();
    }

    public void initCobalt() {
        new ACFManager(this);

        ACFCommandController.registerCompletion("motdTypes", c -> Arrays.stream(MotdType.values())
                .map(Enum::name)
                .collect(Collectors.toList()));

        ACFCommandController.registerAll(this);
    }

    public void registerManagers() {
        ConfigManager.init();
        MotdManager.init();
        WhitelistManager.init();
    }

}
