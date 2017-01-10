package net.poweredbyhate.stafftools;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by Lax on 1/10/2017.
 */
public class dmSlider implements CommandExecutor {

    public dmSlider(StaffTools staffTools) {

    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if (args.length >= 2) {
            if (args[0].equalsIgnoreCase("all") && commandSender.hasPermission("stafftools.dm.all")) {
                for (Player p : Bukkit.getOnlinePlayers()) {
                    p.sendMessage(buildMessage(args));
                }
            }
            if (Bukkit.getPlayer(args[0]) != null && commandSender.hasPermission("stafftools.dm.individual")) {
                Bukkit.getPlayer(args[0]).sendMessage(buildMessage(args));
            }
        }
        return false;
    }

    public String buildMessage(String[] args) {
        StringBuilder sb = new StringBuilder();
        for(int i = 1; i < args.length; i++)
        {
            sb.append(' ').append(args[i]);
        }
        return sb.toString().trim();
    }
}
