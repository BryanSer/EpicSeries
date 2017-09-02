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
public class DamageGem extends Gem {
    private String Codes = "§r§D§r§a§r§m§r§a§r§g§r§e§r§G§r§e§r§m§";

    private int getDamagerDisplay(Level l) {
        switch (l) {
            case One:
                return 100;
            case Two:
                return 200;
            case Three:
                return 400;
            case Four:
                return 800;
            case Five:
                return 1600;
        }
        return 0;
    }

    public DamageGem() {
        super.MaxLevel = Level.Five;
        super.Name = "DamageGem";
        super.Priority = 7;
        ItemStack is = new ItemStack(Material.DIAMOND);
        is.setAmount(1);
        ItemMeta im = is.getItemMeta();
        im.setDisplayName("§b伤害之石-%Level%" + Utils.GemCode);
        is.setItemMeta(im);
        Lores.Lore(is, new String[]{
            "&6&l伤害之石-%Level%",
            "&a&l安装后将增加%data%的攻击属性"
        });
        super.Display = is;
    }

    @Override
    public void onWear(Player p, GemData gd) {
        Br.EpicAttributes.Datas.PlayerData pd = Br.EpicAttributes.Data.PlayerDatas.get(p.getName());
        int get = pd.getGemAttributes().get(AbType.Atk);
        get += this.getDamagerDisplay(gd.getLevel());
        pd.getGemAttributes().put(AbType.Atk, get);
    }

    @Override
    public void onPutout(Player p, GemData gd) {
        Br.EpicAttributes.Datas.PlayerData pd = Br.EpicAttributes.Data.PlayerDatas.get(p.getName());
        int get = pd.getGemAttributes().get(AbType.Atk);
        get -= this.getDamagerDisplay(gd.getLevel());
        pd.getGemAttributes().put(AbType.Atk, get);
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
        Utils.replaceLore(is, "%data%", this.getDamagerDisplay(gd) + "");
        Utils.replaceLore(is, "%Level%", gd.getDisplay());
        Lores.addLore(is, this.Codes + gd.getInt());
        return is;
    }

}
