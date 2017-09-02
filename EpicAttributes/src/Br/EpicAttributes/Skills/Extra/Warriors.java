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
import Br.EpicAttributes.Skills.Skill;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

/**
 *
 * @author Administrator
 */
public class Warriors extends Skill implements Listener {

    public Warriors() {
        super.DisplayName = "§e无双";
    }

    @EventHandler
    public void onAtk(AbtEvent evt) {
        if (evt.getDamager() instanceof Player) {
            Player p = (Player) evt.getDamager();
            if (p.getHealth() == p.getMaxHealth()) {
                PlayerData pd = Data.PlayerDatas.get(p.getName());
                if (pd.getSkills().contains(this.SkiType)) {
                    evt.addExtraDamageRate(0.2D);
                    p.sendMessage("§6触发技能者:你触发了无双技能");
                }
            }
        }
    }

    @Override
    public String[] getDescription() {
        return new String[]{
            "§a[" + this.DisplayName + "§a]",
            "§b技能星级: §d★★",
            "§b触发方式: §a满血时攻击自动触发",
            "§b技能效果: §6造成额外20%的伤害"
        };
    }

    @Override
    public boolean CouldLearn(PlayerData pd) {
        return pd.getAttributes().get(AbType.Atk) >= 150;
    }

}
