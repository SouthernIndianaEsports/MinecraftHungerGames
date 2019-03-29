package me.Zachary_Peculier.SIEAHungerGames.Game;

import java.util.ArrayList;
import java.util.logging.*;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.BlockState;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.ChatColor;
import org.bukkit.Chunk;

public class Game
{
    private GameStatus status = GameStatus.WAITING;
    private ArrayList<Tribute> tributes = new ArrayList<Tribute>();
    private ArrayList<Player> admins = new ArrayList<Player>();
    private boolean frozen = false;

    public GameStatus getStatus()
    {
        return status;
    }

    public void startGame()
    {
        status = GameStatus.STARTED;
        frozen = false;
        Bukkit.broadcastMessage(ChatColor.RED + "Go!");
        Bukkit.broadcastMessage(ChatColor.DARK_AQUA + "Good luck, and may the odds be ever in your favor");
        Bukkit.broadcastMessage(ChatColor.DARK_AQUA + "There are " + ChatColor.YELLOW + tributes.size() + ChatColor.DARK_AQUA + " paricipants");

        for (int i = 0; i < tributes.size(); i++) // ensure everyone is set to
                                                  // alive and is in survival
        {
            if (tributes.get(i).getStatus() == TributeStatus.QUIT)
            {
                continue;
            }

            tributes.get(i).setStatus(TributeStatus.ALIVE);
        }

        Bukkit.getWorld("world").setTime(0);
        for (Chunk c : Bukkit.getWorld("world").getLoadedChunks())
        {

            for (BlockState b : c.getTileEntities())
            {

                if (b instanceof Chest)
                {
                    Chest chest = (Chest) b;
                    Inventory inventory = chest.getBlockInventory();
                    Random rand = new Random();

                    Material[] randomItens =
                    { Material.APPLE, Material.EXP_BOTTLE, Material.GOLD_SWORD, Material.WOOD_SWORD, Material.STONE_AXE, Material.GOLD_PICKAXE, Material.ARROW, Material.BOW, Material.STICK, Material.COBBLESTONE, Material.IRON_INGOT };

                    for (int i = 0; i < 10; i++)
                    {

                        for (int amountOfItems = 0; amountOfItems < 9; amountOfItems++)
                        {
                            int n = (int) (Math.random() * 49);
                            if (n % 2 == 0)
                            {
                                inventory.addItem(new ItemStack(randomItens[rand.nextInt(randomItens.length)]));
                            }

                        }
                    }
                }
            }
        }
    }

    public void startTimer()
    {
        status = GameStatus.WAITING;
        frozen = false;
        for (int i = 0; i < tributes.size(); i++)
        {
            if (tributes.get(i).getStatus() == TributeStatus.QUIT)
            {
                continue;
            }

            tributes.get(i).setStatus(TributeStatus.ALIVE);
        }
    }

    public void end()
    {
        status = GameStatus.FINISHED;

        Tribute tribute = null;
        for (int i = 0; i < tributes.size(); i++)
        { // find the last alive player
            Tribute t = tributes.get(i);
            if (t.getStatus() == TributeStatus.ALIVE)
            {
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
            if (tribute.getUUID().compareTo(player.getUniqueId()) == 0)
            {
                Bukkit.getLogger().log(Level.INFO, "Found player: " + player.getName());
                return tribute;
            }
        }

        return null;
    }

    public int getNumPlayers()
    {
        return tributes.size();
    }

    public int getNumAlive()
    {
        int number = 0;

        for (int i = 0; i < tributes.size(); i++)
        {
            final Tribute tribute = tributes.get(i);
            if (tribute.getStatus() == TributeStatus.ALIVE)
            {
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
            Bukkit.getLogger().log(Level.INFO, "Could not find player: " + player.getName());
            return;
        }

        tribute.setStatus(status);
    }

    public void setPlayerMode(Player player, GameMode mode)
    {
        Tribute tribute = this.getTribute(player);
        if (tribute == null)
        {
            Bukkit.getLogger().log(Level.INFO, "Could not find player: " + player.getName());
            return;
        }

        tribute.setGameMode(mode);
    }

    public void addPlayer(Player player)
    {
        Tribute tribute = new Tribute(player);
        tribute.setStatus(TributeStatus.ALIVE);
        player.setGameMode(GameMode.SURVIVAL);
        tributes.add(tribute);
    }

    /*
     * Sets the player as dead and makes them spectator
     */
    public void removePlayer(Player player)
    {
        Tribute tribute = this.getTribute(player);
        if (tribute == null)
        {
            Bukkit.getLogger().log(Level.INFO, "Unable to remove player: " + player.getName());
            return;
        }

        tribute.setGameMode(GameMode.SPECTATOR);
        tribute.setStatus(TributeStatus.DEAD);
    }

    /*
     * Removes player from game completely
     */
    public void deletePlayer(Player player)
    {
        Tribute tribute = this.getTribute(player);
        if (tribute == null)
        {
            return;
        }

        tributes.remove(tribute);
    }

    public void addAdmin(Player player)
    {
        admins.add(player);
        player.setGameMode(GameMode.CREATIVE);
    }

    public boolean isAdmin(Player player)
    {
        for (int i = 0; i < admins.size(); i++)
        {
            if (admins.get(i) == player)
            {
                return true;
            }
        }

        return false;
    }

    public void removeAdmin(Player player)
    {
        admins.remove(player);
        player.setGameMode(GameMode.SURVIVAL);
    }

    public boolean inGame(Player player)
    {
        for (int i = 0; i < tributes.size(); i++)
        {
            final Tribute tribute = tributes.get(i);
            if (tribute.getUUID().compareTo(player.getUniqueId()) == 0)
            {
                Bukkit.getLogger().log(Level.INFO, "Found player: " + player.getName());
                return true;
            }
        }

        return false;
    }

    public boolean isPlayerAlive(Player player)
    {
        Tribute tribute = this.getTribute(player);
        if (tribute == null)
        {
            return false;
        }

        return tribute.getStatus() == TributeStatus.ALIVE;
    }

    public void listPlayers(Player player)
    {
        for (int i = 0; i < tributes.size(); i++)
        {
            final Tribute tribute = tributes.get(i);
            if (tribute.getStatus() != TributeStatus.ALIVE) // only list people
                                                            // who are alive
            {
                continue;
            }
            int num = i + 1;
            player.sendMessage(ChatColor.DARK_AQUA + "[" + ChatColor.YELLOW + num + ChatColor.DARK_AQUA + "] " + ChatColor.YELLOW + tribute.getName());
        }
        player.sendMessage(ChatColor.YELLOW + "" + this.getNumAlive() + ChatColor.DARK_AQUA + " remain");
        player.sendMessage(ChatColor.YELLOW + "" + this.getNumPlayers() + ChatColor.DARK_AQUA + " in total.");
    }

    public boolean getFrozen()
    {
        return frozen;
    }

    public void setFrozen(boolean f)
    {
        this.frozen = f;
    }
}
