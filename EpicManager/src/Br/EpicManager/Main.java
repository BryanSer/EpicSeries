/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Br.EpicManager;

import Br.EpicAttributes.Datas.AbType;
import Br.EpicAttributes.Utils;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

/**
 *
 * @author Administrator
 */
public class Main extends JavaPlugin {

    @Override
    public void onEnable() {
        if (Bukkit.getPluginManager().isPluginEnabled("ServerSign")) {
            Data.Set = true;
        }
        if (Bukkit.getPluginManager().isPluginEnabled("EpicMob")) {
            Data.Mob = true;
        }
        if (Bukkit.getPluginManager().isPluginEnabled("WeponHand")) {
            Data.Weapon = true;
        }
        Bukkit.getPluginManager().registerEvents(new EpicListener(), this);
    }

    public boolean onCommand(CommandSender sender, Command cmd, String l, String[] args) {
        l = l + " ";
        if (cmd.getName().equalsIgnoreCase("EpicManager")) {
            if (args.length == 0) {
                this.sendHelp(sender, l);
                return true;
            }
            if (args[0].equalsIgnoreCase("help")) {
                this.sendHelp(sender, l);
                return true;
            }

            if (!sender.isOp() && !sender.hasPermission("EpicManager.use")) {
                sender.sendMessage("§c你没有权限访问管理器，请确认自己的权限后尝试");
                return true;
            }

            if (args[0].equalsIgnoreCase("a")) {
                if (args.length < 2) {
                    sender.sendMessage(new String[]{
                        "§b/" + l + "§da give [玩家] [数量]",
                        "                 >>  §a给予一个玩家[数量]的属性点,可为负值 - EA",
                        "§b/" + l + "§da set [玩家] [数量]",
                        "                 >>  §a设置一个玩家的属性点,最低为0 - EA",
                        "§b/" + l + "§da giveall [数量]",
                        "                 >>  §a给予在线的全体玩家[数量]的属性点,可为负值 - EA",
                        "§b/" + l + "§da reset [玩家]",
                        "                 >>  §a完全重置某个玩家的属性点数据,将会暂时踢出服务器 - EA",
                        "§b/" + l + "§da pointlist",
                        "                 >>  §a查看属性列表,用于其他命令 - EA",
                        "§b/" + l + "§da point [玩家] [属性] [点数]",
                        "                 >>  §a设置一个玩家的某个属性为某个点数 - EA",
                        "§b/" + l + "§da get [玩家]",
                        "                 >>  §a获取一个玩家的属性状态 - EA",});
                    return true;
                }

                if (args[1].equalsIgnoreCase("help")) {
                    sender.sendMessage(new String[]{
                        "§b/" + l + "§da give [玩家] [数量]",
                        "                 >>  §a给予一个玩家[数量]的可分配的属性点,可为负值 - EA",
                        "§b/" + l + "§da set [玩家] [数量]",
                        "                 >>  §a设置一个玩家的可分配的属性点,最低为0 - EA",
                        "§b/" + l + "§da giveall [数量]",
                        "                 >>  §a给予在线的全体玩家[数量]的可分配的属性点,可为负值 - EA",
                        "§b/" + l + "§da reset [玩家]",
                        "                 >>  §a完全重置某个玩家的属性点数据,将会暂时踢出服务器 - EA",
                        "§b/" + l + "§da pointlist",
                        "                 >>  §a查看属性列表,用于其他命令 - EA",
                        "§b/" + l + "§da point [玩家] [属性] [点数]",
                        "                 >>  §a设置一个玩家的某个属性为某个点数 - EA",
                        "§b/" + l + "§da get [玩家]",
                        "                 >>  §a获取一个玩家的属性状态 - EA"});
                    return true;
                }

                if (args[1].equalsIgnoreCase("give")) {
                    if (args.length != 4) {
                        sender.sendMessage(new String[]{
                            "§c参数错误",
                            "§b/" + l + "§da give [玩家] [数量]",
                            "                 >>  §a给予一个玩家[数量]的属性点,可为负值 - EA",});
                        return true;
                    }
                    Br.EpicAttributes.Datas.PlayerData pd = Br.EpicAttributes.Data.PlayerDatas.get(args[2]);
                    boolean of = false;
                    if (pd == null) {
                        OfflinePlayer op = Bukkit.getOfflinePlayer(args[2]);
                        if (op == null) {
                            sender.sendMessage("§c没有找到该玩家");
                            return true;
                        }
                        pd = Utils.getOfflineData(op);
                        if (pd == null) {
                            sender.sendMessage("§c发生了错误");
                            return true;
                        }
                        of = true;
                    }
                    try {
                        pd.addPoints(Integer.parseInt(args[3]));
                    } catch (NumberFormatException e) {
                        sender.sendMessage("§c请输入数字");
                        return true;
                    }
                    if (of) {
                        pd.save();
                    }
                    sender.sendMessage("§b操作已完成");
                    return true;
                }

                if (args[1].equalsIgnoreCase("set")) {
                    if (args.length != 4) {
                        sender.sendMessage(new String[]{
                            "§c参数错误",
                            "§b/" + l + "§da set [玩家] [数量]",
                            "                 >>  §a设置一个玩家的可分配的属性点,最低为0 - EA",});
                        return true;
                    }
                    Br.EpicAttributes.Datas.PlayerData pd = Br.EpicAttributes.Data.PlayerDatas.get(args[2]);
                    boolean of = false;
                    if (pd == null) {
                        OfflinePlayer op = Bukkit.getOfflinePlayer(args[2]);
                        if (op == null) {
                            sender.sendMessage("§c没有找到该玩家");
                            return true;
                        }
                        pd = Utils.getOfflineData(op);
                        if (pd == null) {
                            sender.sendMessage("§c发生了错误");
                            return true;
                        }
                        of = true;
                    }
                    try {
                        pd.setPoints(Integer.parseInt(args[3]));
                    } catch (NumberFormatException e) {
                        sender.sendMessage("§c请输入数字");
                        return true;
                    }
                    if (of) {
                        pd.save();
                    }
                    sender.sendMessage("§b操作已完成");
                    return true;
                }

                if (args[1].equalsIgnoreCase("giveall")) {
                    if (args.length != 3) {
                        sender.sendMessage(new String[]{
                            "§c参数错误",
                            "§b/" + l + "§da giveall [数量]",
                            "                 >>  §a给予在线的全体玩家[数量]的属性点,可为负值 - EA",});
                        return true;
                    }
                    int point = 0;
                    try {
                        point = Integer.parseInt(args[2]);
                    } catch (NumberFormatException e) {
                        sender.sendMessage("§c请输入数字");
                        return true;
                    }

                    for (Player p : this.getOnlinePlayers()) {
                        Br.EpicAttributes.Datas.PlayerData pd = Br.EpicAttributes.Data.PlayerDatas.get(p.getName());
                        pd.addPoints(point);
                    }
                    sender.sendMessage("§b操作已完成");
                    return true;
                }
                
                if (args[1].equalsIgnoreCase("reset")) {
                    if (args.length != 3) {
                        sender.sendMessage(new String[]{
                            "§c参数错误",
                            "§b/" + l + "§da reset [玩家]",
                            "                 >>  §a§a完全重置某个玩家的属性点数据,将会暂时踢出服务器 - EA",});
                        return true;
                    }
                    OfflinePlayer op = Bukkit.getOfflinePlayer(args[2]);
                    if (op == null) {
                        sender.sendMessage("§c没有找到该玩家");
                        return true;
                    }
                    if (!op.isOnline()) {
                        Br.EpicAttributes.Datas.PlayerData pd = Utils.getOfflineData(op);
                        pd.Reset();
                        pd.setPoints(Br.EpicAttributes.Datas.PlayerData.DefaultPoints);
                        pd.save();
                        
                    } else {
                        Br.EpicAttributes.Datas.PlayerData pd = Br.EpicAttributes.Data.PlayerDatas.get(op.getName());
                        if (pd != null) {
                            pd.Reset();
                            pd.setPoints(Br.EpicAttributes.Datas.PlayerData.DefaultPoints);
                        }
                        op.getPlayer().kickPlayer("重置数据中");
                    }
                    sender.sendMessage("§b操作已完成");
                    return true;
                }

                if (args[1].equalsIgnoreCase("pointlist")) {
                    sender.sendMessage(AbType.getDip());
                    return true;
                }

                if (args[1].equalsIgnoreCase("point")) {
                    if (args.length != 5) {
                        sender.sendMessage(new String[]{
                            "§c参数错误",
                            "§b/" + l + "§da point [玩家] [属性] [点数]",
                            "                 >>  §a设置一个玩家的某个属性为某个点数 - EA",});
                        return true;
                    }
                    Br.EpicAttributes.Datas.PlayerData pd = Br.EpicAttributes.Data.PlayerDatas.get(args[2]);
                    boolean of = false;
                    if (pd == null) {
                        OfflinePlayer op = Bukkit.getOfflinePlayer(args[2]);
                        if (op == null) {
                            sender.sendMessage("§c没有找到该玩家");
                            return true;
                        }
                        pd = Utils.getOfflineData(op);
                        if (pd == null) {
                            sender.sendMessage("§c发生了错误");
                            return true;
                        }
                        of = true;
                    }
                    AbType type = AbType.getAbType(args[3]);
                    if (type == null) {
                        sender.sendMessage("§c属性不存在 请输入/" + l + "§da pointlist  查看");
                        return true;
                    }
                    try {
                        pd.setAbtype(type, Integer.parseInt(args[4]));
                    } catch (NumberFormatException e) {
                        sender.sendMessage("§c请输入数字");
                        return true;
                    }
                    if (of) {
                        pd.save();
                    }
                    sender.sendMessage("§b操作已完成");
                    return true;
                }
                
                if (args[1].equalsIgnoreCase("get")) {
                    if (args.length != 3) {
                        sender.sendMessage(new String[]{
                            "§c参数错误",
                            "§b/" + l + "§da get [玩家]",
                            "                 >>  §a获取一个玩家的属性状态 - EA",});
                        return true;
                    }
                    Br.EpicAttributes.Datas.PlayerData pd = Br.EpicAttributes.Data.PlayerDatas.get(args[2]);
                    if (pd == null) {
                        OfflinePlayer op = Bukkit.getOfflinePlayer(args[2]);
                        if (op == null) {
                            sender.sendMessage("§c没有找到该玩家");
                            return true;
                        }
                        pd = Utils.getOfflineData(op);
                        if (pd == null) {
                            sender.sendMessage("§c发生了错误");
                            return true;
                        }
                    }
                    sender.sendMessage(pd.getInfo());
                    return true;
                }
            }

            if (args[0].equalsIgnoreCase("s")) {
                if (Data.Set == false) {
                    sender.sendMessage("§c插件未安装");
                    return true;
                }
                if (args[1].equalsIgnoreCase("help")) {
                    sender.sendMessage(new String[]{
                        "§b/" + l + "§6s reset [玩家]",
                        "                 >>  §a完全重置某个玩家的镶嵌数据,将会暂时踢出服务器 - ES",
                        "§b/" + l + "§6s get [玩家]",
                        "                 >>  §a获取一个玩家的镶嵌状态 - ES",});
                    return true;
                }
                if (args[1].equalsIgnoreCase("reset")) {
                    if (args.length != 3) {
                        sender.sendMessage(new String[]{
                            "§c参数错误",
                            "§b/" + l + "§6s reset [玩家]",
                            "                 >>  §a完全重置某个玩家的镶嵌数据,将会暂时踢出服务器 - ES",});
                        return true;
                    }
                    OfflinePlayer op = Bukkit.getOfflinePlayer(args[2]);
                    if (op == null) {
                        sender.sendMessage("§c未找到该玩家");
                        return true;
                    }
                    if (op.isOnline()) {
                        Br.EpicSet.Data.PlayerData pd = Br.EpicSet.Data.Data.PlayerDatas.get(op.getName());
                        if (pd != null) {
                            pd.Reset(op.getPlayer());
                        }
                        op.getPlayer().kickPlayer("重置数据中");
                    } else {
                        Br.EpicSet.Data.PlayerData pd = Br.EpicSet.Data.PlayerData.loadConfig_of(op.getName(), Br.EpicSet.ConfigListener.getFileConfiguration(op));
                        pd.Reset();
                        Br.EpicSet.ConfigListener.Save(pd);
                    }
                    sender.sendMessage("§b操作已完成");
                }
            }
        }
        return true;
    }

