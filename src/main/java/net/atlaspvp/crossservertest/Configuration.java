package net.atlaspvp.crossservertest;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;

public class Configuration {
    //@TODO: cache the config values

    private static org.bukkit.plugin.Plugin Plugin;
    private static File file;
    private static FileConfiguration config;

    public static void Initialize(Plugin plugin) {
        Plugin = plugin;
        file = new File(plugin.getDataFolder(), "config.yml");

        if (!file.exists()) {
            plugin.saveResource("config.yml", false);
        }
        config = YamlConfiguration.loadConfiguration(file);
    }

    public static int getPort(){
        return config.getInt("port");
    }

    public static String getServerName() {
        return config.getString("servername");
    }

}
