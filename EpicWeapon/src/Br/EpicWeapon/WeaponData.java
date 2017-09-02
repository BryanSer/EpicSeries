/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Br.EpicWeapon;

import Br.EpicAttributes.Datas.AbType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import org.apache.commons.codec.binary.Base64;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import think.rpgitems.api.RPGItems;
import think.rpgitems.item.ItemManager;

/**
 *
 *
 * @author Bryan_lzh
 */
public class WeaponData {

    private ItemStack is;
    public Map<AbType, Integer> WeaponAttributes;
    private String Permission = null;

    public boolean isRpgitem() {
        return ItemManager.toRPGItem(is) != null;
    }

    public WeaponData(ItemStack is, Map<AbType, Integer> m) {
        this.is = is;
        this.WeaponAttributes = m;
    }

    public String getPermission() {
        return Permission;
    }

    public void setPermission(String s) {
        this.Permission = s;
    }

    public WeaponData(ItemStack is) {
        this.is = is;
        this.WeaponAttributes = AbType.getDefaultMap();
    }

    public Map<AbType, Integer> getWeaponAttributes() {
        return this.WeaponAttributes;
    }

    public ItemStack getItemStack() {
        return this.is;
    }

    public ItemStack toItemStack() {
        String code = Utils.toCode(this.WeaponAttributes, this.Permission);
        if (!is.hasItemMeta() || !is.getItemMeta().hasLore()) {
            ItemMeta im = is.getItemMeta();
            List<String> lore = new ArrayList<>();
            lore.add(code);
            if (Utils.Display) {
                for (AbType a : AbType.values()) {
                    int i = this.WeaponAttributes.get(a);
                    if (i == 0) {
                        continue;
                    }
                    String result = "";
                    for (char c : a.toString().toCharArray()) {
                        result += "§" + c;
                    }
                    lore.add(result + "§b" + a.getDisplay() + ": +" + i);
                }
            }
            im.setLore(lore);
            is.setItemMeta(im);
            return is;
        }
        ItemMeta im = is.getItemMeta();
        List<String> lore = im.getLore();
        String st = lore.get(0);
        st = st.replaceAll("§", "");
        if (!Base64.isBase64(st)) {
            lore.add(0, code);
        } else {
            lore.set(0, code);
        }
        if (Utils.Display) {
            int o = 0;
            List<AbType> abl = new ArrayList<>(Arrays.asList(AbType.values()));
            boolean SpWrited = false;
            for (String s : lore) {
                for (AbType a : AbType.values()) {
                    int i = this.WeaponAttributes.get(a);
                    if (i == 0) {
                        continue;
                    }
                    String result = "";
                    for (char c : a.toString().toCharArray()) {
                        result += "§" + c;
                    }
                    result += "§b" + a.getDisplay() + ": +";
                    if (s.contains(result)) {
                        lore.set(o, result + i);
                        abl.remove(a);
                    }
                }
                if (s.contains("§b特殊: ") && this.Permission != null) {
                    lore.set(o, "§b特殊: " + this.Permission);
                    SpWrited = true;
                }
                o++;
            }
            for (AbType a : abl) {
                int i = this.WeaponAttributes.get(a);
                if (i == 0) {
                    continue;
                }
                String result = "";
                for (char c : a.toString().toCharArray()) {
                    result += "§" + c;
                }
                lore.add(result + "§b" + a.getDisplay() + ": +" + i);
            }
            if (!SpWrited) {
                if (this.Permission != null) {
                    lore.add("§b特殊: " + this.Permission);
                }
            }
        }
        im.setLore(lore);
        is.setItemMeta(im);
        return is;
    }
}
