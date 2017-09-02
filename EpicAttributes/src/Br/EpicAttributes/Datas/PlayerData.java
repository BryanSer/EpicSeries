/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Br.EpicAttributes.Datas;

import Br.EpicAttributes.Data;
import Br.EpicAttributes.Skills.SkillType;
import Br.EpicAttributes.Utils;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 *
 * @author Bryan_lzh
 */
public class PlayerData {

    public static String Code = "§6§l属性值菜单";
    public static int DefaultPoints = 5;
    public static boolean EnableEnno = false;

    /**
     * 玩家第一次进入服务器时
     *
     * @param p
     */
    public PlayerData(Player p) {
        this.PlayerName = p.getName();
        this.Attributes = AbType.getDefaultMap();
        this.Points = DefaultPoints;
        if (EnableEnno) {
            this.EnnoAttributes = AbType.getDefaultMap();
        }
    }

    public PlayerData(OfflinePlayer p) {
        this.PlayerName = p.getName();
        this.Attributes = AbType.getDefaultMap();
        this.Points = DefaultPoints;
        if (EnableEnno) {
            this.EnnoAttributes = AbType.getDefaultMap();
        }
    }

    public void setAbtype(AbType a, int point) {
        this.Attributes.put(a, point);
    }

    public PlayerData(String p) {
        this.PlayerName = p;
        this.Attributes = new EnumMap<>(AbType.class);
    }

    private String PlayerName;
    private List<SkillType> SkillList = new ArrayList<>();
    private Map<AbType, Integer> Attributes;
    private int Points = 0;

    private Map<AbType, Integer> GemAttributes = AbType.getDefaultMap();
    private Map<AbType, Integer> EnnoAttributes = null;

    public int getPoints() {
        return this.Points;
    }

    public void setPoints(int i) {
        this.Points = i;
    }

