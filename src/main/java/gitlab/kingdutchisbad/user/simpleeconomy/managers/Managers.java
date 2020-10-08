package gitlab.kingdutchisbad.user.simpleeconomy.managers;

import gitlab.kingdutchisbad.user.simpleeconomy.Data;
import gitlab.kingdutchisbad.user.simpleeconomy.SimpleEconomy;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.UUID;

public class Managers implements Economy {
    private final SimpleEconomy plugin = SimpleEconomy.getPlugin(SimpleEconomy.class);

    @Override
    public boolean isEnabled() {
        return false;
    }

    @Override
    public String getName() {
        return "SimpleEconomy";
    }

    @Override
    public boolean hasBankSupport() {
        return false;
    }

    @Override
    public int fractionalDigits() {
        return 0;
    }

    @Override
    public String format(double v) {
        return null;
    }

    @Override
    public String currencyNamePlural() {
        return plugin.getConfig().getString("MoneyNamePlural");
    }

    @Override
    public String currencyNameSingular() {
        return plugin.getConfig().getString("MoneyNameNotPlural");
    }

    @Override
    public boolean hasAccount(String s) {
        return false;
    }

    @Override
    public boolean hasAccount(OfflinePlayer offlinePlayer) {
        return false;
    }

    @Override
    public boolean hasAccount(String s, String s1) {
        return false;
    }

    @Override
    public boolean hasAccount(OfflinePlayer offlinePlayer, String s) {
        return false;
    }

    @Override
    public double getBalance(String s) {
        Player player = Bukkit.getPlayer(s);
        UUID uuid = player.getUniqueId();
        return Data.get().getInt(uuid + ".money");
    }

    @Override
    public double getBalance(OfflinePlayer offlinePlayer) {
        UUID uuid = offlinePlayer.getUniqueId();
        return Data.get().getInt(uuid + ".money");
    }

    @Override
    public double getBalance(String s, String s1) {
        Player player = Bukkit.getPlayer(s);
        UUID uuid = player.getUniqueId();
        return Data.get().getInt( uuid + ".money");
    }

    @Override
    public double getBalance(OfflinePlayer offlinePlayer, String s) {
        UUID uuid = offlinePlayer.getUniqueId();
        return Data.get().getInt(  uuid + ".money");
    }

    @Override
    public boolean has(String s, double v) {
        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(s);
        UUID uuid = offlinePlayer.getUniqueId();
        Double cool = Data.get().getDouble(uuid + ".money", Double.valueOf(0.0D).doubleValue());
        return (cool >= v);
    }

    @Override
    public boolean has(OfflinePlayer offlinePlayer, double v) {
        UUID uuid = offlinePlayer.getUniqueId();
        Double cool = Data.get().getDouble(uuid + ".money", Double.valueOf(0.0D).doubleValue());
        return (cool >= v);
    }

    @Override
    public boolean has(String s, String s1, double v) {
        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(s);
        UUID uuid = offlinePlayer.getUniqueId();
        Double cool = Data.get().getDouble(uuid + ".money", Double.valueOf(0.0D).doubleValue());
        return (cool >= v);
    }

    @Override
    public boolean has(OfflinePlayer offlinePlayer, String s, double v) {
        UUID uuid = offlinePlayer.getUniqueId();
        Double cool = Data.get().getDouble(uuid + ".money", Double.valueOf(0.0D).doubleValue());
        return (cool >= v);
    }

    @Override
    public EconomyResponse withdrawPlayer(String s, double v) {
        Player target = plugin.getServer().getPlayer(s);
        return withPlayerStuff(target, v);
    }

    @Override
    public EconomyResponse withdrawPlayer(OfflinePlayer offlinePlayer, double v) {
        return withPlayerStuff(offlinePlayer, v);
    }

    @Override
    public EconomyResponse withdrawPlayer(String s, String s1, double v) {
        Player player = Bukkit.getPlayer(s);
        return withPlayerStuff(player, v);
    }

    @Override
    public EconomyResponse withdrawPlayer(OfflinePlayer offlinePlayer, String s, double v) {
        return withPlayerStuff(offlinePlayer, v);
    }

