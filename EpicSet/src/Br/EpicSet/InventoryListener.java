/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Br.EpicSet;

import Br.EpicSet.Data.Data;
import Br.EpicSet.Data.GemData;
import Br.EpicSet.Data.PlayerData;
import Br.EpicSet.Enums.InvMode;
import Br.EpicSet.Enums.Level;
import Br.EpicSet.Enums.Solt;
import java.util.HashMap;
import java.util.Map;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 *
 * @author Bryan_lzh
 */
public class InventoryListener implements Listener {

    public static Map<String, InvMode> Modes = new HashMap<>();

    @EventHandler
    public void onUseGem(PlayerInteractEvent evt) {
        if (!evt.hasItem()) {
            return;
        }
        ItemStack is = evt.getItem();
        if (!is.hasItemMeta()) {
            return;
        }
        ItemMeta im = is.getItemMeta();
        if (!im.hasDisplayName()) {
            return;
        }
        if (!im.hasLore()) {
            return;
        }
        if (!im.getDisplayName().contains(Utils.GemCode)) {
            return;
        }
        PlayerData pd = Utils.getPlayerData(evt.getPlayer());
        if (pd == null) {
            return;
        }
        String s[] = im.getLore().get(im.getLore().size() - 1).replaceAll("§r§", "").split("§");
        String GemName = s[0];
        Level level = Level.getLevel(Integer.parseInt(s[1]));
        GemData gd = new GemData(evt.getPlayer(), level, Data.GemDatas.get(GemName));
        Inventory inv = pd.getInv(evt.getPlayer());
        is.setAmount(1);
        InvMode.SetData.put(evt.getPlayer().getName(), is);
        evt.getPlayer().getInventory().remove(is);
        Modes.put(evt.getPlayer().getName(), InvMode.Set);
        evt.getPlayer().openInventory(inv);
    }

    @EventHandler
    public void onClick(InventoryClickEvent evt) {
        if (!Modes.containsKey(evt.getWhoClicked().getName())) {
            return;
        }
        if (evt.getInventory().getTitle() == null) {
            return;
        }
        if(evt.getClickedInventory().getType()==InventoryType.PLAYER){
            evt.getWhoClicked().closeInventory();
            return;
        }
        if (!evt.getInventory().getTitle().contains(InvMode.InvCode)) {
            return;
        }
        if (evt.getCurrentItem() == null) {
            return;
        }
        if (evt.getCurrentItem().getType() == Material.BARRIER) {
            evt.setCancelled(true);
            return;
        }
        if (Modes.get(evt.getWhoClicked().getName()) == InvMode.See) {
            evt.setCancelled(true);
            return;
        }
        if (Modes.get(evt.getWhoClicked().getName()) == InvMode.Remove) {
            Solt Cilck = Solt.getSolt(evt.getSlot() + 1);
            Utils.getPlayerData((Player) evt.getWhoClicked()).removeGems((Player) evt.getWhoClicked(), Cilck);
            evt.getWhoClicked().closeInventory();
            evt.setCancelled(true);
            Utils.getPlayerData((Player) evt.getWhoClicked()).Compared();
            return;
        }
        if (Modes.get(evt.getWhoClicked().getName()) == InvMode.Set) {
            ItemStack is = InvMode.SetData.get(evt.getWhoClicked().getName());
            String gemname[] = is.getItemMeta().getLore().get(is.getItemMeta().getLore().size() - 1).replaceAll("§r§", "").split("§");
            Gem g = Data.GemDatas.get(gemname[0]);
            Level l = Level.getLevel(Integer.parseInt(gemname[1]));
            GemData gd = new GemData((Player) evt.getWhoClicked(), l, g);
            Solt Cilck = Solt.getSolt(evt.getSlot() + 1);
            Utils.getPlayerData(evt.getWhoClicked().getName()).setGems((Player) evt.getWhoClicked(), gd, Cilck);
            InvMode.SetData.remove(evt.getWhoClicked().getName());
            evt.getWhoClicked().closeInventory();
            evt.setCancelled(true);
        }
    }

    @EventHandler
    public void onClose(InventoryCloseEvent evt) {
        if (Modes.containsKey(evt.getPlayer().getName())) {
            if (Modes.get(evt.getPlayer().getName()) == InvMode.Set) {
                if (InvMode.SetData.get(evt.getPlayer().getName()) != null) {
                    evt.getPlayer().getWorld().dropItem(evt.getPlayer().getLocation(), InvMode.SetData.get(evt.getPlayer().getName()));
                }
                InvMode.SetData.remove(evt.getPlayer().getName());
            }
            Modes.remove(evt.getPlayer().getName());
        }
    }
}