    public ItemAndSlot getDisplay(AbType a) {
        ItemAndSlot ias = null;
        switch (a) {
            case Atk:
                ItemStack Atk = new ItemStack(Material.REDSTONE);
                ItemMeta AtkIm = Atk.getItemMeta();
                AtkIm.setDisplayName("§C攻击(ATK): " + this.Attributes.get(AbType.Atk));
                AtkIm.setLore(AbType.Atk.getLore());
                Atk.setItemMeta(AtkIm);
                ias = new ItemAndSlot(Atk, 11);
                break;
            case Def:
                ItemStack Def = new ItemStack(Material.IRON_CHESTPLATE);
                // ItemStack Def = new ItemStack(Material.SHIELD);
                ItemMeta DefIm = Def.getItemMeta();
                DefIm.setDisplayName("§b防御(Def): " + this.Attributes.get(AbType.Def));
                DefIm.setLore(AbType.Def.getLore());
                Def.setItemMeta(DefIm);
                ias = new ItemAndSlot(Def, 12);
                break;
            case Crit:
                ItemStack Crit = new ItemStack(Material.IRON_AXE);
                ItemMeta CritIm = Crit.getItemMeta();
                CritIm.setDisplayName("§b暴击(Crit): " + this.Attributes.get(AbType.Crit));
                CritIm.setLore(AbType.Crit.getLore());
                Crit.setItemMeta(CritIm);
                ias = new ItemAndSlot(Crit, 13);
                break;
            case CritDam:
                ItemStack CritDam = new ItemStack(Material.TNT);
                ItemMeta CritDamIm = CritDam.getItemMeta();
                CritDamIm.setDisplayName("§b暴击伤害(CritDam): " + this.Attributes.get(AbType.CritDam));
                CritDamIm.setLore(AbType.CritDam.getLore());
                CritDam.setItemMeta(CritDamIm);
                ias = new ItemAndSlot(CritDam, 14);
                break;
            case Vampire:
                ItemStack Vampire = new ItemStack(Material.REDSTONE_BLOCK);
                ItemMeta VampireIm = Vampire.getItemMeta();
                VampireIm.setDisplayName("§b吸血(Vampire): " + this.Attributes.get(AbType.Vampire));
                VampireIm.setLore(AbType.Vampire.getLore());
                Vampire.setItemMeta(VampireIm);
                ias = new ItemAndSlot(Vampire, 15);
                break;
            case Sunder:
                ItemStack Sunder = new ItemStack(Material.LEATHER_CHESTPLATE);
                ItemMeta SunderIm = Sunder.getItemMeta();
                SunderIm.setDisplayName("§b破甲(Sunder): " + this.Attributes.get(AbType.Sunder));
                SunderIm.setLore(AbType.Sunder.getLore());
                Sunder.setItemMeta(SunderIm);
                ias = new ItemAndSlot(Sunder, 20);
                break;
            case Health:
                ItemStack Health = new ItemStack(Material.BEACON);
                ItemMeta HealthIm = Health.getItemMeta();
                HealthIm.setDisplayName("§b生命(Health): " + this.Attributes.get(AbType.Health));
                HealthIm.setLore(AbType.Health.getLore());
                Health.setItemMeta(HealthIm);
                ias = new ItemAndSlot(Health, 21);
                break;
            case Recover:
                ItemStack Recover = new ItemStack(Material.GOLDEN_APPLE);
                ItemMeta RecoverIm = Recover.getItemMeta();
                RecoverIm.setDisplayName("§b恢复(Recover): " + this.Attributes.get(AbType.Recover));
                RecoverIm.setLore(AbType.Recover.getLore());
                Recover.setItemMeta(RecoverIm);
                ias = new ItemAndSlot(Recover, 22);
                break;
            case Avoid:
                ItemStack Avoid = new ItemStack(Material.DIAMOND_BOOTS);
                ItemMeta AvoidrIm = Avoid.getItemMeta();
                AvoidrIm.setDisplayName("§b闪避(Avoid): " + this.Attributes.get(AbType.Avoid));
                AvoidrIm.setLore(AbType.Avoid.getLore());
                Avoid.setItemMeta(AvoidrIm);
                ias = new ItemAndSlot(Avoid, 23);
                break;
            case HitP:
                ItemStack HitP = new ItemStack(Material.GLASS);
                ItemMeta HitPIm = HitP.getItemMeta();
                HitPIm.setDisplayName("§b命中(Hit): " + this.Attributes.get(AbType.HitP));
                HitPIm.setLore(AbType.HitP.getLore());
                HitP.setItemMeta(HitPIm);
                ias = new ItemAndSlot(HitP, 24);
                break;
        }
        return ias;
    }

