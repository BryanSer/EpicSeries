/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Br.EpicAttributes.Datas;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Administrator
 */
public enum AbType {

    
    Atk("攻击", 250),
    Def("防御", 250),
    Crit("暴击", 200),
    CritDam("暴击伤害", 100),
    Vampire("吸血", 200),
    Sunder("破甲", 20),
    Recover("恢复", 100),
    Health("生命", 500),
    Avoid("闪避", 200),
    HitP("命中", 150);
    private String Display;
    private int Max;
    private String path;
    private List<String> Lore;

    /**
     * 获取描述
     *
     * @return
     */
    public static String[] getDip() {
        return new String[]{
            "§b属性类型   ：  可用别名",
            "§a" + Atk.getDisplay() + "  :  atk , at",
            "§a" + Def.getDisplay() + "  :  def , d",
            "§a" + Crit.getDisplay() + "  :  crit , c",
            "§a" + CritDam.getDisplay() + ":  critdam , cd",
            "§a" + Vampire.getDisplay() + "  :  vampire , v",
            "§a" + Sunder.getDisplay() + "  :  sunder , s",
            "§a" + Recover.getDisplay() + "  :  recover , r",
            "§a" + Health.getDisplay() + "  :  health , h",
            "§a" + Avoid.getDisplay() + "  :  avoid , a",
            "§a" + HitP.getDisplay() + "  :  hitp , hp",
            "§6只需要输入别名中的一个 即可以表示属性"
        };
    }

    public static AbType getAbType(String s) {
        s = s.toLowerCase();
        if (s.equalsIgnoreCase("at")) {
            return Atk;
        }
        if (s.equalsIgnoreCase("d")) {
            return Def;
        }
        if (s.equalsIgnoreCase("c")) {
            return Crit;
        }
        if (s.equalsIgnoreCase("cd")) {
            return CritDam;
        }
        if (s.equalsIgnoreCase("v")) {
            return Vampire;
        }
        if (s.equalsIgnoreCase("s")) {
            return Sunder;
        }
        if (s.equalsIgnoreCase("r")) {
            return Recover;
        }
        if (s.equalsIgnoreCase("h")) {
            return Health;
        }
        if (s.equalsIgnoreCase("a")) {
            return Avoid;
        }
        if (s.equalsIgnoreCase("hp")) {
            return HitP;
        }
        switch (s) {
            case "atk":
                return Atk;
            case "def":
                return Def;
            case "crit":
                return Crit;
            case "critdam":
                return CritDam;
            case "vampire":
                return Vampire;
            case "sunder":
                return Sunder;
            case "recover":
                return Recover;
            case "health":
                return Health;
            case "avoid":
                return Avoid;
            case "hitp":
                return HitP;
        }
        return null;
    }

    private AbType(String s, int Max) {
        this.Display = s;
        this.Max = Max;
        this.path = "Attributes." + this.toString();
        List<String> lore = new ArrayList<>();
        lore.add("§6最大上限: " + Max);
        switch (s) {
            case "攻击":
                lore.add("§b增加每次造成的伤害");
                lore.add("§a与攻击系技能有关");
                break;
            case "防御":
                lore.add("§b减少每次受到的伤害");
                lore.add("§a与防御系技能有关");
                break;
            case "暴击":
                lore.add("§b增加每次暴击的几率");
                lore.add("§a与攻击系技能有关");
                break;
            case "暴击伤害":
                lore.add("§b增加每次暴击时的伤害");
                lore.add("§a与攻击系技能有关");
                break;
            case "吸血":
                lore.add("§b增加每次攻击时的吸血量");
                lore.add("§a与攻击防御系技能有关");
                break;
            case "破甲":
                lore.add("§b增加每次攻击时的破甲几率");
                lore.add("§b破甲将无视对方护甲与属性造成§e真实§b伤害");
                lore.add("§a与攻击系技能有关");
                break;
            case "恢复":
                lore.add("§b增加生命恢复值");
                lore.add("§a与恢复系技能有关");
                break;
            case "生命":
                lore.add("§b增加最大生命值");
                lore.add("§a与恢复系技能有关");
                break;
            case "闪避":
                lore.add("§b增加闪避几率");
                lore.add("§b闪避能将对方攻击完全无效化");
                lore.add("§a与闪避系技能有关");
                break;
            case "命中":
                lore.add("§b攻击时减少对方的闪避几率");
                lore.add("§a与闪避系技能有关");
                break;
        }
        this.Lore = lore;
    }

    public List<String> getLore() {
        return this.Lore;
    }

    public String getDisplay() {
        return this.Display;
    }

    public String getPath() {
        return this.path;
    }

    public int getMax() {
        return this.Max;
    }

    public static Map<AbType, Integer> getDefaultMap() {
        Map<AbType, Integer> m = new EnumMap<>(AbType.class);
        m.put(AbType.Atk, 0);
        m.put(AbType.Def, 0);
        m.put(AbType.Crit, 0);
        m.put(AbType.CritDam, 0);
        m.put(AbType.Sunder, 0);
        m.put(AbType.Vampire, 0);
        m.put(AbType.Recover, 0);
        m.put(AbType.Health, 0);
        m.put(AbType.Avoid, 0);
        m.put(AbType.HitP, 0);
        return m;
    }
}
