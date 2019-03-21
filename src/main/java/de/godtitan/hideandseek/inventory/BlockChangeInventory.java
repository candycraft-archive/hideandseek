package de.godtitan.hideandseek.inventory;

import de.godtitan.hideandseek.HideAndSeek;
import de.godtitan.hideandseek.Messages;
import de.godtitan.hideandseek.game.GameStates;
import de.godtitan.hideandseek.manager.BlockChangeManager;
import de.godtitan.hideandseek.manager.DisguiseManager;
import de.godtitan.hideandseek.manager.TeamManager;
import de.godtitan.hideandseek.util.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

/**********************************************************************************
 *  Uhreberrechtshinweis                                                          *
 *  Copyright © Jason H. 2019                                                  *
 *  Erstellt: 03.03.2019 / 20:30                                                   *
 *                                                                                *
 *  Alle Inhalte dieses Quelltextes sind urheberrechtlich geschützt.              *
 *  Das Urheberrecht liegt, soweit nicht ausdrücklich anders gekennzeichnet,      *
 *  bei Jason H. Alle Rechte vorbehalten.                                         *
 *                                                                                *
 *  Jede Art der Vervielfältigung, Verbreitung, Vermietung, Verleihung,           *
 *  öffentlich Zugänglichmachung oder anderer Nutzung,                            *
 *  bedarf der ausdrücklichen, schriftlichen Zustimmung von Jason H.              *
 *********************************************************************************/

public class BlockChangeInventory implements Listener {

    private static final String INVENTORY_TITLE = "§cBlock auswählen...";

