package de.godtitan.hideandseek.listener;

import de.godtitan.hideandseek.Data;
import de.godtitan.hideandseek.HideAndSeek;
import de.godtitan.hideandseek.game.GameStates;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;

/**********************************************************************************
 *  Uhreberrechtshinweis                                                          *
 *  Copyright © Jason H. 2019                                                  *
 *  Erstellt: 20.02.2019 / 19:39                                                   *
 *                                                                                *
 *  Alle Inhalte dieses Quelltextes sind urheberrechtlich geschützt.              *
 *  Das Urheberrecht liegt, soweit nicht ausdrücklich anders gekennzeichnet,      *
 *  bei Jason H. Alle Rechte vorbehalten.                                         *
 *                                                                                *
 *  Jede Art der Vervielfältigung, Verbreitung, Vermietung, Verleihung,           *
 *  öffentlich Zugänglichmachung oder anderer Nutzung,                            *
 *  bedarf der ausdrücklichen, schriftlichen Zustimmung von Jason H.              *
 *********************************************************************************/

public class ServerListPingListener implements Listener {

    public ServerListPingListener(HideAndSeek plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onServerPing(ServerListPingEvent event) {
        if (HideAndSeek.getInstance().getGameStates == GameStates.LOBBY) {
            event.setMaxPlayers(Data.MAX_PLAYERS);

        } else if (HideAndSeek.getInstance().getGameStates == GameStates.INGAME) {
            event.setMaxPlayers(100);

        } else {
            event.setMaxPlayers(Bukkit.getOnlinePlayers().size());
        }
    }
}
