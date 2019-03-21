package de.godtitan.hideandseek.util;

import org.bukkit.Color;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BannerMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;

/**********************************************************************************
 *  Uhreberrechtshinweis                                                          *
 *  Copyright © Jason H. 2018                                                  *
 *  Erstellt: 17.11.2018 / 12:38                                                   *
 *                                                                                *
 *  Alle Inhalte dieses Quelltextes sind urheberrechtlich geschützt.              *
 *  Das Urheberrecht liegt, soweit nicht ausdrücklich anders gekennzeichnet,      *
 *  bei Jason H. Alle Rechte vorbehalten.                                         *
 *                                                                                *
 *  Jede Art der Vervielfältigung, Verbreitung, Vermietung, Verleihung,           *
 *  öffentlich Zugänglichmachung oder anderer Nutzung,                            *
 *  bedarf der ausdrücklichen, schriftlichen Zustimmung von Jason H.              *
 *********************************************************************************/

public class ItemBuilder {

    private ItemStack itemStack;

    public ItemBuilder(Material material) {
        this.itemStack = new ItemStack(material);
    }

    public ItemBuilder(Material mat, short sh) {
        this.itemStack = new ItemStack(mat, 1, sh);
    }

    public ItemMeta getItemMeta() {
        return this.itemStack.getItemMeta();
    }

    public ItemBuilder setItemMeta(ItemMeta itemMeta) {
        this.itemStack.setItemMeta(itemMeta);
        return this;
    }

    public ItemBuilder setColor(Color color) {
        LeatherArmorMeta leatherArmorMeta = (LeatherArmorMeta) this.itemStack.getItemMeta();
        leatherArmorMeta.setColor(color);
        setItemMeta(leatherArmorMeta);
        return this;
    }

    public ItemBuilder setBannerColor(DyeColor dyeColor) {
        BannerMeta bannerMeta = (BannerMeta) this.itemStack.getItemMeta();
        bannerMeta.setBaseColor(dyeColor);
        setItemMeta(bannerMeta);
        return this;
    }

    public ItemBuilder setAmount(int amount) {
        this.itemStack.setAmount(amount);
        return this;
    }

    public ItemBuilder setHead(String headOwner) {
        SkullMeta skullMeta = (SkullMeta) this.itemStack.getItemMeta();
        skullMeta.setOwner(headOwner);
        setItemMeta(skullMeta);
        return this;
    }

    public ItemBuilder setDisplayName(String displayName) {
        ItemMeta itemMeta = getItemMeta();
        itemMeta.setDisplayName(displayName);
        setItemMeta(itemMeta);
        return this;
    }

    public ItemBuilder setLore(ArrayList<String> lore) {
        ItemMeta itemMeta = getItemMeta();
        itemMeta.setLore(lore);
        setItemMeta(itemMeta);
        return this;
    }

    public ItemBuilder addEnchantment(Enchantment enchantment, int level) {
        ItemMeta itemMeta = getItemMeta();
        itemMeta.addEnchant(enchantment, level, true);
        setItemMeta(itemMeta);
        return this;
    }

    public ItemBuilder setUnbreakble() {
        ItemMeta itemMeta = getItemMeta();
        itemMeta.spigot().setUnbreakable(true);
        setItemMeta(itemMeta);
        return this;
    }

    public ItemBuilder addItemFlag(ItemFlag itemFlag) {
        ItemMeta itemMeta = getItemMeta();
        itemMeta.addItemFlags(itemFlag);
        setItemMeta(itemMeta);
        return this;
    }

    public ItemStack build() {
        return this.itemStack;
    }
}