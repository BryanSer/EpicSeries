/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Br.EpicAttributes.Skills;

import Br.EpicAttributes.Data;
import Br.EpicAttributes.Skills.Extra.*;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;

/**
 *
 * @author Bryan_lzh
 */
public enum SkillType {
    //  GreatAtk(new GreatAtk(), "ga"),
    //  FastAvoid(new FastAvoid(), "fa"),
    // Parry(new Parry(), "p"),
    //  EmergencyShield(new EmergencyShield(), "ES"),
    Violent(new Violent(), "v"),
    Insidious(new Insidious(), "i"),
    Warriors(new Warriors(), "w"),
    Absorption(new Absorption(), "abs"),
    SuicideAttack(new SuicideAttack(), "sa"),
    Bramble(new Bramble(), "b"),
    Healing(new Healing(), "h"),
    IceJudgment(new IceJudgment(), "ij"),
    UltimateRecovery(new UltimateRecovery(), "ur"),
    HardenedArmor(new HardenedArmor(), "ha");

    private Skill Ski;
    public static Map<String, SkillType> Datas = new HashMap<>();

    public static SkillType FindByCode(String s) {
        return Datas.get(s.toUpperCase());
    }

    SkillType(Skill ski, String s) {
        if (ski instanceof Listener) {
            Listener l = (Listener) ski;
            Bukkit.getPluginManager().registerEvents(l, Data.Main);
        }
        this.Ski = ski;
        ski.setName(ski.getClass().getSimpleName());
        s = s.toUpperCase();
        ski.setSkillType(this);
        ski.setFindCode(s);
        ski.init();
    }

    public Skill getSkill() {
        return this.Ski;
    }

}
