package de.godtitan.hideandseek.game;

import de.godtitan.hideandseek.HideAndSeek;
import de.godtitan.hideandseek.Messages;
import de.godtitan.hideandseek.manager.GameTimeManager;
import de.godtitan.hideandseek.manager.ItemManager;
import de.godtitan.hideandseek.manager.LocationManager;
import de.godtitan.hideandseek.manager.TeamManager;
import de.godtitan.hideandseek.scoreboard.IngameScoreboard;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

/**********************************************************************************
 *  Uhreberrechtshinweis                                                          *
 *  Copyright © Jason H. 2019                                                  *
 *  Erstellt: 17.02.2019 / 22:13                                                   *
 *                                                                                *
 *  Alle Inhalte dieses Quelltextes sind urheberrechtlich geschützt.              *
 *  Das Urheberrecht liegt, soweit nicht ausdrücklich anders gekennzeichnet,      *
 *  bei Jason H. Alle Rechte vorbehalten.                                         *
 *                                                                                *
 *  Jede Art der Vervielfältigung, Verbreitung, Vermietung, Verleihung,           *
 *  öffentlich Zugänglichmachung oder anderer Nutzung,                            *
 *  bedarf der ausdrücklichen, schriftlichen Zustimmung von Jason H.              *
 *********************************************************************************/

public class GameManager {

    public static void startGame() {
        for (Player all : Bukkit.getOnlinePlayers()) {
            TeamManager.NOT_CHOOSED.add(all);
            HideAndSeek.getInstance().getStatsTable().getStats(all.getUniqueId(), stats -> {
                stats.setPlayedGames(stats.getPlayedGames() + 1);
                HideAndSeek.getInstance().getStatsTable().setStats(all.getUniqueId(), stats);
            });
        }
        TeamManager.getRandomTeam();


        for (Player all : Bukkit.getOnlinePlayers()) {
            if (TeamManager.TEAM_HIDER.contains(all)) {
                all.sendMessage(Messages.PREFIX + " §7Du bist ein §eVerstecker§7. §7Du hast §neine Minute§7 Zeit dich vor den Suchern zu verstecken.");
                ItemManager.giveGameItems(all, false);

                if (HideAndSeek.getInstance().getLocationManager.exists("INGAME")) {
                    all.teleport(new LocationManager().getLocation("INGAME"));
                } else {
                    all.sendMessage(Messages.LOCATION_NOT_EXISTS);
                }

            } else {
                ItemManager.clearPlayer(all, GameMode.ADVENTURE);
                all.sendMessage(Messages.PREFIX + " §7Du bist ein §eSucher§7. §7Du musst §nin einer Minute§7 die als Blöcke getarnten Verstecker suchen und töten.");
            }
        }
        TeamManager.NOT_CHOOSED.clear();
        GameTimeManager.startGameTime();
        HideAndSeek.getInstance().getScoreboardManager().setScoreboard(IngameScoreboard.class);
        new PhaseManager().startSeekerCountdown();


    }
}
