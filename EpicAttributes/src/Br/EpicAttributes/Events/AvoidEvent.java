/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Br.EpicAttributes.Events;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

/**
 *
 * @author Bryan_lzh
 */
public class AvoidEvent extends Event {
    public EntityDamageByEntityEvent evt;
    public Player p;//闪避者

    public AvoidEvent(EntityDamageByEntityEvent evt,Player entity) {
        this.evt = evt;
        this.p = entity;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
    private static final HandlerList handlers = new HandlerList();

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }
}
