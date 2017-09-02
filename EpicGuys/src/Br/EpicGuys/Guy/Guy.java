/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Br.EpicGuys.Guy;

import Br.EpicGuys.ChatManager;
import Br.EpicGuys.Data;
import Br.EpicGuys.Main;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

/**
 *
 * @author Bryan_lzh
 */
public class Guy {

    private String DisplayName;//公会名
    private String Owner;//拥有者
    private Set<String> Members = new HashSet<>();//成员们 包括所有的管理员和拥有者
    private Set<String> Admins = new HashSet<>();//管理员们
    private double Bank;//当前有的金钱数
    private double BankTop;//历史最高金钱数量
    private int ActiveValue = 0;//活跃点数
    private Set<String> TempJoinAsk = new HashSet<>();//等待加入的人
    private Shop Shop;

    private Map<String, Integer> MemberPoint = new HashMap<>();//成员贡献度

    public Shop getShop() {
        return this.Shop;
    }

    public Map<String, Integer> getMemberPoint() {
        return this.MemberPoint;
    }

    public void Save() throws IOException {
        File dataFolder = Main.Main.getDataFolder();
        if (!dataFolder.exists()) {
            dataFolder.mkdirs();
        }
        File dataFile = new File(dataFolder, this.DisplayName + ".yml");
        if (!dataFile.exists()) {
            return;
        }
        FileConfiguration config = YamlConfiguration.loadConfiguration(dataFile);

        List<String> pointdata = new LinkedList<>();
        for (Map.Entry<String, Integer> E : this.MemberPoint.entrySet()) {
            pointdata.add(E.getKey() + "|" + E.getValue());
        }
        config.set("Guy.MemberPoint", pointdata);
        config.set("Guy.Name", this.DisplayName);
        config.set("Guy.Members", this.getNormalMemberList());
        config.set("Guy.Admins", new ArrayList<>(this.Admins));
        config.set("Guy.Owner", this.Owner);
        config.set("Guy.Bank", this.Bank);
        config.set("Guy.BankTop", this.BankTop);
        config.set("Guy.ActiveValue", this.ActiveValue);
        this.SaveShop(config);
        config.save(dataFile);
    }

    public void SaveShop(FileConfiguration config) {
        List<String> data = new LinkedList<>();
        for (Item i : this.Shop.ItemList) {
            data.add(i.toString());
        }
        config.set("Guy.ShopData", data);
    }

    public void LoadShop(FileConfiguration config) {
        if (config == null) {
            this.Shop = new Shop(this);
            return;
        }
        if (config.contains("Guy.ShopData")) {
            this.Shop = new Shop(this);
            for (String s : config.getStringList("Guy.ShopData")) {
                Item i = new Item(s);
                this.Shop.ItemList.add(i);
            }
        } else {
            this.Shop = new Shop(this);
        }
    }

    public void Kick(String s) {
        if (!this.Members.contains(s)) {
            return;
        }
        if (this.Owner.equals(s)) {
            return;
        }
        this.Members.remove(s);
        if (this.Admins.contains(s)) {
            this.Admins.remove(s);
        }
        OfflinePlayer op = Bukkit.getPlayer(s);
        if (op != null && op.isOnline()) {
            op.getPlayer().sendMessage("§c你已被公会" + this.getDisplayName() + " 踢了出去");
        }
        for (Player p : this.getOnlinePlayer()) {
            p.sendMessage("§6公会内部已将" + s + "踢了出去");
        }
    }

    public void Disband() {
        this.ActiveValue = 0;
        this.Members.clear();
        this.Admins.clear();
        this.TempJoinAsk.clear();
        this.Bank = 0;
        this.BankTop = 0;
    }

