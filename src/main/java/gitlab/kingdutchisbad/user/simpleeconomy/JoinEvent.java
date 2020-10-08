package gitlab.kingdutchisbad.user.simpleeconomy;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.UUID;

public class JoinEvent implements Listener {
    @EventHandler
    public void onJoinEvent(PlayerJoinEvent event){
        UUID uuid = event.getPlayer().getUniqueId();
        if(Data.get().get(uuid + ".money") == null){
            Data.get().set(uuid +".money" , 0);
            Data.save();
        }
    }
}
