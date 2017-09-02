/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Br.EpicAttributes.Skills.Extra;

import Br.EpicAttributes.Data;
import Br.EpicAttributes.Datas.AbType;
import Br.EpicAttributes.Datas.PlayerData;
import Br.EpicAttributes.Events.CritEvent;
import Br.EpicAttributes.Skills.Skill;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

/**
 *
 * @author Administrator
 */
public class Violent extends Skill implements Listener {

    public Violent() {
        super.DisplayName = "§6狂暴";
    }

    @EventHandler
    public void onCrit(CritEvent evt) {
        if (evt.getAbtEvent().getDamager() instanceof Player) {
            PlayerData pd = Data.PlayerDatas.get(((Player) evt.getAbtEvent().getDamager()).getName());
            if (pd.getSkills().contains(this.SkiType)) {
                ((Player) evt.getAbtEvent().getDamager()).addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 60, 0));
                evt.getAbtEvent().getDamager().sendMessage("§6触发技能者:你触发了狂暴技能");
            }
        }
    }

    @Override
    public String[] getDescription() {
        return new String[]{
            "§a[" + this.DisplayName + "§a]",
            "§b技能星级: §d☆",
            "§b触发方式: §a在打出一次暴击伤害后",
            "§b技能效果: §a获得攻击加速"
        };
    }

    @Override
    public boolean CouldLearn(PlayerData pd) {
        return pd.getAttributes().get(AbType.Atk) >= 30;
    }

}
