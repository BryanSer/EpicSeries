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
public class ResistanceGem extends Gem {

    public int getDisplay(Level l) {
        switch (l) {
            case One:
                return 5;
            case Two:
                return 10;
            case Three:
                return 15;
            case Four:
                return 20;
            case Five:
                return 25;
        }
        return 0;
    }

    public ResistanceGem() {
        super.MaxLevel = Level.Five;
        super.Name = "ResistanceGem";
        super.Priority = 4;
        ItemStack is = new ItemStack(Material.EMERALD);
        is.setAmount(1);
        ItemMeta im = is.getItemMeta();
        im.setDisplayName("§b抵抗之石-%Level%" + Utils.GemCode);
        is.setItemMeta(im);
        Lores.Lore(is, new String[]{
            "&6&l抵抗宝石%Level%",
            "&a&l安装后将增加%data%%原始防御属性",
            "&c&l额外属性将在每次重新登入服务器时刷新"
        });
        super.Display = is;
    }

    @Override
    public void onWear(Player p, GemData gd) {
        Br.EpicAttributes.Datas.PlayerData pd = Br.EpicAttributes.Data.PlayerDatas.get(p.getName());
        int get = pd.getAttributes().get(AbType.Def);
        double rate = (this.getDisplay(gd.getLevel()) * 0.01d);
        get *= rate;
        get += pd.getGemAttributes().get(AbType.Def);
        pd.getGemAttributes().put(AbType.Def, get);
    }

    @Override
    public void onPutout(Player p, GemData gd) {
        Br.EpicAttributes.Datas.PlayerData pd = Br.EpicAttributes.Data.PlayerDatas.get(p.getName());
        int get = pd.getAttributes().get(AbType.Def);
        double rate = (this.getDisplay(gd.getLevel()) * 0.01d);
        get *= rate;
        get = pd.getGemAttributes().get(AbType.Def) - get;
        pd.getGemAttributes().put(AbType.Def, get);
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
        Utils.replaceLore(is, "%data%", this.getDisplay(gd) + "");
        Utils.replaceLore(is, "%Level%", gd.getDisplay());
        Lores.addLore(is, "§r§R§r§e§r§s§r§i§r§s§r§t§r§a§r§n§r§c§r§e§r§G§r§e§r§m§" + gd.getInt());
        return is;
    }
}
