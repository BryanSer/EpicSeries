/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Br.EpicAttributes;

import Br.EpicAttributes.Datas.AbType;
import Br.EpicAttributes.Datas.ItemAndSlot;
import Br.EpicAttributes.Datas.PlayerData;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.scheduler.BukkitRunnable;

/**
 *
 * @author Administrator
 */
public class InvListener implements Listener {

    @EventHandler
    public void onPlayerClick(InventoryClickEvent evt) {
        if (evt.getInventory().getTitle() == null) {
            return;
        }
        if (!evt.getInventory().getTitle().equalsIgnoreCase(PlayerData.Code)) {
            return;
        }
        if (evt.getClickedInventory() == null) {
            return;
        }
        if (evt.getClickedInventory().getType() == InventoryType.PLAYER) {
            evt.getWhoClicked().closeInventory();
            return;
        }
        if (evt.getCurrentItem() == null) {
            return;
        }
        if (evt.getCurrentItem().getType() == Material.AIR) {
            return;
        }

        Material type = evt.getCurrentItem().getType();
        PlayerData pd = Data.PlayerDatas.get(evt.getWhoClicked().getName());
        switch (type) {
            case PAPER:
            case EXP_BOTTLE:
                evt.setCancelled(true);
                return;
            case REDSTONE:
                if (this.hasEnoughPoints(pd)) {
                    if (!pd.Point(AbType.Atk, 1)) {
                        pd.addPoints(1);
                    }
                }
                this.UpdateInv(pd, evt.getWhoClicked(), evt,AbType.Atk);
                return;
            case IRON_CHESTPLATE:
                //case SHIELD:
                if (this.hasEnoughPoints(pd)) {
                    if (!pd.Point(AbType.Def, 1)) {
                        pd.addPoints(1);
                    }
                }
                this.UpdateInv(pd, evt.getWhoClicked(), evt,AbType.Def);
                return;
            case IRON_AXE:
                if (this.hasEnoughPoints(pd)) {
                    if (!pd.Point(AbType.Crit, 1)) {
                        pd.addPoints(1);
                    }
                }
                this.UpdateInv(pd, evt.getWhoClicked(), evt,AbType.Crit);
                return;
            case TNT:
                if (this.hasEnoughPoints(pd)) {
                    if (!pd.Point(AbType.CritDam, 1)) {
                        pd.addPoints(1);
                    }
                }
                this.UpdateInv(pd, evt.getWhoClicked(), evt,AbType.CritDam);
                return;
            case REDSTONE_BLOCK:
                if (this.hasEnoughPoints(pd)) {
                    if (!pd.Point(AbType.Vampire, 1)) {
                        pd.addPoints(1);
                    }
                }
                this.UpdateInv(pd, evt.getWhoClicked(), evt,AbType.Vampire);
                return;
            case LEATHER_CHESTPLATE:
                if (this.hasEnoughPoints(pd)) {
                    if (!pd.Point(AbType.Sunder, 1)) {
                        pd.addPoints(1);
                    }
                }
                this.UpdateInv(pd, evt.getWhoClicked(), evt,AbType.Sunder);
                return;
            case BEACON:
                if (this.hasEnoughPoints(pd)) {
                    if (!pd.Point(AbType.Health, 1)) {
                        pd.addPoints(1);
                    }
                }
                this.UpdateInv(pd, evt.getWhoClicked(), evt,AbType.Health);
                return;
            case GOLDEN_APPLE:
                if (this.hasEnoughPoints(pd)) {
                    if (!pd.Point(AbType.Recover, 1)) {
                        pd.addPoints(1);
                    }
                }
                this.UpdateInv(pd, evt.getWhoClicked(), evt,AbType.Recover);
                return;
            case DIAMOND_BOOTS:
                if (this.hasEnoughPoints(pd)) {
                    if (!pd.Point(AbType.Avoid, 1)) {
                        pd.addPoints(1);
                    }
                }
                this.UpdateInv(pd, evt.getWhoClicked(), evt,AbType.Avoid);
                return;
            case GLASS:
                if (this.hasEnoughPoints(pd)) {
                    if (!pd.Point(AbType.HitP, 1)) {
                        pd.addPoints(1);
                    }
                }
                this.UpdateInv(pd, evt.getWhoClicked(), evt,AbType.HitP);
                return;
        }
    }

    /*he.openInventory(Data.PlayerDatas.get(he.getName()).getInv((Player) he));
                this.cancel();*/
    public void UpdateInv(PlayerData pd, HumanEntity he, InventoryClickEvent evt,AbType a) {
        evt.setCancelled(true);
        if (he instanceof Player) {
            ItemAndSlot d = pd.getDisplay(a);
            evt.getClickedInventory().setItem(d.getSlot(), d.getItemStack());
            ItemAndSlot d2 = pd.getInfoItem();
            evt.getClickedInventory().setItem(d2.getSlot(), d2.getItemStack());
            Player p = (Player) he;
            p.updateInventory();
        }
    }

    /**
     * 如果返回true则为已经扣除1点
     *
     * @param pd
     * @return
     */
    public boolean hasEnoughPoints(PlayerData pd) {
        if (pd.getPoints() >= 1) {
            pd.setPoints(pd.getPoints() - 1);
            return true;
        } else {
            return false;
        }
    }
}
