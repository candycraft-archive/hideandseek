package de.godtitan.hideandseek.game;

import cloud.timo.TimoCloud.api.TimoCloudAPI;
import de.godtitan.hideandseek.Data;
import de.godtitan.hideandseek.HideAndSeek;
import de.godtitan.hideandseek.Messages;
import de.godtitan.hideandseek.manager.*;
import de.godtitan.hideandseek.util.ActionBar;
import de.godtitan.hideandseek.util.RandomFireworkGenerator;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

/**********************************************************************************
 *  Uhreberrechtshinweis                                                          *
 *  Copyright © Jason H. 2019                                                  *
 *  Erstellt: 17.02.2019 / 21:52                                                   *
 *                                                                                *
 *  Alle Inhalte dieses Quelltextes sind urheberrechtlich geschützt.              *
 *  Das Urheberrecht liegt, soweit nicht ausdrücklich anders gekennzeichnet,      *
 *  bei Jason H. Alle Rechte vorbehalten.                                         *
 *                                                                                *
 *  Jede Art der Vervielfältigung, Verbreitung, Vermietung, Verleihung,           *
 *  öffentlich Zugänglichmachung oder anderer Nutzung,                            *
 *  bedarf der ausdrücklichen, schriftlichen Zustimmung von Jason H.              *
 *********************************************************************************/

public class PhaseManager {

    public static int BLOCK_TASK;
    public int START_GAME_TASK;
    public int START_COUNTDOWN;
    public int SEEKER_TASK;
    public int SEEKER_COUNTDOWN;
    public int STOP_GAME_TASK;
    public int STOP_COUNTDOWN;
    private HideAndSeek hideAndSeek;

    public void startGameCountdown() {
        this.START_COUNTDOWN = 60;

        this.START_GAME_TASK = Bukkit.getScheduler().scheduleSyncRepeatingTask(HideAndSeek.getInstance(), new BukkitRunnable() {
            @Override
            public void run() {
                if (HideAndSeek.getInstance().getGameStates == GameStates.LOBBY) {
                    if (Bukkit.getOnlinePlayers().size() >= Data.MIN_PLAYERS) {
                        if (START_COUNTDOWN != 0) {

                            if (Data.GAME_STARTED == true) {
                                START_COUNTDOWN = 1;
                                Data.GAME_STARTED = false;
                            }

                            if (START_COUNTDOWN == 60) {
                                Bukkit.broadcastMessage(Messages.COUNTDOWN_STARTED);
                                for (Player all : Bukkit.getOnlinePlayers()) {
                                    ActionBar.sendActionBar(all, "§fNoch §e60 §fSekunden");
                                    all.playSound(all.getLocation(), Sound.NOTE_BASS, 1F, 1F);
                                } // 60, 40, 30, 20, 15, 10>
                            }
                            if (START_COUNTDOWN == 40 || START_COUNTDOWN == 30 || START_COUNTDOWN == 20 || START_COUNTDOWN == 15 || START_COUNTDOWN <= 10) {
                                if (START_COUNTDOWN != 1) {
                                    for (Player all : Bukkit.getOnlinePlayers()) {
                                        ActionBar.sendActionBar(all, "§fNoch §e" + START_COUNTDOWN + " §fSekunden");
                                        all.playSound(all.getLocation(), Sound.NOTE_BASS, 1F, 1F);
                                    }
                                } else {
                                    for (Player all : Bukkit.getOnlinePlayers()) {
                                        ActionBar.sendActionBar(all, "§fNoch §eeine §fSekunden");
                                        all.playSound(all.getLocation(), Sound.NOTE_BASS, 1F, 1F);
                                    }
                                }
                            }
                            for (Player all : Bukkit.getOnlinePlayers()) {
                                all.setExp(START_COUNTDOWN / 60);
                                all.setLevel(START_COUNTDOWN);
                            }
                            START_COUNTDOWN--;

                        } else /* IF COUNTDOWN == 0 */ {
                            Bukkit.getScheduler().cancelTask(START_GAME_TASK);
                            HideAndSeek.getInstance().getGameStates = GameStates.INGAME;
                            TimoCloudAPI.getBukkitAPI().getThisServer().setState("Ingame");
                            for (Player all : Bukkit.getOnlinePlayers()) {
                                all.sendMessage(Messages.GAME_STARTED);
                            }
                            GameManager.startGame();
                        }
                    } else /* IF NOT ENOUGH PLAYERS */ {
                        if (START_COUNTDOWN != 60) {
                            Bukkit.getScheduler().cancelTask(START_GAME_TASK);
                            for (Player all : Bukkit.getOnlinePlayers()) {
                                all.sendMessage(Messages.NOT_ENOUGH_PLAYERS);
                                all.setExp(0.0F);
                                all.setLevel(0);
                            }
                            START_COUNTDOWN = 60;
                        }
                    }
                }
            }
        }, 20L, 20L);
    }

