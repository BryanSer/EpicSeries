/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Br.EpicSet.Gems;

import Br.API.Lores;
import Br.EpicSet.Data.GemData;
import Br.EpicSet.Enums.Level;
import Br.EpicSet.Gem;
import Br.EpicSet.Utils;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

/**
 *
 * @author Bryan_lzh
 */
public class ScubaGem extends Gem implements Listener {

    private String Codes = "§r§S§r§c§r§u§r§b§r§a§r§G§r§e§r§m§";

    private List<String> PlayerList = new ArrayList<>();

    public ScubaGem() {
        super.Priority = 1;
        super.Name = "ScubaGem";
        super.MaxLevel = Level.One;
        ItemStack is = new ItemStack(Material.DIAMOND);
        is.setAmount(1);
        ItemMeta im = is.getItemMeta();
        im.setDisplayName("§b水肺宝石" + Utils.GemCode);
        is.setItemMeta(im);
        Lores.Lore(is, new String[]{
            "&6&l水肺宝石",
            "&a&l安装后将永久获得水下呼吸"
        });
        super.Display = is;
        super.CellOnJoinAndQuit = true;
    }

    @Override
    public void onWear(Player p, GemData gd) {
        this.OnPlayerJoin(p);
    }

    @Override
    public void OnPlayerJoin(Player p) {
        if (!this.PlayerList.contains(p.getName())) {
            this.PlayerList.add(p.getName());
        }
    }

    @Override
    public void OnPlayerQuit(Player p) {
        if (this.PlayerList.contains(p.getName())) {
            this.PlayerList.remove(p.getName());
        }
    }

    @EventHandler
    public void onPlayerRemoveEffect(PlayerMoveEvent evt) {
        if (this.PlayerList.contains(evt.getPlayer().getName())) {
            if (!evt.getPlayer().hasPotionEffect(PotionEffectType.WATER_BREATHING)) {
                evt.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.WATER_BREATHING, 100, 0));
            }
        }
    }

    @Override
    public void onPutout(Player p, GemData gd) {
        this.OnPlayerQuit(p);
    }

    public ItemStack DrawDisplay(GemData gd) {
        return this.DrawDisplay(gd.getLevel());
    }

    @Override
    public ItemStack DrawDisplay(Level gd) {
        ItemStack is = this.Display.clone();
        Lores.addLore(is, this.Codes + gd.getInt());
        return is;
    }
}