    private EconomyResponse withPlayerStuff(OfflinePlayer player, double amt) {
        if (getBalance(player) - amt >= 0) {
            UUID uuid = player.getUniqueId();
            double balold = Data.get().getDouble(uuid + ".money");
            Data.get().set(player.getUniqueId() + ".money", balold - amt);
            Data.save();
            return responseSuccess(amt, getBalance(player));
        }
        return responseFailed(amt, getBalance(player));
    }

    @Override
    public EconomyResponse depositPlayer(String s, double v) {
        Player player = Bukkit.getPlayerExact(s);
        UUID uuid = player.getUniqueId();
        double balold = Data.get().getDouble(uuid + ".money");
        plugin.playerBank.put(uuid, balold + v);
        Data.get().set(player.getUniqueId() + ".money", balold + v);
        Data.save();
        return responseSuccess(v, getBalance(player));

    }

    @Override
    public EconomyResponse depositPlayer(OfflinePlayer offlinePlayer, double v) {
        UUID uuid = offlinePlayer.getUniqueId();
        double balold = Data.get().getDouble(uuid + ".money");
        plugin.playerBank.put(uuid, balold + v);
        Data.get().set(uuid + ".money", balold + v);
        Data.save();
        return responseSuccess(v, getBalance(offlinePlayer));
    }

    @Override
    public EconomyResponse depositPlayer(String s, String s1, double v) {
        Player player = Bukkit.getPlayerExact(s);
        UUID uuid = player.getUniqueId();
        double balold = Data.get().getDouble(uuid + ".money");
        ;
        plugin.playerBank.put(uuid, balold + v);
        Data.get().set(uuid + ".money", balold + v);
        Data.save();
        return responseSuccess(v, getBalance(player));
    }

    @Override
    public EconomyResponse depositPlayer(OfflinePlayer offlinePlayer, String s, double v) {
        UUID uuid = offlinePlayer.getUniqueId();
        double balold = Data.get().getDouble(uuid + ".money");
        ;
        plugin.playerBank.put(uuid, balold + v);
        Data.get().set(uuid + ".money", balold + v);
        Data.save();
        return responseSuccess(v, getBalance(offlinePlayer));
    }

    @Override
    public EconomyResponse createBank(String s, String s1) {
        return null;
    }

    @Override
    public EconomyResponse createBank(String s, OfflinePlayer offlinePlayer) {
        return null;
    }

    @Override
    public EconomyResponse deleteBank(String s) {
        return null;
    }

    @Override
    public EconomyResponse bankBalance(String s) {
        return null;
    }

    @Override
    public EconomyResponse bankHas(String s, double v) {
        return null;
    }

    @Override
    public EconomyResponse bankWithdraw(String s, double v) {
        return null;
    }

    @Override
    public EconomyResponse bankDeposit(String s, double v) {
        return null;
    }

    @Override
    public EconomyResponse isBankOwner(String s, String s1) {
        return null;
    }

    @Override
    public EconomyResponse isBankOwner(String s, OfflinePlayer offlinePlayer) {
        return null;
    }

    @Override
    public EconomyResponse isBankMember(String s, String s1) {
        return null;
    }

    @Override
    public EconomyResponse isBankMember(String s, OfflinePlayer offlinePlayer) {
        return null;
    }

    @Override
    public List<String> getBanks() {
        return null;
    }

    @Override
    public boolean createPlayerAccount(String s) {
        return false;
    }

    @Override
    public boolean createPlayerAccount(OfflinePlayer offlinePlayer) {
        return false;
    }

    @Override
    public boolean createPlayerAccount(String s, String s1) {
        return false;
    }

    @Override
    public boolean createPlayerAccount(OfflinePlayer offlinePlayer, String s) {
        return false;
    }

    private EconomyResponse responseSuccess(double amountChange, double balance) {
        return new EconomyResponse(amountChange, balance, EconomyResponse.ResponseType.SUCCESS, null);
    }

    private EconomyResponse responseFailed(double amountChange, double balance) {
        return new EconomyResponse(amountChange, balance, EconomyResponse.ResponseType.FAILURE, null);
    }
}
