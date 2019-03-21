package de.godtitan.hideandseek.listener;

import de.godtitan.hideandseek.HideAndSeek;
import de.godtitan.hideandseek.Messages;
import de.godtitan.hideandseek.game.GameStates;
import de.godtitan.hideandseek.game.PhaseManager;
import de.godtitan.hideandseek.manager.GameTimeManager;
import de.godtitan.hideandseek.manager.TeamManager;
import de.godtitan.hideandseek.util.Title;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

/**********************************************************************************
 *  Uhreberrechtshinweis                                                          *
 *  Copyright © Jason H. 2019                                                  *
 *  Erstellt: 17.02.2019 / 23:16                                                   *
 *                                                                                *
 *  Alle Inhalte dieses Quelltextes sind urheberrechtlich geschützt.              *
 *  Das Urheberrecht liegt, soweit nicht ausdrücklich anders gekennzeichnet,      *
 *  bei Jason H. Alle Rechte vorbehalten.                                         *
 *                                                                                *
 *  Jede Art der Vervielfältigung, Verbreitung, Vermietung, Verleihung,           *
 *  öffentlich Zugänglichmachung oder anderer Nutzung,                            *
 *  bedarf der ausdrücklichen, schriftlichen Zustimmung von Jason H.              *
 *********************************************************************************/

public class PlayerQuitListener implements Listener {

    public PlayerQuitListener(HideAndSeek plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        final Player player = event.getPlayer();

        if (HideAndSeek.getInstance().getGameStates == GameStates.LOBBY) {
            event.setQuitMessage(Messages.QUIT_MESSAGE(player));

        } else if (HideAndSeek.getInstance().getGameStates == GameStates.INGAME) {
            event.setQuitMessage(Messages.PREFIX + " §e" + player.getName() + " §7hat das Spiel verlassen!");
            if (TeamManager.TEAM_SEEKER.contains(player)) {
                TeamManager.TEAM_SEEKER.remove(player);

            } else TeamManager.TEAM_HIDER.remove(player);

            if (TeamManager.TEAM_SEEKER.size() == 0) {
                Bukkit.broadcastMessage(Messages.PREFIX + " §7Die §eVerstecker §7haben das Spiel §agewonnen§7!");
                for (Player all : Bukkit.getOnlinePlayers()) {
                    if (TeamManager.TEAM_HIDER.contains(all)) {
                        HideAndSeek.getInstance().getStatsTable().getStats(all.getUniqueId(), stats -> {
                            stats.setWins(stats.getWins() + 1);
                            HideAndSeek.getInstance().getStatsTable().setStats(all.getUniqueId(), stats);
                        });
                        Title.sendTitle(all, "§aGewonnen", "§7Die §eVerstecker §7haben das Spiel gewonnen", 20, 20 * 2, 20);
                    } else {
                        HideAndSeek.getInstance().getStatsTable().getStats(all.getUniqueId(), stats -> {
                            stats.setLooses(stats.getLooses() + 1);
                            HideAndSeek.getInstance().getStatsTable().setStats(all.getUniqueId(), stats);
                        });
                        Title.sendTitle(all, "§cVerloren", "§7Die §eVerstecker §7haben das Spiel gewonnen", 20, 20 * 2, 20);
                    }
                }
                HideAndSeek.getInstance().getGameStates = GameStates.RESTARTING;
                //TimoCloudAPI.getBukkitAPI().getThisServer().setState("Restarting");

                for (Player all : Bukkit.getOnlinePlayers()) {
                    if (HideAndSeek.getInstance().getLocationManager.exists("LOBBY")) {
                        all.teleport(HideAndSeek.getInstance().getLocationManager.getLocation("LOBBY"));
                    } else {
                        all.sendMessage(Messages.LOCATION_NOT_EXISTS);
                    }
                }
                Bukkit.getScheduler().cancelTask(GameTimeManager.GAME_TIME_TASK);
                new PhaseManager().stopGameCountdown();

            } else if (TeamManager.TEAM_HIDER.size() == 0) {
                Bukkit.broadcastMessage(Messages.PREFIX + " §7Die §eSucher §7haben das Spiel §agewonnen§7!");
                for (Player all : Bukkit.getOnlinePlayers()) {
                    if (TeamManager.TEAM_SEEKER.contains(all)) {
                        HideAndSeek.getInstance().getStatsTable().getStats(all.getUniqueId(), stats -> {
                            stats.setWins(stats.getWins() + 1);
                            HideAndSeek.getInstance().getStatsTable().setStats(all.getUniqueId(), stats);
                        });
                        Title.sendTitle(all, "§aGewonnen", "§7Die Sucher haben das Spiel gewonnen", 20, 20 * 2, 20);
                    } else {
                        HideAndSeek.getInstance().getStatsTable().getStats(all.getUniqueId(), stats -> {
                            stats.setLooses(stats.getLooses() + 1);
                            HideAndSeek.getInstance().getStatsTable().setStats(all.getUniqueId(), stats);
                        });
                        Title.sendTitle(all, "§cVerloren", "§7Die Sucher haben das Spiel gewonnen", 20, 20 * 2, 20);
                    }
                }
                HideAndSeek.getInstance().getGameStates = GameStates.RESTARTING;
                //TimoCloudAPI.getBukkitAPI().getThisServer().setState("Restarting");

                for (Player all : Bukkit.getOnlinePlayers()) {
                    if (HideAndSeek.getInstance().getLocationManager.exists("LOBBY")) {
                        all.teleport(HideAndSeek.getInstance().getLocationManager.getLocation("LOBBY"));
                    } else {
                        all.sendMessage(Messages.LOCATION_NOT_EXISTS);
                    }
                }
                new PhaseManager().stopGameCountdown();
            }

        } else {
            event.setQuitMessage(null);
        }
    }
}
