package de.godtitan.hideandseek.listener;

/**********************************************************************************
 *  Uhreberrechtshinweis                                                          *
 *  Copyright © Jason H. 2019                                                  *
 *  Erstellt: 20.02.2019 / 19:24                                                   *
 *                                                                                *
 *  Alle Inhalte dieses Quelltextes sind urheberrechtlich geschützt.              *
 *  Das Urheberrecht liegt, soweit nicht ausdrücklich anders gekennzeichnet,      *
 *  bei Jason H. Alle Rechte vorbehalten.                                         *
 *                                                                                *
 *  Jede Art der Vervielfältigung, Verbreitung, Vermietung, Verleihung,           *
 *  öffentlich Zugänglichmachung oder anderer Nutzung,                            *
 *  bedarf der ausdrücklichen, schriftlichen Zustimmung von Jason H.              *
 *********************************************************************************/

import de.godtitan.hideandseek.HideAndSeek;
import de.pauhull.nickapi.event.PostPlayerNickEvent;
import de.pauhull.nickapi.event.PostPlayerUnnickEvent;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PlayerNickListener implements Listener {

    public PlayerNickListener(HideAndSeek plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onPostPlayerNick(PostPlayerNickEvent event) {
        HideAndSeek.getInstance().getScoreboardManager().updateTeam(event.getPlayer());
    }

    @EventHandler
    public void onPostPlayerUnnick(PostPlayerUnnickEvent event) {
        HideAndSeek.getInstance().getScoreboardManager().updateTeam(event.getPlayer());
    }

}
