/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Br.EpicGuys;

import Br.EpicGuys.Buff.BuffManager;
import Br.EpicGuys.Guy.Guy;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

/**
 *
 * @author Bryan_lzh
 */
public class Main extends JavaPlugin {

    CommandManager CM;
    public static Economy Econ = null;
    public static Main Main;

    @Override
    public void onEnable() {
        Main = this;
        if (!setupEconomy()) {
            getLogger().severe(String.format("[%s] - Vault未找到,自动卸载插件", getDescription().getName()));
            getServer().getPluginManager().disablePlugin(this);
            this.setEnabled(false);
            return;
        }
        File dataFolder = this.getDataFolder();
        if (!dataFolder.exists()) {
            dataFolder.mkdirs();
        }
        for (File f : dataFolder.listFiles()) {
            Guy g = new Guy(f);
            if (g.getDisplayName() != null) {
                Data.Gyus.put(g.getDisplayName(), g);
                for (String s : g.getOriginalMemberSet()) {
                    Data.PlayerData.put(s, g.getDisplayName());
                }
            }
        }

        CM = new CommandManager();
        CM.runTaskTimer(this, 0, 1200L);
        (new TimeTask()).runTaskTimer(this, 5L * 60L * 20L, 5L * 60L * 20L);
        Bukkit.getPluginManager().registerEvents(new ChatManager(), this);
    }

