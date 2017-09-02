/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Br.EpicAttributes.Skills;

import java.util.HashMap;
import java.util.Map;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

/**
 *
 * @author Administrator
 */
public class ColdDown extends BukkitRunnable {

    protected long tick;
    protected Map<String, Integer> Players = new HashMap<>();

    public ColdDown(long l) {
        this.tick = l;
    }

    public void run() {
        for (String s : this.Players.keySet()) {
            int i = Players.get(s) + 1;
            if (i >= this.tick) {
                this.Players.remove(s);
                continue;
            }
            Players.put(s, i);
        }
    }

    public boolean CouldUse(Player p) {
        return !this.Players.containsKey(p.getName());
    }

    public void addCold(Player p) {
        this.Players.put(p.getName(), 0);
    }
}
