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
    public boolean inProgress = false;
    public boolean timerGoing = false;
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

        String command = commandLabel.toLowerCase();
        switch (command)
        {
            case "join":
                if (!inProgress)
                {
                    mpl.addTrib(player);
                    player.sendMessage(ChatColor.DARK_AQUA + "You have joined the game!");
                    tributes++;
                    player.sendMessage(ChatColor.YELLOW + "[" + tributes + "]");
                }
                else
                {
                    player.sendMessage(ChatColor.RED + "GAME IN PROGRESS");
                }
                break;
            case "leave":
                mpl.RemoveTrib(player);
                player.sendMessage(ChatColor.DARK_AQUA + "You have left the game!");
                tributes--;
                break;
            case "alive":
                mpl.listPlayers(player);
                break;
            case "who":
                mpl.listPlayers(player);
                break;
            case "help":
                player.sendMessage(ChatColor.GRAY + "Welcome to the Minecraft Hunger Games!");
                tributes = mpl.updateTributes();
                player.sendMessage(ChatColor.GRAY + "There are " + tributes + " players remaining");
                player.sendMessage(ChatColor.GRAY + "/join - joins game (if not in progress)");
                player.sendMessage(ChatColor.GRAY + "/leave - leaves game");
                player.sendMessage(ChatColor.GRAY + "/alive (or /who) - shows list of remaining players");
            case "start":
                if(timerGoing)
                {
                    player.sendMessage(ChatColor.RED + "Timer in progress!");
                }
                if (tributes < 2)
                {
                    player.sendMessage(ChatColor.RED + "There must be at least 2 players to start the game.");
                    return true;
                }

                if (args.length != 1)
                {
                    player.sendMessage(ChatColor.RED + "Usage: /start <time in seconds>");
                    return true;
                }
                if (isInt(args[0]))
                {
                    if(!timerGoing)
                    {
                        timerGoing = true;
                    }
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

                                if (timer == 0)
                                {
                                    mpl.startGame();
                                    inProgress = true;
                                    Bukkit.broadcastMessage(ChatColor.DARK_AQUA + "Go!");
                                    Bukkit.broadcastMessage(ChatColor.DARK_AQUA + "Good luck, and may the odds be ever in your favor");
                                    Bukkit.broadcastMessage(ChatColor.DARK_AQUA + "There are " + ChatColor.YELLOW + tributes + ChatColor.DARK_AQUA + " paricipants");
                                }
                                else if (timer > 60 && (timer % 60) == 0)
                                {
                                    Bukkit.broadcastMessage(ChatColor.DARK_AQUA + "" + (timer / 60) + " minutes until tournament begins");
                                }
                                else if (timer == 60)
                                {
                                    Bukkit.broadcastMessage(ChatColor.DARK_AQUA + "1 minute until tournament begins");
                                }
                                else if (timer < 60)
                                {
                                    if ((timer % 15) == 0)
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
                                timer--;
                            }
                        }, 0L, 20L);
                        return true;
                    }
                }
                else
                {
                    player.sendMessage(ChatColor.RED + "Please enter a whole number");
                }
                break;
            default:
                break;
        }

        return true;
    }

    private boolean isInt(String string)
    {
        try
        {
            Integer.parseInt(string);
            return true;
        } catch (NumberFormatException e)
        {
            return false;
        }
    }
}