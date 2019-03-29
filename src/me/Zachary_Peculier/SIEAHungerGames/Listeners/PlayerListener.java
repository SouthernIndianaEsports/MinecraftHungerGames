package me.Zachary_Peculier.SIEAHungerGames.Listeners;

import java.util.logging.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import me.Zachary_Peculier.SIEAHungerGames.Game.*;

public class PlayerListener implements Listener
{

    private final Game game;

    public PlayerListener(final Game g)
    {
        this.game = g;
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event)
    {
        Player player = event.getEntity();
        String player_name = player.getName();

        if (!game.inGame(player) || game.getStatus() != GameStatus.STARTED || game.isAdmin(player))
        {
            return;
        }

        event.setDeathMessage(ChatColor.YELLOW + player_name + ChatColor.DARK_AQUA + " has been killed!");
        game.removePlayer(player);

        if (game.getNumAlive() == 1)
        {
            game.end();
        }
        else
        {
            Bukkit.broadcastMessage(ChatColor.DARK_AQUA + "There are " + ChatColor.YELLOW + game.getNumAlive() + ChatColor.DARK_AQUA + " players remaining.");
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event)
    {
        Player player = event.getPlayer();
        event.setJoinMessage(ChatColor.GREEN + player.getName() + ChatColor.RESET + " / " + ChatColor.DARK_GRAY + "has logged in!");

        if (game.isAdmin(player))
        {
            player.sendMessage(ChatColor.RED + "You are in admin mode");
            return;
        }

        if (!game.inGame(player))
        {
            game.addPlayer(player);
            Bukkit.getLogger().log(Level.INFO, "Added " + player.getName() + " to game.");
        }

        Tribute tribute = game.getTribute(player);
        if (tribute == null) {
            Bukkit.getLogger().log(Level.INFO, "Could not find " + player.getName() + ", this shoudln't happen");
            return;
        }
        
        tribute.updatePlayer(player);
        
        if (game.getStatus() == GameStatus.STARTED)
        {
            if (tribute.getStatus() == TributeStatus.QUIT)
            {
                player.sendMessage(ChatColor.RED + "You have disconnected mid game and have been disqualified.");
                player.setGameMode(GameMode.SPECTATOR);
            }
            else if (tribute.getStatus() == TributeStatus.DEAD)
            {
                player.sendMessage(ChatColor.RED + "You are dead");
                player.setGameMode(GameMode.SPECTATOR);
            }
            else
            {
                player.setGameMode(GameMode.SPECTATOR);
                tribute.setStatus(TributeStatus.DEAD);
                player.sendMessage(ChatColor.RED + "You aren't playing");
            }
        } else if (game.getStatus() == GameStatus.WAITING) {
            Bukkit.getLogger().log(Level.INFO, "Setting player to adventure mode.");
            tribute.setGameMode(GameMode.SURVIVAL);
            tribute.setStatus(TributeStatus.ALIVE);
        }

    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event)
    {
        Player player = event.getPlayer();

        event.setQuitMessage(ChatColor.GREEN + player.getName() + ChatColor.RESET + " / " + ChatColor.DARK_GRAY + "has logged out!");

        if (game.getStatus() == GameStatus.STARTED && game.isPlayerAlive(player))
        {
            Bukkit.broadcastMessage(ChatColor.YELLOW + player.getName() + ChatColor.DARK_AQUA + " has disconnected and therefore forfeited the game!");
            Bukkit.broadcastMessage(ChatColor.DARK_AQUA + "There are " + ChatColor.YELLOW + game.getNumPlayers() + ChatColor.DARK_AQUA + " players remaining.");
        }

        game.removePlayer(player);
        game.setPlayerStatus(player, TributeStatus.QUIT);
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event)
    {
        Block block = event.getBlock();
        if (block.getType() == Material.GLASS || game.getStatus() == GameStatus.WAITING)
        {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPvPDamage(EntityDamageEvent event)
    {
        if (game.getStatus() == GameStatus.WAITING || game.getStatus() == GameStatus.FINISHED)
        {
            event.setCancelled(true);
        }
    }
}
