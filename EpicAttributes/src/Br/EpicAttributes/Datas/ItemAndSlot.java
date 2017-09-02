/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Br.EpicAttributes.Datas;

import org.bukkit.inventory.ItemStack;

/**
 *
 * @author Administrator
 */
public class ItemAndSlot {

    ItemAndSlot(ItemStack is, int s) {
        this.is = is;
        this.Slot = s;
    }

    ItemStack is;
    int Slot;

    public ItemStack getItemStack() {
        return this.is;
    }

    public int getSlot() {
        return this.Slot;
    }
}
