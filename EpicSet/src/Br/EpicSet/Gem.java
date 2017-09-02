/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Br.EpicSet;

import Br.EpicSet.Data.Data;
import Br.EpicSet.Data.GemData;
import Br.EpicSet.Enums.Level;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 *
 * @author Bryan_lzh
 */
public abstract class Gem {

    public static Gem getGem(String s) {
        if (Data.GemDatas.containsKey(s)) {
            return Data.GemDatas.get(s);
        }
        return null;
    }

    protected String Name;
    protected ItemStack Display;
    protected Level MaxLevel;
    protected int Priority = 5;

    /**
     * 若为true将在玩家加入和离开的时候调用方法 OnPlayerJoin()OnPlayerQuit() 请注意覆盖原方法
     */
    protected boolean CellOnJoinAndQuit = false;

    public int getPriority() {
        return this.Priority;
    }

    public Level getMaxLevel() {
        return this.MaxLevel;
    }

    public void OnPlayerJoin(Player p) {
    }

    public void OnPlayerQuit(Player p) {
    }

    public boolean isCellOnJoinAndQuit() {
        return CellOnJoinAndQuit;
    }

    public String getName() {
        return this.Name;
    }

    public ItemStack getDisplay() {
        return this.Display;
    }

    /**
     * 在安装该宝石的时候执行
     */
    public abstract void onWear(Player p, GemData gd);

    /**
     * 在卸除该宝石的时候执行
     */
    public abstract void onPutout(Player p, GemData gd);

    /**
     * %Onwer% = Player.<p>
     * %Level% = Level.
     * <p>
     * %Effect% = this.Data.
     *
     * @param gd
     * @return
     */
    public ItemStack DrawDisplay(GemData gd) {
        ItemStack is = this.Display.clone();
        ItemMeta im = is.getItemMeta();
        im.setDisplayName(im.getDisplayName().replaceAll("%Level%", gd.getLevel().getDisplay()));

        return is;
    }

    public ItemStack DrawDisplay(Level gd) {
        ItemStack is = this.Display.clone();
        ItemMeta im = is.getItemMeta();
        im.setDisplayName(im.getDisplayName().replaceAll("%Level%", gd.getDisplay()));
        is.setItemMeta(im);
        return is;
    }

}
