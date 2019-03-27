package me.Zachary_Peculier.SIEAHungerGames.Commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.Zachary_Peculier.SIEAHungerGames.HungerGames;
import me.Zachary_Peculier.SIEAHungerGames.Listeners.PlayerListener;

public class Start implements CommandExecutor {
    
    private final PlayerListener mpl;
    private final HungerGames plugin;

    public Start(final PlayerListener playerListener, final HungerGames p) {
        this.mpl = playerListener;
        this.plugin = p;
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

	@SuppressWarnings("deprecation")
    @Override
	public final boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
	    Player player = (Player) sender;
	    
	    if (player.hasPermission("siea.admin"))
        {
            if (!plugin.inProgress)

                if (plugin.timerGoing)
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
                if (!plugin.timerGoing)
                {
                    plugin.timerGoing = true;
                }
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
                    Bukkit.getServer().getScheduler().scheduleAsyncRepeatingTask(this.plugin, new Runnable()
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
                                plugin.inProgress = true;
                                Bukkit.broadcastMessage(ChatColor.RED + "Go!");
                                Bukkit.broadcastMessage(ChatColor.DARK_AQUA + "Good luck, and may the odds be ever in your favor");
                                Bukkit.broadcastMessage(ChatColor.DARK_AQUA + "There are " + ChatColor.YELLOW + mpl.getTributeSize() + ChatColor.DARK_AQUA + " paricipants");
                            }
                            else if (timer > 60 && (timer % 60) == 0)
                            {
                                Bukkit.broadcastMessage(ChatColor.RED + "Tournament will begin in " + (timer / 60) + " minutes.");
                            }
                            else if (timer == 60)
                            {
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
		return true;
	}

}
