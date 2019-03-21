package de.godtitan.hideandseek.manager;

import de.godtitan.hideandseek.Data;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Random;

/**********************************************************************************
 *  Uhreberrechtshinweis                                                          *
 *  Copyright © Jason H. 2019                                                  *
 *  Erstellt: 17.02.2019 / 23:20                                                   *
 *                                                                                *
 *  Alle Inhalte dieses Quelltextes sind urheberrechtlich geschützt.              *
 *  Das Urheberrecht liegt, soweit nicht ausdrücklich anders gekennzeichnet,      *
 *  bei Jason H. Alle Rechte vorbehalten.                                         *
 *                                                                                *
 *  Jede Art der Vervielfältigung, Verbreitung, Vermietung, Verleihung,           *
 *  öffentlich Zugänglichmachung oder anderer Nutzung,                            *
 *  bedarf der ausdrücklichen, schriftlichen Zustimmung von Jason H.              *
 *********************************************************************************/

public class TeamManager {

    public static ArrayList<Player> NOT_CHOOSED = new ArrayList<>();

    public static ArrayList<Player> TEAM_HIDER = new ArrayList<>();
    public static ArrayList<Player> TEAM_SEEKER = new ArrayList<>();

    public static ArrayList<Player> TEAM_SPECTATOR = new ArrayList<>();

    public static void getRandomTeam() {
        int online = Bukkit.getOnlinePlayers().size();

        TEAM_HIDER.clear();
        TEAM_SEEKER.clear();

        if (online == 2) {
            Player randomPlayer = Data.getRandomPlayer();

            TEAM_SEEKER.add(randomPlayer);
            NOT_CHOOSED.remove(randomPlayer);

            for (Player all : Bukkit.getOnlinePlayers()) {
                if (!(TEAM_SEEKER.contains(all))) {
                    TEAM_HIDER.add(all);
                    NOT_CHOOSED.remove(all);
                }
            }

        } else if (online == 3) {
            Player randomPlayer = Data.getRandomPlayer();

            TEAM_SEEKER.add(randomPlayer);
            NOT_CHOOSED.remove(randomPlayer);

            for (Player all : Bukkit.getOnlinePlayers()) {
                if (!(TEAM_SEEKER.contains(all))) {
                    TEAM_HIDER.add(all);
                    NOT_CHOOSED.remove(all);
                }
            }

        } else if (online == 4) {
            Player randomPlayer = Data.getRandomPlayer();

            TEAM_SEEKER.add(randomPlayer);
            NOT_CHOOSED.remove(randomPlayer);

            int random2 = new Random().nextInt(NOT_CHOOSED.size());
            Player randomPlayer2 = (Player) NOT_CHOOSED.toArray()[random2];
            NOT_CHOOSED.remove(randomPlayer2);

            for (Player all : Bukkit.getOnlinePlayers()) {
                if (!(TEAM_SEEKER.contains(all))) {
                    TEAM_HIDER.add(all);
                    NOT_CHOOSED.remove(all);
                }
            }

        } else if (online == 5) {
            Player randomPlayer = Data.getRandomPlayer();

            TEAM_SEEKER.add(randomPlayer);
            NOT_CHOOSED.remove(randomPlayer);

            int random2 = new Random().nextInt(NOT_CHOOSED.size());
            Player randomPlayer2 = (Player) NOT_CHOOSED.toArray()[random2];
            NOT_CHOOSED.remove(randomPlayer2);

            for (Player all : Bukkit.getOnlinePlayers()) {
                if (!(TEAM_SEEKER.contains(all))) {
                    TEAM_HIDER.add(all);
                    NOT_CHOOSED.remove(all);
                }
            }

        } else if (online == 6) {
            Player randomPlayer = Data.getRandomPlayer();

            TEAM_SEEKER.add(randomPlayer);
            NOT_CHOOSED.remove(randomPlayer);

            int random2 = new Random().nextInt(NOT_CHOOSED.size());
            Player randomPlayer2 = (Player) NOT_CHOOSED.toArray()[random2];
            NOT_CHOOSED.remove(randomPlayer2);

            int random3 = new Random().nextInt(NOT_CHOOSED.size());
            Player randomPlayer3 = (Player) NOT_CHOOSED.toArray()[random3];
            NOT_CHOOSED.remove(randomPlayer3);

            for (Player all : Bukkit.getOnlinePlayers()) {
                if (!(TEAM_SEEKER.contains(all))) {
                    TEAM_HIDER.add(all);
                    NOT_CHOOSED.remove(all);
                }
            }

        } else if (online > 6) {
            Player randomPlayer = Data.getRandomPlayer();

            TEAM_SEEKER.add(randomPlayer);
            NOT_CHOOSED.remove(randomPlayer);

            int random2 = new Random().nextInt(NOT_CHOOSED.size());
            Player randomPlayer2 = (Player) NOT_CHOOSED.toArray()[random2];
            NOT_CHOOSED.remove(randomPlayer2);

            int random3 = new Random().nextInt(NOT_CHOOSED.size());
            Player randomPlayer3 = (Player) NOT_CHOOSED.toArray()[random3];
            NOT_CHOOSED.remove(randomPlayer3);

            for (Player all : Bukkit.getOnlinePlayers()) {
                if (!(TEAM_SEEKER.contains(all))) {
                    TEAM_HIDER.add(all);
                    NOT_CHOOSED.remove(all);
                }
            }
        }
    }
}
