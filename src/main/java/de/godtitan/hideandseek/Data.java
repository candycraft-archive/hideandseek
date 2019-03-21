package de.godtitan.hideandseek;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Random;
import java.util.UUID;

/**********************************************************************************
 *  Uhreberrechtshinweis                                                          *
 *  Copyright © Jason H. 2019                                                  *
 *  Erstellt: 17.02.2019 / 12:03                                                   *
 *                                                                                *
 *  Alle Inhalte dieses Quelltextes sind urheberrechtlich geschützt.              *
 *  Das Urheberrecht liegt, soweit nicht ausdrücklich anders gekennzeichnet,      *
 *  bei Jason H. Alle Rechte vorbehalten.                                         *
 *                                                                                *
 *  Jede Art der Vervielfältigung, Verbreitung, Vermietung, Verleihung,           *
 *  öffentlich Zugänglichmachung oder anderer Nutzung,                            *
 *  bedarf der ausdrücklichen, schriftlichen Zustimmung von Jason H.              *
 *********************************************************************************/

public class Data {

    public static int MAX_PLAYERS = 10;
    public static int MIN_PLAYERS = 2;

    public static ArrayList<UUID> BUILDMODE = new ArrayList<>();
    public static boolean GAME_STARTED = false;

    public static int getPlayerPing(Player player) {
        return ((CraftPlayer) player).getHandle().ping;
    }

    public static Player getRandomPlayer() {
        int random = new Random().nextInt(Bukkit.getServer().getOnlinePlayers().size());
        Player player = (Player) Bukkit.getServer().getOnlinePlayers().toArray()[random];
        return player;
    }
}
