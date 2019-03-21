package de.godtitan.hideandseek.manager;

import cloud.timo.TimoCloud.api.TimoCloudAPI;
import de.godtitan.hideandseek.HideAndSeek;
import de.godtitan.hideandseek.Messages;
import de.godtitan.hideandseek.game.GameStates;
import de.godtitan.hideandseek.game.PhaseManager;
import de.godtitan.hideandseek.util.Title;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**********************************************************************************
 *  Uhreberrechtshinweis                                                          *
 *  Copyright © Jason H. 2019                                                  *
 *  Erstellt: 19.02.2019 / 00:03                                                   *
 *                                                                                *
 *  Alle Inhalte dieses Quelltextes sind urheberrechtlich geschützt.              *
 *  Das Urheberrecht liegt, soweit nicht ausdrücklich anders gekennzeichnet,      *
 *  bei Jason H. Alle Rechte vorbehalten.                                         *
 *                                                                                *
 *  Jede Art der Vervielfältigung, Verbreitung, Vermietung, Verleihung,           *
 *  öffentlich Zugänglichmachung oder anderer Nutzung,                            *
 *  bedarf der ausdrücklichen, schriftlichen Zustimmung von Jason H.              *
 *********************************************************************************/

public class GameTimeManager {

    public static int GAME_TIME_TASK;
    public static int GAME_TIME_COUNTDOWN;

    private static ArrayList<Integer> currentTime = new ArrayList<>();

    public static void startGameTime() {
        GAME_TIME_COUNTDOWN = 0;
        currentTime.clear();
        currentTime.add(0);

        GAME_TIME_TASK = Bukkit.getScheduler().scheduleSyncRepeatingTask(HideAndSeek.getInstance(), new BukkitRunnable() {
            @Override
            public void run() {
                if (GAME_TIME_COUNTDOWN != 480) {
                    System.out.println(getRemainingTime());

                    GAME_TIME_COUNTDOWN++;
                    currentTime.clear();
                    currentTime.add(GAME_TIME_COUNTDOWN);

                } else /* IF COUNTDOWN == 0 */ {
                    Bukkit.getScheduler().cancelTask(GAME_TIME_TASK);
                    Bukkit.broadcastMessage(Messages.PREFIX + " §7Die §eVerstecker §7haben das Spiel §agewonnen§7, da die Sucher es nicht geschafft haben, alle Verstecker zu finden!");
                    for (Player all : Bukkit.getOnlinePlayers()) {
                        if (TeamManager.TEAM_HIDER.contains(all)) {
                            HideAndSeek.getInstance().getStatsTable().getStats(all.getUniqueId(), stats -> {
                                stats.setWins(stats.getWins() + 1);
                                HideAndSeek.getInstance().getStatsTable().setStats(all.getUniqueId(), stats);
                            });
                            Title.sendTitle(all, "§aGewonnen", "§7Die Verstecker haben das Spiel gewonnen", 20, 20 * 2, 20);
                        } else {
                            HideAndSeek.getInstance().getStatsTable().getStats(all.getUniqueId(), stats -> {
                                stats.setDeaths(stats.getDeaths() + 1);
                                HideAndSeek.getInstance().getStatsTable().setStats(all.getUniqueId(), stats);
                            });
                            Title.sendTitle(all, "§cVerloren", "§7Die Verstecker haben das Spiel gewonnen", 20, 20 * 2, 20);
                        }
                    }
                    HideAndSeek.getInstance().getGameStates = GameStates.RESTARTING;
                    TimoCloudAPI.getBukkitAPI().getThisServer().setState("Restarting");

                    for (Player all : Bukkit.getOnlinePlayers()) {
                        if (HideAndSeek.getInstance().getLocationManager.exists("LOBBY")) {
                            all.teleport(HideAndSeek.getInstance().getLocationManager.getLocation("LOBBY"));
                        } else {
                            all.sendMessage(Messages.LOCATION_NOT_EXISTS);
                        }
                    }
                    new PhaseManager().stopGameCountdown();
                }
            }
        }, 20L, 20L);

    }

    public static String getRemainingTime() {
        long seconds = 60 * 8 * 1000 - currentTime.get(0) * 1000;

        String time = new SimpleDateFormat("mm:ss").format(new Date(seconds));
        return time;
    }
}
