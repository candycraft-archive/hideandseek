package de.godtitan.hideandseek.command;

import de.godtitan.hideandseek.HideAndSeek;
import de.godtitan.hideandseek.Messages;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.text.DecimalFormat;

/**********************************************************************************
 *  Uhreberrechtshinweis                                                          *
 *  Copyright © Jason H. 2019                                                  *
 *  Erstellt: 23.02.2019 / 17:11                                                   *
 *                                                                                *
 *  Alle Inhalte dieses Quelltextes sind urheberrechtlich geschützt.              *
 *  Das Urheberrecht liegt, soweit nicht ausdrücklich anders gekennzeichnet,      *
 *  bei Jason H. Alle Rechte vorbehalten.                                         *
 *                                                                                *
 *  Jede Art der Vervielfältigung, Verbreitung, Vermietung, Verleihung,           *
 *  öffentlich Zugänglichmachung oder anderer Nutzung,                            *
 *  bedarf der ausdrücklichen, schriftlichen Zustimmung von Jason H.              *
 *********************************************************************************/

public class StatsCommand implements CommandExecutor {

    public StatsCommand(HideAndSeek plugin) {
        plugin.getCommand("stats").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage(Messages.ONLY_PLAYERS);
            return true;
        }
        final Player player = (Player) commandSender;

        if (strings.length == 0) {
            HideAndSeek.getInstance().getStatsTable().getStats(player.getUniqueId(), stats -> {
                HideAndSeek.getInstance().getStatsTable().getUserRanking(player.getUniqueId(), ranking -> {
                    if (stats != null) {
                        player.sendMessage("§8§m---|§e Statistiken §8§m|---");
                        player.sendMessage("§a");
                        player.sendMessage("§e§lRanking §8» §7#" + ranking);
                        player.sendMessage("§eGespielte Spiele §8» §7" + stats.getPlayedGames());
                        player.sendMessage("§eGewonnene Spiele §8» §7" + stats.getWins());
                        player.sendMessage("§eVerlorene Spiele §8» §7" + stats.getLooses());
                        player.sendMessage("§eWin-Rate §8» §7" + format(stats.getWinRate()));
                        player.sendMessage("§b");
                        player.sendMessage("§eGefundene Spieler §8» §7" + stats.getFoundedPlayers());
                        player.sendMessage("§eTode §8» §7" + stats.getDeaths());
                        player.sendMessage("§eK/D §8» §7" + format(stats.getKD()));
                        player.sendMessage("§c");
                        player.sendMessage("§8§m---|§e Statistiken §8§m|---");
                    }
                });
            });

        } else {
            player.sendMessage(Messages.PREFIX + " §7Du kannst nur deine eigenen Statistiken sehen!");
        }
        return false;
    }

    private String format(double i) {
        DecimalFormat f = new DecimalFormat("#0.00");
        double toFormat = Math.round(i * 100.0D) / 100.0D;
        return f.format(toFormat);
    }
}
