package de.cypix.tagplayers.main;

import de.cypix.tagplayers.listeners.ChatListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class TagPlayers extends JavaPlugin {

    @Override
    public void onEnable() {
        System.out.println("loading...");
        Bukkit.getPluginManager().registerEvents(new ChatListener(), this);
        System.out.println("loaded!");
        loadConfig();
    }

    private void loadConfig() {
        getConfig().addDefault("Deaful", "");

        getConfig().options().copyDefaults(true);
        saveConfig();
    }

    @Override
    public void onDisable() {
        System.out.println("unloading...");
        System.out.println("unloaded!");
    }
}
