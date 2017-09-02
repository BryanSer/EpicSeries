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
import Br.EpicAttributes.Skills.SkillType;
import java.util.Map;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

/**
 *
 * @author Administrator
 */
public class UltimateRecovery extends Skill {

    public UltimateRecovery() {
        super.DisplayName = "§c§l极限恢复";
        URTask urt = new URTask();
        urt.runTaskTimer(Data.Main, 200L, 20L);
    }

    @Override
    public String[] getDescription() {
        return new String[]{
            "§a[" + this.DisplayName + "§a]",
            "§b技能星级: §d★★☆",
            "§b触发方式: §a全自动",
            "§b技能效果: §d每秒回复1%的已损失生命值"
        };
    }

    @Override
    public boolean CouldLearn(PlayerData pd) {
        return pd.getAttributes().get(AbType.Health) >= 300;
    }

}

class URTask extends BukkitRunnable {

    @Override
    public void run() {
        for (Map.Entry<String, PlayerData> E : Data.PlayerDatas.entrySet()) {
            if (E.getValue().getSkills().contains(SkillType.UltimateRecovery)) {
                Player p = Bukkit.getPlayer(E.getKey());
                if (p != null) {
                    double health = (p.getMaxHealth() - p.getHealth()) * 0.01d;
                    if (health == 0d) {
                        continue;
                    }
                    if (p.getHealth() + health >= p.getMaxHealth()) {
                        p.setHealth(p.getMaxHealth());
                    } else {
                        p.setHealth(p.getHealth() + health);
                    }
                }
            }
        }
    }
}
