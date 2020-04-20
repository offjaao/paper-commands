package me.thejaao.papercmd.builder;

import com.google.common.collect.Lists;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

public class ItemBuilder {

    private ItemStack itemStack;
    private Consumer<InventoryClickEvent> event;

    public ItemBuilder(Material material) {
        this(material, 1);
    }

    public ItemBuilder(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    public ItemBuilder(Material material, int amount) {
        itemStack = new ItemStack(material, amount);
    }

    public ItemBuilder type(Material material) {
        itemStack = new ItemStack(material, 1);
        return this;
    }

    public ItemBuilder type(int id) {
        return type(Material.getMaterial(id));
    }

    public ItemBuilder(ItemBuilder item) {
        itemStack = item.build();
        event = item.getEvent();
    }

    public ItemBuilder clone() {
        return new ItemBuilder(itemStack);
    }

    public ItemBuilder durability(int dur) {
        itemStack.setDurability((short) dur);
        return this;
    }

    public ItemBuilder name(String name) {
        ItemMeta im = itemStack.getItemMeta();
        im.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));
        itemStack.setItemMeta(im);
        return this;
    }

    public ItemBuilder unsafeEnchantment(Enchantment ench, int level) {
        itemStack.addUnsafeEnchantment(ench, level);
        return this;
    }

    public ItemBuilder removeEnchantment(Enchantment ench) {
        itemStack.removeEnchantment(ench);
        return this;
    }

    public ItemBuilder owner(String owner) {
        SkullMeta im = (SkullMeta) itemStack.getItemMeta();
        im.setOwner(owner);
        itemStack.setItemMeta(im);
        return this;
    }

    public ItemBuilder enchant(Enchantment ench, int level) {
        ItemMeta im = itemStack.getItemMeta();
        im.addEnchant(ench, level, true);
        itemStack.setItemMeta(im);
        return this;
    }

    public ItemBuilder infinityDurability() {
        itemStack.setDurability(Short.MAX_VALUE);
        return this;
    }

    public ItemBuilder lore(String... lore) {
        ItemMeta im = itemStack.getItemMeta();
        List<String> out = im.getLore() == null ? Lists.newArrayList() : im.getLore();
        Arrays.asList(lore).forEach(string -> out.add(ChatColor.translateAlternateColorCodes('&', string)));
        im.setLore(out);
        itemStack.setItemMeta(im);
        return this;
    }

    public ItemBuilder lore(List<String> lore) {
        if (lore == null) {
            return this;
        }
        ItemMeta im = itemStack.getItemMeta();
        lore = lore.stream().map(string -> ChatColor.translateAlternateColorCodes('&', string)).collect(Collectors.toList());
        im.setLore(lore);
        itemStack.setItemMeta(im);
        return this;
    }

    public ItemBuilder amount(int amount) {
        if (amount > 64) {
            amount = 64;
        }
        itemStack.setAmount(amount);
        return this;
    }

    public ItemBuilder removeAttributes() {
        ItemMeta meta = itemStack.getItemMeta();
        meta.addItemFlags(ItemFlag.values());
        itemStack.setItemMeta(meta);
        return this;
    }

    public ItemBuilder removeLore() {
        ItemMeta im = itemStack.getItemMeta();
        im.setLore(Lists.newArrayList());
        itemStack.setItemMeta(im);
        return this;
    }

    public ItemBuilder event(Consumer<InventoryClickEvent> event) {
        this.event = event;
        return this;
    }

    public Consumer<InventoryClickEvent> getEvent() {
        return event;
    }

    public ItemStack build() {
        return itemStack;
    }
}