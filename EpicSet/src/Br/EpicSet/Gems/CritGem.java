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
public class CritGem extends Gem {

    String Code = "§r§C§r§r§r§i§r§t§r§G§r§e§r§m§";

    public CritGem() {
        super.MaxLevel = Level.Five;
        super.Name = "CritGem";
        super.Priority = 9;
        ItemStack is = new ItemStack(Material.REDSTONE);
        is.setAmount(1);
        ItemMeta im = is.getItemMeta();
        im.setDisplayName("§b暴击之石-%Level%" + Utils.GemCode);
        is.setItemMeta(im);
        Lores.Lore(is, new String[]{
            "&6&l暴击宝石%Level%",
            "&a&l安装后将增加%data%%的原始暴击属性",
            "&c&l额外属性将在每次重新登入服务器时刷新"
        });
        super.Display = is;
    }

    private int getChanceforInt(Level l) {
        switch (l) {
            case One:
                return 3;
            case Two:
                return 6;
            case Three:
                return 9;
            case Four:
                return 12;
            case Five:
                return 15;
        }
        return 0;
    }




    @Override
    public void onWear(Player p, GemData gd) {
        Br.EpicAttributes.Datas.PlayerData pd = Br.EpicAttributes.Data.PlayerDatas.get(p.getName());
        int get = pd.getAttributes().get(AbType.Crit);
        double rate = this.getChanceforInt(gd.getLevel()) *0.01d;
        get *= rate;
        get += pd.getGemAttributes().get(AbType.Crit);
        pd.getGemAttributes().put(AbType.Crit, get);
    }

    @Override
    public void onPutout(Player p, GemData gd) {
        Br.EpicAttributes.Datas.PlayerData pd = Br.EpicAttributes.Data.PlayerDatas.get(p.getName());
        int get = pd.getAttributes().get(AbType.Crit);
        double rate = this.getChanceforInt(gd.getLevel()) *0.01d;
        get *= rate;
        get = pd.getGemAttributes().get(AbType.Crit) - get;
        pd.getGemAttributes().put(AbType.Crit, get);
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
        Utils.replaceLore(is, "%data%", this.getChanceforInt(gd) + "");
        Utils.replaceLore(is, "%Level%", gd.getDisplay());
        Lores.addLore(is, this.Code + gd.getInt());
        return is;
    }

}
