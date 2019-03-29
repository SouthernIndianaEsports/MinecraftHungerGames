package me.Zachary_Peculier.SIEAHungerGames.Game;

import java.lang.ref.WeakReference;
import java.util.UUID;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;

public class Tribute {

    private WeakReference<Player> player;
    private final UUID uuid;
    private TributeStatus status;

    public Tribute(Player player) {
        this.player = new WeakReference<>(player);
        this.uuid = player.getUniqueId();
    }

    public Player getPlayer() {
        return player.get();
    }

    public final UUID getUUID() {
        return uuid;
    }

    public final String getName() {
        return getPlayer().getName();
    }

    public void updatePlayer(Player p) {
        if (!p.equals(player.get())) {
            this.player = new WeakReference<>(p);
        }
    }

    public final TributeStatus getStatus(){
        return status;
    }

    public void setStatus(TributeStatus s) {
        this.status = s;
    }

    public void setGameMode(GameMode mode) {
        getPlayer().setGameMode(mode);
    }

}
