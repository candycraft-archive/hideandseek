package de.godtitan.hideandseek.util;

/**********************************************************************************
 *  Uhreberrechtshinweis                                                          *
 *  Copyright © Jason H. 2019                                                  *
 *  Erstellt: 17.02.2019 / 22:14                                                   *
 *                                                                                *
 *  Alle Inhalte dieses Quelltextes sind urheberrechtlich geschützt.              *
 *  Das Urheberrecht liegt, soweit nicht ausdrücklich anders gekennzeichnet,      *
 *  bei Jason H. Alle Rechte vorbehalten.                                         *
 *                                                                                *
 *  Jede Art der Vervielfältigung, Verbreitung, Vermietung, Verleihung,           *
 *  öffentlich Zugänglichmachung oder anderer Nutzung,                            *
 *  bedarf der ausdrücklichen, schriftlichen Zustimmung von Jason H.              *
 *********************************************************************************/

import de.godtitan.hideandseek.Data;
import de.godtitan.hideandseek.HideAndSeek;
import de.godtitan.hideandseek.game.GameStates;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class ActionBar {

    public static int task;
    public static int number;

    public static void sendActionBar(Player player, String actionBar) {
        IChatBaseComponent actionBarComponent = IChatBaseComponent.ChatSerializer.a("{\"text\":\"" + actionBar + "\"}");
        PacketPlayOutChat packet = new PacketPlayOutChat(actionBarComponent, (byte) 2);
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
    }

    public static void startScheduler() {

        Bukkit.getScheduler().scheduleAsyncRepeatingTask(HideAndSeek.getInstance(), new BukkitRunnable() {
            @Override
            public void run() {

                if (HideAndSeek.getInstance().getGameStates == GameStates.LOBBY) {
                    if (Bukkit.getOnlinePlayers().size() < Data.MIN_PLAYERS) {
                        int i = Data.MIN_PLAYERS - Bukkit.getOnlinePlayers().size();

                        for (Player all : Bukkit.getOnlinePlayers()) {
                            if (i == 1) {
                                sendActionBar(all, "§fEs fehlt noch §cein §fSpieler.");
                            } else {
                                sendActionBar(all, "§fEs fehlen noch §c" + i + " §fSpieler.");
                            }
                        }
                    }
                }
            }
        }, 20L, 20L);
    }
}