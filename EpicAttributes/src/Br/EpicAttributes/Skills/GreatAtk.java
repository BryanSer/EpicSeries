/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Br.EpicAttributes.Skills;

import Br.EpicAttributes.Data;
import Br.EpicAttributes.Datas.AbType;
import Br.EpicAttributes.Datas.PlayerData;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class GreatAtk extends Skill implements Listener{

    public GreatAtk() {
        super.DisplayName = "§6格瑞特打击";
        super.CD = new ColdDown(20L * 20L);
    }

    @EventHandler
    public void onPlayerAtk(EntityDamageByEntityEvent evt) {
        if (evt.getDamager()instanceof Player) {
            Player p = (Player) evt.getDamager();
            PlayerData pd = Data.PlayerDatas.get(p.getName());
            if (pd != null) {
                if (pd.getSkills().contains(this.SkiType)) {
                    if (super.CD.CouldUse(p)) {
                        double d = (pd.getAttributes().get(AbType.Atk) / 2d - 30d);
                        if (d <= 5d) {
                            d = 5d;
                        }
                        evt.setDamage(evt.getDamage() + d);
                        super.CD.addCold(p);
                        p.sendMessage("§b你发动了一次" + this.DisplayName + "§b 造成了§a" + d
                                + "点§e额外§a伤害,冷却时间20s");
                    }
                }
            }
        }
    }

    @Override
    public boolean CouldLearn(PlayerData pd) {
        int atk = pd.getAttributes().get(AbType.Atk);
        int crit = pd.getAttributes().get(AbType.Crit);
        return atk + crit >= 50;
    }

    @Override
    public String[] getDescription() {
        return new String[]{
            "§a[" + this.DisplayName + "§a]",
            "§b技能星级: §d★☆",
            "§b技能冷却时间:§a 20s",
            "§b触发方式: §a攻击时自动触发",
            "§b技能效果: §a造成玩家 §d ATK/2 - 30 的§e额外§a伤害",
            "§e额外§a伤害最少为5"
        };
    }
}
