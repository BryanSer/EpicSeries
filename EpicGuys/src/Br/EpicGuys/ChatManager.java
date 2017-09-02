/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Br.EpicGuys;

import Br.EpicGuys.Guy.Guy;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.projectiles.ProjectileSource;

/**
 *
 * @author Bryan_lzh
 */
public class ChatManager implements Listener {

    public static List<String> InGuyChats = new ArrayList<>();

    public static List<String> InGuyFires = new ArrayList<>();

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent evt) {
        if (evt.getEntity() instanceof Player) {
            Player p = (Player) evt.getEntity();
            Player d = null;
            if (evt.getDamager() instanceof Projectile) {
                ProjectileSource s = ((Projectile) evt.getDamager()).getShooter();
                if (s instanceof Player) {
                    d = (Player) s;
                } else {
                    return;
                }
            }
            if (evt.getDamager() instanceof Player) {
                d = (Player) evt.getDamager();
            }
            if (d == null) {
                return;
            }
            if (Data.PlayerData.containsKey(p.getName()) && Data.PlayerData.containsKey(d.getName())) {
                Guy pg = Data.Gyus.get(Data.PlayerData.get(p.getName()));
                Guy dg = Data.Gyus.get(Data.PlayerData.get(d.getName()));
                if (pg == dg) {
                    if (ChatManager.InGuyFires.contains(p.getName()) && ChatManager.InGuyFires.contains(d.getName())) {
                    } else {
                        evt.getDamager().sendMessage("§c你和对方为同一公会的,双方必须同时开启友方伤害才可攻击");
                        evt.setCancelled(true);
                    }
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onChat(org.bukkit.event.player.AsyncPlayerChatEvent evt) {
        if (ChatManager.InGuyChats.contains(evt.getPlayer().getName())) {
            evt.setCancelled(true);
            String msg = evt.getMessage();
            Guy g = Data.Gyus.get(Data.PlayerData.get(evt.getPlayer().getName()));
            if (g == null) {
                evt.setCancelled(false);
                return;
            }
            msg = "§d[公会频道]" + g.getPrefix() + "§a<" + evt.getPlayer().getName() + ">: §r" + msg;
            for (Player p : g.getOnlinePlayer()) {
                p.sendMessage(msg);
            }
        }

        Guy g = Data.Gyus.get(Data.PlayerData.get(evt.getPlayer().getName()));
        if (g != null) {
            String f = evt.getFormat();
            f = g.getPrefix() + f;
            evt.setFormat(f);
        }
    }
}
