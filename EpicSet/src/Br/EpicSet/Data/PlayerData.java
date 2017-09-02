/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Br.EpicSet.Data;

import Br.EpicSet.Enums.InstallResult;
import Br.EpicSet.Enums.InvMode;
import Br.EpicSet.Enums.Solt;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 *
 * @author Bryan_lzh
 */
public class PlayerData {
    
    public static PlayerData loadConfig_of(String PlayerName, FileConfiguration config)throws NumberFormatException{
        PlayerData pd = new PlayerData();
        pd.PlayerName = PlayerName;
        List<Solt> Unlock = new ArrayList<>();
        for (String s : config.getStringList(PlayerName + ".Unlocked")) {
            Unlock.add(Solt.getSolt(s));
        }
        pd.Unlocked = Unlock;

        Map<Solt, GemData> Gems = new HashMap<>();
        for (String s : config.getStringList(PlayerName + ".Gems.Solts")) {
            String ss[] = s.split("\\|");
            GemData gd = new GemData(config, PlayerName, ss[1]);
            Gems.put(Solt.getSolt(Integer.parseInt(ss[0])), gd);
        }
        pd.Gems = Gems;
        pd.Compared();
        return pd;
    }

    public static PlayerData loadConfig(String PlayerName, FileConfiguration config) throws NumberFormatException {
        PlayerData pd = new PlayerData();
        pd.PlayerName = PlayerName;
        List<Solt> Unlock = new ArrayList<>();
        for (String s : config.getStringList(PlayerName + ".Unlocked")) {
            Unlock.add(Solt.getSolt(s));
        }
        pd.Unlocked = Unlock;

        Map<Solt, GemData> Gems = new HashMap<>();
        for (String s : config.getStringList(PlayerName + ".Gems.Solts")) {
            String ss[] = s.split("\\|");
            GemData gd = new GemData(config, PlayerName, ss[1]);
            gd.getGem().onWear(Bukkit.getPlayer(PlayerName), gd);
            if (gd.getGem().isCellOnJoinAndQuit()) {
                gd.getGem().OnPlayerJoin(Bukkit.getPlayer(PlayerName));
            }
            Gems.put(Solt.getSolt(Integer.parseInt(ss[0])), gd);
        }
        pd.Gems = Gems;
        pd.Compared();
        return pd;
    }

    public static PlayerData getNewPlayerData(Player p) {
        PlayerData pd = new PlayerData();
        pd.PlayerName = p.getName();
        pd.Gems = Solt.getEmptyMap();
        pd.Unlocked = Solt.getDefaultUnlocked();
        return pd;
    }

    private String PlayerName;
    private Map<Solt, GemData> Gems;
    private List<Solt> Unlocked;
    private List<Solt> Compared = new ArrayList<>();//返回排序

    public void Unlock(Solt s) {
        this.Unlocked.add(s);
    }

    public void Reset(Player p) {
        for (GemData gd : this.Gems.values()) {
            gd.getGem().onPutout(p, gd);
        }
        this.Gems.clear();
        this.Unlocked = Solt.getDefaultUnlocked();
    }
    
    public void Reset(){
        this.Gems.clear();
        this.Unlocked = Solt.getDefaultUnlocked();
    }
    

    /**
     * 设置宝石 将调用Gem安装方法，如果原来有宝石将调用Gem卸除方法
     *
     * @param p
     * @param gd
     * @param s
     * @return
     */
    public InstallResult setGems(Player p, GemData gd, Solt s) {
        if (!this.Unlocked.contains(s)) {
            return InstallResult.Locked;
        }
        if (this.Gems.get(s) != null) {
            GemData old = this.Gems.get(s);
            old.getGem().onPutout(p, old);
            this.Gems.remove(s);
        }
        gd.getGem().onWear(p, gd);
        this.Gems.put(s, gd);
        this.Compared();
        p.sendMessage("§b宝石已安装");
        return InstallResult.Succeed;
    }

    /**
     * 移除宝石 将调用Gem卸除方法
     *
     * @param p
     * @param s
     */
    public void removeGems(Player p, Solt s) {
        if (this.Gems.containsKey(s)) {
            GemData old = this.Gems.get(s);
            old.getGem().onPutout(p, old);
            ItemStack gem = old.getGem().DrawDisplay(old);
            this.Gems.remove(s);
            Br.API.Utils.safeGiveItem(p, gem);
        }
    }

