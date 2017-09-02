/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Br.EpicAttributes.Events;

import org.bukkit.entity.LivingEntity;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 *
 * @author Administrator
 */
public class HealthEvent extends Event {

    public enum HealType {
        Recover,
        Vampire
    }

    private double Health;
    private HealType Type;
    private LivingEntity E;

    public HealthEvent(double d, HealType t, LivingEntity e) {
        this.Health = d;
        this.Type = t;
        this.E = e;
    }

    public LivingEntity getEntity() {
        return this.E;
    }

    public HealType getType() {
        return this.Type;
    }

    public double getHealth() {
        return this.Health;
    }

    public void setHealth(double d) {
        this.Health = d;
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
