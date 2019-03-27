package me.Zachary_Peculier.SIEAHungerGames.Commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.Zachary_Peculier.SIEAHungerGames.Listeners.PlayerListener;

public class Help implements CommandExecutor {
    
    private final PlayerListener mpl;

    public Help(final PlayerListener playerListener) {
        this.mpl = playerListener;
    }

	@Override
	public final boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
        Player player = (Player) sender;
        player.sendMessage(ChatColor.GRAY + "Welcome to the Minecraft Hunger Games!");
        player.sendMessage(ChatColor.GRAY + "There are " + mpl.getTributeSize() + " players remaining");
        player.sendMessage(ChatColor.GRAY + "/alive (or /who) - shows list of remaining players");
        return true;
    }

}