    /**
     * 返回这个玩家的宝石镶嵌
     *
     * @param p
     * @return
     */
    public Inventory getInv(Player p) {
        Inventory inv = Bukkit.createInventory(p, 9, "§d[EpicSet]" + InvMode.InvCode);
        ItemStack Locked = new ItemStack(Material.BARRIER);
        ItemMeta im = Locked.getItemMeta();
        im.setDisplayName("§c未解锁此槽");
        Locked.setItemMeta(im);
        inv.setItem(0, Locked);
        inv.setItem(1, Locked);
        inv.setItem(2, Locked);
        inv.setItem(3, Locked);
        inv.setItem(4, Locked);
        inv.setItem(5, Locked);
        inv.setItem(6, Locked);
        inv.setItem(7, Locked);
        inv.setItem(8, Locked);
        for (Solt s : this.Compared) {
            if (this.Gems.get(s) == null) {
                continue;
            }
            inv.setItem(s.getInt() - 1, this.Gems.get(s).getGem().DrawDisplay(this.Gems.get(s)));
        }
        ItemStack ni = new ItemStack(Material.GLASS);
        ItemMeta niim = Locked.getItemMeta();
        niim.setDisplayName("§a未安装宝石");
        ni.setItemMeta(niim);
        for (Solt s : this.Unlocked) {
            if (this.Gems.get(s) == null) {
                inv.setItem(s.getInt() - 1, ni);
            }
        }
        return inv;
    }

    /**
     * 返回已解锁的列表
     *
     * @return
     */
    public List<Solt> getUnlocked() {
        return this.Unlocked;
    }

    /**
     * 返回Gem Map
     *
     * @return
     */
    public Map<Solt, GemData> getGems() {
        return this.Gems;
    }

    /**
     * 返回玩家
     *
     * @return
     */
    public OfflinePlayer getPlayer() {
        return Bukkit.getOfflinePlayer(this.PlayerName);
    }

    /**
     * 传入的Solt是否解锁
     *
     * @param s
     * @return
     */
    public boolean isUnlock(Solt s) {
        return this.Unlocked.contains(s);
    }

    /**
     * 排序方法 请在移除以及设置后调用
     */
    public void Compared() {
        this.Compared = sortMapByValue(this.Gems);
    }

    /**
     * 返回排序后的结果
     *
     * @return
     */
    public List<Solt> getCompared() {
        return this.Compared;
    }

    /**
     * 使用 Map按value进行排序
     *
     * @param oriMap
     * @return
     */
    public static List<Solt> sortMapByValue(Map<Solt, GemData> oriMap) {
        if (oriMap == null || oriMap.isEmpty()) {
            return new ArrayList<>();
        }
        List<Map.Entry<Solt, Integer>> entryList = new ArrayList<>();
        for (final Map.Entry<Solt, GemData> E : oriMap.entrySet()) {
            if (E.getValue() == null) {
                continue;
            }
            entryList.add(new Map.Entry<Solt, Integer>() {
                Solt name = E.getKey();
                Integer i = E.getValue().getGem().getPriority();

                @Override
                public Solt getKey() {
                    return this.name;
                }

                @Override
                public Integer getValue() {
                    return this.i;
                }

                @Override
                public Integer setValue(Integer value) {
                    this.i = value;
                    return i;
                }
            });
        }
        Collections.sort(entryList, new MapValueComparator());

        Iterator<Map.Entry<Solt, Integer>> iter = entryList.iterator();
        Map.Entry<Solt, Integer> tmpEntry = null;
        List<Solt> result = new ArrayList<>();
        while (iter.hasNext()) {
            tmpEntry = iter.next();
            result.add(tmpEntry.getKey());
        }

        return result;
    }
}

class MapValueComparator implements Comparator<Map.Entry<Solt, Integer>> {

    @Override
    public int compare(Map.Entry<Solt, Integer> me1, Map.Entry<Solt, Integer> me2) {
        return me2.getValue().compareTo(me1.getValue());
    }
}