    public ItemAndSlot getInfoItem() {
        ItemStack Info = new ItemStack(Material.PAPER);
        ItemMeta InfoIm = Info.getItemMeta();
        InfoIm.setDisplayName("§e§l属性状态:");
        List<String> Lore = new ArrayList<>();
        Lore.add("§a()内的为宝石所添加的属性");
        Lore.add("§6生命值: +" + (this.Attributes.get(AbType.Health) * 2)
                + "§a(+" + (this.GemAttributes.get(AbType.Health) * 2) + ")");
        Lore.add("§c攻击力: +" + (this.Attributes.get(AbType.Atk) * 2)
                + "§a(+" + (this.GemAttributes.get(AbType.Atk) * 2) + ")");
        Lore.add("§d防御力: +" + (this.Attributes.get(AbType.Def) * 0.1f) + "%"
                + "§a(+" + (this.GemAttributes.get(AbType.Def) * 0.1f) + "%)");
        Lore.add("§2暴击率: +" + (this.Attributes.get(AbType.Crit) * 0.1f) + "%"
                + "§a(+" + (this.GemAttributes.get(AbType.Crit) * 0.1f) + "%)");
        Lore.add("§c暴击伤害: +" + ((this.Attributes.get(AbType.CritDam) * 0.5f) + 50f) + "%"
                + "§a(+" + (this.GemAttributes.get(AbType.Crit) * 0.5f) + "%)");
        Lore.add("§4吸血: +" + (this.Attributes.get(AbType.Vampire) * 0.2f) + "%"
                + "§a(+" + (this.GemAttributes.get(AbType.Vampire) * 0.2f) + "%)");
        Lore.add("§e破甲率: +" + (this.Attributes.get(AbType.Sunder) * 1f) + "%"
                + "§a(+" + (this.GemAttributes.get(AbType.Sunder) * 1f) + "%)");
        Lore.add("§b闪避率: +" + (this.Attributes.get(AbType.Avoid) * 0.1f) + "%"
                + "§a(+" + (this.GemAttributes.get(AbType.Avoid) * 0.1f) + "%)");
        Lore.add("§b额外命中率: +" + (this.Attributes.get(AbType.HitP) * 0.1f) + "%"
                + "§a(+" + (this.GemAttributes.get(AbType.HitP) * 0.1f) + "%)");
        Lore.add("§6生命回复: +" + (this.Attributes.get(AbType.Recover) * 0.25f) + "%"
                + "§a(+" + (this.GemAttributes.get(AbType.Recover) * 0.25f) + "%)");
        InfoIm.setLore(Lore);
        Info.setItemMeta(InfoIm);
        return new ItemAndSlot(Info, 26);
    }