    public void sendHelp(CommandSender cs, String l) {
        cs.sendMessage("§e§lEpicManager——Epic系列管理器");
        if (!cs.isOp() && !cs.hasPermission("EpicManager.use")) {
            cs.sendMessage("§c你没有权限访问管理器，请确认自己的权限后尝试");
            return;
        }
        cs.sendMessage(new String[]{
            "§b/" + l + "list  >>  §a显示已安装的Epic系列",
            "§b/" + l + "§da give [玩家] [数量]",
            "                 >>  §a给予一个玩家[数量]的属性点,可为负值 - EA",
            "§b/" + l + "§da set [玩家] [数量]",
            "                 >>  §a设置一个玩家的属性点,最低为0 - EA",
            "§b/" + l + "§da giveall [数量]",
            "                 >>  §a给予在线的全体玩家[数量]的属性点,可为负值 - EA",
            "§b/" + l + "§da reset [玩家]",
            "                 >>  §a完全重置某个玩家的属性点数据,将会暂时踢出服务器 - EA",
            "§b/" + l + "§da pointlist",
            "                 >>  §a查看属性列表,用于其他命令 - EA",
            "§b/" + l + "§da point [玩家] [属性] [点数]",
            "                 >>  §a设置一个玩家的某个属性为某个点数 - EA",
            "§b/" + l + "§da get [玩家]",
            "                 >>  §a获取一个玩家的属性状态 - EA",
            "§b/" + l + "§6s reset [玩家]",
            "                 >>  §a完全重置某个玩家的镶嵌数据,将会暂时踢出服务器 - ES",
            "§b/" + l + "§6s get [玩家]",
            "                 >>  §a获取一个玩家的镶嵌状态 - ES", //  "§b/" + l + "§ew check",
        //   "                 >>  §a检查手上的物品是否有属性,并且显示属性 - EW"
        });
    }

    private List<Player> getOnlinePlayers() {
        List<Player> resule = new ArrayList<>();
        try {
            Method onlinePlayerMethod = Server.class.getMethod("getOnlinePlayers");
            if (onlinePlayerMethod.getReturnType().equals(Collection.class)) {
                return new ArrayList<>(((Collection<Player>) onlinePlayerMethod.invoke(Bukkit.getServer())));
            } else {
                resule.addAll(Arrays.asList((Player[]) onlinePlayerMethod.invoke(Bukkit.getServer())));
            }
        } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
        }
        return resule;
    }
}
