package de.godtitan.hideandseek.command;

import de.godtitan.hideandseek.HideAndSeek;
import de.godtitan.hideandseek.Messages;
import de.godtitan.hideandseek.Permissions;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**********************************************************************************
 *  Uhreberrechtshinweis                                                          *
 *  Copyright © Jason H. 2019                                                  *
 *  Erstellt: 17.02.2019 / 23:01                                                   *
 *                                                                                *
 *  Alle Inhalte dieses Quelltextes sind urheberrechtlich geschützt.              *
 *  Das Urheberrecht liegt, soweit nicht ausdrücklich anders gekennzeichnet,      *
 *  bei Jason H. Alle Rechte vorbehalten.                                         *
 *                                                                                *
 *  Jede Art der Vervielfältigung, Verbreitung, Vermietung, Verleihung,           *
 *  öffentlich Zugänglichmachung oder anderer Nutzung,                            *
 *  bedarf der ausdrücklichen, schriftlichen Zustimmung von Jason H.              *
 *********************************************************************************/

public class setLocationCommand implements CommandExecutor {

    public setLocationCommand(HideAndSeek plugin) {
        plugin.getCommand("setlocation").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage(Messages.ONLY_PLAYERS);
            return true;
        }
        final Player player = (Player) commandSender;

        if (player.hasPermission(Permissions.COMMAND_SETLOCATION)) {
            if (strings.length == 1) {
                if (strings[0].equalsIgnoreCase("lobby")) {
                    HideAndSeek.getInstance().getLocationManager.setLocation(player.getLocation(), strings[0].toUpperCase());
                    player.sendMessage(Messages.LOCATION_SET(strings[0].toUpperCase()));

                } else if (strings[0].equalsIgnoreCase("ingame")) {
                    HideAndSeek.getInstance().getLocationManager.setLocation(player.getLocation(), strings[0].toUpperCase());
                    player.sendMessage(Messages.LOCATION_SET(strings[0].toUpperCase()));

                } else {
                    player.sendMessage(Messages.LOCATION_NOT_EXISTS);
                }
            } else {
                player.sendMessage(Messages.SEND_USAGE("setlocation <location>"));
            }
        } else {
            player.sendMessage(Messages.NO_PERMISSIONS);
        }
        return false;
    }
}
