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
 * @author Administrator
 */
public class CritDamGem extends Gem {

    String Code = "§r§C§r§r§r§i§r§t§r§D§r§a§r§m§r§G§r§e§r§m§";

    private int getChanceforInt(Level l) {
        switch (l) {
            case One:
                return 6;
            case Two:
                return 12;
            case Three:
                return 18;
            case Four:
                return 24;
            case Five:
                return 30;
        }
        return 0;
    }

    public CritDamGem() {
        super.MaxLevel = Level.Five;
        super.Name = "CritDamGem";
        super.Priority = 9;
        ItemStack is = new ItemStack(Material.REDSTONE);
        is.setAmount(1);
        ItemMeta im = is.getItemMeta();
        im.setDisplayName("§b暴伤之石-%Level%" + Utils.GemCode);
        is.setItemMeta(im);
        Lores.Lore(is, new String[]{
            "&6&l暴伤宝石%Level%",
            "&a&l安装后将增加%data%%的原始暴击伤害属性",
            "&c&l额外属性将在每次重新登入服务器时刷新"
        });
        super.Display = is;
    }

    @Override
    public void onWear(Player p, GemData gd) {
        Br.EpicAttributes.Datas.PlayerData pd = Br.EpicAttributes.Data.PlayerDatas.get(p.getName());
        int get = pd.getAttributes().get(AbType.CritDam);
        double rate = this.getChanceforInt(gd.getLevel()) *0.01d;
        get *= rate;
        get += pd.getGemAttributes().get(AbType.CritDam);
        pd.getGemAttributes().put(AbType.CritDam, get);
    }

    @Override
    public void onPutout(Player p, GemData gd) {
        Br.EpicAttributes.Datas.PlayerData pd = Br.EpicAttributes.Data.PlayerDatas.get(p.getName());
        int get = pd.getAttributes().get(AbType.CritDam);
        double rate = this.getChanceforInt(gd.getLevel()) *0.01d;
        get *= rate;
        get = pd.getGemAttributes().get(AbType.CritDam) - get;
        pd.getGemAttributes().put(AbType.CritDam, get);
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
