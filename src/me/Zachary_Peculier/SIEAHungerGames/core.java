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
        this.logger.info(pdFile.getName() + " " + pdFile.getVersion() + " has been activated!");
    }

    @Override
    public void onDisable()
    {
        this.logger.info(pdFile.getName() + " " + pdFile.getVersion() + " has been deactivated!");
    }

    @SuppressWarnings("deprecation")
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args)
    {
        Player player = (Player) sender;
        if (commandLabel.equalsIgnoreCase("join"))
        {
            mpl.InstantiateTrib(player);
            player.sendMessage(ChatColor.DARK_AQUA + "You have joined the game!");
            tributes++;
        }
        else if (commandLabel.equalsIgnoreCase("start"))
        {
            if (tributes < 2) {
                player.sendMessage(ChatColor.RED + "There must be at least 2 players to start the game.");
                return;
            }

            if (args.length != 1)
            {
                player.sendMessage(ChatColor.RED + "Usage: /start <time in seconds>");
            }
            else
            {
                int time = Integer.parseInt(args[0]);
                if (time > 0)
                {
                    int seconds = time % 60;
                    int minutes = time / 60;
                    player.sendMessage(ChatColor.GREEN + "Timer for " + minutes + ":" + seconds + " started!");
                    this.getServer().getScheduler().scheduleAsyncRepeatingTask(this, new Runnable()
                    {
                        int timer = time;

                        public void run()
                        {
                            if (timer == -1)
                            {
                                return;
                            }

                            if (timer != 0)
                            {
                                if (timer > 60)
                                {
                                    if ((timer % 60) == 0)
                                    {
                                        Bukkit.broadcastMessage(ChatColor.DARK_AQUA + "" + (timer / 60) + " minutes until tournament begins");
                                    }
                                }
                                else
                                {
                                    if (timer == 60)
                                    {
                                        Bukkit.broadcastMessage(ChatColor.DARK_AQUA + " 1 minute until tournament begins");
                                    }
                                    else if ((timer % 15) == 0)
                                    {
                                        Bukkit.broadcastMessage(ChatColor.DARK_AQUA + "" + timer + " seconds until tournament begins");
                                    }
                                    else if (timer <= 10)
                                    {
                                        if (timer == 1)
                                        {
                                            Bukkit.broadcastMessage(ChatColor.DARK_AQUA + "" + timer + " second until tournament begins");
                                        }
                                        else
                                        {
                                            Bukkit.broadcastMessage(ChatColor.DARK_AQUA + "" + timer + " seconds until tournament begins");
                                        }
                                    }
                                }
                            }
                            else
                            {
                                Bukkit.broadcastMessage(ChatColor.DARK_AQUA + "Go!");
                                Bukkit.broadcastMessage(ChatColor.DARK_AQUA + "Good luck, and may the odds be ever in your favor");
                                Bukkit.broadcastMessage(ChatColor.DARK_AQUA + "There are " + ChatColor.YELLOW + tributes + ChatColor.DARK_AQUA + " paricipants");
                                mpl.startGame();
                            }
                            timer--;
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
