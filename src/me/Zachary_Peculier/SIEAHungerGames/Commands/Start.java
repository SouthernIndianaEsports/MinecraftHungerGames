package me.Zachary_Peculier.SIEAHungerGames.Commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import me.Zachary_Peculier.SIEAHungerGames.HungerGames;
import me.Zachary_Peculier.SIEAHungerGames.Game.Game;

class Timer
{

    private HungerGames plugin;
    private Game game;
    private int task = -1;
    private int time;

    public Timer(HungerGames plugin, Game g, long preDelay, long delay, final int times, final Runnable eachDelay, final Runnable end)
    {
        this.game = g;
        this.plugin = plugin;
        time = times;
        task = plugin.getServer().getScheduler().runTaskTimer(plugin, new Runnable()
        {

            @Override
            public void run()
            {
                if (eachDelay != null)
                    eachDelay.run();

                if (time == times && time > 0)
                {
                    Bukkit.broadcastMessage("Time remaining: " + time);
                    time--;
                    return;
                }
                
                if ((time % 30) == 0 && time >= 60)
                {
                    Bukkit.broadcastMessage("Time remaining: " + time);
                }
                else if ((time % 15) == 0 && time > 20 && time < 60)
                {
                    Bukkit.broadcastMessage("Time remaining: " + time);
                }
                else if ((time % 10) == 0 && time > 5 && time <= 20)
                {
                    Bukkit.broadcastMessage("Time remaining: " + time);
                }
                else if (time < 6 && time > 0)
                {
                    Bukkit.broadcastMessage("Time remaining: " + time);
                }

                time--;
                if (time < 1)
                {
                    end();
                    if (end != null)
                        end.run();
                }
            }
        }, preDelay, delay).getTaskId();
    }

    public int getTime()
    {
        return time;
    }

    public boolean isRunning()
    {
        return task != -1 && (plugin.getServer().getScheduler().isCurrentlyRunning(task) || plugin.getServer().getScheduler().isQueued(task));
    }

    public void end()
    {
        if (isRunning())
        {
            plugin.getServer().getScheduler().cancelTask(task);
            task = -1;
        }
    }

}

public class Start implements CommandExecutor
{

    private final HungerGames plugin;
    private final Game game;

    public Start(final HungerGames p, final Game g)
    {
        this.plugin = p;
        this.game = g;
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

    @Override
    public final boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args)
    {
        Player player = (Player) sender;

        if (!player.hasPermission("siea.admin"))
        {
            player.sendMessage(ChatColor.RED + "You do not have permission to start the game.");
            return true;
        }

        /*
         * if (game.getNumPlayers() < 2) { player.sendMessage(ChatColor.RED +
         * "There must be at least 2 players to start the game."); return true; }
         */

        if (args.length != 1)
        {
            player.sendMessage(ChatColor.RED + "Usage: /start <time in seconds>");
            return true;
        }

        if (isInt(args[0]))
        {
            int time = Integer.parseInt(args[0]);

            if (time > 0)
            {
                int seconds = time % 60;
                int minutes = time / 60;
                if (seconds >= 10)
                {
                    player.sendMessage(ChatColor.GREEN + "Timer for " + minutes + ":" + seconds + " started!");
                }
                else
                {
                    player.sendMessage(ChatColor.GREEN + "Timer for " + minutes + ":0" + seconds + " started!");
                }
                game.startTimer();
                Timer timer = new Timer(this.plugin, game, 0, 20, time, null, new Runnable()
                {
                    @Override
                    public void run()
                    {
                        game.startGame();
                        game.setFrozen(false);
                    }
                });
                return true;
            }
        }
        else
        {
            player.sendMessage(ChatColor.RED + "Please enter a whole number");
        }
        return true;
    }

}
