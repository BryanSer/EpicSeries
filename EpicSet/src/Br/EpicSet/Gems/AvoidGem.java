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
public class AvoidGem extends Gem {

    public AvoidGem() {
        super.MaxLevel = Level.Five;
        super.Name = "AvoidGem";
        super.Priority = 10;
        ItemStack is = new ItemStack(Material.EMERALD);
        is.setAmount(1);
        ItemMeta im = is.getItemMeta();
        im.setDisplayName("§b闪避之石-%Level%" + Utils.GemCode);
        is.setItemMeta(im);
        Lores.Lore(is, new String[]{
            "&6&l闪避宝石%Level%",
            "&a&l安装后将增肌%data%的闪避属性"
        });
        super.Display = is;
    }

    @Override
    public void onWear(Player p, GemData gd) {
        Br.EpicAttributes.Datas.PlayerData pd = Br.EpicAttributes.Data.PlayerDatas.get(p.getName());
        int get = pd.getGemAttributes().get(AbType.Avoid);
        get += gd.getLevel().getInt() * 5;
        pd.getGemAttributes().put(AbType.Avoid, get);
    }

    @Override
    public void onPutout(Player p, GemData gd) {
        Br.EpicAttributes.Datas.PlayerData pd = Br.EpicAttributes.Data.PlayerDatas.get(p.getName());
        int get = pd.getGemAttributes().get(AbType.Avoid);
        get -= gd.getLevel().getInt() * 5;
        pd.getGemAttributes().put(AbType.Avoid, get);
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
        Utils.replaceLore(is, "%data%", gd.getInt() * 5 + "");
        Utils.replaceLore(is, "%Level%", gd.getDisplay());
        Lores.addLore(is, "§r§A§r§v§r§o§r§i§r§d§r§G§r§e§r§m§" + gd.getInt());
        return is;
    }
}