    public BlockChangeInventory(HideAndSeek plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    public static void show(Player player) {
        Inventory inventory = Bukkit.createInventory(null, 9 * 3, INVENTORY_TITLE);

        ArrayList<String> lore = new ArrayList<>();
        lore.add("§a");
        lore.add("§a§lAUSGEWÄHLT");
        lore.add("§b");

        for (int i = 0; i < inventory.getSize(); i++) {
            inventory.setItem(i, new ItemBuilder(Material.STAINED_GLASS_PANE, (short) 15).setDisplayName(" ").build());
        }

        if (BlockChangeManager.BLOCK_WORKBENCH.contains(player.getUniqueId())) {
            inventory.setItem(10, new ItemBuilder(Material.WORKBENCH).setDisplayName("§8» §eWerkbank").setLore(lore).addEnchantment(Enchantment.ARROW_INFINITE, 10).addItemFlag(ItemFlag.HIDE_ENCHANTS).build());
        } else {
            inventory.setItem(10, new ItemBuilder(Material.WORKBENCH).setDisplayName("§8» §eWerkbank").build());
        }

        if (BlockChangeManager.BLOCK_ANVIL.contains(player.getUniqueId())) {
            inventory.setItem(12, new ItemBuilder(Material.ANVIL).setDisplayName("§8» §eAmboss").setLore(lore).addEnchantment(Enchantment.ARROW_INFINITE, 10).addItemFlag(ItemFlag.HIDE_ENCHANTS).build());
        } else {
            inventory.setItem(12, new ItemBuilder(Material.ANVIL).setDisplayName("§8» §eAmboss").build());
        }

        if (BlockChangeManager.BLOCK_CHEST.contains(player.getUniqueId())) {
            inventory.setItem(14, new ItemBuilder(Material.LEAVES).setDisplayName("§8» §eBlätter").setLore(lore).addEnchantment(Enchantment.ARROW_INFINITE, 10).addItemFlag(ItemFlag.HIDE_ENCHANTS).build());
        } else {
            inventory.setItem(14, new ItemBuilder(Material.LEAVES).setDisplayName("§8» §eBlätter").build());
        }

        if (BlockChangeManager.BLOCK_WOOD.contains(player.getUniqueId())) {
            inventory.setItem(16, new ItemBuilder(Material.WOOD).setDisplayName("§8» §eHolz").setLore(lore).addEnchantment(Enchantment.ARROW_INFINITE, 10).addItemFlag(ItemFlag.HIDE_ENCHANTS).build());
        } else {
            inventory.setItem(16, new ItemBuilder(Material.WOOD).setDisplayName("§8» §eHolz").build());
        }

        player.closeInventory();
        player.playSound(player.getLocation(), Sound.ITEM_PICKUP, 1F, 1F);
        player.openInventory(inventory);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        final Player player = (Player) event.getWhoClicked();

        final Inventory inventory = event.getClickedInventory();
        final ItemStack currentItem = event.getCurrentItem();

        if (inventory == null || inventory.getType() == null || !inventory.getTitle().equals(INVENTORY_TITLE) || HideAndSeek.getInstance().getGameStates != GameStates.INGAME
                || !TeamManager.TEAM_HIDER.contains(player)) {
            return;
        } else {
            event.setCancelled(true);
        }

        if (currentItem != null) {
            if (currentItem.getType() == Material.WORKBENCH) {
                if (BlockChangeManager.BLOCK_WORKBENCH.contains(player.getUniqueId())) {
                    player.closeInventory();
                    player.playSound(player.getLocation(), Sound.NOTE_BASS, 1F, 1F);
                } else {

                    if (BlockChangeManager.BLOCK_CHEST.contains(player.getUniqueId())) {
                        BlockChangeManager.BLOCK_CHEST.remove(player.getUniqueId());
                    } else if (BlockChangeManager.BLOCK_WOOD.contains(player.getUniqueId())) {
                        BlockChangeManager.BLOCK_WOOD.remove(player.getUniqueId());
                    } else BlockChangeManager.BLOCK_ANVIL.remove(player.getUniqueId());

                    BlockChangeManager.BLOCK_WORKBENCH.add(player.getUniqueId());
                    player.closeInventory();
                    player.playSound(player.getLocation(), Sound.NOTE_PLING, 1F, 1F);
                    player.sendMessage(Messages.SWITCHED_DISGUISE("Werkbank"));
                }

            } else if (currentItem.getType() == Material.ANVIL) {
                if (BlockChangeManager.BLOCK_ANVIL.contains(player.getUniqueId())) {
                    player.closeInventory();
                    player.playSound(player.getLocation(), Sound.NOTE_BASS, 1F, 1F);

                } else {

                    if (BlockChangeManager.BLOCK_CHEST.contains(player.getUniqueId())) {
                        BlockChangeManager.BLOCK_CHEST.remove(player.getUniqueId());
                    } else if (BlockChangeManager.BLOCK_WOOD.contains(player.getUniqueId())) {
                        BlockChangeManager.BLOCK_WOOD.remove(player.getUniqueId());
                    } else BlockChangeManager.BLOCK_WORKBENCH.remove(player.getUniqueId());

                    BlockChangeManager.BLOCK_ANVIL.add(player.getUniqueId());
                    player.closeInventory();
                    player.playSound(player.getLocation(), Sound.NOTE_PLING, 1F, 1F);
                    player.sendMessage(Messages.SWITCHED_DISGUISE("Amboss"));
                }

            } else if (currentItem.getType() == Material.WOOD) {
                if (BlockChangeManager.BLOCK_WOOD.contains(player.getUniqueId())) {
                    player.closeInventory();
                    player.playSound(player.getLocation(), Sound.NOTE_BASS, 1F, 1F);

                } else {

                    if (BlockChangeManager.BLOCK_CHEST.contains(player.getUniqueId())) {
                        BlockChangeManager.BLOCK_CHEST.remove(player.getUniqueId());
                    } else if (BlockChangeManager.BLOCK_ANVIL.contains(player.getUniqueId())) {
                        BlockChangeManager.BLOCK_ANVIL.remove(player.getUniqueId());
                    } else BlockChangeManager.BLOCK_WORKBENCH.remove(player.getUniqueId());

                    BlockChangeManager.BLOCK_WOOD.add(player.getUniqueId());
                    player.closeInventory();
                    player.playSound(player.getLocation(), Sound.NOTE_PLING, 1F, 1F);
                    player.sendMessage(Messages.SWITCHED_DISGUISE("Holzblock"));
                }

            } else if (currentItem.getType() == Material.LEAVES) {
                if (BlockChangeManager.BLOCK_CHEST.contains(player.getUniqueId())) {
                    player.closeInventory();
                    player.playSound(player.getLocation(), Sound.NOTE_BASS, 1F, 1F);

                } else {

                    if (BlockChangeManager.BLOCK_WOOD.contains(player.getUniqueId())) {
                        BlockChangeManager.BLOCK_WOOD.remove(player.getUniqueId());
                    } else if (BlockChangeManager.BLOCK_ANVIL.contains(player.getUniqueId())) {
                        BlockChangeManager.BLOCK_ANVIL.remove(player.getUniqueId());
                    } else BlockChangeManager.BLOCK_WORKBENCH.remove(player.getUniqueId());

                    BlockChangeManager.BLOCK_CHEST.add(player.getUniqueId());
                    player.closeInventory();
                    player.playSound(player.getLocation(), Sound.NOTE_PLING, 1F, 1F);
                    player.sendMessage(Messages.SWITCHED_DISGUISE("Blätter"));
                }
            }
            DisguiseManager
        }
    }
}
