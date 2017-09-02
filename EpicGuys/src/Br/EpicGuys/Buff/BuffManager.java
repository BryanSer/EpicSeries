/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Br.EpicGuys.Buff;

import Br.API.Data.DataManager;
import Br.API.Data.DataService;
import Br.API.Data.EasyData;
import Br.EpicGuys.Data;
import Br.EpicGuys.Guy.Guy;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author Administrator
 */
public class BuffManager implements Listener {

    private BuffManager() {
    }
    private static BuffManager BF;

    private final String Code = "§6§e§a§r";

    public Inventory getBuffs(Guy g, Player p) {
        Inventory inv = Bukkit.createInventory(p, 54, Code + "公会BUFF选择");

        return inv;
    }

    /**
     * 返回余剩的BUFF选择
     */
    private ItemStack getTimes(Guy g) {
        int def = g.getLevel();
        for (Buff b : Buffs) {
            if (b.isEnable(g)) {
                def--;
            }
        }
        ItemStack is = new ItemStack(Material.DIAMOND);
        is.setAmount(def <= 0 ? 1 : def);
        return is;
    }

    public static BuffManager getBuffManager() {
        if (BF == null) {
            BF = new BuffManager();
        }
        return BF;
    }

    private List<Buff> Buffs = new ArrayList<>();

    public void RegisterBuff(Buff b) {
        this.Buffs.add(b);
    }

    public void SaveData() {
        DataService ds;
        DataManager.RegisterData((ds = new EasyData("EpicGuy.Buff")), "EpicGuy.Buff");
        for (Guy g : Data.Gyus.values()) {
            if (Data.RemoveList.contains(g.getDisplayName())) {
                continue;
            }
            for (Buff b : this.Buffs) {
                ds.set(g.getDisplayName() + "." + b.getName(), b.isEnable(g));
            }
        }
    }
}
