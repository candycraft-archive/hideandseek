package de.godtitan.hideandseek.manager;

import de.godtitan.hideandseek.Permissions;
import de.godtitan.hideandseek.util.ItemBuilder;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**********************************************************************************
 *  Uhreberrechtshinweis                                                          *
 *  Copyright © Jason H. 2019                                                  *
 *  Erstellt: 17.02.2019 / 14:30                                                   *
 *                                                                                *
 *  Alle Inhalte dieses Quelltextes sind urheberrechtlich geschützt.              *
 *  Das Urheberrecht liegt, soweit nicht ausdrücklich anders gekennzeichnet,      *
 *  bei Jason H. Alle Rechte vorbehalten.                                         *
 *                                                                                *
 *  Jede Art der Vervielfältigung, Verbreitung, Vermietung, Verleihung,           *
 *  öffentlich Zugänglichmachung oder anderer Nutzung,                            *
 *  bedarf der ausdrücklichen, schriftlichen Zustimmung von Jason H.              *
 *********************************************************************************/

public class ItemManager {

    // LOBBY-ITEMS
    public static final ItemStack START_GAME = new ItemBuilder(Material.FEATHER).setDisplayName("§aSpiel starten §7§o<Rechtsklick>").build();
    public static final ItemStack LEAVE = new ItemBuilder(Material.SLIME_BALL).setDisplayName("§eZur Lobby §7§o<Rechtsklick>").build();

    // INGAME-ITEMS
    public static final ItemStack SWORD = new ItemBuilder(Material.IRON_SWORD).setDisplayName("§eSchwert").setUnbreakble().build();

    public static final ItemStack BLOCK_CHANGE = new ItemBuilder(Material.BOOK).setDisplayName("§aTarnung auswählen §7§o<Rechtsklick>").build();

    public static void giveLobbyItems(Player player) {
        clearPlayer(player, GameMode.ADVENTURE);

        player.getInventory().setHeldItemSlot(0);
        if (player.hasPermission(Permissions.COMMAND_START)) {
            player.getInventory().setItem(2, START_GAME);
            player.getInventory().setItem(6, LEAVE);

        } else {
            player.getInventory().setItem(4, LEAVE);
        }
        player.updateInventory();
    }

    public static void giveGameItems(Player player, boolean seeker) {
        if (seeker == true) {
            clearPlayer(player, GameMode.ADVENTURE);

            player.getInventory().setHeldItemSlot(0);
            player.getInventory().setItem(0, SWORD);
            player.updateInventory();

        } else {
            clearPlayer(player, GameMode.ADVENTURE);

            player.getInventory().setItem(4, BLOCK_CHANGE);
            player.updateInventory();
        }
    }

    public static void giveSpectatorItems(Player player) {
        clearPlayer(player, GameMode.ADVENTURE);
    }

    public static void clearPlayer(Player player, GameMode gameMode) {
        player.setGameMode(gameMode);
        player.setBedSpawnLocation(null);
        player.setMaxHealth(20.0D);
        player.setHealth(20.0D);
        player.setFoodLevel(20);
        player.setAllowFlight(false);
        player.setExp(0.0F);
        player.setLevel(0);
        player.setFireTicks(0);
        player.getInventory().clear();
        player.getInventory().setArmorContents(null);
        player.updateInventory();
    }
}
