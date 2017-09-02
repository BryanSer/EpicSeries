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
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

/**
 *
 * @author Administrator
 */
public class HardenedArmor extends Skill implements Listener {
    
    public HardenedArmor(){
        super.DisplayName = "§6硬化护甲";
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onCrit(CritEvent evt) {
        if (evt.getAbtEvent().isEntityPlayer()) {
            Player p = (Player) evt.getAbtEvent().getEntity();
            PlayerData pd = Data.PlayerDatas.get(p.getName());
            if (pd != null && pd.getSkills().contains(this.SkiType)) {
                evt.setDamage(evt.getDamage() * 0.9d);
            }
        }
    }

    @Override
    public String[] getDescription() {
        return new String[]{
            "§a[" + this.DisplayName + "§a]",
            "§b技能星级: §d★★★",
            "§b触发方式: §a遭受暴击",
            "§b技能效果: §6减少10%的暴击伤害"
        };
    }

    @Override
    public boolean CouldLearn(PlayerData pd) {
        return pd.getAttributes().get(AbType.Health) >= AbType.Health.getMax();
    }

}
