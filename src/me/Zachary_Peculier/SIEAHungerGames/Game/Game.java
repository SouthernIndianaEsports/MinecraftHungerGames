package me.Zachary_Peculier.SIEAHungerGames.Game;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.ChatColor;

public class Game
{

    private GameStatus status = GameStatus.DISABLED;
    private ArrayList<Tribute> tributes = new ArrayList<Tribute>();

    public GameStatus getStatus()
    {
        return status;
    }

    public void startGame()
    {
        status = GameStatus.STARTED;

        Bukkit.broadcastMessage(ChatColor.RED + "Go!");
        Bukkit.broadcastMessage(ChatColor.DARK_AQUA + "Good luck, and may the odds be ever in your favor");
        Bukkit.broadcastMessage(ChatColor.DARK_AQUA + "There are " + ChatColor.YELLOW + tributes.size() + ChatColor.DARK_AQUA + " paricipants");

        for (int i = 0; i < tributes.size(); i++)
        {
            tributes.get(i).setGameMode(GameMode.SURVIVAL);
        }
    }

    public void startTimer()
    {
        status = GameStatus.WAITING;

        for (int i = 0; i < tributes.size(); i++)
        {
            tributes.get(i).setGameMode(GameMode.SPECTATOR);
        }
    }

    public void end()
    {
        status = GameStatus.FINISHED;
        for (int i = 0; i < 10; i++)
        {
            Bukkit.broadcastMessage(ChatColor.RED + "GAME OVER! WE HAVE A WINNER! CONGRATULATIONS, " + tributes.get(0).getName() + "!!!!");
        }
        tributes.clear();
    }

    public Tribute getTribute(Player player)
    {
        for (int i = 0; i < tributes.size(); i++)
        {
            Tribute tribute = tributes.get(i);
            if (tribute.getPlayer() == player)
            {
                return tribute;
            }
        }

        return null;
    }

    public int getNumPlayers()
    {
        return tributes.size();
    }

    public void setPlayerStatus(Player player, TributeStatus status)
    {
        Tribute tribute = this.getTribute(player);
        if (tribute == null)
        {
            return;
        }

        tribute.setStatus(status);
    }
    
    public void setPlayerMode(Player player, GameMode mode)
    {
        Tribute tribute = this.getTribute(player);
        if (tribute == null)
        {
            return;
        }

        tribute.setGameMode(mode);
    }

    public void addPlayer(Player player)
    {
        Tribute tribute = new Tribute(player);
        tribute.setStatus(TributeStatus.ALIVE);
        tribute.setGameMode(GameMode.ADVENTURE);
        tributes.add(tribute);
    }

    public void removePlayer(Player player)
    {
        for (int i = 0; i < tributes.size(); i++) {
            if (tributes.get(i).getPlayer() == player) {
                tributes.get(i).setStatus(TributeStatus.DEAD);
                tributes.get(i).setGameMode(GameMode.SPECTATOR);
            }
        }
    }

    public boolean inGame(Player player)
    {
        Tribute tribute = getTribute(player);
        return tribute != null && tribute.getStatus() == TributeStatus.ALIVE;
    }

    public void listPlayers(Player player)
    {
        for (int i = 0; i != tributes.size(); i++)
        {
            int num = i + 1;
            player.sendMessage(ChatColor.DARK_AQUA + "[" + ChatColor.YELLOW + num + ChatColor.DARK_AQUA + "] " + ChatColor.YELLOW + tributes.get(i).getName());
        }
        player.sendMessage(ChatColor.YELLOW + "" + (tributes.size()) + ChatColor.DARK_AQUA + " remain");
    }
}
