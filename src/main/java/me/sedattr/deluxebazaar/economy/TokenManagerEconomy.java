package me.sedattr.deluxebazaar.economy;

import me.realized.tokenmanager.api.TokenManager;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.OptionalLong;

public class TokenManagerEconomy implements EconomyManager {
    private final TokenManager api;

    public TokenManagerEconomy() {
        this.api = (TokenManager) Bukkit.getPluginManager().getPlugin("TokenManager");
    }

    @Override
    public boolean addBalance(OfflinePlayer player, Double count) {
        this.api.addTokens(player.getUniqueId().toString(), Long.parseLong(String.valueOf(count)));
        return true;
    }

    @Override
    public boolean removeBalance(OfflinePlayer player, Double count) {
        this.api.removeTokens(player.getUniqueId().toString(), Long.parseLong(String.valueOf(count)));
        return true;
    }

    @Override
    public double getBalance(OfflinePlayer player) {
        OptionalLong tokens = this.api.getTokens((Player) player);
        return (double) tokens.getAsLong();
    }
}
