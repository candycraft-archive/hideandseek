package de.godtitan.hideandseek.listener;

import de.godtitan.hideandseek.HideAndSeek;
import de.godtitan.hideandseek.game.GameStates;
import de.godtitan.hideandseek.manager.TeamManager;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

/**********************************************************************************
 *  Uhreberrechtshinweis                                                          *
 *  Copyright © Jason H. 2019                                                  *
 *  Erstellt: 18.02.2019 / 17:44                                                   *
 *                                                                                *
 *  Alle Inhalte dieses Quelltextes sind urheberrechtlich geschützt.              *
 *  Das Urheberrecht liegt, soweit nicht ausdrücklich anders gekennzeichnet,      *
 *  bei Jason H. Alle Rechte vorbehalten.                                         *
 *                                                                                *
 *  Jede Art der Vervielfältigung, Verbreitung, Vermietung, Verleihung,           *
 *  öffentlich Zugänglichmachung oder anderer Nutzung,                            *
 *  bedarf der ausdrücklichen, schriftlichen Zustimmung von Jason H.              *
 *********************************************************************************/

public class EntityDamageEntityListener implements Listener {

    public EntityDamageEntityListener(HideAndSeek plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onEntityDamageEntity(EntityDamageByEntityEvent event) {
        if (HideAndSeek.getInstance().getGameStates != GameStates.INGAME) {
            event.setCancelled(true);

        } else {

            if (TeamManager.TEAM_HIDER.contains(event.getEntity()) && (TeamManager.TEAM_HIDER.contains(event.getDamager()))) {
                event.setCancelled(true);

            } else {
                event.setCancelled(false);
            }
            if (TeamManager.TEAM_SEEKER.contains(event.getEntity())) {
                event.setCancelled(true);
            }
            if (TeamManager.TEAM_SPECTATOR.contains(event.getEntity())) {
                event.setCancelled(true);
            }
            if (TeamManager.TEAM_SPECTATOR.contains(event.getDamager())) {
                event.setCancelled(true);
            }
        }
    }
}
