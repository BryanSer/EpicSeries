/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Br.EpicGuys.Guy;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 *
 * @author Administrator
 */
public class Shop {

    public List<Item> ItemList = new LinkedList<>();
    Guy G;

    public Shop(Guy g) {
        this.G = g;
    }

    public Item getItem(int index) {
        return ItemList.get(index);
    }

    public List<ItemStack> getUpdataList() {
        List<ItemStack> is = new ArrayList<>();
        for (Item i : this.ItemList) {
            is.add(i.getItem());
        }
        return is;
    }

    public Inventory getShop(Player p) {
        Inventory inv = Bukkit.createInventory(p, 54, "§6" + G.getDisplayName() + " 商店");
        inv.setItem(0, Shop.getInfo(p, G));
        int index = 1;
        for (Item i : this.ItemList) {
            if (index >= 54) {
                break;
            }
            inv.setItem(index, i.getItem());
            index++;
        }
        return inv;
    }

    public static ItemStack getInfo(Player p, Guy g) {
        ItemStack is = new ItemStack(Material.PAPER);
        ItemMeta im = is.getItemMeta();
        im.setDisplayName("§6公会仓库");
        List<String> lore = new ArrayList<>();
        lore.add("§e点击后面的物品即可使用贡献度兑换");
        lore.add("§a你当前拥有的贡献度为:§l" + g.getMemberPoint().get(p.getName()));
        return is;
    }
}
