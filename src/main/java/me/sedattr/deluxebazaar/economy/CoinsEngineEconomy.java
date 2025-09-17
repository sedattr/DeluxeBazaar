package me.sedattr.deluxebazaar.economy;

import me.sedattr.deluxebazaar.DeluxeBazaar;
import org.bukkit.OfflinePlayer;
import su.nightexpress.coinsengine.api.CoinsEngineAPI;
import su.nightexpress.coinsengine.api.currency.Currency;

public class CoinsEngineEconomy implements EconomyManager {
    Currency currency;

    public CoinsEngineEconomy() {
        this.currency = CoinsEngineAPI.getCurrency(DeluxeBazaar.getInstance().configFile.getString("economy.coinsengine_settings.currency_name", "coins"));
    }

    @Override
    public boolean addBalance(OfflinePlayer player, Double count) {
        return CoinsEngineAPI.addBalance(player.getUniqueId(), this.currency, count);
    }

    @Override
    public boolean removeBalance(OfflinePlayer player, Double count) {
        return CoinsEngineAPI.removeBalance(player.getUniqueId(), this.currency, count);
    }

    @Override
    public double getBalance(OfflinePlayer player) {
        return CoinsEngineAPI.getBalance(player.getUniqueId(), this.currency);
    }
}
