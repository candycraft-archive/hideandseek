package de.godtitan.hideandseek.listener;

import de.godtitan.hideandseek.HideAndSeek;
import de.godtitan.hideandseek.inventory.BlockChangeInventory;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

/**********************************************************************************
 *  Uhreberrechtshinweis                                                          *
 *  Copyright © Jason H. 2019                                                  *
 *  Erstellt: 18.02.2019 / 22:48                                                   *
 *                                                                                *
 *  Alle Inhalte dieses Quelltextes sind urheberrechtlich geschützt.              *
 *  Das Urheberrecht liegt, soweit nicht ausdrücklich anders gekennzeichnet,      *
 *  bei Jason H. Alle Rechte vorbehalten.                                         *
 *                                                                                *
 *  Jede Art der Vervielfältigung, Verbreitung, Vermietung, Verleihung,           *
 *  öffentlich Zugänglichmachung oder anderer Nutzung,                            *
 *  bedarf der ausdrücklichen, schriftlichen Zustimmung von Jason H.              *
 *********************************************************************************/

public class PlayerInteractListener implements Listener {

    public PlayerInteractListener(HideAndSeek plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        final Player player = event.getPlayer();

        if (event.getAction() == Action.RIGHT_CLICK_BLOCK || event.getAction() == Action.RIGHT_CLICK_AIR) {
            if ((event.getItem() != null) && (event.getItem().getType() != null)) {
                if (event.getItem().getItemMeta().hasDisplayName()) {
                    if ((event.getItem().getType() == Material.FEATHER) && (event.getItem().getItemMeta().getDisplayName().equalsIgnoreCase("§aSpiel starten §7§o<Rechtsklick>"))) {
                        player.performCommand("start");

                    } else if ((event.getItem().getType() == Material.SLIME_BALL) && (event.getItem().getItemMeta().getDisplayName().equalsIgnoreCase("§eZur Lobby §7§o<Rechtsklick>"))) {
                        player.kickPlayer(null);

                    } else if ((event.getItem().getType() == Material.BOOK) && (event.getItem().getItemMeta().getDisplayName().equalsIgnoreCase("§aTarnung auswählen §7§o<Rechtsklick>"))) {
                        BlockChangeInventory.show(player);

                    }
                }
            }
        }
    }
}
