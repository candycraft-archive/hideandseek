package de.godtitan.hideandseek.manager;

import de.godtitan.hideandseek.HideAndSeek;
import de.godtitan.hideandseek.game.GameStates;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.UUID;

/**********************************************************************************
 *  Uhreberrechtshinweis                                                          *
 *  Copyright © Jason H. 2019                                                  *
 *  Erstellt: 03.03.2019 / 20:41                                                   *
 *                                                                                *
 *  Alle Inhalte dieses Quelltextes sind urheberrechtlich geschützt.              *
 *  Das Urheberrecht liegt, soweit nicht ausdrücklich anders gekennzeichnet,      *
 *  bei Jason H. Alle Rechte vorbehalten.                                         *
 *                                                                                *
 *  Jede Art der Vervielfältigung, Verbreitung, Vermietung, Verleihung,           *
 *  öffentlich Zugänglichmachung oder anderer Nutzung,                            *
 *  bedarf der ausdrücklichen, schriftlichen Zustimmung von Jason H.              *
 *********************************************************************************/

public class BlockChangeManager {

    public static ArrayList<UUID> BLOCK_WORKBENCH = new ArrayList<>();
    public static ArrayList<UUID> BLOCK_ANVIL = new ArrayList<>();
    public static ArrayList<UUID> BLOCK_CHEST = new ArrayList<>();
    public static ArrayList<UUID> BLOCK_WOOD = new ArrayList<>();
    private static HideAndSeek hideAndSeek;

    public static String getCurrentBlock(Player player) {
        if (hideAndSeek.getGameStates == GameStates.INGAME) {
            if (TeamManager.TEAM_HIDER.contains(player)) {
                if (BLOCK_WORKBENCH.contains(player.getUniqueId())) {
                    return "§7Der Spieler ist eine §eWerkbank";

                } else if (BLOCK_ANVIL.contains(player.getUniqueId())) {
                    return "§7Der Spieler ist ein §eAmboss";

                } else if (BLOCK_CHEST.contains(player.getUniqueId())) {
                    return "§7Der Spieler ist eine §eTruhe";

                } else if (BLOCK_WOOD.contains(player.getUniqueId())) {
                    return "§7Der Spieler ist ein §eHolzblock";

                } else {
                    return "§7Der Spieler hat §o(noch) §ckeine §7Verwandlung.";
                }
            } else {
                return "§7Der Spieler ist kein §eVerstecker";
            }
        } else {
            return "§7Das Spiel ist §cnicht §7Ingame";
        }
    }
}
