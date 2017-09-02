/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Br.EpicWeapon;

import Br.EpicAttributes.Datas.AbType;
import Br.EpicWeapon.RPGItems.RPGData;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.codec.binary.Base64;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author Administrator
 */
public class Utils {

    public static EpicWeapon Main;
    public static boolean Display = true;

    public static String toCode(Map<AbType, Integer> map, String p) {
        String s = "";
        for (AbType a : AbType.values()) {
            s += map.get(a) + "|";
        }
        if (p != null) {
            s += p;
        } else {
            s += "null";
        }
        s = encodeBase64(s);
        String result = "";
        for (char c : s.toCharArray()) {
            result += "§" + c;
        }
        return result;
    }

    public static WeaponData getWeaponData(ItemStack is) {
        return RPGData.toWeaponData(is);
    }

    public static WeaponData unCode(ItemStack is) {
        if (!is.hasItemMeta() || !is.getItemMeta().hasLore()) {
            return null;
        }
        Map<AbType, Integer> map = new EnumMap<>(AbType.class);
        List<String> lore = is.getItemMeta().getLore();
        String st = lore.get(0);
        st = st.replaceAll("§", "");
        if (!Base64.isBase64(st)) {
            return null;
        }
        st = decodeBase64(st);
        String s[] = st.split("\\|");
        int i = 0;
        for (AbType a : AbType.values()) {
            map.put(a, Integer.parseInt(s[i]));
            i++;
        }
        WeaponData wd = new WeaponData(is, map);
        wd.setPermission(s[i]);
        return wd;
    }

    /**
     * base64加密
     *
     * @param s 要加密的字符串
     * @return 加密后的字符串
     */
    public static String encodeBase64(String s) {
        return new String(Base64.encodeBase64(s.getBytes()));
    }

    /**
     * base64解密
     *
     * @param s 要解密的字符串
     * @return 解密后的字符串
     */
    public static String decodeBase64(String s) {
        return new String(Base64.decodeBase64(s));
    }
}
