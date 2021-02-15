package de.cypix.tagplayers.listeners;

import de.dytanic.cloudnet.driver.permission.IPermissionUser;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatListener implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onChat(AsyncPlayerChatEvent e){
        if(e.getPlayer().hasPermission("TagPlayers")){
            if(e.getMessage().contains("@")){
                String playerName = e.getMessage().substring(e.getMessage().indexOf("@") + 1).split(" ")[0];
                Player tagged = Bukkit.getPlayer(playerName);
                if(tagged != null && tagged.isOnline()){
                    tagged.playSound(tagged.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 10F, 0.2F);
                    e.setMessage(e.getMessage().replace("@"+playerName,"§c@"+playerName+"§7"));
                }
            }
        }
    }


    private String getGroupColor(Player player){
        IPermissionUser permissionUser = CloudPermissionManagement.getInstance().getUser(player.getUniqueId());

    }

}
