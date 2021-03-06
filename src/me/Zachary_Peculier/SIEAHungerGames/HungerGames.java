package me.Zachary_Peculier.SIEAHungerGames;

import java.util.logging.Logger;
import me.Zachary_Peculier.SIEAHungerGames.Commands.*;
import me.Zachary_Peculier.SIEAHungerGames.Game.Game;
import me.Zachary_Peculier.SIEAHungerGames.Listeners.PlayerListener;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class HungerGames extends JavaPlugin
{

    PluginDescriptionFile pdFile = this.getDescription();
    public final Logger logger = Logger.getLogger("Minecraft");
    private Game game;

    @Override
    public void onEnable()
    {
        this.logger.info(pdFile.getName() + " " + pdFile.getVersion() + " has been activated!");
        Bukkit.getWorld("world").setSpawnLocation(-8, 156, 10);

        game = new Game();

        PluginManager plm = this.getServer().getPluginManager();
        plm.registerEvents(new PlayerListener(game), this);

        getCommand("help").setExecutor(new Help(game));
        getCommand("alive").setExecutor(new Alive(game));
        getCommand("who").setExecutor(new Who(game));
        getCommand("admin").setExecutor(new Admin(game));
        getCommand("start").setExecutor(new Start(this, game));
        getCommand("say").setExecutor(new Say());
    }

    @Override
    public void onDisable()
    {
        this.logger.info(pdFile.getName() + " " + pdFile.getVersion() + " has been deactivated!");
    }
}
