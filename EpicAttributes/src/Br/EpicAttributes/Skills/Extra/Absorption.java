/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Br.EpicAttributes.Skills.Extra;

import Br.EpicAttributes.Data;
import Br.EpicAttributes.Datas.AbType;
import Br.EpicAttributes.Datas.PlayerData;
import Br.EpicAttributes.Skills.ColdDown;
import Br.EpicAttributes.Skills.Skill;
import Br.EpicAttributes.Utils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class Absorption extends Skill implements Listener {

    public Absorption() {
        super.CD = new ColdDown(2L * 60L * 20L);
        super.DisplayName = "§6吸收";
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onAtk(EntityDamageByEntityEvent evt) {
        if (evt.getEntity() instanceof Player) {
            Player p = (Player) evt.getEntity();
            PlayerData pd = Data.PlayerDatas.get(p.getName());
            if (pd != null) {
                if (pd.getSkills().contains(this.SkiType)) {
                    if (!super.CD.CouldUse(p)) {
                        return;
                    }
                    double health = p.getHealth() - evt.getFinalDamage();
                    if (health <= p.getMaxHealth() * 0.3d) {
                        int def = pd.getAttributes().get(AbType.Def);
                        if (def >= 200) {
                            def = 200;
                        }
                        Utils.Absorb(p, def, 20);
                        p.sendMessage("§6触发技能者:你触发了吸收技能");
                    }
                }
            }
        }
    }

    @Override
    public String[] getDescription() {
        return new String[]{
            "§a[" + this.DisplayName + "§a]",
            "§b技能星级: §d★★",
            "§b技能冷却时间:§a 2分钟",
            "§b触发方式: §a生命值低于30%时自动触发",
            "§b技能效果: §d每点Def§a增加§d4点§a伤害吸收",
            "§a最大吸收§d800点",
            "§a持续§d20s"
        };
    }

    @Override
    public boolean CouldLearn(PlayerData pd) {
        return pd.getAttributes().get(AbType.Def) >= 80;
    }

}
