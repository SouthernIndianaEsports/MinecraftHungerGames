<<<<<<< HEAD
package me.Zachary_Peculier.SIEAHungerGames.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.Zachary_Peculier.SIEAHungerGames.Game.Game;

public class Who implements CommandExecutor {
    
    private final Game game;

    public Who(final Game g) {
        this.game = g;
    }

	@Override
	public final boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
	    Player player = (Player) sender;
        game.listPlayers(player);
		return true;
	}

}
=======
package me.Zachary_Peculier.SIEAHungerGames.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.Zachary_Peculier.SIEAHungerGames.Listeners.PlayerListener;

public class Who implements CommandExecutor
{

    private final PlayerListener mpl;

    public Who(final PlayerListener playerListener)
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
>>>>>>> 669535ce77d6a990fb0dd6ba0988f97d49413a5e