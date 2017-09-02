/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Br.EpicGuys;

import Br.EpicGuys.Guy.Guy;
import java.util.Map;
import org.bukkit.scheduler.BukkitRunnable;

/**
 *
 * @author Administrator
 */
public class TimeTask extends BukkitRunnable {

    @Override
    public void run() {
        for (Map.Entry<String, Guy> E : Data.Gyus.entrySet()) {
            E.getValue().setActiveValue(E.getValue().getActiveValue() + E.getValue().getOnlinePlayers());
        }
    }

}
