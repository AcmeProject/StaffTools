package net.poweredbyhate.stafftools;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by Lax on 1/10/2017.
 */
public class StaffTools extends JavaPlugin implements Listener {

    public ArrayList<UUID> staffers = new ArrayList<>();
    static StaffTools instance;

    public void onEnable() {
        instance = this;
        Bukkit.getPluginManager().registerEvents(this, this);
        Bukkit.getPluginManager().registerEvents(new SignClickListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerJoinListener(), this);
        getCommand("dm").setExecutor(new dmSlider(this));
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent ev) {
        Player p = ev.getPlayer();
        if (p.hasPermission("stafftools.staffchat") && staffers.contains(p.getUniqueId())) {
            ev.setCancelled(true);
            for (Player x : Bukkit.getOnlinePlayers()) {
                x.sendMessage(ChatColor.translateAlternateColorCodes('&', getConfig().getString("scformat").replace("{PLAYER}", p.getName()).replace("{MESSAGE}", ev.getMessage()) ));
            }
        }
    }

    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent ev) {
        Player p = ev.getPlayer();
        if (ev.getMessage().startsWith("/sc") && p.hasPermission("stafftools.staffchat")) {
            ev.setCancelled(true);
            if (staffers.contains(p.getUniqueId())) {
                p.sendMessage(ChatColor.GOLD + "You have toggled Staff Chat: " + ChatColor.RED  + "off");
                staffers.remove(p.getUniqueId());
            } else {
                p.sendMessage(ChatColor.GOLD + "You have toggled Staff Chat: " + ChatColor.GREEN  + "on");
                staffers.add(p.getUniqueId());
            }
        }
    }


}