    @Override
    public void onDisable() {
        BuffManager.getBuffManager().SaveData();
        for (Guy g : Data.Gyus.values()) {
            try {
                g.Save();
            } catch (IOException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        for(String s : Data.RemoveList){
            File dataFolder = Main.Main.getDataFolder();
        if (!dataFolder.exists()) {
            dataFolder.mkdirs();
        }
        File dataFile = new File(dataFolder, s + ".yml");
        if (!dataFile.exists()) {
            continue;
        }
        dataFile.delete();
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("EpicGuys")) {
            if (args.length < 1) {
                this.ShowHelp(sender, label);
                return true;
            }
            if(args[0].equalsIgnoreCase("store")){
                CM.Store(sender, args, label);
                return true;
            }
            if(args[0].equalsIgnoreCase("point")){
                CM.Point(sender);
                return true;
            }
            if (args[0].equalsIgnoreCase("removeadmin")) {
                CM.RemoveAdmin(sender, (args.length >= 2) ? args[1] : null);
                return true;
            }
            if (args[0].equalsIgnoreCase("setadmin")) {
                CM.SetAdmin(sender, (args.length >= 2) ? args[1] : null);
                return true;
            }
            if (args[0].equalsIgnoreCase("joinlist")) {
                CM.JoinList(sender);
                return true;
            }
            if (args[0].equalsIgnoreCase("info")) {
                if (!(sender instanceof Player)) {
                    sender.sendMessage("§c此命令需要玩家才能执行");
                    return true;
                }
                if (!Data.PlayerData.containsKey(sender.getName())) {
                    sender.sendMessage("§c你还没有加入任何一个公会");
                    return true;
                }
                Guy g = Data.Gyus.get(Data.PlayerData.get(sender.getName()));
                if (g != null) {
                    sender.sendMessage(g.getInfo());
                    return true;
                }
            }
            if (args[0].equalsIgnoreCase("accept")) {
                CM.Accept(sender, (args.length >= 2) ? args[1] : null);
                return true;
            }
            if (args[0].equalsIgnoreCase("kick")) {
                CM.Kick(sender, (args.length >= 2) ? args[1] : null);
                return true;
            }
            if (args[0].equalsIgnoreCase("deliver")) {
                CM.Deliver(sender, (args.length >= 2) ? args[1] : null);
                return true;
            }
            if (args[0].equalsIgnoreCase("disband")) {
                CM.Disband(sender);
                return true;
            }
            if (args[0].equalsIgnoreCase("withdraw")) {
                CM.Withdraw(sender, (args.length >= 2) ? args[1] : null);
                return true;
            }
            if (args[0].equalsIgnoreCase("create")) {
                CM.Create(sender, (args.length >= 2) ? args[1] : null);
                return true;
            }
            if (args[0].equalsIgnoreCase("donate")) {
                CM.Donate(sender, (args.length >= 2) ? args[1] : null);
            }
            if (args[0].equalsIgnoreCase("tf") || args[0].equalsIgnoreCase("togglefire")) {
                CM.ToggleFire(sender, (args.length >= 2) ? args[1] : null);
                return true;
            }
            if (args[0].equalsIgnoreCase("tc") || args[0].equalsIgnoreCase("togglechat")) {
                CM.ToggleChat(sender, (args.length >= 2) ? args[1] : null);
                return true;
            }
            if (args[0].equalsIgnoreCase("leave")) {
                CM.Leave(sender);
                return true;
            }
            if (args[0].equalsIgnoreCase("top")) {
                CM.Top(sender);
                return true;
            }
            if (args[0].equalsIgnoreCase("join")) {
                CM.Join(sender, args[1]);
                return true;
            }
            if (args[0].equalsIgnoreCase("giveav")) {
                if (!sender.isOp()) {
                    return false;
                }
                if (args.length < 3) {
                    sender.sendMessage("§c参数不足");
                    return true;
                }
                if (!Data.Gyus.containsKey(args[1])) {
                    sender.sendMessage("§c找不到这个公会 请再次确认公会名");
                    return true;
                }
                Guy g = Data.Gyus.get(args[1]);
                try {
                    int i = Integer.parseInt(args[2]);
                    g.setActiveValue(g.getActiveValue() + i);
                    sender.sendMessage("§6已完成操作 该公会当前的活跃度为" + g.getActiveValue());
                    return true;
                } catch (NumberFormatException e) {
                    sender.sendMessage("§c你需要输入一个数量");
                    return true;
                }
            }
            if (args[0].equalsIgnoreCase("help")) {
                if (args.length < 2) {
                    this.ShowHelp(sender, label);
                    return true;
                }
                args[1] = args[1].toLowerCase();
                sender.sendMessage("§a/" + label + " " + args[1]);
                switch (args[1]) {
                    case "removeadmin":
                        sender.sendMessage("§c将某成员取消管理员");
                        return true;
                    case "setadmin":
                        sender.sendMessage("§c将某成员设置为管理员");
                        return true;
                    case "help":
                        sender.sendMessage("§c&m你不无聊吗? &c显示帮助");
                        return true;
                    case "top":
                        sender.sendMessage("§b显示公会排名前十的和自己公会的排名");
                        return true;
                    case "join":
                        sender.sendMessage("§b加入指定的工会");
                        return true;
                    case "members":
                        sender.sendMessage("§b显示你所在工会的所有玩家");
                        sender.sendMessage("§b不在线的将显示§c红色§b,在线的将显示§a绿色");
                        return true;
                    case "leave":
                        sender.sendMessage("§b离开你所在的工会");
                        return true;
                    /*case "spawn":
                        sender.sendMessage("§b传送回你的工会");
                        return true;*/
                    case "tc":
                    case "togglechat":
                        sender.sendMessage("§b打开或关闭同工会聊天");
                        sender.sendMessage("§b开启后你说的所有话都只能被同工会的看见");
                        sender.sendMessage("§b可简写为tc");
                        return true;
                    case "tf":
                    case "togglefire":
                        sender.sendMessage("§b打开或关闭同工会伤害");
                        sender.sendMessage("§b开启后你将会对同工会的玩家造成伤害");
                        sender.sendMessage("§b可简写为tf");
                        return true;
                    case "donate":
                        sender.sendMessage("§b向你所在的工会捐献金钱");
                        return true;
                    case "skills":
                        sender.sendMessage("§b显示你工会的技能列表");
                        sender.sendMessage("§b§l蓝色:已学 §a§l绿色:可用 §c§l不可用");
                        sender.sendMessage("§c本功能暂不开放");
                        return true;
                    case "learn":
                        sender.sendMessage("§b学习工会所拥有的技能 输入/" + label + " skills 查看可用的");
                        sender.sendMessage("§c本功能暂不开放");
                        return true;
                    case "create":
                        sender.sendMessage("§b创建一个新的工会");
                        return true;
                    case "withdraw":
                        sender.sendMessage("§b从工会银行中提出金钱");
                        sender.sendMessage("§e需要工会管理员");
                        return true;
                    case "disband":
                        sender.sendMessage("§b解散你的工会");
                        sender.sendMessage("§c需要工会会长");
                        return true;
                    case "deliver":
                        sender.sendMessage("§b将会长职位让给他人");
                        sender.sendMessage("§c需要工会会长");
                        return true;
                    /*case "setbase":
                        sender.sendMessage("§b设置工会的传送点");
                        sender.sendMessage("§c需要工会会长");
                        return true;*/
                    case "kick":
                        sender.sendMessage("§b踢出工会的某个玩家");
                        sender.sendMessage("§e需要工会管理员");
                        return true;
                    case "accept":
                        sender.sendMessage("§b同意某个玩家的加入请求");
                        sender.sendMessage("§e需要工会管理员");
                        return true;
                    case "reject":
                        sender.sendMessage("§b拒绝某个玩家的加入请求");
                        sender.sendMessage("§e需要工会管理员");
                        return true;
                    case "info":
                        sender.sendMessage("§b显示自己公会的信息");
                        return true;
                    case "joinlist":
                        sender.sendMessage("§b查看自己公会的加入请求");
                        sender.sendMessage("§e需要工会管理员");
                        return true;
                    case "giveav":
                        return true;
                    case "point":
                        sender.sendMessage("§b查看自己的贡献值");
                        return true;
                }
            }
            sender.sendMessage("§c未知的命令 请输入/" + label + " help 查看帮助");
            return true;
        }
        return true;
    }

    public void ShowHelp(CommandSender sender, String label) {
        sender.sendMessage(new String[]{
            ChatColor.translateAlternateColorCodes('&', "&6&lEpicGuys  工会插件"),
            ChatColor.translateAlternateColorCodes('&', "&b主命令: " + label),
            ChatColor.translateAlternateColorCodes('&', "&b&l():必填 []:可选 <y/n>:选择 &a``:简写"),
            ChatColor.translateAlternateColorCodes('&', "&d&l/" + label + " help [命令] >> 详细解释命令"),
            ChatColor.translateAlternateColorCodes('&', "&a/" + label + " top >> 显示工会排行榜"),
            ChatColor.translateAlternateColorCodes('&', "&a/" + label + " join (工会名) >> 申请加入某个工会"),
            ChatColor.translateAlternateColorCodes('&', "&a/" + label + " members >> 显示工会的所有成员"),
            ChatColor.translateAlternateColorCodes('&', "&a/" + label + " leave >> 离开所在的工会"),
            // ChatColor.translateAlternateColorCodes('&', "&a/" + label + " spawn >> 传送至工会"),
            ChatColor.translateAlternateColorCodes('&', "&a/" + label + " togglechat [<y/n>] >> 切换工会聊天模式 `tc`"),
            ChatColor.translateAlternateColorCodes('&', "&a/" + label + " togglefire [<y/n>} >> 切换工会伤害模式 `tf`"),
            ChatColor.translateAlternateColorCodes('&', "&a/" + label + " donate (金钱) >> 捐献金钱"),
            ChatColor.translateAlternateColorCodes('&', "&a/" + label + " skills >> 显示能学习的技能"),
            ChatColor.translateAlternateColorCodes('&', "&a/" + label + " learn (技能名/代号) >> 学习工会技能"),
            ChatColor.translateAlternateColorCodes('&', "&a/" + label + " create (工会名字) >> 创建一个工会"),
            ChatColor.translateAlternateColorCodes('&', "&a/" + label + " info  >> 查看自己公会的信息"),
            ChatColor.translateAlternateColorCodes('&', "&a/" + label + " point  >> 查看自己的贡献值"),
            ChatColor.translateAlternateColorCodes('&', "&a/" + label + " store  >> 商店命令"),
            ChatColor.translateAlternateColorCodes('&', "&c/" + label + " disband >> 解散工会"),
            ChatColor.translateAlternateColorCodes('&', "&c/" + label + " deliver (玩家名字) >> 移交会长"),
            ChatColor.translateAlternateColorCodes('&', "&c/" + label + " setadmin (玩家名字) >> 将某成员设置为管理员"),
            ChatColor.translateAlternateColorCodes('&', "&c/" + label + " removeadmin (玩家名字) >> 将某成员取消管理员"),
            // ChatColor.translateAlternateColorCodes('&', "&c/" + label + " setbase >> 设置工会基地"),
            ChatColor.translateAlternateColorCodes('&', "&e/" + label + " withdraw (金钱) >> 提取工会金钱"),
            ChatColor.translateAlternateColorCodes('&', "&e/" + label + " kick (玩家名字) >> 踢出玩家"),
            ChatColor.translateAlternateColorCodes('&', "&e/" + label + " accept (玩家名字) >> 同意加入请求"),
            ChatColor.translateAlternateColorCodes('&', "&e/" + label + " reject (玩家名字) [理由] >> 拒绝加入请求"),
            ChatColor.translateAlternateColorCodes('&', "&e/" + label + " joinlist  >> 查看自己公会的加入请求")
        });
        if (sender.isOp()) {
            sender.sendMessage("§c§l管理员限定命令:/" + label + " giveav (公会名字) (数量) >> 给一个公会一定的活跃度 可以为负数");
        }
    }

    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        Econ = rsp.getProvider();
        return Econ != null;
    }

}
