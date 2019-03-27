package me.Zachary_Peculier.SIEAHungerGames.Commands;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.Zachary_Peculier.SIEAHungerGames.Listeners.PlayerListener;

public class Admin implements CommandExecutor
{

    private final PlayerListener mpl;

    public Admin(final PlayerListener playerListener)
    {
        this.mpl = playerListener;
    }

    @Override
    public final boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args)
    {
        Player player = (Player) sender;

        if (player.hasPermission("siea.admin"))
        {
            mpl.RemoveTrib(player);
            player.setGameMode(GameMode.CREATIVE);
        }
        else
        {
            player.sendMessage(ChatColor.RED + "No Permission!");
        }

        return true;
    }

}
