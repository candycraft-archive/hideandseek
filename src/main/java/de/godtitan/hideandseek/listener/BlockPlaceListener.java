package de.godtitan.hideandseek.listener;

import de.godtitan.hideandseek.Data;
import de.godtitan.hideandseek.HideAndSeek;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

/**********************************************************************************
 *  Uhreberrechtshinweis                                                          *
 *  Copyright © Jason H. 2019                                                  *
 *  Erstellt: 18.02.2019 / 21:20                                                   *
 *                                                                                *
 *  Alle Inhalte dieses Quelltextes sind urheberrechtlich geschützt.              *
 *  Das Urheberrecht liegt, soweit nicht ausdrücklich anders gekennzeichnet,      *
 *  bei Jason H. Alle Rechte vorbehalten.                                         *
 *                                                                                *
 *  Jede Art der Vervielfältigung, Verbreitung, Vermietung, Verleihung,           *
 *  öffentlich Zugänglichmachung oder anderer Nutzung,                            *
 *  bedarf der ausdrücklichen, schriftlichen Zustimmung von Jason H.              *
 *********************************************************************************/

public class BlockPlaceListener implements Listener {

    public BlockPlaceListener(HideAndSeek plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        final Player player = event.getPlayer();

        if (Data.BUILDMODE.contains(player.getUniqueId())) {
            event.setCancelled(false);

        } else {
            event.setCancelled(true);
        }
    }
}
