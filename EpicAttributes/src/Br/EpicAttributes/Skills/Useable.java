/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Br.EpicAttributes.Skills;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author Administrator
 */
public interface Useable {
    public ItemStack getUseItem();
    public boolean CouldUse(Player p,ItemStack onhand);
    public void onUse(Player p);
}
