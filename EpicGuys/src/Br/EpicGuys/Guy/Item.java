/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Br.EpicGuys.Guy;

import Br.API.Item.ItemManager;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author Administrator
 */
public class Item {

    public Item(String s) {
        try {
            String ss[] = s.split("\\|");
            item = ItemManager.getItemByName(ss[0]).getItemStack();
            owner = ss[1];
            price = Integer.parseInt(ss[2]);
        } catch (ArrayIndexOutOfBoundsException|NumberFormatException a) {
        }
    }

    public Item(ItemStack is, String o, int pri) {
        this.item = is;
        this.owner = o;
        this.price = pri;
    }
    private ItemStack item;
    private String owner;
    private int price;

    public ItemStack getItem() {
        return this.item;
    }

    public String getOwner() {
        return this.owner;
    }

    public int getPrice() {
        return this.price;
    }

    public String toString() {
        return ItemManager.createItem(this.item) + "|" + this.owner + "|" + this.price;
    }
}
