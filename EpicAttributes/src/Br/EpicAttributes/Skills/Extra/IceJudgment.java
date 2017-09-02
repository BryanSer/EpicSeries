/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Br.EpicAttributes.Skills.Extra;

import Br.EpicAttributes.Data;
import Br.EpicAttributes.Datas.AbType;
import Br.EpicAttributes.Datas.PlayerData;
import Br.EpicAttributes.Events.AbtEndEvent;
import Br.EpicAttributes.Skills.ColdDown;
import Br.EpicAttributes.Skills.Skill;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

/**
 *
 * @author Bryan_lzh
 */
public class IceJudgment extends Skill implements Listener {

    public IceJudgment() {
        super.DisplayName = "§b寒冰惩戒";
        super.CD = new ColdDown(200L);
    }

    @EventHandler
    public void onAbt(AbtEndEvent evt) {
        if (evt.getAbtEvent().isDamagerPlayer() && evt.getAbtEvent().isEntityPlayer()) {
            Player d = (Player) evt.getAbtEvent().getDamager();
            double h = d.getHealth() / d.getMaxHealth();
            if (h > 0.5d) {
                return;
            }
            PlayerData pd = Data.PlayerDatas.get(d.getName());
            if (pd != null && pd.getSkills().contains(super.SkiType)) {
                Player le = (Player) evt.getAbtEvent().getEntity();
                le.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 40, 1));
                super.CD.addCold(d);
                d.sendMessage("§b触发技能者:你触发了技能寒冰惩戒");
            }

        }
    }

    @Override
    public String[] getDescription() {
        return new String[]{
            "§a[" + this.DisplayName + "§a]",
            "§b技能星级: §d★☆",
            "§b技能冷却时间:§a 10s",
            "§b触发方式: §a自身血量少于50%时攻击自动触发",
            "§b技能效果: §a对对方造成2秒的减速II"
        };
    }

    @Override
    public boolean CouldLearn(PlayerData pd) {
        return pd.getAttributes().get(AbType.Health) >= 100;
    }

}
