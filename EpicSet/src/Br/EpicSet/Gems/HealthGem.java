/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Br.EpicSet.Gems;

import Br.API.Lores;
import Br.EpicAttributes.Datas.AbType;
import Br.EpicSet.Data.GemData;
import Br.EpicSet.Enums.Level;
import Br.EpicSet.Gem;
import Br.EpicSet.Utils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 *
 * @author Bryan_lzh
 */
public class HealthGem extends Gem {

    private int getDisplayHeal(Level l) {
        switch (l) {
            case One:
                return 500;
            case Two:
                return 1000;
            case Three:
                return 1500;
            case Four:
                return 2000;
            case Five:
                return 2500;
        }
        return 0;
    }

    public HealthGem() {
        super.Priority = 1;
        super.Name = "HealthGem";
        super.MaxLevel = Level.Five;
        ItemStack is = new ItemStack(Material.EMERALD);
        is.setAmount(1);
        ItemMeta im = is.getItemMeta();
        im.setDisplayName("§b生命之石-%Level%" + Utils.GemCode);
        is.setItemMeta(im);
        Lores.Lore(is, new String[]{
            "&6&l生命宝石%Level%",
            "&a&l安装后将提升%data%点生命值属性"
        });
        super.Display = is;
    }

    @Override
    public void onWear(Player p, GemData gd) {
        Br.EpicAttributes.Datas.PlayerData pd = Br.EpicAttributes.Data.PlayerDatas.get(p.getName());
        int get = pd.getGemAttributes().get(AbType.Health);
        get += this.getDisplayHeal(gd.getLevel());
        pd.getGemAttributes().put(AbType.Health, get);
    }

    @Override
    public void onPutout(Player p, GemData gd) {
        Br.EpicAttributes.Datas.PlayerData pd = Br.EpicAttributes.Data.PlayerDatas.get(p.getName());
        int get = pd.getGemAttributes().get(AbType.Health);
        get -= this.getDisplayHeal(gd.getLevel());
        pd.getGemAttributes().put(AbType.Health, get);
    }

    @Override
    public ItemStack DrawDisplay(GemData gd) {
        return this.DrawDisplay(gd.getLevel());
    }

    @Override
    public ItemStack DrawDisplay(Level gd) {
        ItemStack is = this.Display.clone();
        ItemMeta im = is.getItemMeta();
        im.setDisplayName(im.getDisplayName().replaceAll("%Level%", gd.getDisplay()));
        is.setItemMeta(im);
        Utils.replaceLore(is, "%data%", this.getDisplayHeal(gd) + "");
        Utils.replaceLore(is, "%Level%", gd.getDisplay());
        Lores.addLore(is, "§r§H§r§e§r§a§r§l§r§t§r§h§r§G§r§e§r§m§" + gd.getInt());
        return is;
    }
}
