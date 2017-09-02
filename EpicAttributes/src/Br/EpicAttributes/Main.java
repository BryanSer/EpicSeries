/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Br.EpicAttributes;

import Br.EpicAttributes.Datas.AbType;
import Br.EpicAttributes.Datas.PlayerData;
import Br.EpicAttributes.Skills.SkillType;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

/**
 *
 * @author Bryan_lzh
 */
public class Main extends JavaPlugin {

    @Override
    public void onEnable() {
        Data.Main = this;
        for (SkillType s : SkillType.values()) {
            SkillType.Datas.put(s.getSkill().getFindCode(), s);
        }
        Bukkit.getPluginManager().registerEvents(new ConfigListener(), this);
        Bukkit.getPluginManager().registerEvents(new AtkListener(), this);
        Bukkit.getPluginManager().registerEvents(new InvListener(), this);
        RecoverTask rt = new RecoverTask();
        rt.runTaskTimer(this, 120L, 40L);

        (new AutoSaveTask()).runTaskTimer(this, 1000L, 60L * 20L);

        for (AbType a : AbType.values()) {
        }

        for (SkillType st : SkillType.values()) {
            Bukkit.getConsoleSender().sendMessage("§6[" + st.getSkill().getFindCode() + "] " + st.getSkill().getDisplayName() + " ");
            Bukkit.getConsoleSender().sendMessage(st.getSkill().getDescription());
        }
    }

