package de.godtitan.hideandseek.command;

import de.godtitan.hideandseek.Data;
import de.godtitan.hideandseek.HideAndSeek;
import de.godtitan.hideandseek.Messages;
import de.godtitan.hideandseek.Permissions;
import de.godtitan.hideandseek.game.GameStates;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**********************************************************************************
 *  Uhreberrechtshinweis                                                          *
 *  Copyright © Jason H. 2019                                                  *
 *  Erstellt: 18.02.2019 / 22:05                                                   *
 *                                                                                *
 *  Alle Inhalte dieses Quelltextes sind urheberrechtlich geschützt.              *
 *  Das Urheberrecht liegt, soweit nicht ausdrücklich anders gekennzeichnet,      *
 *  bei Jason H. Alle Rechte vorbehalten.                                         *
 *                                                                                *
 *  Jede Art der Vervielfältigung, Verbreitung, Vermietung, Verleihung,           *
 *  öffentlich Zugänglichmachung oder anderer Nutzung,                            *
 *  bedarf der ausdrücklichen, schriftlichen Zustimmung von Jason H.              *
 *********************************************************************************/

public class startCommand implements CommandExecutor {

    public startCommand(HideAndSeek plugin) {
        plugin.getCommand("start").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage(Messages.ONLY_PLAYERS);
            return true;
        }
        final Player player = (Player) commandSender;

        if (player.hasPermission(Permissions.COMMAND_START)) {
            if (HideAndSeek.getInstance().getGameStates == GameStates.LOBBY) {
                if (Bukkit.getOnlinePlayers().size() >= Data.MIN_PLAYERS) {
                    if (Data.GAME_STARTED == false) {
                        Data.GAME_STARTED = true;
                        player.sendMessage(Messages.PREFIX + " §7Du hast das Spiel §agestartet§7!");
                        player.playSound(player.getLocation(), Sound.SUCCESSFUL_HIT, 1F, 1F);

                    } else {
                        player.sendMessage(Messages.GAME_ALREADY_STARTED);
                    }
                } else {
                    player.sendMessage(Messages.NOT_ENOUGH_PLAYER_ONLINE);
                    player.playSound(player.getLocation(), Sound.NOTE_BASS, 1F, 1F);
                }
            } else {
                player.sendMessage(Messages.GAME_ALREADY_STARTED);
            }
        } else {
            player.sendMessage(Messages.NO_PERMISSIONS);
        }
        return false;
    }
}
