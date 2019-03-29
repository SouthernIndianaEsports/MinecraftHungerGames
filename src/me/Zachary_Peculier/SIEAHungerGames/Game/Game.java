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
    private ArrayList<Player> admins = new ArrayList<Player>();

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

        for (int i = 0; i < tributes.size(); i++) //ensure everyone is set to alive and is in survival
        {
            tributes.get(i).setGameMode(GameMode.SURVIVAL);
            tributes.get(i).setStatus(TributeStatus.ALIVE);
        }

        Bukkit.getWorld("world").setTime(0);
    }

    public void startTimer()
    {
        status = GameStatus.WAITING;

        for (int i = 0; i < tributes.size(); i++)
        {
            tributes.get(i).setGameMode(GameMode.ADVENTURE);
            tributes.get(i).setStatus(TributeStatus.ALIVE);
        }
    }

    public void end()
    {
        status = GameStatus.FINISHED;

        Tribute tribute = null;
        for (int i = 0; i < tributes.size(); i++) { //find the last alive player
            Tribute t = tributes.get(i);
            if (t.getStatus() == TributeStatus.ALIVE) {
                tribute = t;
            }
        }

        for (int i = 0; i < 10; i++)
        {
            Bukkit.broadcastMessage(ChatColor.RED + "GAME OVER! WE HAVE A WINNER! CONGRATULATIONS, " + tribute.getName() + "!!!!");
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

    public int getNumAlive() {
        int number = 0;

        for (int i = 0; i < tributes.size(); i++) {
            final Tribute tribute = tributes.get(i);
            if (tribute.getStatus() == TributeStatus.ALIVE) {
                number++;
            }
        }

        return number;
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

    public void deletePlayer(Player player) {
        Tribute tribute = this.getTribute(player);
        if (tribute == null) {
            return;
        }

        tributes.remove(tribute);
    }

    public void addAdmin(Player player) {
        admins.add(player);
        player.setGameMode(GameMode.CREATIVE);
    }

    public boolean isAdmin(Player player) {
        for (int i = 0; i < admins.size(); i++) {
            if (admins.get(i) == player) {
                return true;
            }
        }

        return false;
    }

    public void removeAdmin(Player player) {
        admins.remove(player);
        player.setGameMode(GameMode.SURVIVAL);
    }

    public boolean inGame(Player player)
    {
        return getTribute(player) != null;
    }

    public boolean isPlayerAlive(Player player) {
        Tribute tribute = this.getTribute(player);
        if (tribute == null) {
            return false;
        }

        return tribute.getStatus() == TributeStatus.ALIVE;
    }

    public void listPlayers(Player player)
    {
        for (int i = 0; i != tributes.size(); i++)
        {
            final Tribute tribute = tributes.get(i);
            if (tribute.getStatus() != TributeStatus.ALIVE) { //only list people who are alive
                continue;
            }
            int num = i + 1;
            player.sendMessage(ChatColor.DARK_AQUA + "[" + ChatColor.YELLOW + num + ChatColor.DARK_AQUA + "] " + ChatColor.YELLOW + tribute.getName());
        }
        player.sendMessage(ChatColor.YELLOW + "" + this.getNumAlive() + ChatColor.DARK_AQUA + " remain");
    }
}
