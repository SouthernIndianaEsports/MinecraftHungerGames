package me.Zachary_Peculier.SIEAHungerGames.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.Zachary_Peculier.SIEAHungerGames.Listeners.PlayerListener;

public class Alive implements CommandExecutor
{

    private final PlayerListener mpl;

    public Alive(final PlayerListener playerListener)
    {
        this.mpl = playerListener;
    }

    @Override
    public final boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args)
    {
        Player player = (Player) sender;
        mpl.listPlayers(player);
        return true;
    }

}