    /**
     * O 1 2 3 4 5 6 7 8
     * .<P>
     * 9 10 11 12 13 14 15 16 17
     * .<P>
     * 18 19 20 21 22 23 24 25 26.
     *
     * @param p
     * @return
     */
    public Inventory getInv(Player p) {
        Inventory inv = Bukkit.createInventory(p, 27, Code);

        ItemStack Point = new ItemStack(Material.EXP_BOTTLE);
        if (this.Points >= 64) {
            Point.setAmount(64);
        } else {
            Point.setAmount(this.Points);
        }
        ItemMeta PointIm = Point.getItemMeta();
        PointIm.setDisplayName("§6余剩点数: " + this.Points);
        Point.setItemMeta(PointIm);
        inv.setItem(0, Point);

        ItemStack Atk = new ItemStack(Material.REDSTONE);
        ItemMeta AtkIm = Atk.getItemMeta();
        AtkIm.setDisplayName("§C攻击(ATK): " + this.Attributes.get(AbType.Atk));
        AtkIm.setLore(AbType.Atk.getLore());
        Atk.setItemMeta(AtkIm);
        inv.setItem(11, Atk);

        ItemStack Def = new ItemStack(Material.IRON_CHESTPLATE);
        // ItemStack Def = new ItemStack(Material.SHIELD);
        ItemMeta DefIm = Def.getItemMeta();
        DefIm.setDisplayName("§b防御(Def): " + this.Attributes.get(AbType.Def));
        DefIm.setLore(AbType.Def.getLore());
        Def.setItemMeta(DefIm);
        inv.setItem(12, Def);

        ItemStack Crit = new ItemStack(Material.IRON_AXE);
        ItemMeta CritIm = Crit.getItemMeta();
        CritIm.setDisplayName("§b暴击(Crit): " + this.Attributes.get(AbType.Crit));
        CritIm.setLore(AbType.Crit.getLore());
        Crit.setItemMeta(CritIm);
        inv.setItem(13, Crit);

        ItemStack CritDam = new ItemStack(Material.TNT);
        ItemMeta CritDamIm = CritDam.getItemMeta();
        CritDamIm.setDisplayName("§b暴击伤害(CritDam): " + this.Attributes.get(AbType.CritDam));
        CritDamIm.setLore(AbType.CritDam.getLore());
        CritDam.setItemMeta(CritDamIm);
        inv.setItem(14, CritDam);

        ItemStack Vampire = new ItemStack(Material.REDSTONE_BLOCK);
        ItemMeta VampireIm = Vampire.getItemMeta();
        VampireIm.setDisplayName("§b吸血(Vampire): " + this.Attributes.get(AbType.Vampire));
        VampireIm.setLore(AbType.Vampire.getLore());
        Vampire.setItemMeta(VampireIm);
        inv.setItem(15, Vampire);

        ItemStack Sunder = new ItemStack(Material.LEATHER_CHESTPLATE);
        ItemMeta SunderIm = Sunder.getItemMeta();
        SunderIm.setDisplayName("§b破甲(Sunder): " + this.Attributes.get(AbType.Sunder));
        SunderIm.setLore(AbType.Sunder.getLore());
        Sunder.setItemMeta(SunderIm);
        inv.setItem(20, Sunder);

        ItemStack Health = new ItemStack(Material.BEACON);
        ItemMeta HealthIm = Health.getItemMeta();
        HealthIm.setDisplayName("§b生命(Health): " + this.Attributes.get(AbType.Health));
        HealthIm.setLore(AbType.Health.getLore());
        Health.setItemMeta(HealthIm);
        inv.setItem(21, Health);

        ItemStack Recover = new ItemStack(Material.GOLDEN_APPLE);
        ItemMeta RecoverIm = Recover.getItemMeta();
        RecoverIm.setDisplayName("§b恢复(Recover): " + this.Attributes.get(AbType.Recover));
        RecoverIm.setLore(AbType.Recover.getLore());
        Recover.setItemMeta(RecoverIm);
        inv.setItem(22, Recover);

        ItemStack Avoid = new ItemStack(Material.DIAMOND_BOOTS);
        ItemMeta AvoidrIm = Avoid.getItemMeta();
        AvoidrIm.setDisplayName("§b闪避(Avoid): " + this.Attributes.get(AbType.Avoid));
        AvoidrIm.setLore(AbType.Avoid.getLore());
        Avoid.setItemMeta(AvoidrIm);
        inv.setItem(23, Avoid);

        ItemStack HitP = new ItemStack(Material.GLASS);
        ItemMeta HitPIm = HitP.getItemMeta();
        HitPIm.setDisplayName("§b命中(Hit): " + this.Attributes.get(AbType.HitP));
        HitPIm.setLore(AbType.HitP.getLore());
        HitP.setItemMeta(HitPIm);
        inv.setItem(24, HitP);

        ItemStack Info = new ItemStack(Material.PAPER);
        ItemMeta InfoIm = Info.getItemMeta();
        InfoIm.setDisplayName("§e§l属性状态:");
        List<String> Lore = new ArrayList<>();
        Lore.add("§a()内的为宝石所添加的属性");
        Lore.add("§6生命值: +" + (this.Attributes.get(AbType.Health) * 2)
                + "§a(+" + (this.GemAttributes.get(AbType.Health) * 2) + ")");
        Lore.add("§c攻击力: +" + (this.Attributes.get(AbType.Atk) * 2)
                + "§a(+" + (this.GemAttributes.get(AbType.Atk) * 2) + ")");
        Lore.add("§d防御力: +" + (this.Attributes.get(AbType.Def) * 0.1f) + "%"
                + "§a(+" + (this.GemAttributes.get(AbType.Def) * 0.1f) + "%)");
        Lore.add("§2暴击率: +" + (this.Attributes.get(AbType.Crit) * 0.1f) + "%"
                + "§a(+" + (this.GemAttributes.get(AbType.Crit) * 0.1f) + "%)");
        Lore.add("§c暴击伤害: +" + ((this.Attributes.get(AbType.CritDam) * 0.5f) + 50f) + "%"
                + "§a(+" + (this.GemAttributes.get(AbType.Crit) * 0.5f) + "%)");
        Lore.add("§4吸血: +" + (this.Attributes.get(AbType.Vampire) * 0.2f) + "%"
                + "§a(+" + (this.GemAttributes.get(AbType.Vampire) * 0.2f) + "%)");
        Lore.add("§e破甲率: +" + (this.Attributes.get(AbType.Sunder) * 1f) + "%"
                + "§a(+" + (this.GemAttributes.get(AbType.Sunder) * 1f) + "%)");
        Lore.add("§b闪避率: +" + (this.Attributes.get(AbType.Avoid) * 0.1f) + "%"
                + "§a(+" + (this.GemAttributes.get(AbType.Avoid) * 0.1f) + "%)");
        Lore.add("§b额外命中率: +" + (this.Attributes.get(AbType.HitP) * 0.1f) + "%"
                + "§a(+" + (this.GemAttributes.get(AbType.HitP) * 0.1f) + "%)");
        Lore.add("§6生命回复: +" + (this.Attributes.get(AbType.Recover) * 0.25f) + "%"
                + "§a(+" + (this.GemAttributes.get(AbType.Recover) * 0.25f) + "%)");
        InfoIm.setLore(Lore);
        Info.setItemMeta(InfoIm);
        inv.setItem(26, Info);
        return inv;
    }

