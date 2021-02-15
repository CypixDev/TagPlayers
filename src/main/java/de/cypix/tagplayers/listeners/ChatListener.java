package de.cypix.tagplayers.listeners;

import de.dytanic.cloudnet.CloudNet;
import de.dytanic.cloudnet.driver.CloudNetDriver;
import de.dytanic.cloudnet.driver.permission.IPermissionGroup;
import de.dytanic.cloudnet.driver.permission.IPermissionUser;
import de.dytanic.cloudnet.driver.permission.PermissionUserGroupInfo;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.Objects;

public class ChatListener implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onChat(AsyncPlayerChatEvent e) {
        System.out.println(e.getMessage());
        if (e.getPlayer().hasPermission("TagPlayers")) {
            if (e.getMessage().contains("@")) {
                String taggedName = e.getMessage().substring(e.getMessage().indexOf("@") + 1).split(" ")[0];
                System.out.println(taggedName);
                Player tagged = Bukkit.getPlayer(taggedName);
                if (tagged != null && tagged.isOnline()) {
                    tagged.playSound(tagged.getLocation(), Sound.ORB_PICKUP, 10F, 0.2F);
                    e.setMessage(e.getMessage().replace("@" + taggedName, getGroupColor(tagged) + "@" + taggedName + "§r"));
                } else {
                    for (IPermissionGroup group : CloudNetDriver.getInstance().getPermissionManagement().getGroups()) {
                        if (group.getName().equalsIgnoreCase(taggedName)) {
                            e.setMessage(e.getMessage().replace("@" + taggedName, group.getColor() + "@" + taggedName + "§r"));
                            for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                                if (Objects.requireNonNull(CloudNetDriver.getInstance().getPermissionManagement().getUser(onlinePlayer.getUniqueId())).inGroup(group.getName())) {
                                    onlinePlayer.playSound(onlinePlayer.getLocation(), Sound.ORB_PICKUP, 10F, 0.2F);
                                }
                            }

                            break;
                        }
                    }
                }
            }
        }
    }


    private String getGroupColor(Player player) {
        //CloudNetDriver.getInstance().getPermissionManagement().get
        IPermissionUser permissionUser = CloudNetDriver.getInstance().getPermissionManagement().getUser(player.getUniqueId());
        assert permissionUser != null;
        return CloudNetDriver.getInstance().getPermissionManagement().getHighestPermissionGroup(permissionUser).getColor();
    }

}
