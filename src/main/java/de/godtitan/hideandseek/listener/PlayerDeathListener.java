package de.godtitan.hideandseek.listener;

import de.godtitan.hideandseek.HideAndSeek;
import de.godtitan.hideandseek.Messages;
import de.godtitan.hideandseek.game.GameStates;
import de.godtitan.hideandseek.game.PhaseManager;
import de.godtitan.hideandseek.manager.ItemManager;
import de.godtitan.hideandseek.manager.TeamManager;
import de.godtitan.hideandseek.util.Title;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

/**********************************************************************************
 *  Uhreberrechtshinweis                                                          *
 *  Copyright © Jason H. 2019                                                  *
 *  Erstellt: 18.02.2019 / 17:57                                                   *
 *                                                                                *
 *  Alle Inhalte dieses Quelltextes sind urheberrechtlich geschützt.              *
 *  Das Urheberrecht liegt, soweit nicht ausdrücklich anders gekennzeichnet,      *
 *  bei Jason H. Alle Rechte vorbehalten.                                         *
 *                                                                                *
 *  Jede Art der Vervielfältigung, Verbreitung, Vermietung, Verleihung,           *
 *  öffentlich Zugänglichmachung oder anderer Nutzung,                            *
 *  bedarf der ausdrücklichen, schriftlichen Zustimmung von Jason H.              *
 *********************************************************************************/

public class PlayerDeathListener implements Listener {

    public PlayerDeathListener(HideAndSeek plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        event.setDeathMessage(null);
        event.setDroppedExp(0);
        event.getDrops().clear();
        event.setKeepInventory(true);

        if (event.getEntity() instanceof Player) {
            final Player player = event.getEntity().getPlayer();

            if (HideAndSeek.getInstance().getGameStates == GameStates.INGAME) {
                if (event.getEntity().getKiller() == null) {
                    respawnPlayer(player, false);

                } else /* IF PLAYER DIE WITH KILLER */ {
                    if (event.getEntity().getKiller() instanceof Player) {
                        final Player killer = event.getEntity().getKiller();
                        HideAndSeek.getInstance().getStatsTable().getStats(killer.getUniqueId(), stats -> {
                            stats.setFoundedPlayers(stats.getFoundedPlayers() + 1);
                            HideAndSeek.getInstance().getStatsTable().setStats(killer.getUniqueId(), stats);
                        });
                        respawnPlayer(player, true);
                        Bukkit.broadcastMessage(Messages.PREFIX + " §e" + killer.getName() + " §7hat den Spieler §c" + player.getName() + " §7gefunden!");

                    } else {
                        respawnPlayer(player, false);
                    }
                }
                if (TeamManager.TEAM_HIDER.size() == 0) {
                    Bukkit.broadcastMessage(Messages.PREFIX + " §7Die §eSucher §7haben das Spiel §agewonnen§7!");
                    for (Player all : Bukkit.getOnlinePlayers()) {
                        if (TeamManager.TEAM_SEEKER.contains(all)) {
                            HideAndSeek.getInstance().getStatsTable().getStats(all.getUniqueId(), stats -> {
                                stats.setWins(stats.getWins() + 1);
                                HideAndSeek.getInstance().getStatsTable().setStats(all.getUniqueId(), stats);
                            });
                            Title.sendTitle(all, "§aGewonnen", "§7Die Sucher haben das Spiel gewonnen", 20, 20 * 2, 20);
                        } else {
                            HideAndSeek.getInstance().getStatsTable().getStats(all.getUniqueId(), stats -> {
                                stats.setLooses(stats.getLooses() + 1);
                                HideAndSeek.getInstance().getStatsTable().setStats(all.getUniqueId(), stats);
                            });
                            Title.sendTitle(all, "§cVerloren", "§7Die Sucher haben das Spiel gewonnen", 20, 20 * 2, 20);
                        }
                    }
                    HideAndSeek.getInstance().getGameStates = GameStates.RESTARTING;
                    //TimoCloudAPI.getBukkitAPI().getThisServer().setState("Restarting");

                    for (Player all : Bukkit.getOnlinePlayers()) {
                        if (HideAndSeek.getInstance().getLocationManager.exists("LOBBY")) {
                            all.teleport(HideAndSeek.getInstance().getLocationManager.getLocation("LOBBY"));
                        } else {
                            all.sendMessage(Messages.LOCATION_NOT_EXISTS);
                        }
                    }
                    new PhaseManager().stopGameCountdown();
                }
            }
        }
    }

    public void respawnPlayer(Player player, boolean killer) {
        if (TeamManager.TEAM_HIDER.contains(player)) {
            TeamManager.TEAM_HIDER.remove(player);
            TeamManager.TEAM_SPECTATOR.add(player);

            Title.sendTitle(player, "§cAusgeschieden", "§7Du wurdest von einem §eSucher §7gefunden", 20 * 1, 20 * 2, 20 * 1);

            HideAndSeek.getInstance().getStatsTable().getStats(player.getUniqueId(), stats -> {
                stats.setDeaths(stats.getDeaths() + 1);
                HideAndSeek.getInstance().getStatsTable().setStats(player.getUniqueId(), stats);
            });
            if (killer == false) {
                Bukkit.broadcastMessage(Messages.PREFIX + " §e" + player.getName() + " §7ist §causgeschieden§7!");
            }
            ItemManager.giveSpectatorItems(player);
            for (Player all : Bukkit.getOnlinePlayers()) {
                all.hidePlayer(player);
            }
            player.setCompassTarget(HideAndSeek.getInstance().getLocationManager.getLocation("INGAME"));
            player.setAllowFlight(true);
            player.setFlying(true);

        } else {
            player.teleport(HideAndSeek.getInstance().getLocationManager.getLocation("INGAME"));
        }
    }
}
