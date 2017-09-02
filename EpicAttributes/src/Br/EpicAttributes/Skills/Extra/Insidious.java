/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Br.EpicAttributes.Skills.Extra;

import Br.EpicAttributes.Data;
import Br.EpicAttributes.Datas.AbType;
import Br.EpicAttributes.Datas.PlayerData;
import Br.EpicAttributes.Events.AbtEvent;
import Br.EpicAttributes.Skills.ColdDown;
import Br.EpicAttributes.Skills.Skill;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

/**
 *
 * @author Administrator
 */
public class Insidious extends Skill implements Listener {

    public Insidious() {
        super.DisplayName = "§b阴险";
        super.CD = new ColdDown(40L);
    }

    @EventHandler
    public void onAtk(AbtEvent evt) {
        if (evt.getDamager() instanceof Player) {
            Player p = (Player) evt.getDamager();
            PlayerData pd = Data.PlayerDatas.get(p.getName());
            if (pd.getSkills().contains(this.SkiType)) {
                if (!this.CD.CouldUse(p)) {
                    return;
                }
                double h = p.getHealth() / p.getMaxHealth();
                if (h <= 0.5d) {
                    if (evt.getEntity() instanceof LivingEntity) {
                        LivingEntity le = (LivingEntity) evt.getEntity();
                        le.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 60, 1));
                        p.sendMessage("§b触发技能者:你触发了阴险技能");
                        this.CD.addCold(p);
                    }
                }
            }
        }
    }

    @Override
    public String[] getDescription() {
        return new String[]{
            "§a[" + this.DisplayName + "§a]",
            "§b技能星级: §d★☆",
            "§b技能冷却时间:§a 2s",
            "§b触发方式: §a自身血量少于50%时攻击自动触发",
            "§b技能效果: §a对对方造成3秒的失明II"
        };
    }

    @Override
    public boolean CouldLearn(PlayerData pd) {
        return pd.getAttributes().get(AbType.Atk) >= 80;
    }

}
