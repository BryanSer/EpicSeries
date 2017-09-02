/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Br.EpicAttributes.Events;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

/**
 *
 * @author Administrator
 */
public class CritEvent extends Event {

    AbtEvent abe;
    double CritDamage = 0d;

    public CritEvent(AbtEvent abe, double damage) {
        this.abe = abe;
        this.CritDamage = damage;
    }

    public void setDamage(double d) {
        this.CritDamage = d;
    }

    public double getDamage() {
        return this.CritDamage;
    }

    public AbtEvent getAbtEvent() {
        return this.abe;
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
