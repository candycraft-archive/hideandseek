package de.godtitan.hideandseek.manager;

import me.libraryaddict.disguise.DisguiseAPI;
import me.libraryaddict.disguise.disguisetypes.DisguiseType;
import me.libraryaddict.disguise.disguisetypes.MiscDisguise;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**********************************************************************************
 *  Uhreberrechtshinweis                                                          *
 *  Copyright © Jason H. 2019                                                  *
 *  Erstellt: 08.03.2019 / 22:12                                                   *
 *                                                                                *
 *  Alle Inhalte dieses Quelltextes sind urheberrechtlich geschützt.              *
 *  Das Urheberrecht liegt, soweit nicht ausdrücklich anders gekennzeichnet,      *
 *  bei Jason H. Alle Rechte vorbehalten.                                         *
 *                                                                                *
 *  Jede Art der Vervielfältigung, Verbreitung, Vermietung, Verleihung,           *
 *  öffentlich Zugänglichmachung oder anderer Nutzung,                            *
 *  bedarf der ausdrücklichen, schriftlichen Zustimmung von Jason H.              *
 *********************************************************************************/

public class DisguiseManager {

    public static void makePlayerUnsolid(Player player) {
        ItemStack block = player.getInventory().getItem(8);
        Block pBlock = player.getLocation().getBlock();

        block.setAmount(5);
        for (Player pl : Bukkit.getOnlinePlayers()) {
            pl.sendBlockChange(pBlock.getLocation(), Material.AIR,
                    (byte) 0);
        }

        player.playSound(player.getLocation(), Sound.ENTITY_BAT_HURT, 1, 1);
        block.removeEnchantment(Enchantment.DURABILITY);

        for (Player playerShow : Bukkit.getOnlinePlayers()) {
            playerShow.showPlayer(player);
        }

        MiscDisguise disguise = new MiscDisguise(DisguiseType.FALLING_BLOCK,
                block.getTypeId(), block.getDurability());
        DisguiseAPI.disguiseToAll(player, disguise);
    }

}
