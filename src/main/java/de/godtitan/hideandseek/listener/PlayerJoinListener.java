package de.godtitan.hideandseek.listener;

import de.godtitan.hideandseek.Data;
import de.godtitan.hideandseek.HideAndSeek;
import de.godtitan.hideandseek.Messages;
import de.godtitan.hideandseek.game.GameStates;
import de.godtitan.hideandseek.game.PhaseManager;
import de.godtitan.hideandseek.manager.ItemManager;
import de.godtitan.hideandseek.mysql.table.StatsTable;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

/**********************************************************************************
 *  Uhreberrechtshinweis                                                          *
 *  Copyright © Jason H. 2019                                                  *
 *  Erstellt: 17.02.2019 / 22:33                                                   *
 *                                                                                *
 *  Alle Inhalte dieses Quelltextes sind urheberrechtlich geschützt.              *
 *  Das Urheberrecht liegt, soweit nicht ausdrücklich anders gekennzeichnet,      *
 *  bei Jason H. Alle Rechte vorbehalten.                                         *
 *                                                                                *
 *  Jede Art der Vervielfältigung, Verbreitung, Vermietung, Verleihung,           *
 *  öffentlich Zugänglichmachung oder anderer Nutzung,                            *
 *  bedarf der ausdrücklichen, schriftlichen Zustimmung von Jason H.              *
 *********************************************************************************/

public class PlayerJoinListener implements Listener {

    public PlayerJoinListener(HideAndSeek plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        final Player player = event.getPlayer();

        HideAndSeek.getInstance().getStatsTable().getStats(player.getUniqueId(), stats -> {
            if (stats == null) {
                HideAndSeek.getInstance().getStatsTable().setStats(player.getUniqueId(), new StatsTable.Stats(player.getUniqueId(), 0, 0, 0, 0, 0));
            }
        });

        if (HideAndSeek.getInstance().getGameStates == GameStates.LOBBY) {
            event.setJoinMessage(Messages.JOIN_MESSAGE(player));
            ItemManager.giveLobbyItems(player);

            if (HideAndSeek.getInstance().getLocationManager.exists("LOBBY")) {
                player.teleport(HideAndSeek.getInstance().getLocationManager.getLocation("LOBBY"));

            } else {
                player.sendMessage(Messages.LOCATION_NOT_EXISTS);
            }

            Data.BUILDMODE.remove(player.getUniqueId());

            if (Bukkit.getOnlinePlayers().size() == Data.MIN_PLAYERS) {
                new PhaseManager().startGameCountdown();
            }


        } else if (HideAndSeek.getInstance().getGameStates == GameStates.INGAME) {
            event.setJoinMessage(null);

            ItemManager.giveSpectatorItems(player);
            for (Player all : Bukkit.getOnlinePlayers()) {
                all.hidePlayer(player);
            }
            player.setCompassTarget(HideAndSeek.getInstance().getLocationManager.getLocation("INGAME"));
            player.setAllowFlight(true);
            player.setFlying(true);


        } else {
            event.setJoinMessage(null);
        }
    }
}
