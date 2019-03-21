package de.godtitan.hideandseek;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

/**********************************************************************************
 *  Uhreberrechtshinweis                                                          *
 *  Copyright © Jason H. 2019                                                  *
 *  Erstellt: 17.02.2019 / 11:54                                                   *
 *                                                                                *
 *  Alle Inhalte dieses Quelltextes sind urheberrechtlich geschützt.              *
 *  Das Urheberrecht liegt, soweit nicht ausdrücklich anders gekennzeichnet,      *
 *  bei Jason H. Alle Rechte vorbehalten.                                         *
 *                                                                                *
 *  Jede Art der Vervielfältigung, Verbreitung, Vermietung, Verleihung,           *
 *  öffentlich Zugänglichmachung oder anderer Nutzung,                            *
 *  bedarf der ausdrücklichen, schriftlichen Zustimmung von Jason H.              *
 *********************************************************************************/

public class Messages {

    public static String PREFIX = "§f[§3HideAndSeek§f]";

    public static String NO_PERMISSIONS = PREFIX + " §7Dafür hast du §ckeine §7Rechte!";
    public static String PLAYER_NOT_ONLINE = PREFIX + " §7Der Spieler ist §cnicht §7online!";
    public static String ONLY_PLAYERS = PREFIX + " §7Dafür musst du ein Spieler sein!";
    public static String NOT_ENOUGH_PLAYERS = PREFIX + " §7Der Countdown wurde §cabgebrochen§7, da nicht genügend Spieler online sind.";
    public static String COUNTDOWN_STARTED = PREFIX + " §7Der Countdown wurde §agestartet§7!";
    public static String GAME_STARTED = PREFIX + " §7Das Spiel §astartet §7jetzt!";
    public static String LOCATION_NOT_EXISTS = PREFIX + " §7Diese Location exestiert §cnicht§7!";
    public static String GAME_ALREADY_STARTED = PREFIX + " §7Das Spiel ist bereits gestartet!";
    public static String NOT_ENOUGH_PLAYER_ONLINE = PREFIX + " §7Es sind §cnicht §7genug Spieler vorhanden, um das Spiel zu starten!";
    public static String JOINED_BUILDMODE = PREFIX + " §7Du kannst nun bauen!";
    public static String LEAVED_BUILDMODE = PREFIX + " §7Du kannst nun §cnicht §7mehr bauen!";

    public static String SWITCHED_DISGUISE(String string) {
        return PREFIX + " §7Du bist nun als §e" + string + " §7getarnt!";
    }

    public static String SEND_USAGE(String command) {
        return PREFIX + " §7Bitte nutze: §e/" + command;
    }

    public static String LOCATION_SET(String locationName) {
        return PREFIX + " §7Du hast die Location §e" + locationName + " §7gesetzt!";
    }

    public static String JOIN_MESSAGE(Player player) {
        return PREFIX + " §e" + player.getName() + " §7ist dem Spiel §abeigetreten§7! §8[§e" + Bukkit.getOnlinePlayers().size() + "§8/§e" + Data.MAX_PLAYERS + "§8]";
    }

    public static String QUIT_MESSAGE(Player player) {
        return PREFIX + " §e" + player.getName() + " §7hat das Spiel §cverlassen§7! §8[§e" + Bukkit.getOnlinePlayers().size() + "§8/§e" + Data.MAX_PLAYERS + "§8]";
    }

}