    public Guy(File f) {
        if (!f.exists()) {
            return;
        }
        FileConfiguration config = YamlConfiguration.loadConfiguration(f);
        this.DisplayName = config.getString("Guy.Name");
        this.Members = new HashSet<>(config.getStringList("Guy.Members"));
        this.Admins = new HashSet<>(config.getStringList("Guy.Admins"));
        this.Owner = config.getString("Guy.Owner");
        this.Bank = config.getDouble("Guy.Bank");
        this.BankTop = config.getDouble("Guy.BankTop");
        this.ActiveValue = config.getInt("Guy.ActiveValue");
        for (String s : this.Admins) {
            this.Members.add(s);
        }
        for (String s : config.getStringList("Guy.MemberPoint")) {
            String ss[] = s.split("\\|");
            try {
                this.MemberPoint.put(ss[0], Integer.parseInt(ss[1]));
            } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
            }
        }
        this.LoadShop(config);
        this.Members.add(this.Owner);
    }

    public Guy(String name) {
        File dataFolder = Main.Main.getDataFolder();
        if (!dataFolder.exists()) {
            dataFolder.mkdirs();
        }
        File dataFile = new File(dataFolder, name + ".yml");
        if (!dataFile.exists()) {
            return;
        }
        FileConfiguration config = YamlConfiguration.loadConfiguration(dataFile);
        this.DisplayName = config.getString("Guy.Name");
        this.Members = new HashSet<>(config.getStringList("Guy.Members"));
        this.Admins = new HashSet<>(config.getStringList("Guy.Admins"));
        this.Owner = config.getString("Guy.Owner");
        this.Bank = config.getDouble("Guy.Bank");
        this.BankTop = config.getDouble("Guy.BankTop");
        this.ActiveValue = config.getInt("Guy.ActiveValue");
        for (String s : this.Admins) {
            this.Members.add(s);
        }
        for (String s : config.getStringList("Guy.MemberPoint")) {
            String ss[] = s.split("\\|");
            try {
                this.MemberPoint.put(ss[0], Integer.parseInt(ss[1]));
            } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
            }
        }
        this.LoadShop(config);
        this.Members.add(this.Owner);
    }

    public Guy(String owner, String name) {
        try {
            this.DisplayName = name;
            this.Owner = owner;
            this.Members.add(owner);
            this.Bank = 0d;
            this.BankTop = 0d;
            File dataFolder = Main.Main.getDataFolder();
            if (!dataFolder.exists()) {
                dataFolder.mkdirs();
            }
            File dataFile = new File(dataFolder, name + ".yml");
            if (dataFile.exists()) {
                return;
            }
            dataFile.createNewFile();
            FileConfiguration config = YamlConfiguration.loadConfiguration(dataFile);
            config.set("Guy.Name", name);
            config.set("Guy.Members", this.getNormalMemberList());
            config.set("Guy.Admins", this.Admins);
            config.set("Guy.Owner", owner);
            config.set("Guy.Bank", 0d);
            config.set("Guy.BankTop", 0d);
            config.set("Guy.ActiveValue", 0);
            this.LoadShop(config);
            config.save(dataFile);
        } catch (IOException ex) {
            Logger.getLogger(Guy.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String getPrefix() {
        if (this.getLevel() >= 3) {
            switch (this.getLevel()) {
                case 3:
                    return "§a[§2" + this.DisplayName + "§a]§f";
                case 4:
                    return "§a[§6" + this.DisplayName + "§a]§f";
                case 5:
                    return "§6>>§b" + this.DisplayName + "§6<<§f";
            }
        }
        return "";
    }

    /**
     * 玩家主动离开
     *
     * @param p
     */
    public void PlayerLeave(Player p) {
        if (!this.Members.contains(p.getName())) {
            p.sendMessage("§c你不属于" + this.DisplayName + "公会");
            return;
        }
        if (this.Owner.equals(p.getName())) {
            p.sendMessage("§c身为会长无法退出公会 请将管理权移交给他人");
            return;
        }
        this.Members.remove(p.getName());
        if (this.Admins.contains(p.getName())) {
            this.Admins.remove(p.getName());
        }
        p.sendMessage("§c你已成功的退出了" + this.DisplayName);
        ChatManager.InGuyChats.remove(p.getName());
        ChatManager.InGuyFires.remove(p.getName());
        for (String s : this.getOriginalMemberSet()) {
            OfflinePlayer op = Bukkit.getOfflinePlayer(s);
            if (op != null && op.isOnline()) {
                op.getPlayer().sendMessage("§6§l玩家" + p.getName() + " 已退出了你所在的公会");
            }
        }
    }

    /**
     * 返回有权限的玩家列表
     *
     * @return
     */
    public List<String> getWhoHaveAdmin() {
        List<String> result = new ArrayList<>(this.Admins);
        result.add(this.Owner);
        return result;
    }

    /**
     * 返回当前等待通过申请的玩家
     *
     * @return
     */
    public Set<String> getTempJoinAsk() {
        return this.TempJoinAsk;
    }

    /**
     * 添加一个玩家的申请
     *
     * @param p
     */
    public void AddJoinAsk(Player p) {
        this.TempJoinAsk.add(p.getName());
        for (String s : this.getWhoHaveAdmin()) {
            OfflinePlayer op = Bukkit.getOfflinePlayer(s);
            if (op != null && op.isOnline()) {
                op.getPlayer().sendMessage("§c当前有一份新的加入申请 请输入/eg joinlist 查看");
            }
        }
    }

    /**
     * 同意某个玩家的申请
     *
     * @param p
     * @return
     */
    public String AcceptJoin(Player p) {
        if (!this.TempJoinAsk.contains(p.getName())) {
            return "§c无法同意 对方还没有申请加入过";
        }
        this.TempJoinAsk.remove(p.getName());
        if (Data.PlayerData.containsKey(p.getName())) {
            return "§c无法同意 对方已加入了一个公会";
        }
        if (this.Members.size() >= this.getLevel() * 6) {
            return "§c无法同意 公会人数已满";
        }
        this.Members.add(p.getName());
        Data.PlayerData.put(p.getName(), this.DisplayName);
        return "§b已同意" + p.getName() + "加入公会";
    }

    /**
     * 拒绝某个玩家的加入请求
     *
     * @param s
     * @return
     */
    public String RejectJoin(String s) {
        if (!this.TempJoinAsk.contains(s)) {
            return "§c无法拒绝 对方还没有申请加入过";
        }
        this.TempJoinAsk.remove(s);
        return "§6已拒绝" + s + "的加入请求";
    }

    /**
     * 同意某个玩家的申请
     *
     * @param p
     * @return
     */
    public String AcceptJoin(String p) {
        if (!this.TempJoinAsk.contains(p)) {
            return "§c无法同意 对方还没有申请加入过";
        }
        this.TempJoinAsk.remove(p);
        if (Data.PlayerData.containsKey(p)) {
            return "§c无法同意 对方已加入了一个公会";
        }
        if (this.Members.size() >= this.getLevel() * 6) {
            return "§c无法同意 公会人数已满";
        }
        this.Members.add(p);
        Data.PlayerData.put(p, this.DisplayName);
        return "§b已同意" + p + "加入公会";
    }

    /**
     * 也用作内部名
     * @return
     */
    public String getDisplayName() {
        return this.DisplayName;
    }

    /**
     * 返回某个玩家的权限
     *
     * @param s
     * @return
     */
    public Competence getCompetence(String s) {
        if (!this.Members.contains(s)) {
            return null;
        }
        if (this.Owner.equals(s)) {
            return Competence.Owner;
        }
        if (this.Admins.contains(s)) {
            return Competence.Admin;
        }
        return Competence.Member;
    }

    /**
     * 返回公会等级 1~5
     *
     * @return
     */
    public int getLevel() {
        if (this.ActiveValue < 500) {
            return 1;
        }
        if (this.ActiveValue < 1300) {
            return 2;
        }
        if (this.ActiveValue < 2100) {
            return 3;
        }
        if (this.ActiveValue < 3000) {
            return 4;
        }
        return 5;
    }

    /**
     * 返回公会信息
     *
     * @return
     */
    public String[] getInfo() {
        return new String[]{
            "§a公会名称: §f" + this.DisplayName,
            "§6公会等级: §2" + this.getLevel(),
            "§5公会活跃度: §b" + this.ActiveValue,
            "§a在线成员: §2" + this.getOnlinePlayers() + "/§d" + this.Members.size(),
            "§e公会银行拥有金额: " + this.Bank,
            "§f公会银行历史最高金额: " + this.BankTop
        };
    }

    /**
     * 操作银行方法
     *
     * @param b
     */
    public void OperatingBank(double b) {
        double r = b + this.Bank;
        this.Bank = r;
        if (r > this.BankTop) {
            this.BankTop = r;
        }
    }

    /**
     * 返回公会历史上最多的金钱数
     *
     * @return
     */
    public double getBankTop() {
        return this.BankTop;
    }

    /**
     * 返回公会现在的金钱数量
     *
     * @return
     */
    public double getBank() {
        return this.Bank;
    }

    /**
     * 设置公会的活跃值
     *
     * @param i
     */
    public void setActiveValue(int i) {
        this.ActiveValue = i;
    }

    /**
     * 返回公会当前的活跃值
     *
     * @return
     */
    public int getActiveValue() {
        return this.ActiveValue;
    }

    /**
     * 返回当前公会在线人数
     *
     * @return
     */
    public int getOnlinePlayers() {
        int a = 0;
        for (String s : this.Members) {
            OfflinePlayer op = Bukkit.getOfflinePlayer(s);
            if (op == null) {
                continue;
            }
            if (op.isOnline()) {
                a += 1;
            }
        }
        return a;
    }

    /**
     * 返回当前公会在线人
     *
     * @return
     */
    public Collection<Player> getOnlinePlayer() {
        List<Player> p = new ArrayList<>();
        for (String s : this.Members) {
            OfflinePlayer op = Bukkit.getOfflinePlayer(s);
            if (op == null) {
                continue;
            }
            if (op.isOnline()) {
                p.add(op.getPlayer());
            }
        }
        return p;
    }

    /**
     * 将某个人移除管理
     *
     * @param s
     */
    public void removeAdmin(String s) {
        if (this.Members.contains(s)) {
            if (s.equals(this.Owner)) {
                return;
            }
            this.Admins.remove(s);
        }
    }

    /**
     * 将某个人设置为管理员
     *
     * @param s
     */
    public void setAdmin(String s) {
        if (this.Members.contains(s)) {
            if (s.equals(this.Owner)) {
                return;
            }
            this.Admins.add(s);
        }
    }

    /**
     * 返回没有权限的成员
     *
     * @return
     */
    public List<String> getNormalMemberList() {
        List<String> result = new ArrayList<>();
        for (String s : this.Members) {
            if (s.equals(this.Owner)) {
                continue;
            }
            if (this.Admins.contains(s)) {
                continue;
            }
            result.add(s);
        }
        return result;
    }

    /**
     * 返回原始成员列表. 包括拥有者 管理员 普通用户
     *
     * @return
     */
    public Set<String> getOriginalMemberSet() {
        return this.Members;
    }

    /**
     * 返回拥有者
     *
     * @return
     */
    public String getOwner() {
        return this.Owner;
    }

    /**
     * 设置拥有者
     *
     * @param s
     */
    public void setOwner(String s) {
        this.Owner = s;
    }
}
