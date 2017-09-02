/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Br.EpicGuys.Buff;

import Br.API.Lores;
import Br.EpicAttributes.Events.HealthEvent;
import Br.EpicGuys.Data;
import Br.EpicGuys.Guy.Guy;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 *
 * @author Administrator
 */
public class Tough implements Buff {

    public final String Display = "§6坚韧";
    public final String Name = "Tough";
    public final ItemStack Item;

    public Tough() {
        ItemStack is = new ItemStack(Material.IRON_CHESTPLATE);
        ItemMeta im = is.getItemMeta();
        im.setDisplayName("§6§l坚韧");
        is = Lores.Lore(is, new String[]{
            "§a给予全体公会成员额外的生命回复",
            "§b玩家生命值越低时回复效果越强",
            "§e每损失1%的生命增加1%的回复效果",
            "§b对于属性值所给予的生命回复效果(包括技能) 最大增加30%的效果",
            "§b对于其他的生命回复(如吸血) 最大增加50%的效果"
        });
        this.Item = is;
    }

    private List<String> Learned = new ArrayList<>();

    @EventHandler
    public void onHealth(HealthEvent evt) {
        if (!(evt.getEntity() instanceof Player)) {
            return;
        }
        Guy g = Data.Gyus.get(Data.PlayerData.get(evt.getEntity().getName()));
        if (g == null) {
            return;
        }
        if (!Learned.contains(g.getDisplayName())) {
            return;
        }

        Player p = (Player) evt.getEntity();
        double boost = 1 - (p.getHealth() / p.getMaxHealth());
        if (boost >= 0.5d) {
            boost = 0.5d;
        }
        if (evt.getType() == HealthEvent.HealType.Recover) {
            if (boost >= 0.3d) {
                boost = 0.3d;
            }
        }
        evt.setHealth(evt.getHealth() * (1 + boost));
    }

    @Override
    public boolean isEnable(Guy g) {
        return this.Learned.contains(g.getDisplayName());
    }

    @Override
    public void addGuy(Guy g) {
        this.Learned.add(g.getDisplayName());
    }

    @Override
    public void removeGuy(Guy g) {
        this.Learned.remove(g.getDisplayName());
    }

    @Override
    public Collection<String> getEnable() {
        return this.Learned;
    }

    @Override
    public ItemStack getDisplay() {
        return this.Item.clone();
    }

    @Override
    public int NeedLevel() {
        return 2;
    }

    @Override
    public String getName() {
        return this.Name;
    }

    @Override
    public String getDisplayName() {
        return this.Display;
    }

}
