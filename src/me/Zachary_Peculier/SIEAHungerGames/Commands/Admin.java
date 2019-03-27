package me.Zachary_Peculier.SIEAHungerGames.Commands;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.Zachary_Peculier.SIEAHungerGames.Game.Game;
import me.Zachary_Peculier.SIEAHungerGames.Game.TributeStatus;

public class Admin implements CommandExecutor {
    
    private final Game game;

    public Admin(final Game g) {
        this.game = g;
    }

	@Override
	public final boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
	    Player player = (Player) sender;
	    
	    if (player.hasPermission("siea.admin"))
        {
	        game.setPlayerStatus(player, TributeStatus.ADMIN);
	        game.setPlayerMode(player, GameMode.CREATIVE);
        }
        else
        {
            player.sendMessage(ChatColor.RED + "No Permission!");
        }
	    
		return true;
	}

}
