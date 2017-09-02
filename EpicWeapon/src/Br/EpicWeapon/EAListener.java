/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Br.EpicWeapon;

import Br.EpicAttributes.Datas.AbType;
import Br.EpicAttributes.Datas.PlayerData;
import Br.EpicAttributes.Events.AbtEvent;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType.SlotType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

/**
 *
 * @author Bryan_lzh
 */
public class EAListener implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerJoin(PlayerJoinEvent evt) {
        PlayerData pd = Br.EpicAttributes.Data.PlayerDatas.get(evt.getPlayer().getName());
        PlayerInventory pi = evt.getPlayer().getInventory();
        for (ItemStack is : pi.getArmorContents()) {
            if (is == null) {
                continue;
            }
            WeaponData wd = Utils.getWeaponData(is);
            if (wd != null) {
                for (AbType a : AbType.values()) {
                    pd.getGemAttributes().put(a, pd.getGemAttributes().get(a) + wd.WeaponAttributes.get(a));
                }
            }
        }
    }

    @EventHandler
    public void onPlayerWear(InventoryClickEvent evt) {
        if (evt.getAction() == InventoryAction.NOTHING) {
            return;
        }
        if (evt.getSlotType() != SlotType.ARMOR) {
            return;
        }
        WeaponData wd = Utils.getWeaponData(evt.getCursor());
        PlayerData pd = Br.EpicAttributes.Data.PlayerDatas.get(evt.getWhoClicked().getName());
        if (evt.getAction().toString().toLowerCase().contains("place")) {
            WeaponData 手中 = Utils.getWeaponData(evt.getCursor());
            if (手中 != null) {
                if (手中.getPermission() != null
                        && !evt.getWhoClicked().hasPermission("use." + 手中.getPermission())) {
                    evt.setCancelled(true);
                    return;
                }
                for (AbType a : AbType.values()) {
                    pd.getGemAttributes().put(a, pd.getGemAttributes().get(a) + 手中.WeaponAttributes.get(a));
                }
            }
            WeaponData 目标栏 = Utils.getWeaponData(evt.getCurrentItem());
            if (目标栏 != null) {
                for (AbType a : AbType.values()) {
                    pd.getGemAttributes().put(a, pd.getGemAttributes().get(a) - 目标栏.WeaponAttributes.get(a));
                }
            }
            pd.Refresh();
            return;
        }
        if (evt.getAction().toString().toLowerCase().contains("pickup")) {
            WeaponData 目标栏 = Utils.getWeaponData(evt.getCurrentItem());
            if (目标栏 != null) {
                for (AbType a : AbType.values()) {
                    pd.getGemAttributes().put(a, pd.getGemAttributes().get(a) - 目标栏.WeaponAttributes.get(a));
                }
            }
            pd.Refresh();
        }
    }

    @EventHandler
    public void onPlayerI(PlayerInteractEvent evt) {
        if (!evt.hasItem()) {
            return;
        }
        ItemStack is = evt.getItem();
        WeaponData wd = Utils.getWeaponData(is);
        if (wd == null) {
            return;
        }
        if (wd.getPermission() == null) {
            return;
        }
        String p = "use." + wd.getPermission();
        if (evt.getPlayer().hasPermission(p)) {
            return;
        }
        if (evt.getPlayer().getInventory().getHeldItemSlot() == 0) {
            evt.getPlayer().getInventory().setHeldItemSlot(5);
        } else {
            evt.getPlayer().getInventory().setHeldItemSlot(0);
        }
        evt.setCancelled(true);
    }

    @EventHandler
    public void onA(AbtEvent evt) {
        if (evt.getDamager() != null && (evt.getDamager() instanceof LivingEntity)) {
            LivingEntity d = (LivingEntity) evt.getDamager();
            ItemStack isd = d.getEquipment().getItemInHand();
            if (isd != null && isd.getType() != Material.AIR) {
                WeaponData wd = Utils.getWeaponData(isd);
                if (wd != null) {
                    for (AbType a : AbType.values()) {
                        evt.getDamagerAb().put(a, evt.getDamagerAb().get(a) + wd.getWeaponAttributes().get(a));
                    }
                }
            }
        }
        if (evt.getEntity() != null && (evt.getEntity() instanceof LivingEntity)) {
            LivingEntity e = (LivingEntity) evt.getEntity();
            ItemStack ise = e.getEquipment().getItemInHand();
            if (ise != null && ise.getType() != Material.AIR) {
                WeaponData wd = Utils.getWeaponData(ise);
                if (wd != null) {
                    for (AbType a : AbType.values()) {
                        evt.getEntityAb().put(a, evt.getEntityAb().get(a) + wd.getWeaponAttributes().get(a));
                    }
                }
            }
        }
    }
}