    @Override
    public void onDisable() {
        for (PlayerData pd : Data.PlayerDatas.values()) {
            pd.save();
        }
        Data.PlayerDatas.clear();
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("EpicAttributes")) {
            if (args.length == 0) {
                sender.sendMessage("§c参数不足 请输入/" + label + " help 查看帮助");
                return true;
            }
            if (args[0].equalsIgnoreCase("help")) {
                sender.sendMessage(new String[]{
                    "§b/" + label + " help       >> 查看本帮助",
                    "§b/" + label + " add        >> 打开加点面板",
                    "§b/" + label + " skill      >> 查看你学会的技能",
                    "§b/" + label + " skill [ID] >> 查看某个技能的详细说明",
                    "§b/" + label + " skills     >> 这个服务器上所有的技能"
                });
                if (sender.isOp()) {
                    sender.sendMessage(new String[]{
                        "§b/" + label + " give [玩家] [数量] >> 给予某个玩家点数",
                        "§b/" + label + " get [数量]         >> 为自己获取数量 可与rpgitem配合"
                    });
                }
            }
            if (args[0].equalsIgnoreCase("skill")) {
                if (!(sender instanceof Player)) {
                    if (args.length < 2) {
                        return false;
                    }
                    SkillType st = SkillType.FindByCode(args[1]);
                    if (st == null) {
                        sender.sendMessage("§c没有找到这个技能");
                        return true;
                    }
                    sender.sendMessage("§6[" + st.getSkill().getFindCode() + "] " + st.getSkill().getDisplayName() + " ");
                    sender.sendMessage(st.getSkill().getDescription());
                    return true;
                }
                PlayerData pd = Data.PlayerDatas.get(sender.getName());
                if (pd == null) {
                    sender.sendMessage("§c找不到你的数据");
                    return true;
                }
                if (args.length < 2) {
                    for (SkillType st : pd.getSkills()) {
                        sender.sendMessage("§6[" + st.getSkill().getFindCode() + "] " + st.getSkill().getDisplayName() + " ");
                    }
                    sender.sendMessage("§b输入/" + label + " skill [ID] 可查看技能详细说明 "
                            + "\n§bID为上面的[]中的大写字母");
                    return true;
                }
                SkillType st = SkillType.FindByCode(args[1]);
                if (st == null) {
                    sender.sendMessage("§c没有找到这个技能");
                    return true;
                }
                if (pd.getSkills().contains(st)) {
                    sender.sendMessage("§6[" + st.getSkill().getFindCode() + "] " + st.getSkill().getDisplayName() + " ");
                    sender.sendMessage(st.getSkill().getDescription());
                    return true;
                } else {
                    if (sender.isOp()) {
                        sender.sendMessage("§6[" + st.getSkill().getFindCode() + "] " + st.getSkill().getDisplayName() + " ");
                        sender.sendMessage(st.getSkill().getDescription());
                        return true;
                    }
                    sender.sendMessage("§6[???] ????????????? ");
                    sender.sendMessage(new String[]{
                        "§b?????????????????",
                        "§b?????????????????",
                        "§b?????????????????",
                        "§b?????????????????"});
                    return true;
                }
            }
            if (args[0].equalsIgnoreCase("skills")) {
                if (!(sender instanceof Player) || sender.isOp()) {
                    for (SkillType s : SkillType.values()) {
                        sender.sendMessage("§6[" + s.getSkill().getFindCode() + "] " + s.getSkill().getDisplayName() + " ");
                    }
                    sender.sendMessage("§b输入/" + label + " skill [ID] 可查看技能详细说明 "
                            + "\n§bID为上面的[]中的大写字母");
                    return true;
                } else {
                    PlayerData pd = Data.PlayerDatas.get(sender.getName());
                    if (pd == null) {
                        sender.sendMessage("§c找不到你的数据");
                        return true;
                    }
                    for (SkillType s : SkillType.values()) {
                        if (pd.getSkills().contains(s)) {
                            sender.sendMessage("§6[" + s.getSkill().getFindCode() + "] " + s.getSkill().getDisplayName() + " ");
                        } else {
                            sender.sendMessage("§6[???] ????????????? ");
                        }
                    }
                    sender.sendMessage("§b输入/" + label + " skill [ID] 可查看技能详细说明 "
                            + "\n§bID为上面的[]中的大写字母");
                    return true;
                }
            }
            if (args[0].equalsIgnoreCase("get")) {
                if (!sender.isOp() && !sender.hasPermission("EpicAttributes.get")) {
                    return false;
                }
                if (!(sender instanceof Player)) {
                    sender.sendMessage("§c这个命令只能由玩家发出");
                    return true;
                }
                PlayerData pd = Data.PlayerDatas.get(sender.getName());
                if (pd == null) {
                    sender.sendMessage("§c没有找到你的数据");
                    return false;
                }
                if (args.length < 2) {
                    sender.sendMessage("§c参数过短");
                    return true;
                }
                int i = 0;
                try {
                    i = Integer.parseInt(args[1]);
                } catch (NumberFormatException e) {
                    sender.sendMessage("§c请输入数字");
                    return true;
                }
                pd.addPoints(i);
                sender.sendMessage("§b你已获得了: " + i + " 点属性值");
                return true;
            }
            if (args[0].equalsIgnoreCase("add")) {
                if (!(sender instanceof Player)) {
                    return false;
                }
                PlayerData pd = Data.PlayerDatas.get(sender.getName());
                if (pd == null) {
                    return false;
                }
                ((Player) sender).openInventory(pd.getInv((Player) sender));
                return true;
            }
            if (args[0].equalsIgnoreCase("info")) {
                if (!(sender instanceof Player)) {
                    return false;
                }
                Player p = (Player) sender;
                PlayerData pd = Data.PlayerDatas.get(p.getName());
                if (pd == null) {
                    sender.sendMessage("§c发生了错误");
                    return true;
                }
                sender.sendMessage(pd.getInfo());
                return true;
            }
            if (args[0].equalsIgnoreCase("give")) {
                if (!sender.isOp()) {
                    sender.sendMessage("§c你的权限不足");
                    return true;
                }
                if (args.length < 3) {
                    sender.sendMessage("§c参数不足 应该是: /" + label + " give [玩家] [数量]");
                    return true;
                }
                int point = 0;
                try {
                    point = Integer.parseInt(args[2]);
                } catch (NumberFormatException e) {
                    sender.sendMessage("§c参数错误 请输入数字");
                    return true;
                }
                PlayerData pd = Data.PlayerDatas.get(Bukkit.getOfflinePlayer(args[1]).getName());
                if (pd == null) {
                    sender.sendMessage("§c玩家未找到");
                    return true;
                }
                pd.addPoints(point);
                return true;
            }
            return true;
        }
        return false;
    }
}
