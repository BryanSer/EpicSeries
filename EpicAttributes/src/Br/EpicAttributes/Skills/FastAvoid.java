/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Br.EpicAttributes.Skills;

import Br.EpicAttributes.Data;
import Br.EpicAttributes.Datas.AbType;
import Br.EpicAttributes.Datas.PlayerData;
import Br.EpicAttributes.Events.AvoidEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

/**
 *
 * @author Administrator
 */
public class FastAvoid extends Skill implements Listener {

    public FastAvoid() {
        super.DisplayName = "§b快速闪避";
    }

    @EventHandler
    public void onAvoid(AvoidEvent evt) {
        PlayerData pd = Data.PlayerDatas.get(evt.p.getName());
        if (pd != null) {
            if (pd.getSkills().contains(this.SkiType)) {
                evt.p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 60, 0));
                evt.p.sendMessage("§a你发动了" + this.DisplayName);
            }
        }
    }

    @Override
    public String[] getDescription() {
        return new String[]{
            "§a[" + this.DisplayName + "§a]",
            "§b技能星级： §d★☆",
            "§b无冷却时间",
            "§b触发方式: §a闪避时触发",
            "§b技能效果: §a闪避后给予速度I的效果"
        };
    }

    @Override
    public boolean CouldLearn(PlayerData pd) {
        return pd.getAttributes().get(AbType.Avoid) >= 50;
    }

}
