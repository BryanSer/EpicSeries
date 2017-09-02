/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Br.EpicAttributes;

import Br.EpicAttributes.Datas.AbType;
import Br.EpicAttributes.Datas.PlayerData;
import Br.EpicAttributes.Events.HealthEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

/**
 *
 * @author Administrator
 */
public class RecoverTask extends BukkitRunnable {

    public static List<String> onAtk = new ArrayList<>();
    public static Map<String, BukkitTask> Tasks = new HashMap<>();

    @Override
    public void run() {

        for (Entry<String, PlayerData> E : Data.PlayerDatas.entrySet()) {
            OfflinePlayer op = Bukkit.getOfflinePlayer(E.getKey());
            if (op.isOnline()) {
                if (onAtk.contains(op.getName())) {
                    continue;
                }
                Player p = op.getPlayer();

                double heal = p.getMaxHealth() * (Data.PlayerDatas.get(p.getName()).getAttribute(AbType.Recover) * 0.0025);
                HealthEvent he = new HealthEvent(heal, HealthEvent.HealType.Recover, p);
                Bukkit.getPluginManager().callEvent(he);
                heal = he.getHealth();
                if (p.getHealth() + heal >= p.getMaxHealth()) {
                    p.setHealth(p.getMaxHealth());
                } else {
                    p.setHealth(p.getHealth() + heal);
                }
            }
        }
    }

    public static void addAtk(Player p) {
        if (Tasks.containsKey(p.getName())) {
            Tasks.get(p.getName()).cancel();
            Tasks.remove(p.getName());
        }
        if (!onAtk.contains(p.getName())) {
            onAtk.add(p.getName());
        }

        RecoverSubTask rst = new RecoverSubTask(p.getName());
        Tasks.put(p.getName(), rst.runTaskLater(Data.Main, 60L));
    }
}

class RecoverSubTask extends BukkitRunnable {

    RecoverSubTask(String s) {
        this.Name = s;
    }

    public String Name;

    @Override
    public void run() {
        RecoverTask.onAtk.remove(this.Name);
        this.cancel();
    }

}
