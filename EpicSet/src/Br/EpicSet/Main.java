/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Br.EpicSet;

import Br.EpicSet.Data.Data;
import Br.EpicSet.Data.PlayerData;
import Br.EpicSet.Enums.InvMode;
import Br.EpicSet.Enums.Level;
import Br.EpicSet.Enums.Solt;
import Br.EpicSet.Gems.*;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

/**
 *
 * @author Bryan_lzh
 */
public class Main extends JavaPlugin {

    @Override
    public void onEnable() {
        Data.Main = this;
        Data.GemDatas.put("AvoidGem", new AvoidGem());
        Data.GemDatas.put("HealthGem", new HealthGem());
        Data.GemDatas.put("ResistanceGem", new ResistanceGem());
        Data.GemDatas.put("CritDamGem", new CritDamGem());
        Data.GemDatas.put("CritGem", new CritGem());
        Data.GemDatas.put("DamageGem", new DamageGem());
        ScubaGem sg = new ScubaGem();
        Data.GemDatas.put("ScubaGem", sg);
        Bukkit.getPluginManager().registerEvents(sg, this);
        Bukkit.getPluginManager().registerEvents(new ConfigListener(), this);
        Bukkit.getPluginManager().registerEvents(new InventoryListener(), this);
        for (Gem g : Data.GemDatas.values()) {
            ItemStack is = g.DrawDisplay(g.getMaxLevel());
            ItemMeta im = is.getItemMeta();
            Bukkit.getConsoleSender().sendMessage(im.getDisplayName());
            for (String s : im.getLore()) {
                Bukkit.getConsoleSender().sendMessage(s);
            }
        }
    }

    @Override
    public void onDisable() {
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("EpicSet")) {
            if (args.length == 0) {
                sender.sendMessage("§c参数不足，请输入/" + label + " help 查看帮助");
                return true;
            }
            if (args[0].equalsIgnoreCase("help")) {
                sender.sendMessage(new String[]{
                    "§b/" + label + " help >> 查看本帮助",
                    "§b" + label + "see >> 查看自己的宝石",
                    "§b" + label + "remove >> 移除一个自己的宝石",});
                if (sender.isOp()) {
                    sender.sendMessage("§b/" + label + " get [宝石名] [等级] >> 获得一个宝石");
                    sender.sendMessage("§b/" + label + " list >> 显示可用的宝石列表");
                    sender.sendMessage("§b/" + label + " unlock [玩家] [槽] >> 为一个玩家解锁一个槽");
                }
                if (!sender.isOp() && sender.hasPermission("EpicSet.unlock")) {
                    sender.sendMessage("§b/" + label + " unlock [玩家] [槽] >> 为一个玩家解锁一个槽");
                }
                return true;
            }
            if (args[0].equalsIgnoreCase("unlock")) {
                if (sender.isOp() || sender.hasPermission("EpicSet.unlock")) {
                    if (args.length < 3) {
                        sender.sendMessage("§c参数不足");
                        return true;
                    }
                    try {
                        int i = Integer.parseInt(args[1]);
                        if (!(sender instanceof Player)) {
                            return false;
                        }
                        Solt s = Solt.getSolt(i);
                        PlayerData pd = Data.PlayerDatas.get(sender.getName());
                        if (!pd.getPlayer().isOnline()) {
                            sender.sendMessage("§c玩家不在线");
                        }
                        if (s == null) {
                            sender.sendMessage("§c请输入的等级介于1~9");
                        }
                        pd.Unlock(s);
                        sender.sendMessage("§B槽位已解锁");
                        return true;
                    } catch (NumberFormatException e) {
                        if (Data.PlayerDatas.containsKey(args[1])) {
                            try {
                                PlayerData pd = Data.PlayerDatas.get(args[1]);
                                if (!pd.getPlayer().isOnline()) {
                                    sender.sendMessage("§c玩家不在线");
                                }
                                Solt s = Solt.getSolt(Integer.parseInt(args[2]));
                                if (s == null) {
                                    sender.sendMessage("§c请输入的等级介于1~9");
                                }
                                pd.Unlock(s);
                                sender.sendMessage("§B已解锁对方槽位");
                                pd.getPlayer().getPlayer().sendMessage("§b你的槽位§a" + s.getInt() + "§b已经被解锁");
                            } catch (NumberFormatException ee) {
                                sender.sendMessage("§c请输入数字槽位");
                            }
                        } else {
                            sender.sendMessage("§c没有找到该玩家");
                        }
                        return true;
                    }
                } else {
                    sender.sendMessage("§c你没有权限");
                }
                return true;
            }
            if (args[0].equalsIgnoreCase("remove")) {
                if (sender instanceof Player) {
                    Inventory inv = Data.PlayerDatas.get(sender.getName()).getInv((Player) sender);
                    InventoryListener.Modes.put(sender.getName(), InvMode.Remove);
                    ((Player) sender).openInventory(inv);
                }
                return true;
            }
            if (args[0].equalsIgnoreCase("see")) {
                if (sender instanceof Player) {
                    Inventory inv = Data.PlayerDatas.get(sender.getName()).getInv((Player) sender);
                    InventoryListener.Modes.put(sender.getName(), InvMode.See);
                    ((Player) sender).openInventory(inv);
                }
                return true;
            }
            if (args[0].equalsIgnoreCase("list")) {
                if (sender.isOp()) {
                    for (Gem g : Data.GemDatas.values()) {
                        sender.sendMessage("§b[EpicSet] " + g.getName() + " 最大等级:" + g.getMaxLevel().getDisplay());
                    }
                } else {
                    sender.sendMessage("§c你没有权限查看");
                }
                return true;
            }
            if (args[0].equalsIgnoreCase("get")) {
                if (!sender.isOp()) {
                    sender.sendMessage("§c权限不足");
                    return true;
                }
                if (!(sender instanceof Player)) {
                    return false;
                }
                if (args.length < 3) {
                    sender.sendMessage("§c参数不足");
                    sender.sendMessage("§b/" + label + " get [宝石名] [等级] >> 获得一个宝石");
                    return true;
                }
                try {
                    String GemName = args[1];
                    int i = Integer.parseInt(args[2]);
                    Gem g = Data.GemDatas.get(GemName);
                    if (g == null) {
                        sender.sendMessage("§c未找到宝石,请输入/" + label + " list 查看列表");
                        return true;
                    }
                    if (g.getMaxLevel().getInt() < i) {
                        sender.sendMessage("§c等级过高 最大为" + g.getMaxLevel().getInt());
                    }
                    Level l = Level.getLevel(i);
                    ItemStack is = g.DrawDisplay(l);
                    ((Player) sender).getInventory().addItem(is);
                } catch (NumberFormatException e) {
                    sender.sendMessage("§c等级请输入数字");
                }
            }
            return true;
        }
        return false;
    }
}
