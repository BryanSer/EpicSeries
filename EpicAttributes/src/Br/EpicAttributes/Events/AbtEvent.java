/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Br.EpicAttributes.Events;

import Br.EpicAttributes.Data;
import Br.EpicAttributes.Datas.AbType;
import Br.EpicAttributes.Datas.PlayerData;
import java.util.EnumMap;
import java.util.Map;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

/**
 *
 * @author Bryan_lzh
 */
public class AbtEvent extends Event {

    public AbtEvent(EntityDamageByEntityEvent evt) {
        this.Damager = evt.getDamager();
        this.Entity = evt.getEntity();
        if (this.Damager != null) {
            if (this.Damager instanceof Projectile) {
                Projectile a = (Projectile) this.Damager;
                if (a.getShooter() instanceof Entity) {
                    this.Damager = (Entity) a.getShooter();
                }
            }
            if (this.Damager instanceof Player) {
                PlayerData pd = Data.PlayerDatas.get(this.Damager.getName());
                if (pd != null) {
                    this.DamagerAb = new EnumMap<>(AbType.class);
                    for (AbType a : AbType.values()) {
                        this.DamagerAb.put(a, pd.getAttribute(a));
                    }
                } else {
                    this.DamagerAb = AbType.getDefaultMap();
                }
            } else {
                this.DamagerAb = AbType.getDefaultMap();
            }
        } else {
            this.DamagerAb = AbType.getDefaultMap();
        }
        if (this.Entity instanceof Player) {
            PlayerData pd = Data.PlayerDatas.get(this.Entity.getName());
            if (pd != null) {
                this.EntityAb = new EnumMap<>(AbType.class);
                for (AbType a : AbType.values()) {
                    this.EntityAb.put(a, pd.getAttribute(a));
                }
            } else {
                this.EntityAb = AbType.getDefaultMap();
            }
        } else {
            this.EntityAb = AbType.getDefaultMap();
        }
    }

    Entity Damager;
    Entity Entity;
    Map<AbType, Integer> DamagerAb;
    Map<AbType, Integer> EntityAb;
    double ExtraDamageRate = 0d;

    public double getExtraDamageRate() {
        return this.ExtraDamageRate;
    }

    public void addExtraDamageRate(double d) {
        this.ExtraDamageRate += d;
    }

    public Map<AbType, Integer> getDamagerAb() {
        return this.DamagerAb;
    }

    public Map<AbType, Integer> getEntityAb() {
        return this.EntityAb;
    }

    public Entity getDamager() {
        return this.Damager;
    }

    public Entity getEntity() {
        return this.Entity;
    }

    public boolean isDamagerPlayer() {
        return this.Damager instanceof Player;
    }

    public boolean isEntityPlayer() {
        return this.Entity instanceof Player;
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
