package de.godtitan.hideandseek.scoreboard;

import cloud.timo.TimoCloud.api.TimoCloudAPI;
import de.pauhull.scoreboard.CustomScoreboard;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Team;

/**********************************************************************************
 *  Uhreberrechtshinweis                                                          *
 *  Copyright © Jason H. 2019                                                  *
 *  Erstellt: 18.02.2019 / 10:33                                                   *
 *                                                                                *
 *  Alle Inhalte dieses Quelltextes sind urheberrechtlich geschützt.              *
 *  Das Urheberrecht liegt, soweit nicht ausdrücklich anders gekennzeichnet,      *
 *  bei Jason H. Alle Rechte vorbehalten.                                         *
 *                                                                                *
 *  Jede Art der Vervielfältigung, Verbreitung, Vermietung, Verleihung,           *
 *  öffentlich Zugänglichmachung oder anderer Nutzung,                            *
 *  bedarf der ausdrücklichen, schriftlichen Zustimmung von Jason H.              *
 *********************************************************************************/

public class LobbyScoreboard extends CustomScoreboard {

    private DisplayScore online, map, server;

    public LobbyScoreboard(Player player) {
        super(player, "sb_lobby", "§5§lWarten auf Spielstart...");
    }

    @Override
    public void show() {
        new DisplayScore();
        new DisplayScore("§fOnline:");
        this.online = new DisplayScore(" §aLädt...");
        new DisplayScore();
        new DisplayScore("§fMap:");
        this.map = new DisplayScore(" §cLädt...");
        new DisplayScore();
        new DisplayScore("§fServer:");
        this.server = new DisplayScore(" §d§lCandyCraft§7.§dde");

        super.show();
    }

    @Override
    public void updateTeam(Player player) {
        Team team;

        if (scoreboard.getTeam(player.getName()) != null) {
            team = scoreboard.getTeam(player.getName());
        } else {
            team = scoreboard.registerNewTeam(player.getName());
        }
        team.setPrefix("§7");
        team.addEntry(player.getName());
    }

    @Override
    public void update() {

        String online = " §a" + Bukkit.getOnlinePlayers().size() + "§b";
        if (!this.online.getScore().getEntry().equals(online)) {
            this.online.setName(online);
        }

        String map = " §c" + TimoCloudAPI.getBukkitAPI().getThisServer().getMap() + "§b";
        if (!this.map.getScore().getEntry().equals(map)) {
            this.map.setName(map);
        }
    }
}