/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Br.EpicWeapon.RPGItems;

import Br.EpicAttributes.Datas.AbType;
import Br.EpicWeapon.Utils;
import Br.EpicWeapon.WeaponData;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import think.rpgitems.api.RPGItems;
import think.rpgitems.item.ItemManager;
import think.rpgitems.item.RPGItem;

/**
 *
 * @author Bryan_lzh
 */
public class RPGData {

    public static boolean EnableRPGItem = false;
    public static FileConfiguration Data;
    public static Map<String, Map<AbType, Integer>> Datas = new HashMap<>();

    public static void saveData() throws IOException {
        for (Entry<String, Map<AbType, Integer>> E : Datas.entrySet()) {
            String path = "Data." + E.getKey() + ".";
            for (Entry<AbType, Integer> e : E.getValue().entrySet()) {
                Data.set(path + e.getKey().getPath(), e.getValue());
            }
        }
        Data.save(new File(Utils.Main.getDataFolder(), "RPGItems.yml"));
    }

    public static WeaponData toWeaponData(ItemStack is) {
        if (!EnableRPGItem) {
            WeaponData m = Utils.unCode(is);
            return m;
        }
        RPGItem rpg = ItemManager.toRPGItem(is);
        if (rpg == null) {
            WeaponData m = Utils.unCode(is);
            return m;
        }
        Map<AbType, Integer> m = Datas.get(rpg.getName());
        if (m == null) {
            return null;
        }
        return new WeaponData(is, m);
    }

    public static void loadData() throws IOException {
        File DataFolde = Utils.Main.getDataFolder();
        if (!DataFolde.exists()) {
            DataFolde.mkdirs();
        }
        File DataFile = new File(DataFolde, "RPGItems.yml");
        if (!DataFile.exists()) {
            DataFile.createNewFile();
        }
        Data = YamlConfiguration.loadConfiguration(DataFile);
        if (Data.contains("Data")) {
            ConfigurationSection CS = Data.getConfigurationSection("Data");
            for (String s : CS.getKeys(false)) {
                Map<AbType, Integer> data = new HashMap<>();
                for (AbType a : AbType.values()) {
                    data.put(a, Data.getInt("Data." + s + "." + a.getPath()));
                }
                Datas.put(s, data);
            }
        }
    }
}
