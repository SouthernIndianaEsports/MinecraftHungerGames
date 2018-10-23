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
    List<String> alive = new ArrayList<String>();
    List<String> dead = new ArrayList<String>();

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event)
    {
        Player player = event.getEntity();
        String player_name = player.getName();

        event.setDeathMessage(ChatColor.YELLOW + player_name + ChatColor.AQUA + " has been killed!");
        players--;

        player.setGameMode(GameMode.SPECTATOR);
        alive.remove(player_name);
        dead.add(player_name);

        if (players == 1) {
            Bukkit.broadcastMessage(ChatColor.RED + "GAME OVER! WE HAVE A WINNER! CONGRATULATIONS " + player_name + "!!!!");
        } else {
            Bukkit.broadcastMessage(ChatColor.AQUA + "There are " + ChatColor.YELLOW + players + ChatColor.AQUA + " players remaining");
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event)
    {
        Player player = event.getPlayer();
        event.setJoinMessage(ChatColor.GREEN + player.getName() + ChatColor.RESET + " / " + ChatColor.DARK_GRAY + "has logged in!");
        alive.add(player.getName());
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event)
    {
        Player player = event.getPlayer();
        String player_name = player.getName();

        event.setQuitMessage(ChatColor.GREEN + player_name + ChatColor.RESET + " / " + ChatColor.DARK_GRAY + "has logged out!");

        if (alive.contains(player_name)) {
            alive.remove(player_name);
        }

        if (dead.contains(player_name)) {
            dead.remove(player_name);
        }
    }

    @SuppressWarnings("unused")
    public void InstantiateTribs(int tributes)
    {
        players = tributes;
    }
}