    public void Reset() {
        this.Attributes = AbType.getDefaultMap();
        this.Points = DefaultPoints;
    }

    public List<SkillType> getSkills() {
        return this.SkillList;
    }

    public void addPoints(int i) {
        String command1 = "";
        String command2 = "";
        if (i <= 0) {
            i = Math.abs(i);
            if (this.Points - i <= 0) {
                i = this.Points;
            }
            this.Points -= i;
            String s = this.Points + "";
            command1 = "title " + this.PlayerName + " subtitle "
                    + "{text:目前可分配点数为:\"" + s
                    + "\",color:aqua,bold:true}";
            command2 = "title " + this.PlayerName + " title "
                    + "{text:\"当前点数减少了" + i
                    + "\",color:green,bold:true}";
            /*    command1 = "title " + op.getName() + " subtitle "
                    + "{\"text\":\"目前可分配点数为:" + this.Points
                    + "\",\"color\":\"aqua\",\"bold\":\"true\"}";
            command2 = "title " + op.getName() + " title "
                    + "{\"text\":\"当前点数减少了" + i
                    + "\",\"color\":\"green\",\"bold\":\"true\"}";*/
        } else {
            this.Points += i;
            String s = this.Points + "";
            command1 = "title " + this.PlayerName + " subtitle "
                    + "{text:\"目前可分配点数为:" + s
                    + "\",color:aqua,bold:true}";
            command2 = "title " + this.PlayerName + " title "
                    + "{text:\"当前点数增加了" + i
                    + "\",color:green,bold:true}";
            /*  command1 = "title " + op.getName() + " subtitle "
                    + "{\"text\":\"目前可分配点数为:" + this.Points
                    + "\",\"color\":\"aqua\",\"bold\":\"true\"}";
            command2 = "title " + op.getName() + " title "
                    + "{\"text\":\"当前点数增加了" + i
                    + "\",\"color\":\"green\",\"bold\":\"true\"}";*/
        }
        //  Bukkit.broadcastMessage(command1);
        //   Bukkit.broadcastMessage(command2);
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command1);
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command2);
    }

    /**
     *
     * @return @deprecated 仅用于本体技能
     */
    @Deprecated
    public Map<AbType, Integer> getAttributes() {
        return this.Attributes;
    }

    public Map<AbType, Integer> getGemAttributes() {
        return GemAttributes;
    }

    public Map<AbType, Integer> getEnnoAttributes() {
        return this.EnnoAttributes;
    }

    /**
     * 泛用方法
     *
     * @param ab
     * @return
     */
    public int getAttribute(AbType ab) {
        int i = 0;
        i += this.Attributes.get(ab);
        i += this.GemAttributes.get(ab);
        if (EnableEnno) {
            i += this.EnnoAttributes.get(ab);
        }
        return i;
    }

    public void CheckSkill() {
        for (SkillType st : SkillType.values()) {
            if (!this.SkillList.contains(st)) {
                if (st.getSkill().CouldLearn(this)) {
                    this.SkillList.add(st);
                    Bukkit.getPlayer(this.PlayerName).sendMessage("§6你学会了§e"
                            + st.getSkill().getDisplayName());
                }
            }
        }
    }

    /**
     *
     * @param ab
     * @param p
     * @return false为此点数已满
     */
    public boolean Point(AbType ab, int p) {
        p = this.Attributes.get(ab) + p;
        if (p >= ab.getMax()) {
            this.Attributes.put(ab, ab.getMax());
            return false;
        }
        this.Attributes.put(ab, p);
        this.Refresh();
        return true;
    }

    public void Refresh() {
        Player p = Bukkit.getPlayer(this.PlayerName);
        p.setMaxHealth(Utils.getHealth(this.getAttribute(AbType.Health)));
        p.setHealthScale(60);
        p.setHealthScaled(true);
        this.CheckSkill();
    }

    public String[] getInfo() {
        PlayerData pd = this;
        return new String[]{
            "§d§l当前的属性状态如下: §a()中为宝石所增加的属性"
            + (EnableEnno ? "\n§b[]中为种族技能所增加的属性" : ""),
            "§b攻击(ATK):  " + pd.getAttributes().get(AbType.Atk)
            + " §a(+" + pd.getGemAttributes().get(AbType.Atk) + ")"
            + (EnableEnno ? "§b(+" + pd.getEnnoAttributes().get(AbType.Atk) + ")" : ""),
            "§b防御(Def):  " + pd.getAttributes().get(AbType.Def)
            + " §a(+" + pd.getGemAttributes().get(AbType.Def) + ")"
            + (EnableEnno ? "§b(+" + pd.getEnnoAttributes().get(AbType.Def) + ")" : ""),
            "§b暴击(Crit): " + pd.getAttributes().get(AbType.Crit)
            + " §a(+" + pd.getGemAttributes().get(AbType.Crit) + ")"
            + (EnableEnno ? "§b(+" + pd.getEnnoAttributes().get(AbType.Crit) + ")" : ""),
            "§b暴击伤害(CritDam): " + pd.getAttributes().get(AbType.CritDam)
            + " §a(+" + pd.getGemAttributes().get(AbType.CritDam) + ")"
            + (EnableEnno ? "§b(+" + pd.getEnnoAttributes().get(AbType.CritDam) + ")" : ""),
            "§b吸血(Vampire): " + pd.getAttributes().get(AbType.Vampire)
            + " §a(+" + pd.getGemAttributes().get(AbType.Vampire) + ")"
            + (EnableEnno ? "§b(+" + pd.getEnnoAttributes().get(AbType.Vampire) + ")" : ""),
            "§b破甲(Sunder): " + pd.getAttributes().get(AbType.Sunder)
            + " §a(+" + pd.getGemAttributes().get(AbType.Sunder) + ")"
            + (EnableEnno ? "§b(+" + pd.getEnnoAttributes().get(AbType.Sunder) + ")" : ""),
            "§b生命(Health): " + pd.getAttributes().get(AbType.Health)
            + " §a(+" + pd.getGemAttributes().get(AbType.Health) + ")"
            + (EnableEnno ? "§b(+" + pd.getEnnoAttributes().get(AbType.Health) + ")" : ""),
            "§b恢复(Recover): " + pd.getAttributes().get(AbType.Recover)
            + " §a(+" + pd.getGemAttributes().get(AbType.Recover) + ")"
            + (EnableEnno ? "§b(+" + pd.getEnnoAttributes().get(AbType.Recover) + ")" : ""),
            "§b闪避(Avoid): " + pd.getAttributes().get(AbType.Avoid)
            + " §a(+" + pd.getGemAttributes().get(AbType.Avoid) + ")"
            + (EnableEnno ? "§b(+" + pd.getEnnoAttributes().get(AbType.Avoid) + ")" : ""),
            "§b命中(Hit): " + pd.getAttributes().get(AbType.HitP)
            + " §a(+" + pd.getGemAttributes().get(AbType.HitP) + ")"
            + (EnableEnno ? "§b(+" + pd.getEnnoAttributes().get(AbType.HitP) + ")" : ""),
            "§d§l============================================",
            "§6§l当前最大生命值: " + Utils.getHealth(pd.getAttribute(AbType.Health))
        };
    }

    public void save() {
        try {
            File dataFolder = Data.Main.getDataFolder();
            if (!dataFolder.exists()) {
                dataFolder.mkdirs();
            }
            File dataFile = new File(dataFolder, this.PlayerName + ".yml");
            if (!dataFile.exists()) {
                dataFile.createNewFile();
            }
            FileConfiguration config = YamlConfiguration.loadConfiguration(dataFile);
            for (Entry<AbType, Integer> E : this.Attributes.entrySet()) {
                config.set(E.getKey().getPath(), E.getValue());
            }
            config.set("Points", this.Points);
            config.save(dataFile);
        } catch (IOException ex) {
            Logger.getLogger(PlayerData.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static PlayerData LoadConfig(Player p) {
        try {
            File dataFolder = Data.Main.getDataFolder();
            if (!dataFolder.exists()) {
                dataFolder.mkdirs();
            }
            File dataFile = new File(dataFolder, p.getName() + ".yml");
            PlayerData pd;
            if (!dataFile.exists()) {
                dataFile.createNewFile();
                pd = new PlayerData(p);
                FileConfiguration config = YamlConfiguration.loadConfiguration(dataFile);
                for (AbType t : AbType.values()) {
                    config.set(t.getPath(), 0);
                }
                config.set("Points", PlayerData.DefaultPoints);
                config.save(dataFile);
                return pd;
            }
            pd = new PlayerData(p.getName());
            FileConfiguration config = YamlConfiguration.loadConfiguration(dataFile);
            for (AbType t : AbType.values()) {
                pd.Attributes.put(t, config.getInt(t.getPath()));
            }
            pd.Points = config.getInt("Points");
            for (SkillType st : SkillType.values()) {
                if (!pd.SkillList.contains(st)) {
                    if (st.getSkill().CouldLearn(pd)) {
                        pd.SkillList.add(st);
                    }
                }
            }
            return pd;
        } catch (IOException ex) {
            Logger.getLogger(PlayerData.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public static PlayerData LoadConfig_of(OfflinePlayer p) {
        try {
            File dataFolder = Data.Main.getDataFolder();
            if (!dataFolder.exists()) {
                dataFolder.mkdirs();
            }
            File dataFile = new File(dataFolder, p.getName() + ".yml");
            PlayerData pd;
            if (!dataFile.exists()) {
                dataFile.createNewFile();
                pd = new PlayerData(p);
                FileConfiguration config = YamlConfiguration.loadConfiguration(dataFile);
                for (AbType t : AbType.values()) {
                    config.set(t.getPath(), 0);
                }
                config.set("Points", PlayerData.DefaultPoints);
                config.save(dataFile);
                return pd;
            }
            pd = new PlayerData(p.getName());
            FileConfiguration config = YamlConfiguration.loadConfiguration(dataFile);
            for (AbType t : AbType.values()) {
                pd.Attributes.put(t, config.getInt(t.getPath()));
            }
            pd.Points = config.getInt("Points");
            for (SkillType st : SkillType.values()) {
                if (!pd.SkillList.contains(st)) {
                    if (st.getSkill().CouldLearn(pd)) {
                        pd.SkillList.add(st);
                    }
                }
            }
            return pd;
        } catch (IOException ex) {
            Logger.getLogger(PlayerData.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
}
