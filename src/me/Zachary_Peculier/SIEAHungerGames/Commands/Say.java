package me.Zachary_Peculier.SIEAHungerGames.Commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.Zachary_Peculier.SIEAHungerGames.Listeners.PlayerListener;

public class Say implements CommandExecutor
{
    public Say(final PlayerListener playerListener)
    {
    }

    @Override
    public final boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args)
    {
        Player player = (Player) sender;
        if (player.hasPermission("siea.admin"))
        {
            if (args.length == 0)
            {
                player.sendMessage(ChatColor.RED + "Usage: /say <message>");
                return true;
            }
            else if (args.length >= 1)
            {
                String message = "";
                for (int i = 0; i < args.length; ++i)
                {
                    message += args[i] + " ";
                }
                Bukkit.broadcastMessage(ChatColor.LIGHT_PURPLE + "[Server] " + message);
                return true;
            }
        }
        else
        {
            player.sendMessage(ChatColor.RED + "No Permissions!");
            return true;
        }
        return true;
    }

}
