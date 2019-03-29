package me.Zachary_Peculier.SIEAHungerGames.Game;

import java.util.UUID;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;

public class Tribute {

    private Player player;
    private final UUID uuid;
    private TributeStatus status;

    public Tribute(Player player) {
        this.player = player;
        this.uuid = player.getUniqueId();
    }
    
    public Player getPlayer() {
        return player;
    }

    public final UUID getUUID() {
        return uuid;
    }
    
    public final String getName() {
        return player.getName();
    }

    public final TributeStatus getStatus(){
        return status;
    }
    
    public void setStatus(TributeStatus s) {
        this.status = s;
    }
    
    public void setGameMode(GameMode mode) {
        this.player.setGameMode(mode);
    }

}
