package me.sedattr.deluxebazaar.economy;

import me.angeschossen.lands.api.LandsIntegration;
import me.angeschossen.lands.api.land.Land;
import me.angeschossen.lands.api.player.LandPlayer;
import me.sedattr.deluxebazaar.DeluxeBazaar;
import org.bukkit.OfflinePlayer;

public class LandsEconomy implements EconomyManager {
    public final LandsIntegration api;

    public LandsEconomy() {
        this.api = LandsIntegration.of(DeluxeBazaar.getInstance());
    }

    @Override
    public boolean addBalance(OfflinePlayer player, Double count) {
        LandPlayer landPlayer = this.api.getLandPlayer(player.getUniqueId());
        if (landPlayer == null)
            return false;

        Land land = landPlayer.getOwningLand();
        if (land == null)
            return false;

        return land.setBalance(land.getBalance()+count);
    }

    @Override
    public boolean removeBalance(OfflinePlayer player, Double count) {
        LandPlayer landPlayer = this.api.getLandPlayer(player.getUniqueId());
        if (landPlayer == null)
            return false;

        Land land = landPlayer.getOwningLand();
        if (land == null)
            return false;

        double balance = land.getBalance();
        return land.setBalance(balance - count);
    }

    @Override
    public double getBalance(OfflinePlayer player) {
        LandPlayer landPlayer = this.api.getLandPlayer(player.getUniqueId());
        if (landPlayer == null)
            return 0.0;

        Land land = landPlayer.getOwningLand();
        if (land == null)
            return 0.0;

        return land.getBalance();
    }
}
