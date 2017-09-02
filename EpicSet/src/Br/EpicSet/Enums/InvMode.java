/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Br.EpicSet.Enums;

import java.util.HashMap;
import java.util.Map;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author Administrator
 */
public enum InvMode {
    See,
    Remove,
    Set;
    public static Map<String,ItemStack> SetData = new HashMap<>();
    public static String InvCode = "§e§r";
}
