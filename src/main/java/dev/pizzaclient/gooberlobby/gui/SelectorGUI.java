package dev.pizzaclient.gooberlobby.gui;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;
import xyz.xenondevs.invui.gui.Gui;
import xyz.xenondevs.invui.item.ItemProvider;
import xyz.xenondevs.invui.item.builder.ItemBuilder;
import xyz.xenondevs.invui.item.impl.AbstractItem;
import xyz.xenondevs.invui.window.Window;

import java.util.Arrays;

import static dev.pizzaclient.gooberlobby.utils.Utils.*;

public class SelectorGUI {

    public static void showSelector(Player player) {

        Gui gui = Gui.normal()
                .setStructure(
                        ". . . . . . . . .",
                        ". . s . a . s . .",
                        ". . . . . . . . .")
                .addIngredient('a', new AbstractItem() {
                        @Override
                        public ItemProvider getItemProvider() {
                            ItemStack itemStack = new ItemStack(Material.SLIME_BALL, 1);
                            ItemMeta itemMeta = itemStack.getItemMeta();
                            itemMeta.setDisplayName(coloredString("&a&lGooberTag"));
                            itemMeta.setLore(Arrays.asList(
                                    coloredString("&fClick to Connect!"),
                                    coloredString(""),
                                    coloredString("&7Online " + tagPlayerCount +"/50" )
                            ));
                            itemStack.setItemMeta(itemMeta);
                            ItemBuilder itemBuilder = new ItemBuilder(itemStack);
                            return itemBuilder;
                        }

                    @Override
                    public void handleClick(@NotNull ClickType clickType, @NotNull Player player, @NotNull InventoryClickEvent event) {
                        sendServer(player, "tag");
                    }
                })
                .addIngredient('s', new AbstractItem() {
                    @Override
                    public ItemProvider getItemProvider() {
                        ItemStack itemStack = new ItemStack(Material.BARRIER, 1);
                        ItemMeta itemMeta = itemStack.getItemMeta();
                        itemMeta.setDisplayName(coloredString("&c&lComing Soon"));
                        itemMeta.setLore(Arrays.asList(
                                coloredString("&fSupport the server to allow us to"),
                                coloredString("&fmake more awesome gamemodes!")
                        ));
                        itemStack.setItemMeta(itemMeta);
                        ItemBuilder itemBuilder = new ItemBuilder(itemStack);
                        return itemBuilder;
                    }

                    @Override
                    public void handleClick(@NotNull ClickType clickType, @NotNull Player player, @NotNull InventoryClickEvent event) {

                    }
                })
                .build();
        Window window = Window.single()
                .setViewer(player)
                .setTitle(coloredString("&a&lS&2&le&a&lr&2&lv&a&le&2&lr &a&lS&2&le&a&ll&2&le&a&lc&2&lt&a&lo&2&lr"))
                .setGui(gui)
                .build();

        window.open();
    }

}
