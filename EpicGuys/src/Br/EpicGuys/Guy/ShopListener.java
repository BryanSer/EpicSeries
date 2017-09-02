/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Br.EpicGuys.Guy;

import Br.API.Utils;
import Br.EpicGuys.Data;
import java.util.HashMap;
import java.util.Map;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author Administrator
 */
public class ShopListener implements Listener {

    public static void Updata(Player p, Shop s) {
        if (p.getOpenInventory() == null) {
            return;
        }
        Inventory inv = p.getOpenInventory().getTopInventory();
        if (inv == null) {
            return;
        }
        if (inv.getType() != InventoryType.CHEST) {
            return;
        }
        if (!inv.getTitle().contains("§6" + s.G.getDisplayName() + " 商店")) {
            return;
        }
        int index = 1;
        for (ItemStack is : s.getUpdataList()) {
            inv.setItem(index, is);
            index++;
        }
        p.updateInventory();
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent evt) {
        if (evt.getEntity().getKiller() == null) {
            return;
        }
        if (Data.PlayerData.containsKey(evt.getEntity().getName())
                && Data.PlayerData.containsKey(evt.getEntity().getKiller().getName())) {
            String n1 = Data.PlayerData.get(evt.getEntity().getName());
            String n2 = Data.PlayerData.get(evt.getEntity().getKiller().getName());
            if (!n1.equals(n2)) {
                Guy g = Data.Gyus.get(n2);
                int i = g.getMemberPoint().get(evt.getEntity().getKiller().getName()) + 5;
                g.getMemberPoint().put(evt.getEntity().getKiller().getName(), i);
                evt.getEntity().getKiller().sendMessage("§6你击杀了其他工会的玩家 获得了5点贡献度");
            }
        }
    }

    @EventHandler
    public void onClick(InventoryClickEvent evt) {
        if (evt.getClickedInventory() == null
                || evt.getClickedInventory().getTitle() == null) {
            return;
        }
        if (evt.getClickedInventory().getType() != InventoryType.CHEST) {
            return;
        }
        Guy g = Data.Gyus.get(Data.PlayerData.get(evt.getWhoClicked().getName()));
        if (g == null) {
            return;
        }

        if (!evt.getClickedInventory().getTitle().contains("§6" + g.getDisplayName() + " 商店")) {
            return;
        }
        if (evt.getCurrentItem().getType() == Material.PAPER
                && evt.getCurrentItem().hasItemMeta()
                && evt.getCurrentItem().getItemMeta().hasDisplayName()
                && evt.getCurrentItem().getItemMeta().getDisplayName().equals("§6公会仓库")) {
            evt.setCancelled(true);
            return;
        }
        Item item = g.getShop().getItem(evt.getRawSlot());
        if (!item.getItem().equals(evt.getCurrentItem())) {
            evt.setCancelled(true);
            return;
        }
        int player = g.getMemberPoint().get(evt.getWhoClicked().getName());
        if (player < item.getPrice()) {
            evt.setCancelled(true);
            return;
        }
        Utils.safeGiveItem((Player) evt.getWhoClicked(), item.getItem());
        g.getShop().ItemList.remove(item);
        evt.setCancelled(true);
        for (Player p : g.getOnlinePlayer()) {
            ShopListener.Updata((Player) evt.getWhoClicked(), g.getShop());
        }
    }
}
