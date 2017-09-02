/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Br.EpicAttributes.Events;

import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * 不会对攻击产生任何影响的
 *
 * @author Administrator
 */
public class AbtEndEvent extends Event implements Cancellable {

    AbtEvent abe;
    double Damage;

    public double getFinalDamage() {
        return this.Damage;
    }

    public AbtEndEvent(AbtEvent a, double dam) {
        this.abe = a;
        this.Damage = dam;
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

    @Override
    public boolean isCancelled() {
        return this.c;
    }

    boolean c = false;

    @Override
    public void setCancelled(boolean cancel) {
        this.c = cancel;
    }
}
