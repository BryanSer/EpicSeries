/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Br.EpicManager;

import Br.EpicAttributes.Datas.AbType;
import java.util.Map;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerExpChangeEvent;
import org.bukkit.event.player.PlayerLevelChangeEvent;

/**
 *
 * @author Bryan_lzh
 */
public class EpicListener implements Listener {

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerLevelUp(PlayerLevelChangeEvent evt) {
        if (evt.getNewLevel() > evt.getOldLevel()) {
            Br.EpicAttributes.Datas.PlayerData pd = Br.EpicAttributes.Data.PlayerDatas.get(evt.getPlayer().getName());
            pd.addPoints(1);
            
            int count = 0;
            for (Map.Entry<AbType, Integer> E : pd.getAttributes().entrySet()) {
                count += E.getValue();
            }
            count += pd.getPoints();
            if (count < evt.getNewLevel()) {
                int add = evt.getNewLevel() - count;
                pd.addPoints(add);
            }
        }
    }
}
