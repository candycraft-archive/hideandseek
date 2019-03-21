package de.godtitan.hideandseek.mysql.table;

import de.godtitan.hideandseek.mysql.MySQL;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.function.Consumer;

/**********************************************************************************
 *  Uhreberrechtshinweis                                                          *
 *  Copyright © Jason H. 2019                                                  *
 *  Erstellt: 23.02.2019 / 16:33                                                   *
 *                                                                                *
 *  Alle Inhalte dieses Quelltextes sind urheberrechtlich geschützt.              *
 *  Das Urheberrecht liegt, soweit nicht ausdrücklich anders gekennzeichnet,      *
 *  bei Jason H. Alle Rechte vorbehalten.                                         *
 *                                                                                *
 *  Jede Art der Vervielfältigung, Verbreitung, Vermietung, Verleihung,           *
 *  öffentlich Zugänglichmachung oder anderer Nutzung,                            *
 *  bedarf der ausdrücklichen, schriftlichen Zustimmung von Jason H.              *
 *********************************************************************************/

public class StatsTable {

    private static final String TABLE_NAME = "hideandseek_stats";

    private MySQL mySQL;
    private ExecutorService executorService;

    public StatsTable(MySQL mySQL, ExecutorService executorService) {
        this.mySQL = mySQL;
        this.executorService = executorService;

        mySQL.update("CREATE TABLE IF NOT EXISTS `" + TABLE_NAME + "` (`id` INT AUTO_INCREMENT, `uuid` VARCHAR(255), `wins` INT, `looses` INT, `founded_players` INT, `deaths` INT, `played_games` INT, PRIMARY KEY (`id`))");
    }

    public void getUserRanking(UUID uuid, Consumer<Integer> consumer) {
        executorService.execute(() -> {
            try {
                int rank = 0;
                ResultSet result = mySQL.query("SELECT * FROM `" + TABLE_NAME + "` ORDER BY `wins` DESC");
                while (result.next()) {
                    rank++;
                    if (result.getString("uuid").equals(uuid.toString())) {
                        consumer.accept(rank);
                        return;
                    }
                }
                consumer.accept(-1);

            } catch (SQLException e) {
                e.printStackTrace();
                consumer.accept(-1);
            }

        });
    }

    public void getStats(UUID uuid, Consumer<Stats> consumer) {
        executorService.execute(() -> {
            try {
                ResultSet result = mySQL.query("SELECT * FROM `" + TABLE_NAME + "` WHERE `uuid`='" + uuid + "'");
                if (result.next()) {

                    int wins = result.getInt("wins");
                    int looses = result.getInt("looses");
                    int foundedPlayers = result.getInt("founded_players");
                    int deaths = result.getInt("deaths");
                    int playedGames = result.getInt("played_games");

                    Stats stats = new Stats(uuid, wins, looses, foundedPlayers, deaths, playedGames);
                    consumer.accept(stats);
                    return;

                }
                consumer.accept(null);

            } catch (SQLException e) {
                e.printStackTrace();
                consumer.accept(null);
            }
        });
    }

    public void setStats(UUID uuid, Stats stats) {
        getStats(uuid, currentStats -> {

            if (currentStats == null) {
                String sql = "INSERT INTO `" + TABLE_NAME + "` VALUES (0, '" + uuid + "', " + stats.getWins() + ", " +
                        stats.getLooses() + ", " + stats.getFoundedPlayers() + ", " + stats.getDeaths() + ", " + stats.getPlayedGames() + ")";
                mySQL.update(sql);

            } else {
                String sql = "UPDATE `" + TABLE_NAME + "` SET `wins`=" + stats.wins + ", `looses`=" + stats.looses + ", `founded_players`=" +
                        stats.foundedPlayers + ", `deaths`=" + stats.deaths + ", `played_games`=" + stats.playedGames + " WHERE `uuid`='" + uuid + "'";
                mySQL.update(sql);
            }
        });
    }

    @ToString
    @AllArgsConstructor
    public static class Stats {

        @Getter
        @Setter
        private UUID uuid;

        @Getter
        @Setter
        private int wins, looses, foundedPlayers, deaths, playedGames;

        public static Stats getDefault() {
            return new Stats(null, 0, 0, 0, 0, 0);
        }

        public double getKD() {
            return foundedPlayers / (double) deaths;
        }

        public double getWinRate() {
            return wins / (double) playedGames;
        }
    }
}
