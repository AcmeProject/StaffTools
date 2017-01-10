package net.poweredbyhate.stafftools;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

/**
 * Created by Lax on 1/10/2017.
 */
public class PlayerJoinListener implements Listener {

    @EventHandler
    public void onLogin(PlayerLoginEvent ev) {
        if (!(ev.getResult() == PlayerLoginEvent.Result.KICK_FULL)) {
            return;
        }
        if (ev.getPlayer().hasPermission("stafftools.fullserverjoin")) {
            ev.allow();
        } else {
            ev.setKickMessage(ChatColor.translateAlternateColorCodes('&', StaffTools.instance.getConfig().getString("fullmsg")));
        }
    }
}
