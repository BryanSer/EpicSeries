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
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.scheduler.BukkitRunnable;

/**
 *
 * @author Administrator
 */
public class SuicideAttack extends Skill implements Listener {

    public SuicideAttack() {
        super.DisplayName = "§c过河拆桥";
    }

    @EventHandler
    public void onPlayerDeath(final PlayerDeathEvent evt) {
        PlayerData pd = Data.PlayerDatas.get(evt.getEntity().getName());
        if (pd.getSkills().contains(this.SkiType)) {
            Location loc = evt.getEntity().getLocation();
            final double x = loc.getX();
            final double y = loc.getY();
            final double z = loc.getZ();
            final String world = loc.getWorld().getName();
            (new BukkitRunnable() {
                @Override
                public void run() {
                    evt.getEntity().sendMessage("§6§l技能触发者: 准备自爆~");
                    Bukkit.getWorld(world).createExplosion(x, y, z, 10F, false, false);
                    evt.getEntity().spigot().respawn();
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "spawn " + evt.getEntity().getName());
                }
            }).runTaskLater(Data.Main, 30L);
        }
    }

    @Override
    public String[] getDescription() {
        return new String[]{
            "§a[" + this.DisplayName + "§a]",
            "§b技能星级: §d★★★",
            "§b触发方式与效果: §a死亡后1.5秒造成大爆炸",};
    }

    @Override
    public boolean CouldLearn(PlayerData pd) {
        return pd.getAttributes().get(AbType.Atk) >= AbType.Atk.getMax();
    }

}
