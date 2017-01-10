package net.poweredbyhate.stafftools;

import net.minecraft.server.v1_11_R1.BlockPosition;
import net.minecraft.server.v1_11_R1.PacketPlayOutOpenSignEditor;
import org.bukkit.ChatColor;
import org.bukkit.block.Sign;
import org.bukkit.craftbukkit.v1_11_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import java.lang.reflect.Field;

/**
 * Created by Lax on 1/10/2017.
 */
public class SignClickListener implements Listener {

    @EventHandler
    public void onInteract(PlayerInteractEvent ev) {
        if (ev.getAction() == Action.RIGHT_CLICK_BLOCK && ev.getClickedBlock().getState() instanceof Sign && ev.getPlayer().isSneaking() && ev.getPlayer().hasPermission("stafftools.signedit")) {
            openEditor(ev.getPlayer(), (Sign) ev.getClickedBlock().getState());
        }
    }

    @EventHandler
    public void onSignChange(SignChangeEvent ev) {
        if (ev.getPlayer().hasPermission("stafftools.signedit")) {
            int i = 0;
            for (String s : ev.getLines()) {
                ev.setLine(i, ChatColor.translateAlternateColorCodes('&', s));i++;
            }
        }
    }

    public void openEditor(Player p, Sign s) { //https://bukkit.org/threads/open-signgui-with-packets-not-protocollib.359553/#post-3126850
        CraftPlayer cp = (CraftPlayer) p;
        try {
            Field field = s.getClass().getDeclaredField("sign"); //Get the "TileEntitySign sign" field
            field.setAccessible(true); //Make it accessible
            Object tileEntity = field.get(s); //Get the TileEntitySign from the field.

            Field editable = tileEntity.getClass().getDeclaredField("isEditable"); //Get the field "editable" from TileEntitySign
            editable.set(tileEntity, true); //Set it to true

            Field owner = tileEntity.getClass().getDeclaredField("h"); //Get the field "k" from TileEntitySign
            owner.setAccessible(true); //Make it accessible
            owner.set(tileEntity, cp.getHandle()); //Set it to the craftplayer
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
        PacketPlayOutOpenSignEditor ppoose = new PacketPlayOutOpenSignEditor(new BlockPosition(s.getX(),s.getY(),s.getZ()));
        cp.getHandle().playerConnection.sendPacket(ppoose);
    }
}
