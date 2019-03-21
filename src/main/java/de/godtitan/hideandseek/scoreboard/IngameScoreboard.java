package de.godtitan.hideandseek.scoreboard;


import de.godtitan.hideandseek.manager.GameTimeManager;
import de.godtitan.hideandseek.manager.TeamManager;
import de.pauhull.scoreboard.CustomScoreboard;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.NameTagVisibility;
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

public class IngameScoreboard extends CustomScoreboard {

    private DisplayScore time, hider, server;

    public IngameScoreboard(Player player) {
        super(player, "sb_ingame", "§e§lHide§6§lAnd§e§lSeek");
    }

    @Override
    public void show() {
        time = new DisplayScore("§8- §eLädt...");
        new DisplayScore();
        new DisplayScore(" §fVerstecker:");
        hider = new DisplayScore(" §aLädt...");
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
        if (TeamManager.TEAM_SEEKER.contains(player)) {
            team.setPrefix("§eS §8× §7");
            team.setAllowFriendlyFire(false);

        } else if (TeamManager.TEAM_HIDER.contains(player)) {
            team.setPrefix("§eV §8× §7");
            team.setAllowFriendlyFire(false);
            team.setNameTagVisibility(NameTagVisibility.NEVER); // makes the nametag invisibility (jedenfalls think ich das)

        } else {
            team.setPrefix("§7§o");
            team.setAllowFriendlyFire(false);
        }
        team.addEntry(player.getName());
    }

    @Override
    public void update() {

        String hider = " §a" + TeamManager.TEAM_HIDER.size() + "§a";
        if (!this.hider.getScore().getEntry().equals(hider)) {
            this.hider.setName(hider);
        }

        String time = "§8- §e" + GameTimeManager.getRemainingTime() + "§b";
        if (!this.time.getScore().getEntry().equals(time)) {
            this.time.setName(time);
        }
    }
}