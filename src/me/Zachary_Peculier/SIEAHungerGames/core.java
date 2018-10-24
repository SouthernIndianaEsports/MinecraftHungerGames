package me.Zachary_Peculier.SIEAHungerGames;

import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

public class core extends JavaPlugin
{
    public static core plugin;
    PluginDescriptionFile pdFile = this.getDescription();
    public int tributes;
    public final java.util.logging.Logger logger = Logger.getLogger("Minecraft");
    public final MyPlayerListener mpl = new MyPlayerListener();

    @Override
    public void onEnable()
    {
        this.logger.info(pdFile.getName() + " " + pdFile.getVersion() + "has been activated!");
    }
    @Override
    public void onDisable()
    {
        this.logger.info(pdFile.getName() + " " + pdFile.getVersion() + "has been deactivated!");
    }
    @SuppressWarnings("deprecation")
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args)
    {
        Player player = (Player) sender;
        if (commandLabel.equalsIgnoreCase("join")) 
        {
            mpl.InstantiateTrib(player.getName());
        } 
        else if (commandLabel.equalsIgnoreCase("start")) {
            if (args.length != 1) 
            {
                player.sendMessage(ChatColor.RED + "Usage: /start <time in seconds>");
            } 
            else 
            {
                int time = Integer.parseInt(args[0]);
                if (time > 0) {
                    int seconds = time % 60;
                    int minutes = time / 60;
                    player.sendMessage(ChatColor.GREEN + "Timer for " + minutes + ":" + seconds + " started!");
                    this.getServer().getScheduler().scheduleAsyncRepeatingTask(this, new Runnable(){
                            int timer = time;
                            public void run() 
                            {
                                if (timer == -1) 
                                {
                                    return;
                                }

                                if (timer != 0) 
                                {
                                    Bukkit.broadcastMessage(ChatColor.LIGHT_PURPLE + "[Server] " + timer);
                                    timer--;
                                } 
                                else 
                                {
                                    Bukkit.broadcastMessage(ChatColor.LIGHT_PURPLE + "[Server] Go!");
                                    Bukkit.broadcastMessage(ChatColor.LIGHT_PURPLE + "[Server] Good luck, and may the odds be ever in your favor");
                                    Bukkit.broadcastMessage(ChatColor.AQUA + "There are " + ChatColor.YELLOW + tributes + ChatColor.AQUA + " paricipants");
                                    timer--;
                                }
                            }
                        }, 0L, 20L);
                    return true;
                } 
                else 
                {
                    player.sendMessage(ChatColor.RED + "Please enter a whole number");
                }
            }
        }
        return true;
    }
}