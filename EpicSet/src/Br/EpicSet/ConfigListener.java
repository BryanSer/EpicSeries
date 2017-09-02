/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Br.EpicSet;

import Br.EpicSet.Data.Data;
import Br.EpicSet.Data.GemData;
import Br.EpicSet.Data.PlayerData;
import Br.EpicSet.Enums.Solt;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 *
 * @author Bryan_lzh
 */
public class ConfigListener implements Listener {

    public static void Save(PlayerData pd) {
        try {
            File dataFolder = Data.Main.getDataFolder();
            if (!dataFolder.exists()) {
                dataFolder.mkdirs();
            }
            File dataFile = new File(dataFolder, pd.getPlayer().getName() + ".yml");
            if (!dataFile.exists()) {
                dataFile.createNewFile();
            }
            FileConfiguration PlayerConfig = YamlConfiguration.loadConfiguration(dataFile);
            String PlayerName = pd.getPlayer().getName();
            List<String> Unlocked = new ArrayList<>();
            for (Solt s : pd.getUnlocked()) {
                Unlocked.add(s.toString());
            }
            PlayerConfig.set(PlayerName + ".Unlocked", Unlocked);

            List<String> Solts = new ArrayList<>();
            PlayerConfig.set(PlayerName + ".Gems.GemDatas", null);
            for (Entry<Solt, GemData> E : pd.getGems().entrySet()) {
                if (E.getValue() == null) {
                    continue;
                }
                String GemDataCode = E.getValue().getGem().getName() + "%" + E.getValue().hashCode();
                Solts.add(E.getKey().getInt() + "|" + GemDataCode);
                PlayerConfig.set(PlayerName + ".Gems.GemDatas." + GemDataCode + ".Type", E.getValue().getGem().getName());
                PlayerConfig.set(PlayerName + ".Gems.GemDatas." + GemDataCode + ".Level", E.getValue().getLevel().getInt());
            }
            PlayerConfig.set(PlayerName + ".Gems.Solts", Solts);
            PlayerConfig.save(dataFile);
        } catch (IOException | NumberFormatException ex) {
            Logger.getLogger(ConfigListener.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent evt) {
        try {
            File dataFolder = Data.Main.getDataFolder();
            if (!dataFolder.exists()) {
                dataFolder.mkdirs();
            }
            File dataFile = new File(dataFolder, evt.getPlayer().getName() + ".yml");
            if (!dataFile.exists()) {
                dataFile.createNewFile();
            }
            FileConfiguration PlayerConfig = YamlConfiguration.loadConfiguration(dataFile);
            String PlayerName = evt.getPlayer().getName();
            PlayerData pd = Utils.getPlayerData(evt.getPlayer());
            if (pd == null) {
                return;
            }
            List<String> Unlocked = new ArrayList<>();
            for (Solt s : pd.getUnlocked()) {
                Unlocked.add(s.toString());
            }
            PlayerConfig.set(PlayerName + ".Unlocked", Unlocked);

            List<String> Solts = new ArrayList<>();
            PlayerConfig.set(PlayerName + ".Gems.GemDatas", null);
            for (Entry<Solt, GemData> E : pd.getGems().entrySet()) {
                if (E.getValue() == null) {
                    continue;
                }
                E.getValue().getGem().onPutout(evt.getPlayer(), E.getValue());
                if (E.getValue().getGem().CellOnJoinAndQuit) {
                    E.getValue().getGem().OnPlayerQuit(evt.getPlayer());
                }
                String GemDataCode = E.getValue().getGem().getName() + "%" + E.getValue().hashCode();
                Solts.add(E.getKey().getInt() + "|" + GemDataCode);
                PlayerConfig.set(PlayerName + ".Gems.GemDatas." + GemDataCode + ".Type", E.getValue().getGem().getName());
                PlayerConfig.set(PlayerName + ".Gems.GemDatas." + GemDataCode + ".Level", E.getValue().getLevel().getInt());
            }
            PlayerConfig.set(PlayerName + ".Gems.Solts", Solts);
            PlayerConfig.save(dataFile);
        } catch (IOException | NumberFormatException ex) {
            Logger.getLogger(ConfigListener.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent evt) {
        try {
            File dataFolder = Data.Main.getDataFolder();
            if (!dataFolder.exists()) {
                dataFolder.mkdirs();
            }
            File dataFile = new File(dataFolder, evt.getPlayer().getName() + ".yml");
            if (!dataFile.exists()) {
                dataFile.createNewFile();
            }
            FileConfiguration PlayerConfig = YamlConfiguration.loadConfiguration(dataFile);
            String PlayerName = evt.getPlayer().getName();

            if (!PlayerConfig.contains("Version")) {
                List<String> unlock = new ArrayList<>();
                for (Solt s : Solt.getDefaultUnlocked()) {
                    unlock.add(s.toString());
                }
                PlayerConfig.set(PlayerName + ".Unlocked", unlock);
                PlayerConfig.set("Version", 1);
            }
            PlayerData pd = PlayerData.loadConfig(PlayerName, PlayerConfig);
            Data.PlayerDatas.put(PlayerName, pd);
            PlayerConfig.save(dataFile);

        } catch (IOException | NumberFormatException ex) {
            evt.getPlayer().sendMessage("§c在读取你的宝石数据时发生错误");
            Logger
                    .getLogger(ConfigListener.class
                            .getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static FileConfiguration getFileConfiguration(OfflinePlayer op) {
        try {
            File dataFolder = Data.Main.getDataFolder();
            if (!dataFolder.exists()) {
                dataFolder.mkdirs();
            }
            File dataFile = new File(dataFolder, op.getName() + ".yml");
            if (!dataFile.exists()) {
                dataFile.createNewFile();
            }
            FileConfiguration PlayerConfig = YamlConfiguration.loadConfiguration(dataFile);
            String PlayerName = op.getName();
            if (!PlayerConfig.contains("Version")) {
                List<String> unlock = new ArrayList<>();
                for (Solt s : Solt.getDefaultUnlocked()) {
                    unlock.add(s.toString());
                }
                PlayerConfig.set(PlayerName + ".Unlocked", unlock);
                PlayerConfig.set("Version", 1);
            }
            PlayerConfig.save(dataFile);
            return PlayerConfig;
        } catch (IOException ex) {
            Logger.getLogger(ConfigListener.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
}
