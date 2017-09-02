/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Br.EpicSet;

import Br.EpicSet.Data.Data;
import Br.EpicSet.Data.PlayerData;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 *
 * @author Bryan_lzh
 */
public class Utils {

    public static final String GemCode = "§c§r§5§r";
    public static Random Random = new Random();

    public static ItemStack replaceLore(ItemStack is, String old, String newString) {
        if (is == null) {
            throw new NullPointerException();
        }
        ItemMeta im = is.getItemMeta();
        List<String> r = new ArrayList<>();
        for (String s : im.getLore()) {
            r.add(s.replaceAll(old, newString));
        }
        im.setLore(r);
        is.setItemMeta(im);
        return is;
    }

    public static boolean RandomforBoolean(int i) {
        if (i >= 100) {
            return true;
        }
        int result = Random.nextInt(100) + 1;
        return i >= result;
    }

    public static boolean RandomforBoolean(double i) {
        if (i >= 100d) {
            return true;
        }
        int result = Random.nextInt(100) + 1;
        int e = (int) (i * 100d);
       // Bukkit.broadcastMessage("result:" + result + " e:" + e);
        return e >= result;
    }

    public static PlayerData getPlayerData(Player p) {
        if (Data.PlayerDatas.containsKey(p.getName())) {
            return Data.PlayerDatas.get(p.getName());
        } else {
            return null;
        }
    }

    public static PlayerData getPlayerData(String p) {
        if (Data.PlayerDatas.containsKey(p)) {
            return Data.PlayerDatas.get(p);
        } else {
            return null;
        }
    }
}
