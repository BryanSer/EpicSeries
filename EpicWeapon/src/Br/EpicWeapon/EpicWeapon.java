/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Br.EpicWeapon;

import Br.EpicAttributes.Datas.AbType;
import Br.EpicWeapon.RPGItems.RPGData;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import think.rpgitems.api.RPGItems;
import think.rpgitems.item.ItemManager;
import think.rpgitems.item.RPGItem;

/**
 *
 * @author Bryan_lzh
 */
public class EpicWeapon extends JavaPlugin {

    @Override
    public void onEnable() {
        Utils.Main = this;
        if (Bukkit.getPluginManager().isPluginEnabled("RPG_Items")) {
            try {
                RPGData.EnableRPGItem = true;
                RPGData.loadData();
            } catch (IOException ex) {
                Logger.getLogger(EpicWeapon.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        Bukkit.getPluginManager().registerEvents(new EAListener(), this);
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("EpicWeapon")) {
            if (!sender.isOp()) {
                sender.sendMessage("§c这个命令只能由管理员执行");
                return true;
            }
            if (!(sender instanceof Player)) {
                sender.sendMessage("§c这个命令不能在后台执行");
                return true;
            }
            if (args.length == 0) {
                sender.sendMessage("§c参数不足,请输入/" + label + " help 查看帮助");
                return true;
            }
            if (args[0].equalsIgnoreCase("help")) {
                sender.sendMessage(new String[]{
                    "§b/" + label + " set [属性类型] [值] 设置手上的物品的属性",
                    "§b/" + label + " list  显示属性类型的字符 用于set命令"
                });
                return true;
            }
            if (args[0].equalsIgnoreCase("list")) {
                sender.sendMessage(AbType.getDip());
                sender.sendMessage("§b特殊权限请使用 sp");
                return true;
            }
            if (args[0].equalsIgnoreCase("set")) {
                if (args.length < 3) {
                    sender.sendMessage("§c参数不足,请输入/" + label + " help 查看帮助");
                    return true;
                }
                if (args[1].equalsIgnoreCase("sp")) {
                    Player p = (Player) sender;
                    if (p.getItemInHand() == null) {
                        sender.sendMessage("§c你手上没有物品");
                        return true;
                    }
                    ItemStack is = p.getItemInHand();
                    if (is.getType() == Material.AIR || is.getAmount() == 0) {
                        sender.sendMessage("§c你手上没有物品");
                        return true;
                    }
                    WeaponData wd = Utils.getWeaponData(is);
                    wd.setPermission(args[2]);
                    return true;
                }
                AbType a = AbType.getAbType(args[1]);
                if (a == null) {
                    sender.sendMessage("§c参数错误,请输入/" + label + " list 查看属性类型");
                    return true;
                }
                int i = 0;
                try {
                    i = Integer.parseInt(args[2]);
                } catch (NumberFormatException e) {
                    sender.sendMessage("§c参数错误,数值需要输入数字");
                    return true;
                }
                if (i == 0) {
                    sender.sendMessage("§c参数异常 输入0和没输入一样的");
                    return true;
                }
                Player p = (Player) sender;
                if (p.getItemInHand() == null) {
                    sender.sendMessage("§c你手上没有物品");
                    return true;
                }
                ItemStack is = p.getItemInHand();
                if (is.getType() == Material.AIR || is.getAmount() == 0) {
                    sender.sendMessage("§c你手上没有物品");
                    return true;
                }
                WeaponData wd = Utils.getWeaponData(is);
                if (wd == null) {
                    wd = new WeaponData(is);
                }
                if (wd.isRpgitem()) {
                   
                    RPGItem rpg = ItemManager.toRPGItem(wd.getItemStack());
                    wd.getWeaponAttributes().put(a, i);
                    RPGData.Datas.put(rpg.getName(), wd.getWeaponAttributes());
                    sender.sendMessage("§b属性已添加");
                    return true;
                }
                wd.getWeaponAttributes().put(a, i);
                ItemStack iis = wd.toItemStack();
                p.setItemInHand(iis);
                sender.sendMessage("§b属性已添加");
            }
        }
        return true;
    }

    public void onDisable() {
        if (!RPGData.EnableRPGItem) {
            return;
        }
        try {
            RPGData.saveData();
        } catch (IOException ex) {
            Logger.getLogger(EpicWeapon.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
