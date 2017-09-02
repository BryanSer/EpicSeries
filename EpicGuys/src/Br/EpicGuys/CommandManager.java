/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Br.EpicGuys;

import Br.EpicGuys.Guy.Competence;
import Br.EpicGuys.Guy.Guy;
import Br.API.Map.SortableMap;
import Br.EpicGuys.Guy.Item;
import java.text.DateFormat;
import java.util.Date;
import java.util.Map;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.scheduler.BukkitRunnable;

/**
 *
 * @author Administrator
 */
public class CommandManager extends BukkitRunnable {

    private SortableMap<Guy, Integer> Top = new SortableMap<>();
    private Map<Guy, Integer> TempTop;
    private long Date;

    public void Store(CommandSender sender, String args[], String l) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§c此命令需要玩家才能执行");
            return;
        }
        if (!Data.PlayerData.containsKey(sender.getName())) {
            sender.sendMessage("§c你还没有加入任何一个公会");
            return;
        }
        Guy g = Data.Gyus.get(Data.PlayerData.get(sender.getName()));
        if (g == null) {
            return;
        }
        if (args.length < 2 || args[1].equalsIgnoreCase("help")) {
            sender.sendMessage(new String[]{
                "§a/" + l + " " + args[0] + "open >> 打开自己公会的商店",
                "§e/" + l + " " + args[0] + "add [售价] >> 将手上的物品放入公会商店 需要管理员以上",
                "§e            注意 如果手上有多个物品将会一起上架 并且购买也是多个捆绑购买"
            });
            return;
        }
        if (args[1].equalsIgnoreCase("open")) {
            Inventory inv = g.getShop().getShop((Player) sender);
            ((Player) sender).openInventory(inv);
            return;
        }
        if (args[1].equalsIgnoreCase("add")) {
            if (g.getWhoHaveAdmin().contains(sender.getName())) {
                Player p = (Player) sender;
                if (p.getItemInHand() == null
                        || p.getItemInHand().getType() == Material.AIR
                        || p.getItemInHand().getAmount() == 0) {
                    sender.sendMessage("§c你的手上什么都没有");
                    return;
                }
                if (args.length < 3) {
                    sender.sendMessage("§c参数不足");
                    return;
                }
                int price = 0;
                try {
                    price = Integer.parseInt(args[2]);
                } catch (NumberFormatException e) {
                    sender.sendMessage("§c参数错误");
                    return;
                }
                if(price <=0){
                    sender.sendMessage("§c参数过小");
                    return;
                }
                Item i = new Item(p.getItemInHand().clone(), p.getName(), price);
                p.getItemInHand().setAmount(0);
                g.getShop().ItemList.add(i);
                sender.sendMessage("§6上架成功");
            } else {
                sender.sendMessage("§c你没有公会管理权限");
                return;
            }
        }
    }

    public void Point(CommandSender sender) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§c此命令需要玩家才能执行");
            return;
        }
        if (!Data.PlayerData.containsKey(sender.getName())) {
            sender.sendMessage("§c你还没有加入任何一个公会");
            return;
        }
        Guy g = Data.Gyus.get(Data.PlayerData.get(sender.getName()));
        if (g != null) {
            sender.sendMessage("§6你当前的贡献值为:" + g.getMemberPoint().get(sender.getName()));
        }
    }

    public void RemoveAdmin(CommandSender sender, String args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§c此命令需要玩家才能执行");
            return;
        }
        if (!Data.PlayerData.containsKey(sender.getName())) {
            sender.sendMessage("§c你还没有加入任何一个公会");
            return;
        }
        if (args == null) {
            sender.sendMessage("§c你需要输入一个玩家名字");
            return;
        }
        OfflinePlayer op = Bukkit.getPlayer(args);
        if (op != null && op.hasPlayedBefore()) {
            args = op.getName();
        }
        Guy g = Data.Gyus.get(Data.PlayerData.get(sender.getName()));
        if (g != null) {
            if (!g.getOwner().equals(sender.getName())) {
                sender.sendMessage("§c需要会长才能设置");
                return;
            }
            if (!g.getOriginalMemberSet().contains(args)) {
                sender.sendMessage("§c查无此人");
                return;
            }
            if (!g.getWhoHaveAdmin().contains(args)) {
                sender.sendMessage("§c对方本来就没有管理权限");
                return;
            }
            g.removeAdmin(args);
            sender.sendMessage("§6已取消对方的管理员权限");
        }
    }

    public void SetAdmin(CommandSender sender, String args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§c此命令需要玩家才能执行");
            return;
        }
        if (!Data.PlayerData.containsKey(sender.getName())) {
            sender.sendMessage("§c你还没有加入任何一个公会");
            return;
        }
        if (args == null) {
            sender.sendMessage("§c你需要输入一个玩家名字");
            return;
        }
        OfflinePlayer op = Bukkit.getPlayer(args);
        if (op != null && op.hasPlayedBefore()) {
            args = op.getName();
        }
        Guy g = Data.Gyus.get(Data.PlayerData.get(sender.getName()));
        if (g != null) {
            if (!g.getOwner().equals(sender.getName())) {
                sender.sendMessage("§c需要会长才能设置");
                return;
            }
            if (!g.getOriginalMemberSet().contains(args)) {
                sender.sendMessage("§c查无此人");
                return;
            }
            if (g.getWhoHaveAdmin().contains(args)) {
                sender.sendMessage("§c对方已有管理权限");
                return;
            }
            g.setAdmin(args);
            sender.sendMessage("§6已设置对方为管理员");
        }
    }

    public void JoinList(CommandSender sender) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§c此命令需要玩家才能执行");
            return;
        }
        if (!Data.PlayerData.containsKey(sender.getName())) {
            sender.sendMessage("§c你还没有加入任何一个公会");
            return;
        }
        Guy g = Data.Gyus.get(Data.PlayerData.get(sender.getName()));
        if (g != null) {
            if (!g.getWhoHaveAdmin().contains(sender.getName())) {
                sender.sendMessage("§c你没有管理权限 查看列表");
                return;
            }
            int i = 1;
            for (String s : g.getTempJoinAsk()) {
                sender.sendMessage("§e[" + i++ + "] §a" + s);
            }
            sender.sendMessage("§6请输入/eg accept [名字] 来同意请求");
        }
    }

    public void Reject(CommandSender sender, String args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§c此命令需要玩家才能执行");
            return;
        }
        if (!Data.PlayerData.containsKey(sender.getName())) {
            sender.sendMessage("§c你还没有加入任何一个公会");
            return;
        }
        if (args == null) {
            sender.sendMessage("§c你需要输入一个玩家名字");
            return;
        }
        OfflinePlayer op = Bukkit.getPlayer(args);
        if (op != null && op.hasPlayedBefore()) {
            args = op.getName();
        }
        Guy g = Data.Gyus.get(Data.PlayerData.get(sender.getName()));
        if (g != null) {
            sender.sendMessage(g.RejectJoin(args));
        }
    }

    public void Accept(CommandSender sender, String args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§c此命令需要玩家才能执行");
            return;
        }
        if (!Data.PlayerData.containsKey(sender.getName())) {
            sender.sendMessage("§c你还没有加入任何一个公会");
            return;
        }
        if (args == null) {
            sender.sendMessage("§c你需要输入一个玩家名字");
            return;
        }
        OfflinePlayer op = Bukkit.getPlayer(args);
        if (op != null && op.hasPlayedBefore()) {
            args = op.getName();
        }
        Guy g = Data.Gyus.get(Data.PlayerData.get(sender.getName()));
        if (g != null) {
            if (!g.getWhoHaveAdmin().contains(sender.getName())) {
                sender.sendMessage("§c你没有管理权限 无法同意");
                return;
            }
            sender.sendMessage(g.AcceptJoin(args));
        }
    }

    public void Kick(CommandSender sender, String args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§c此命令需要玩家才能执行");
            return;
        }
        if (!Data.PlayerData.containsKey(sender.getName())) {
            sender.sendMessage("§c你还没有加入任何一个公会");
            return;
        }
        if (args == null) {
            sender.sendMessage("§c你需要输入一个玩家名字");
            return;
        }
        OfflinePlayer op = Bukkit.getPlayer(args);
        if (op != null && op.hasPlayedBefore()) {
            args = op.getName();
        }
        Guy g = Data.Gyus.get(Data.PlayerData.get(sender.getName()));
        if (g != null) {
            if (!g.getOriginalMemberSet().contains(args)) {
                sender.sendMessage("§c查无此人");
                return;
            }
            if (!g.getWhoHaveAdmin().contains(sender.getName())) {
                sender.sendMessage("§c你没有权限T出任何人");
                return;
            }
            if (g.getOwner().equals(args)) {
                sender.sendMessage("§c你没有权限T出会长");
                return;
            }
            if (!g.getOwner().equals(sender.getName()) && g.getWhoHaveAdmin().contains(args)) {
                sender.sendMessage("§c你没有权限T出其他管理员");
                return;
            }
            g.Kick(args);
        }
    }

    public void Deliver(CommandSender sender, String args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§c此命令需要玩家才能执行");
            return;
        }
        if (!Data.PlayerData.containsKey(sender.getName())) {
            sender.sendMessage("§c你还没有加入任何一个公会");
            return;
        }
        if (args == null) {
            sender.sendMessage("§c你需要输入一个玩家名字");
            return;
        }
        OfflinePlayer op = Bukkit.getPlayer(args);
        if (op != null && op.hasPlayedBefore()) {
            args = op.getName();
        }
        Guy g = Data.Gyus.get(Data.PlayerData.get(sender.getName()));
        if (g != null) {
            if (!g.getOwner().equals(sender.getName())) {
                sender.sendMessage("§c你不是会长");
                return;
            }
            if (!g.getOriginalMemberSet().contains(args)) {
                sender.sendMessage("§c此人不在你公会内");
                return;
            }
            if (args.equalsIgnoreCase(sender.getName())) {
                sender.sendMessage("§c没必要将会长转到自己身上");
                return;
            }
            g.setOwner(args);
            for (Player p : g.getOnlinePlayer()) {
                p.sendMessage("§b公会:" + g.getDisplayName()
                        + "原会长" + sender.getName() + " 已将会长一职转给§c" + args
                );
            }
        }
    }

    public void Disband(CommandSender sender) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§c此命令需要玩家才能执行");
            return;
        }
        if (!Data.PlayerData.containsKey(sender.getName())) {
            sender.sendMessage("§c你还没有加入任何一个公会");
            return;
        }
        Guy g = Data.Gyus.get(Data.PlayerData.get(sender.getName()));
        if (g != null) {
            if (!g.getOwner().equals(sender.getName())) {
                sender.sendMessage("§c你不是会长");
                return;
            }
            for (Player p : g.getOnlinePlayer()) {
                p.sendMessage("§c你所在的公会" + g.getDisplayName() + "已解散");
                Data.PlayerData.remove(p.getName());
            }
        }
        Main.Econ.depositPlayer(g.getOwner(), g.getBank());
        g.OperatingBank(-g.getBank());
        Data.Gyus.remove(g.getDisplayName());
        g.Disband();
        Data.RemoveList.add(g.getDisplayName());
        this.run();
    }

    public void Withdraw(CommandSender sender, String args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§c此命令需要玩家才能执行");
            return;
        }
        if (!Data.PlayerData.containsKey(sender.getName())) {
            sender.sendMessage("§c你还没有加入任何一个公会");
            return;
        }
        if (args == null) {
            sender.sendMessage("§c你需要输入一个金钱数量");
            return;
        }
        double money = 0;
        try {
            money = Double.parseDouble(args);
        } catch (NumberFormatException e) {
            sender.sendMessage("§c你需要输入一个金钱数量");
            return;
        }
        Guy g = Data.Gyus.get(Data.PlayerData.get(sender.getName()));
        if (g != null) {
            if (g.getWhoHaveAdmin().contains(sender.getName())) {
                if (g.getBank() <= money) {
                    sender.sendMessage("§c工会里没有这么多钱");
                    return;
                }
                g.OperatingBank(-money);
                Main.Econ.depositPlayer(sender.getName(), money);
                for (Player p : g.getOnlinePlayer()) {
                    if (p.getName().equals(sender.getName())) {
                        p.sendMessage("§6你成功取走了工会银行里的" + money + Main.Econ.currencyNamePlural());
                        continue;
                    }
                    p.sendMessage(((g.getCompetence(sender.getName()) == Competence.Admin) ? "§e管理员" : "§c会长") + sender.getName()
                            + " §6取走了工会银行里的" + money + Main.Econ.currencyNamePlural());
                }
            } else {
                sender.sendMessage("§c你不是工会管理员");
            }
        }
    }

    public void Create(CommandSender sender, String args) {
        if (!(sender instanceof Player)) {

            sender.sendMessage("§c此命令需要玩家才能执行");
            return;
        }
        Player p = (Player) sender;
        if (p.getLevel() < 600) {
            sender.sendMessage("§c需要600级才能创建");
            return;
        }
        if (Main.Econ.getBalance(p) < 500000) {
            sender.sendMessage("§c需要50W" + Main.Econ.currencyNamePlural()
                    + "金钱才能创建");
            return;
        }
        if (Data.Gyus.containsKey(args)) {
            sender.sendMessage("§c已有同名公会");
            return;
        }
        if (args == null) {
            sender.sendMessage("§c请输入一个名字");
            return;
        }
        if (Data.PlayerData.containsKey(sender.getName())) {
            sender.sendMessage("§c你已在一个公会里");
            return;
        }
        Main.Econ.withdrawPlayer(p, 500000d);
        Guy g = new Guy(sender.getName(), args);
        Data.Gyus.put(g.getDisplayName(), g);
        Data.PlayerData.put(sender.getName(), g.getDisplayName());
        sender.sendMessage("§6你花费50W" + Main.Econ.currencyNamePlural()
                + "已成功创建公会" + g.getDisplayName());
        this.run();
    }

    public void Donate(CommandSender sender, String args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§c此命令需要玩家才能执行");
            return;
        }
        if (!Data.PlayerData.containsKey(sender.getName())) {
            sender.sendMessage("§c你还没有加入任何一个公会");
            return;
        }
        if (args == null) {
            sender.sendMessage("§c你需要输入一个金钱数量");
            return;
        }
        double money = 0;
        try {
            money = Double.parseDouble(args);
        } catch (NumberFormatException e) {
            sender.sendMessage("§c你需要输入一个金钱数量");
            return;
        }
        if (money <= 0) {
            sender.sendMessage("§c你不能存入一个非正数");
            return;
        }
        if (Main.Econ.getBalance(sender.getName()) < money) {
            sender.sendMessage("§c你没有足够的金钱存入");
            return;
        }
        Guy g = Data.Gyus.get(Data.PlayerData.get(sender.getName()));
        if (g != null) {
            Main.Econ.withdrawPlayer(sender.getName(), money);
            g.OperatingBank(money);
            int gar = (int) (money % 2500);
            int i = g.getMemberPoint().get(sender.getName()) + gar;
            g.getMemberPoint().put(sender.getName(), i);
            sender.sendMessage("§c你已成功的向公会银行里储存了" + money + Main.Econ.currencyNamePlural() + "的钱.并且获得了"
                    + i + "点贡献值");
        }
    }

    public void ToggleFire(CommandSender sender, String args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§c此命令需要玩家才能执行");
            return;
        }
        if (!Data.PlayerData.containsKey(sender.getName())) {
            sender.sendMessage("§c你还没有加入任何一个公会");
            return;
        }
        Guy g = Data.Gyus.get(Data.PlayerData.get(sender.getName()));
        if (g != null) {
            if (args != null) {
                args = args.toLowerCase();
                switch (args) {
                    case "y":
                    case "yes":
                    case "on":
                        ChatManager.InGuyFires.add(sender.getName());
                        sender.sendMessage("§6已进入同公会伤害模式");
                        break;
                    case "n":
                    case "no":
                    case "off":
                        ChatManager.InGuyFires.remove(sender.getName());
                        sender.sendMessage("§6已离开同公会伤害模式");
                        break;
                }
                return;
            }
            if (ChatManager.InGuyFires.contains(sender.getName())) {
                ChatManager.InGuyFires.remove(sender.getName());
                sender.sendMessage("§6已离开同公会伤害模式");
            } else {
                ChatManager.InGuyFires.add(sender.getName());
                sender.sendMessage("§6已进入同公会伤害模式");
            }
        }
    }

    public void ToggleChat(CommandSender sender, String args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§c此命令需要玩家才能执行");
            return;
        }
        if (!Data.PlayerData.containsKey(sender.getName())) {
            sender.sendMessage("§c你还没有加入任何一个公会");
            return;
        }
        Guy g = Data.Gyus.get(Data.PlayerData.get(sender.getName()));
        if (g != null) {
            if (args != null) {
                args = args.toLowerCase();
                switch (args) {
                    case "y":
                    case "yes":
                    case "on":
                        ChatManager.InGuyChats.add(sender.getName());
                        sender.sendMessage("§6已进入公会频道");
                        break;
                    case "n":
                    case "no":
                    case "off":
                        ChatManager.InGuyChats.remove(sender.getName());
                        sender.sendMessage("§6已离开公会频道");
                        break;
                }
                return;
            }
            if (ChatManager.InGuyChats.contains(sender.getName())) {
                ChatManager.InGuyChats.remove(sender.getName());
                sender.sendMessage("§6已离开公会频道");
            } else {
                ChatManager.InGuyChats.add(sender.getName());
                sender.sendMessage("§6已进入公会频道");
            }
        }
    }

    @Override
    public void run() {
        this.Sort();
        this.Date = new Date().getTime();
    }

    public void Sort() {
        Top.clear();
        for (Map.Entry<String, Guy> E : Data.Gyus.entrySet()) {
            Top.put(E.getValue(), E.getValue().getActiveValue());
        }
        this.TempTop = Top.sortMapByValue();
    }

    public void Leave(CommandSender sender) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§c此命令需要玩家才能执行");
            return;
        }
        if (!Data.PlayerData.containsKey(sender.getName())) {
            sender.sendMessage("§c你还没有加入任何一个公会");
            return;
        }
        Guy g = Data.Gyus.get(Data.PlayerData.get(sender.getName()));
        if (g != null) {
            g.PlayerLeave((Player) sender);
        }
    }

    public void Members(CommandSender sender) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§c此命令需要玩家才能执行");
            return;
        }
        if (!Data.PlayerData.containsKey(sender.getName())) {
            sender.sendMessage("§c你还没有加入任何一个公会");
            return;
        }
        Guy g = Data.Gyus.get(Data.PlayerData.get(sender.getName()));
        if (g != null) {
            sender.sendMessage("§b公会:" + g.getDisplayName() + " 成员列表:");
            sender.sendMessage("§e黄色:管理员 §a绿色:普通成员 §c红色:会长 §a§l加粗: 在线");
            int i = 1;
            for (String s : g.getOriginalMemberSet()) {
                OfflinePlayer op = Bukkit.getOfflinePlayer(s);
                switch (g.getCompetence(s)) {
                    case Admin:
                        if (op != null && op.isOnline()) {
                            sender.sendMessage("§d§l[" + i + "] §e§l" + s);
                            break;
                        }
                        sender.sendMessage("§d[" + i + "] §e" + s);
                        break;
                    case Member:
                        if (op != null && op.isOnline()) {
                            sender.sendMessage("§d§l[" + i + "] §a§l" + s);
                            break;
                        }
                        sender.sendMessage("§d[" + i + "] §a" + s);
                        break;
                    case Owner:
                        if (op != null && op.isOnline()) {
                            sender.sendMessage("§d§l[" + i + "] §c§l" + s);
                            break;
                        }
                        sender.sendMessage("§d[" + i + "] §c" + s);
                        break;
                }
            }
        }
    }

    public void Join(CommandSender sender, String to) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§c此命令需要玩家才能执行");
            return;
        }
        if (!Data.Gyus.containsKey(to)) {
            sender.sendMessage("§c找不到这个公会 请再次确认公会名");
            return;
        }
        if (Data.PlayerData.containsKey(sender.getName())) {
            sender.sendMessage("§c你已经加入了一个公会 请先退出后再加入别的");
            return;
        }
        Guy g = Data.Gyus.get(to);
        if (g != null) {
            g.AddJoinAsk((Player) sender);
            sender.sendMessage("§6请求已发送");
        }
    }

    public void Top(CommandSender sender) {
        Guy g = null;
        if (sender instanceof Player) {
            if (Data.PlayerData.containsKey(sender.getName())) {
                g = Data.Gyus.get(Data.PlayerData.get(sender.getName()));
            }
        }
        sender.sendMessage("§b公会活跃度排行榜");
        sender.sendMessage("§a上一次排序时间为: " + DateFormat.getDateInstance().format(new Date(this.Date)));
        int index = 1;
        for (Map.Entry<Guy, Integer> E : TempTop.entrySet()) {
            if (index == 11) {
                break;
            }
            sender.sendMessage("§e[" + index + "] 公会:"
                    + E.getKey().getDisplayName() + "§b<" + E.getKey().getLevel() + "> §a人数:" + E.getKey().getOriginalMemberSet().size());
            index++;
        }
        if (g != null) {
            sender.sendMessage("§d-------------------");
            sender.sendMessage("§a你所在的公会:");
            sender.sendMessage(g.getInfo());
        }
    }

}
