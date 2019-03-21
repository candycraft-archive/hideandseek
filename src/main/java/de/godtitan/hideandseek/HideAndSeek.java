package de.godtitan.hideandseek;

import de.godtitan.hideandseek.command.BuildCommand;
import de.godtitan.hideandseek.command.StatsCommand;
import de.godtitan.hideandseek.command.setLocationCommand;
import de.godtitan.hideandseek.command.startCommand;
import de.godtitan.hideandseek.game.GameStates;
import de.godtitan.hideandseek.inventory.BlockChangeInventory;
import de.godtitan.hideandseek.listener.*;
import de.godtitan.hideandseek.manager.LocationManager;
import de.godtitan.hideandseek.mysql.MySQL;
import de.godtitan.hideandseek.mysql.table.StatsTable;
import de.godtitan.hideandseek.scoreboard.LobbyScoreboard;
import de.godtitan.hideandseek.util.ActionBar;
import de.pauhull.scoreboard.ScoreboardManager;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**********************************************************************************
 *  Uhreberrechtshinweis                                                          *
 *  Copyright © Jason H. 2019                                                  *
 *  Erstellt: 17.02.2019 / 11:42                                                   *
 *                                                                                *
 *  Alle Inhalte dieses Quelltextes sind urheberrechtlich geschützt.              *
 *  Das Urheberrecht liegt, soweit nicht ausdrücklich anders gekennzeichnet,      *
 *  bei Jason H. Alle Rechte vorbehalten.                                         *
 *                                                                                *
 *  Jede Art der Vervielfältigung, Verbreitung, Vermietung, Verleihung,           *
 *  öffentlich Zugänglichmachung oder anderer Nutzung,                            *
 *  bedarf der ausdrücklichen, schriftlichen Zustimmung von Jason H.              *
 *********************************************************************************/

public class HideAndSeek extends JavaPlugin {

    @Getter
    private static HideAndSeek instance;
    public LocationManager getLocationManager;
    public GameStates getGameStates;
    @Getter
    private ScoreboardManager scoreboardManager;
    @Getter
    private StatsTable statsTable;
    @Getter
    private MySQL mySQL;
    @Getter
    private ExecutorService executorService;
    private FileConfiguration config;

    @Override
    public void onEnable() {
        instance = this;
        register();
        ActionBar.startScheduler();
        Bukkit.getConsoleSender().sendMessage("§f[§3HideAndSeek§f] §aDas Plugin wurde geladen und aktiviert.");
    }

    @Override
    public void onDisable() {
        instance = null;

        this.mySQL.close();
        this.executorService.shutdown();
        Bukkit.getConsoleSender().sendMessage("§f[§3HideAndSeek§f] §cDas Plugin wurde gestoppt.");
    }

    // loading the commands, listeners and other stuff xd
    private void register() {

        /* ExecutorService */
        this.executorService = Executors.newSingleThreadExecutor();

        /* Scoreboard */
        this.scoreboardManager = new ScoreboardManager(this, LobbyScoreboard.class);

        /* MySQL */
        this.config = copyAndLoad("config.yml", new File(getDataFolder(), "config.yml"));

        this.mySQL = new MySQL(config.getString("MySQL.Host"),
                config.getString("MySQL.Port"),
                config.getString("MySQL.Database"),
                config.getString("MySQL.User"),
                config.getString("MySQL.Password"),
                config.getBoolean("MySQL.SSL"));

        if (!(this.mySQL.connect())) {
            return;
        }
        this.statsTable = new StatsTable(mySQL, executorService);

        /* Other stuff xD */
        this.getGameStates = GameStates.LOBBY;
        this.getLocationManager = new LocationManager();

        /* Commands */
        Bukkit.getConsoleSender().sendMessage("§f[§3HideAndSeek§f] §aloading commands...");
        new BuildCommand(this);
        new setLocationCommand(this);
        new startCommand(this);
        new StatsCommand(this);

        /* Listeners */
        Bukkit.getConsoleSender().sendMessage("§f[§3HideAndSeek§f] §aloading listeners...");
        new PlayerJoinListener(this);
        new PlayerQuitListener(this);
        new PlayerLoginListener(this);
        new EntityDamageListener(this);
        new EntityDamageEntityListener(this);
        new EntityRegainHealthListener(this);
        new PlayerDeathListener(this);
        new BlockBreakListener(this);
        new BlockPlaceListener(this);
        new WeatherChangeListener(this);
        new FoodLevelChangeListener(this);
        new PlayerPickUpItemListener(this);
        new PlayerDropItemListener(this);
        new PlayerInteractListener(this);
        new InventoryClickListener(this);
        new PlayerAchievementAwardedListener(this);
        new PlayerNickListener(this);
        new ServerListPingListener(this);
        new BlockChangeInventory(this);
    }

    private void copy(String resource, File file, boolean override) {
        if (!file.exists() || override) {
            file.getParentFile().mkdirs();

            if (file.exists()) {
                file.delete();
            }

            try {
                Files.copy(getResource(resource), file.toPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private YamlConfiguration copyAndLoad(String resource, File file) {
        copy(resource, file, false);
        return YamlConfiguration.loadConfiguration(file);
    }

}