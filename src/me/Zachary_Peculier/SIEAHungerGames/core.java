package me.Zachary_Peculier.SIEAHungerGames;

import java.util.ArrayList;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.WorldBorder;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

public class core extends JavaPlugin
{
    PluginDescriptionFile pdFile = this.getDescription();
    public static core plugin;
    public boolean inProgress = false;
    public boolean timerGoing = false;
    public final Logger logger = Logger.getLogger("Minecraft");
    public final MyPlayerListener mpl = new MyPlayerListener();
    ArrayList<Player> tributes = new ArrayList<Player>();

    @Override
    public void onEnable()
    {
        this.logger.info(pdFile.getName() + " " + pdFile.getVersion() + " has been activated!");
        Bukkit.getWorld("world").setSpawnLocation(0, 72, 0);
        org.bukkit.plugin.PluginManager plm = this.getServer().getPluginManager();
        plm.registerEvents(new MyPlayerListener(), this);
        plm.registerEvents(this.mpl, this);
    }

    @Override
    public void onDisable()
    {
        this.logger.info(pdFile.getName() + " " + pdFile.getVersion() + " has been deactivated!");
    }

    @SuppressWarnings(
    { "deprecation", "unused" })
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args)
    {
        Player player = (Player) sender;

        String command = commandLabel.toLowerCase();
        for (Player players : getServer().getOnlinePlayers())
            switch (command)
            {
                case "admin":
                {
                    if (player.hasPermission("siea.admin"))
                    {
                        mpl.RemoveTrib(player);
                        player.setGameMode(GameMode.CREATIVE);
                    }
                    else
                    {
                        player.sendMessage(ChatColor.RED + "No Permission!");
                    }
                }
                case "alive":
                    mpl.listPlayers(player);
                    break;
                case "who":
                    mpl.listPlayers(player);
                    break;
                case "help":
                    player.sendMessage(ChatColor.GRAY + "Welcome to the Minecraft Hunger Games!");
                    player.sendMessage(ChatColor.GRAY + "There are " + mpl.getTributeSize() + " players remaining");
                    player.sendMessage(ChatColor.GRAY + "/alive (or /who) - shows list of remaining players");
                    break;
                case "start":
                    if (player.hasPermission("siea.admin"))
                    {
                        if (!inProgress)

                            if (timerGoing)
                            {
                                player.sendMessage(ChatColor.RED + "Timer in progress!");
                            }

                        if (mpl.getTributeSize() < 2)
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
                            if (!timerGoing)
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
                                            mpl.unfreezePlayers();
                                            Bukkit.broadcastMessage(ChatColor.RED + "Go!");
                                            Bukkit.broadcastMessage(ChatColor.DARK_AQUA + "Good luck, and may the odds be ever in your favor");
                                            Bukkit.broadcastMessage(ChatColor.DARK_AQUA + "There are " + ChatColor.YELLOW + tributes + ChatColor.DARK_AQUA + " paricipants");
                                            Bukkit.broadcastMessage(ChatColor.GREEN + "Over the next hour, the border will be slowly shrinking to the center 32x32 blocks. ");
                                        }
                                        else if (timer > 60 && (timer % 60) == 0)
                                        {
                                            Bukkit.broadcastMessage(ChatColor.RED + "Tournament will begin in " + (timer / 60) + " minutes.");
                                        }
                                        else if (timer == 60)
                                        {
                                            mpl.freezePlayers();
                                            Bukkit.broadcastMessage(ChatColor.RED + "Tournament will begin in 1 minute.");
                                        }
                                        else if (timer < 60)
                                        {
                                            if ((timer % 15 == 0) || (timer <= 10))
                                            {
                                                Bukkit.broadcastMessage(ChatColor.RED + "Tournament will begin in " + timer + " seconds.");
                                            }
                                            else if (timer <= 10)
                                            {
                                                if (timer == 1)
                                                {
                                                    Bukkit.broadcastMessage(ChatColor.RED + "Tournament will begin in 1 second.");
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
                    }
                    else
                    {
                        player.sendMessage(ChatColor.RED + "No Permission!");
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