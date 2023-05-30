package dev.pizzaclient.gooberlobby.events;

import dev.pizzaclient.gooberlobby.gui.SelectorGUI;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.awt.*;
import java.util.Arrays;
import java.util.List;

import static dev.pizzaclient.gooberlobby.utils.Utils.*;

public class Events implements Listener {

    static ItemStack compass = new ItemStack(Material.COMPASS, 1);
    static ItemMeta compassMeta = compass.getItemMeta();
    static List compassLore = Arrays.asList(
            coloredString("&fClick to open the server selector!")
    );

    static {
        compassMeta.setDisplayName(coloredString("&a&lS&2&le&a&lr&2&lv&a&le&2&lr &a&lS&2&le&a&ll&2&le&a&lc&2&lt&a&lo&2&lr"));
        compassMeta.setLore(compassLore);
        compass.setItemMeta(compassMeta);
    }

    // Player Join Event
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        event.setJoinMessage(coloredString("&8[&a&l+&8] &7" + event.getPlayer().getName()));

        event.getPlayer().getInventory().setItem(4, compass);
        // Teleport player to 0.5 6 -9.5
        event.getPlayer().teleport(new Location(Bukkit.getWorld("world"), 0.5, 6, -9.5));
        giveScoreboard(event.getPlayer());
    }

    // Player Leave Event
    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event) {
        event.setQuitMessage(coloredString("&8[&c&l-&8] &7" + event.getPlayer().getName()));
        event.getPlayer().getInventory().clear();
        removeScoreboard(event.getPlayer());
    }

    // Player Inventory Click Event
    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        if(e.getWhoClicked().isOp()) return;
        e.setCancelled(true);
    }

    // Player Damage Event
    @EventHandler
    public void onDamage(EntityDamageEvent e) {
        e.setCancelled(true);
    }

    // Player Hunger Event
    @EventHandler
    public void onHunger(FoodLevelChangeEvent e) {
        e.setCancelled(true);
    }

    // Player Right Click Event
    @EventHandler
    public void onRightClick(PlayerInteractEvent e) {
        if(!e.getPlayer().isOp()) {
            e.setCancelled(true);
        }
        if(e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if(!e.getPlayer().getInventory().getItemInMainHand().hasItemMeta()) return;
            if(e.getPlayer().getInventory().getItemInMainHand().equals(compass)) {
                SelectorGUI.showSelector(e.getPlayer());
            }
        }
    }

    // Player Drop Event
    @EventHandler
    public void onDrop(PlayerDropItemEvent e) {
        if(e.getPlayer().isOp()) return;
        e.setCancelled(true);
    }

    // Block Break Event
    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        if(e.getPlayer().isOp()) return;
        e.setCancelled(true);
    }

    // Block Place Event
    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e) {
        if(e.getPlayer().isOp()) return;
        e.setCancelled(true);
    }

    // Player Slime Block Boost
    @EventHandler
    public void SlimeJump(PlayerMoveEvent e) {

        Player p = e.getPlayer();

        if(e.getTo().getBlockX() != e.getFrom().getBlockX() || e.getTo().getBlockZ() != e.getFrom().getBlockZ()) {

            Location blockBelow = new Location(p.getWorld(), e.getTo().getX(), e.getTo().getY() - 1, e.getTo().getZ());
            if(blockBelow.getBlock().getType() != Material.GREEN_CONCRETE) return;
            Location blockBelowSlime = new Location(p.getWorld(), e.getTo().getX(), e.getTo().getY() - 2, e.getTo().getZ());


//            if(blockBelowSlime.getBlock().getType() == Material.GRAY_CONCRETE) {
                Location velocityThingy = new Location(p.getWorld(),
                        p.getLocation().getX(),
                        p.getLocation().getY(),
                        p.getLocation().getZ(),
                        0,
                        -35);
                p.setVelocity(velocityThingy.getDirection().multiply(2.5));

//            }

        }
    }

}
