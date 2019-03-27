package me.Zachary_Peculier.SIEAHungerGames;

import java.util.logging.Logger;
import me.Zachary_Peculier.SIEAHungerGames.Commands.*;
import me.Zachary_Peculier.SIEAHungerGames.Listeners.PlayerListener;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

public class HungerGames extends JavaPlugin
{
    
    PluginDescriptionFile pdFile = this.getDescription();
    public final Logger logger = Logger.getLogger("Minecraft");
    public final PlayerListener mpl = new PlayerListener();
    public boolean inProgress = false;
    public boolean timerGoing = false;

    @Override
    public void onEnable()
    {
        this.logger.info(pdFile.getName() + " " + pdFile.getVersion() + " has been activated!");
        Bukkit.getWorld("world").setSpawnLocation(0, 72, 0);
        org.bukkit.plugin.PluginManager plm = this.getServer().getPluginManager();
        plm.registerEvents(this.mpl, this);
        getCommand("help").setExecutor(new Help(mpl));
        getCommand("alive").setExecutor(new Alive(mpl));
        getCommand("who").setExecutor(new Who(mpl));
        getCommand("admin").setExecutor(new Admin(mpl));
        getCommand("start").setExecutor(new Start(mpl, this));
    }

    @Override
    public void onDisable()
    {
        this.logger.info(pdFile.getName() + " " + pdFile.getVersion() + " has been deactivated!");
    }
}
