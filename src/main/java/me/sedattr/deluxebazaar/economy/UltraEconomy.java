package me.sedattr.deluxebazaar.economy;

import me.TechsCode.UltraEconomy.UltraEconomyAPI;
import me.TechsCode.UltraEconomy.objects.Account;
import me.TechsCode.UltraEconomy.objects.Currency;
import me.sedattr.deluxebazaar.DeluxeBazaar;
import org.bukkit.OfflinePlayer;

import java.util.Optional;

public class UltraEconomy implements EconomyManager {
    UltraEconomyAPI api;
    Currency currency;

    public UltraEconomy() {
        this.api = me.TechsCode.UltraEconomy.UltraEconomy.getAPI();

        String currencyName = DeluxeBazaar.getInstance().configFile.getString("economy.ultraeconomy_settings.currency_name");
        if (currencyName != null && !currencyName.isEmpty()) {
            Optional<Currency> currency1 = this.api.getCurrencies().name(currencyName);
            currency1.ifPresent(value -> this.currency = value);
        }
    }

    @Override
    public boolean addBalance(OfflinePlayer player, Double count) {
        Optional<Account> account = api.getAccounts().uuid(player.getUniqueId());
        return account.map(value -> value.addBalance(currency, count)).orElse(false);
    }

    @Override
    public boolean removeBalance(OfflinePlayer player, Double count) {
        Optional<Account> account = api.getAccounts().uuid(player.getUniqueId());
        return account.map(value -> value.removeBalance(currency, count)).orElse(false);
    }

    @Override
    public double getBalance(OfflinePlayer player) {
        Optional<Account> account = api.getAccounts().uuid(player.getUniqueId());
        return account.map(value -> value.getBalance(currency).getOnHand()).orElse(0.0);
    }
}
