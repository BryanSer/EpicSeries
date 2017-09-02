/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Br.EpicAttributes;

import Br.EpicAttributes.Datas.PlayerData;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 *
 * @author Bryan_lzh
 */
public class ConfigListener implements Listener {

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerJoin(PlayerJoinEvent evt) {
        PlayerData pd = PlayerData.LoadConfig(evt.getPlayer());
        if (pd != null) {
            Data.PlayerDatas.put(evt.getPlayer().getName(), pd);
        }
        evt.getPlayer().setHealthScale(60D);
        evt.getPlayer().setHealthScaled(true);
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerQuit(PlayerQuitEvent evt) {
        PlayerData pd = Data.PlayerDatas.get(evt.getPlayer().getName());
        if (pd != null) {
            pd.save();
            Data.PlayerDatas.remove(evt.getPlayer().getName());
        }
    }
}
