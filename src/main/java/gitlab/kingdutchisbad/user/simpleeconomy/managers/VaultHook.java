package gitlab.kingdutchisbad.user.simpleeconomy.managers;

import gitlab.kingdutchisbad.user.simpleeconomy.SimpleEconomy;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.ServicePriority;

public class VaultHook {
    private SimpleEconomy plugin= SimpleEconomy.getPlugin(SimpleEconomy.class);
    private Managers provider;

    public void hook() {
        provider = plugin.managers;
        Bukkit.getServicesManager().register(Managers.class, this.provider, this.plugin, ServicePriority.Normal);
        Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "VaultAPI hooked into " + ChatColor.AQUA + plugin.getName());
    }

    public void unhook() {
        Bukkit.getServicesManager().unregister(SimpleEconomy.class, this.provider);
        Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "VaultAPI unhooked from " + ChatColor.AQUA + plugin.getName());

    }
}