    public void startSeekerCountdown() {
        this.SEEKER_COUNTDOWN = 60;

        BlockChangeManager.BLOCK_WORKBENCH.clear();
        BlockChangeManager.BLOCK_CHEST.clear();
        BlockChangeManager.BLOCK_ANVIL.clear();
        BlockChangeManager.BLOCK_WOOD.clear();

        for (Player all : Bukkit.getOnlinePlayers()) {
            if (TeamManager.TEAM_HIDER.contains(all)) {
                BlockChangeManager.BLOCK_WORKBENCH.add(all.getUniqueId());
            }
        }


        this.SEEKER_TASK = Bukkit.getScheduler().scheduleSyncRepeatingTask(HideAndSeek.getInstance(), new BukkitRunnable() {
            @Override
            public void run() {
                if (HideAndSeek.getInstance().getGameStates == GameStates.INGAME) {
                    if (SEEKER_COUNTDOWN != 0) {

                        if (TeamManager.TEAM_HIDER.size() == 0 || TeamManager.TEAM_SEEKER.size() == 0) {
                            Bukkit.getScheduler().cancelTask(SEEKER_TASK);
                            return;
                        }

                        for (Player all : Bukkit.getOnlinePlayers()) {
                            if (SEEKER_COUNTDOWN < 10)
                                all.playSound(all.getLocation(), Sound.NOTE_BASS, 1F, 1F);

                            if (TeamManager.TEAM_SEEKER.contains(all)) {
                                if (SEEKER_COUNTDOWN != 1) {
                                    ActionBar.sendActionBar(all, "§fBitte warte noch §e" + SEEKER_COUNTDOWN + " §fSekunden");
                                } else {
                                    ActionBar.sendActionBar(all, "§fBitte warte noch §eeine §fSekunde");
                                }

                            } else {
                                if (SEEKER_COUNTDOWN != 1) {
                                    ActionBar.sendActionBar(all, "§frestliche Versteckzeit: §e" + SEEKER_COUNTDOWN + " §fSekunden.");
                                } else {
                                    ActionBar.sendActionBar(all, "§frestliche Versteckzeit: §eeine §fSekunde.");
                                }
                            }
                        }
                        SEEKER_COUNTDOWN--;
                    } else /* IF COUNTDOWN == 0 */ {
                        Bukkit.getScheduler().cancelTask(SEEKER_TASK);
                        for (Player all : Bukkit.getOnlinePlayers()) {
                            all.closeInventory();
                            if (TeamManager.TEAM_SEEKER.contains(all)) {
                                all.sendMessage(Messages.PREFIX + " §7Suche jetzt die §eVerstecker §7und töte sie!");
                                if (new LocationManager().exists("INGAME")) {
                                    all.teleport(new LocationManager().getLocation("INGAME"));
                                } else {
                                    all.sendMessage(Messages.LOCATION_NOT_EXISTS);
                                }
                                ItemManager.giveGameItems(all, true);
                                all.playSound(all.getLocation(), Sound.SUCCESSFUL_HIT, 1F, 1F);

                            } else {
                                ItemManager.clearPlayer(all, GameMode.ADVENTURE);
                                all.setMaxHealth(6.0D);
                                all.setHealth(6.0D);
                                all.sendMessage(Messages.PREFIX + " §7ACHTUNG! Die §eSucher §7suchen dich jetzt.");
                                all.playSound(all.getLocation(), Sound.ENDERDRAGON_GROWL, 1F, 1F);

                                //DisguiseManager.disguiseToBlock(all);

                            }
                        }
                    }

                } else {
                    Bukkit.getScheduler().cancelTask(SEEKER_TASK);
                }
            }
        }, 20L, 20L);
    }

    public void stopGameCountdown() {
        this.STOP_COUNTDOWN = 10;
        Bukkit.getScheduler().cancelTask(GameTimeManager.GAME_TIME_TASK);

        Bukkit.getScheduler().runTaskLater(HideAndSeek.getInstance(), new BukkitRunnable() {
            @Override
            public void run() {
                RandomFireworkGenerator.shootRandomFirework(new LocationManager().getLocation("LOBBY"), 3);
            }
        }, 20L);

        STOP_GAME_TASK = Bukkit.getScheduler().scheduleSyncRepeatingTask(HideAndSeek.getInstance(), new BukkitRunnable() {
            @Override
            public void run() {
                if (HideAndSeek.getInstance().getGameStates == GameStates.RESTARTING) {
                    if (STOP_COUNTDOWN != 0) {
                        if (Bukkit.getOnlinePlayers().size() >= 2) {
                            STOP_COUNTDOWN--;

                        } else {
                            Bukkit.getScheduler().cancelTask(START_GAME_TASK);
                            Bukkit.getServer().shutdown();
                        }
                    } else {
                        Bukkit.getScheduler().cancelTask(START_GAME_TASK);
                        Bukkit.getServer().shutdown();
                        //TimoCloudAPI.getBukkitAPI().getThisServer().stop();
                    }
                }
            }
        }, 20L, 20L);
    }
}
