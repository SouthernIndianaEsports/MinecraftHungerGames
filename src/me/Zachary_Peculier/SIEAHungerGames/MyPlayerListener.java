package me.Zachary_Peculier.SIEAHungerGames;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class MyPlayerListener implements Listener
{
    public static core plugin;
    ArrayList<Player> alive = new ArrayList<Player>();
    ArrayList<Player> dead = new ArrayList<Player>();

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event)
    {
        Player player = event.getEntity();
        String player_name = player.getName();

        if (!alive.contains(player))
        {
            return;
        }
        event.setDeathMessage(ChatColor.YELLOW + player_name + ChatColor.DARK_AQUA + " has been killed!");
        player.setGameMode(GameMode.SPECTATOR);
        alive.remove(player);
        dead.add(player);

        if (alive.size() == 1)
        {
            for (int i = 0; i < 10; i++)
            {
                Bukkit.broadcastMessage(ChatColor.RED + "GAME OVER! WE HAVE A WINNER! CONGRATULATIONS " + alive.get(0).getName() + "!!!!");
            }
        }
        else
        {
            Bukkit.broadcastMessage(ChatColor.DARK_AQUA + "There are " + ChatColor.YELLOW + alive.size() + ChatColor.DARK_AQUA + " players remaining");
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event)
    {
        Player player = event.getPlayer();
        event.setJoinMessage(ChatColor.GREEN + player.getName() + ChatColor.RESET + " / " + ChatColor.DARK_GRAY + "has logged in!");
        player.setGameMode(GameMode.ADVENTURE);
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event)
    {
        Player player = event.getPlayer();

        event.setQuitMessage(ChatColor.GREEN + player.getName() + ChatColor.RESET + " / " + ChatColor.DARK_GRAY + "has logged out!");

        if (alive.contains(player))
        {
            alive.remove(player);
        }

        if (dead.contains(player))
        {
            dead.remove(player);
        }
    }

    public void InstantiateTrib(Player player)
    {
        alive.add(player);
    }

    public void RemoveTrib(Player player)
    {
        alive.remove(player);

    }

    public void startGame()
    {
        for (int i = 0; i < alive.size(); i++)
        {
            alive.get(i).setGameMode(GameMode.SURVIVAL);
        }
    }
    public int updateTributes()
    {
        return alive.size();
    }

    public void listPlayers(Player player)
    {
        for (int i = 0; i == alive.size(); i++)
        {

            player.sendMessage(alive.get(i).getName());
        }
        player.sendMessage(ChatColor.YELLOW + "" + (alive.size() + 1) + ChatColor.DARK_AQUA + " remain");

    }
}
