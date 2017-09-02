/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Br.EpicAttributes.Skills.Extra;

import Br.EpicAttributes.Data;
import Br.EpicAttributes.Datas.AbType;
import Br.EpicAttributes.Datas.PlayerData;
import Br.EpicAttributes.Skills.Skill;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

/**
 *
 * @author Administrator
 */
public class Healing extends Skill implements Listener {

    public Healing() {
        super.DisplayName = "§d治愈";
    }

    @EventHandler
    public void onKill(PlayerDeathEvent evt) {
        if (evt.getEntity().getKiller() != null) {
            Player killer = evt.getEntity().getKiller();
            PlayerData pd = Data.PlayerDatas.get(killer.getName());
            if (pd != null) {
                if (pd.getSkills().contains(this.SkiType)) {
                    double health = killer.getMaxHealth() * 0.2d;
                    if (killer.getHealth() + health >= killer.getMaxHealth()) {
                        killer.setHealth(killer.getMaxHealth());
                    } else {
                        killer.setHealth(killer.getHealth() + health);
                    }
                    killer.sendMessage("§6触发技能者:你触发了恢复技能");
                }
            }
        }
    }

    @Override
    public String[] getDescription() {
        return new String[]{
            "§a[" + this.DisplayName + "§a]",
            "§b技能星级: §d☆",
            "§b触发方式: §a击杀玩家时",
            "§b技能效果: §d回复20%的最大生命值"
        };
    }

    @Override
    public boolean CouldLearn(PlayerData pd) {
        return pd.getAttributes().get(AbType.Health) >= 20;
    }

}
