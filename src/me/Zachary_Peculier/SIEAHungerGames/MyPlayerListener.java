package me.Zachary_Peculier.SIEAHungerGames;
import java.util.ArrayList;
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
    ArrayList<String> alive = new ArrayList<String>();
    ArrayList<String> dead = new ArrayList<String>();
    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event)
    {
	Player player = event.getEntity();
	String player_name = player.getName();

	if (!alive.contains(player_name)) // player is not participating in the game, ignore their death
	{
	    return;
	}
	event.setDeathMessage(ChatColor.YELLOW + player_name + ChatColor.AQUA + " has been killed!");
	player.setGameMode(GameMode.SPECTATOR);
	players--;
	alive.remove(player_name);
	dead.add(player_name);

	if (players == 1)
	{
	    for (int i = 0; i < 10; i++)
	    {
		Bukkit.broadcastMessage(ChatColor.RED + "GAME OVER! WE HAVE A WINNER! CONGRATULATIONS " + alive.get(0) + "!!!!");
	    }
	}
	else
	{
	    Bukkit.broadcastMessage(ChatColor.AQUA + "There are " + ChatColor.YELLOW + players + ChatColor.AQUA + " players remaining");
	}
    }
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event)
    {
	Player player = event.getPlayer();
	event.setJoinMessage(ChatColor.GREEN + player.getName() + ChatColor.RESET + " / " + ChatColor.DARK_GRAY + "has logged in!");
    }
    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event)
    {
	Player player = event.getPlayer();
	String player_name = player.getName();
	event.setQuitMessage(ChatColor.GREEN + player_name + ChatColor.RESET + " / " + ChatColor.DARK_GRAY + "has logged out!");
	if (alive.contains(player_name))
	{
	    alive.remove(player_name);
	}

	if (dead.contains(player_name))
	{
	    dead.remove(player_name);
	}
    }
    public void InstantiateTrib(String playerName)
    {
	alive.add(playerName);
    }
}