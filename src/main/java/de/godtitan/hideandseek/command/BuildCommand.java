package de.godtitan.hideandseek.command;

import de.godtitan.hideandseek.Data;
import de.godtitan.hideandseek.HideAndSeek;
import de.godtitan.hideandseek.Messages;
import de.godtitan.hideandseek.Permissions;
import de.godtitan.hideandseek.manager.TeamManager;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**********************************************************************************
 *  Uhreberrechtshinweis                                                          *
 *  Copyright © Jason H. 2019                                                  *
 *  Erstellt: 17.02.2019 / 12:07                                                   *
 *                                                                                *
 *  Alle Inhalte dieses Quelltextes sind urheberrechtlich geschützt.              *
 *  Das Urheberrecht liegt, soweit nicht ausdrücklich anders gekennzeichnet,      *
 *  bei Jason H. Alle Rechte vorbehalten.                                         *
 *                                                                                *
 *  Jede Art der Vervielfältigung, Verbreitung, Vermietung, Verleihung,           *
 *  öffentlich Zugänglichmachung oder anderer Nutzung,                            *
 *  bedarf der ausdrücklichen, schriftlichen Zustimmung von Jason H.              *
 *********************************************************************************/

public class BuildCommand implements CommandExecutor {

    public BuildCommand(HideAndSeek plugin) {
        plugin.getCommand("build").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage(Messages.ONLY_PLAYERS);
            return true;
        }
        final Player player = (Player) commandSender;

        if (player.hasPermission(Permissions.COMMAND_BUILD)) {
            if (strings.length == 0) {
                if (!(Data.BUILDMODE.contains(player.getUniqueId()))) {
                    Data.BUILDMODE.add(player.getUniqueId());
                    player.sendMessage(Messages.JOINED_BUILDMODE);
                    player.setGameMode(GameMode.CREATIVE);
                    TeamManager.getRandomTeam();

                } else {
                    Data.BUILDMODE.remove(player.getUniqueId());
                    player.sendMessage(Messages.LEAVED_BUILDMODE);
                    player.setGameMode(GameMode.ADVENTURE);
                    TeamManager.getRandomTeam();
                }

            } else if (strings.length == 1) {
                final Player target = Bukkit.getPlayer(strings[0]);

                if (target != null) {

                    if (target.getName() == player.getName()) {
                        if (!(Data.BUILDMODE.contains(player.getUniqueId()))) {
                            Data.BUILDMODE.add(player.getUniqueId());
                            player.sendMessage(Messages.JOINED_BUILDMODE);
                            player.setGameMode(GameMode.CREATIVE);

                        } else {
                            Data.BUILDMODE.remove(player.getUniqueId());
                            player.sendMessage(Messages.LEAVED_BUILDMODE);
                            player.setGameMode(GameMode.ADVENTURE);
                        }
                        return true;
                    }

                    if (!(Data.BUILDMODE.contains(target.getUniqueId()))) {
                        Data.BUILDMODE.add(target.getUniqueId());
                        target.sendMessage(Messages.JOINED_BUILDMODE);
                        target.setGameMode(GameMode.CREATIVE);
                        player.sendMessage("§7Der Spieler kann nun bauen.");

                    } else {
                        Data.BUILDMODE.remove(target.getUniqueId());
                        target.sendMessage(Messages.LEAVED_BUILDMODE);
                        target.setGameMode(GameMode.ADVENTURE);
                        player.sendMessage("§7Der Spieler kann nun nd mehr bauen.");

                    }
                } else {
                    player.sendMessage(Messages.PLAYER_NOT_ONLINE);
                }
            } else {
                player.sendMessage(Messages.SEND_USAGE("build <Spieler>"));
            }
        } else {
            player.sendMessage(Messages.NO_PERMISSIONS);
        }
        return false;
    }
}
