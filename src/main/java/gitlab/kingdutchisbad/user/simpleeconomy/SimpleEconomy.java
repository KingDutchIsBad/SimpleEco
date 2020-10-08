package gitlab.kingdutchisbad.user.simpleeconomy;

import gitlab.kingdutchisbad.user.simpleeconomy.commands.eco;
import gitlab.kingdutchisbad.user.simpleeconomy.managers.Managers;
import gitlab.kingdutchisbad.user.simpleeconomy.managers.VaultHook;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.java.JavaPlugin;
import java.util.HashMap;
import java.util.UUID;

public  class SimpleEconomy extends JavaPlugin {
    public HashMap<UUID, Double> playerBank = new HashMap<UUID, Double>();
    public Managers managers;
    private VaultHook vaultHook;
    private Economy Econ;


    @Override
    public void onEnable() {
        Econ = new Managers();
        this.getServer().getServicesManager().register(Economy.class, Econ, this.getServer().getPluginManager().getPlugin("Vault"), ServicePriority.Normal);
        getConfig().options().copyDefaults(true);
        saveConfig();
        saveDefaultConfig();
        managers = new Managers();
        vaultHook = new VaultHook();
        try{
            getCommand("eco").setExecutor(new eco());
        }catch (NullPointerException e){
            e.printStackTrace();
        }

        getServer().getPluginManager().registerEvents(new JoinEvent() , this);
        vaultHook.hook();
        Data.setup();
        Data.get().options().copyDefaults(true);
        Data.save();
    }
    @Override
   public void onDisable() {
        vaultHook.unhook();
        Bukkit.getPluginManager().disablePlugin(this);
    }

}
