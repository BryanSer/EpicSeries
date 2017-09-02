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
import Br.EpicAttributes.Events.CritEvent;
import Br.EpicAttributes.Skills.Skill;
import Br.EpicAttributes.Utils;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

/**
 *
 * @author Administrator
 */
public class Bramble extends Skill implements Listener {
    
    public Bramble(){
        super.DisplayName = "§c荆棘";
    }

    List<String> DamageList = new ArrayList<>();

    @EventHandler
    public void onCrit(CritEvent evt) {
        if (evt.getAbtEvent().isEntityPlayer() && !this.DamageList.contains(evt.getAbtEvent().getEntity().getName())) {
            Player p = (Player) evt.getAbtEvent().getEntity();
            if (p.getHealth() / p.getMaxHealth() < 0.6d) {
                return;
            }
            PlayerData pd = Data.PlayerDatas.get(evt.getAbtEvent().getEntity().getName());
            if (pd.getSkills().contains(this.SkiType)) {
                this.DamageList.add(p.getName());
            }
        }
    }

    @EventHandler
    public void onEnd(AbtEndEvent evt) {
        if (evt.getAbtEvent().isEntityPlayer()) {
            PlayerData pd = Data.PlayerDatas.get(evt.getAbtEvent().getEntity().getName());
            if (pd.getSkills().contains(this.SkiType) && this.DamageList.contains(evt.getAbtEvent().getEntity().getName())) {
                if (evt.getAbtEvent().getDamager() instanceof LivingEntity) {
                    LivingEntity le = (LivingEntity) evt.getAbtEvent().getDamager();
                    le.damage(evt.getFinalDamage());
                    le.sendMessage("§c对方触发荆棘技能，反弹给你" + Utils.getDecimalString(evt.getFinalDamage())
                            + "伤害");
                    evt.setCancelled(true);
                }
                this.DamageList.remove(evt.getAbtEvent().getEntity().getName());
            }
        }
    }

    @Override
    public String[] getDescription() {
        return new String[]{
            "§a[" + this.DisplayName + "§a]",
            "§b技能星级: §d★★",
            "§b触发方式: §a自己血量高于百分之60，且对方打出了一次暴击",
            "§b技能效果: §6完全反弹下一次受到的伤害"
        };
    }

    @Override
    public boolean CouldLearn(PlayerData pd) {
        return pd.getAttributes().get(AbType.Def) >= 120;
    }

}
