/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Br.EpicAttributes;

import Br.EpicAttributes.Datas.AbType;
import Br.EpicAttributes.Datas.PlayerData;
import Br.EpicAttributes.Events.AbtEndEvent;
import Br.EpicAttributes.Events.AbtEvent;
import Br.EpicAttributes.Events.AvoidEvent;
import Br.EpicAttributes.Events.CritEvent;
import Br.EpicAttributes.Events.HealthEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageModifier;

/**
 *
 * @author Bryan_lzh
 */
public class AtkListener implements Listener {

    @EventHandler
    public void onPlayerAtk(EntityDamageByEntityEvent evt) {
        //商业代码
    }

    @Deprecated
    public void onPlayerAttack(EntityDamageByEntityEvent evt) {
        if (evt.getEntity() instanceof Player) {//闪避最优先
            Player p = (Player) evt.getEntity();
            PlayerData pd = Data.PlayerDatas.get(p.getName());
            double hitp = 0d;
            if (evt.getDamager() instanceof Player) {
                Player d = (Player) evt.getDamager();
                PlayerData dd = Data.PlayerDatas.get(d.getName());
                if (dd != null) {
                    hitp = dd.getAttribute(AbType.HitP) * 0.001d;
                }
            }
            if (Utils.RandomforBoolean((pd.getAttribute(AbType.Avoid) * 0.001d) - hitp)) {
                p.sendMessage("§b你成功的闪避了敌人的攻击");
                AvoidEvent e = new AvoidEvent(evt, p);
                Bukkit.getPluginManager().callEvent(e);
                evt.getDamager().sendMessage("§b敌人闪避了你的攻击");
                evt.setCancelled(true);
                return;
            }
        }
        if (evt.getDamager() instanceof Player) {
            Player Damager = (Player) evt.getDamager();
            PlayerData pd = Data.PlayerDatas.get(Damager.getName());
            if (pd != null) {
                evt.setDamage(evt.getDamage() + pd.getAttribute(AbType.Atk) * 2);
                if (Utils.RandomforBoolean(0.1d + pd.getAttribute(AbType.Crit) * 0.001d)) {
                    double Crit = evt.getDamage();
                    Crit *= (1.5d + pd.getAttribute(AbType.CritDam) * 0.005d);
                    Damager.sendMessage("§b你打出了一次暴击 造成 §a"
                            + (1.5d + pd.getAttribute(AbType.CritDam) * 0.005d) + "倍 §b的伤害");
                    evt.setDamage(Crit);
                }
                double vap = evt.getDamage() * (pd.getAttribute(AbType.Vampire) * 0.002d);
                Damager.setHealth(Damager.getHealth() + vap);
                if (Utils.RandomforBoolean(pd.getAttribute(AbType.Sunder) * 0.01d)) {
                    Damager.sendMessage("§b你打出了一次破甲攻击 造成了§a"
                            + evt.getDamage() + "§b点真实伤害");
                    if (evt.getEntity() instanceof Player) {
                        Player player = (Player) evt.getEntity();
                        player.sendMessage("§c你受到了一次破甲攻击");
                    }
                    evt.setDamage(DamageModifier.MAGIC, evt.getDamage());
                    evt.setDamage(DamageModifier.BASE, 0d);
                    return;
                }
            }
        }

        if (evt.getEntity() instanceof Player) {
            Player p = (Player) evt.getEntity();
            PlayerData pd = Data.PlayerDatas.get(p.getName());
            if (pd != null) {
                evt.setDamage(evt.getDamage() * (1d - pd.getAttribute(AbType.Def) * 0.001d));
                /*   if (pd.getSkills().contains(SkillType.Parry)) {
                    if (Utils.RandomforBoolean(0.2d)) {
                        p.sendMessage("§6你招架了来自敌方的攻击");
                        evt.getDamager().sendMessage("§c对方招架了你的攻击");
                        evt.setDamage(evt.getDamage() * 0.55d);
                    }
                }*/
            }
        }
    }
}
