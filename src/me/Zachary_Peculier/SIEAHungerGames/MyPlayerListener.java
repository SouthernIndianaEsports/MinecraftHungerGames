package me.Zachary_Peculier.SIEAHungerGames;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class MyPlayerListener implements Listener
{
    public static core plugin;
    int players;

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event)
    {
	Player player = event.getEntity();
	event.setDeathMessage(ChatColor.YELLOW + player.getName() + ChatColor.AQUA + " has been killed!");
	players--;

	if (player.isDead())
	{
	    player.setGameMode(GameMode.SPECTATOR);
	}
	if (players == 1)
	{
	    Bukkit.broadcastMessage(ChatColor.RED + "GAME OVER! WE HAVE A WINNER!");
	    
	} else
	{
	    Bukkit.broadcastMessage(ChatColor.AQUA + "There are " + ChatColor.YELLOW + players + ChatColor.AQUA + " players remaining");
	}
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event)
    {
	Player player = event.getPlayer();
	event.setJoinMessage(
		ChatColor.GREEN + player.getName() + ChatColor.RESET + " / " + ChatColor.DARK_GRAY + "has logged in!");

    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event)
    {
	Player player = event.getPlayer();
	event.setQuitMessage(
		ChatColor.GREEN + player.getName() + ChatColor.RESET + " / " + ChatColor.DARK_GRAY + "has logged out!");
    }

    @SuppressWarnings("unused")
    public void InstantiateTribs(int tributes)
    {
	players = tributes;

    }
}
