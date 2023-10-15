package me.bredisrising.spicycoconut.customitems;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ItemManager {
    public static HashMap<String, ItemStack> items;

    public static void init() {
        items = new HashMap<String, ItemStack>();
        items.put("SlimySaddle", createSlimySaddle());
        items.put("SlimySpeedsterSaddle", createSlimySpeedsterSaddle());
    }

    public static ItemStack createSlimySpeedsterSaddle(){
        ItemStack slimy = new ItemStack(Material.SADDLE);
        ItemMeta meta = slimy.getItemMeta();
        meta.setDisplayName("§a §kA "+"§aSlimy Speedster Saddle"+ "§a §kA");

        List<String> lore = new ArrayList<String>();
        lore.add("I AM SPED!");
        meta.setLore(lore);
        meta.addEnchant(Enchantment.LUCK, 1, false);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        slimy.setItemMeta(meta);
        return slimy;
    }

    public static ItemStack createSlimySaddle() {
        ItemStack slimy = new ItemStack(Material.SADDLE);
        ItemMeta meta = slimy.getItemMeta();
        meta.setDisplayName("§a §kA "+"§aSlimy Saddle"+ "§a §kA");

        List<String> lore = new ArrayList<String>();
        lore.add("BOOOIIINNNG!");
        meta.setLore(lore);
        meta.addEnchant(Enchantment.LUCK, 1, false);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        slimy.setItemMeta(meta);
        return slimy;
    }
}
