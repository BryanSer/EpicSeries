/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Br.EpicSet.Data;

import Br.EpicSet.Enums.Level;
import Br.EpicSet.Gem;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

/**
 *
 * @author Bryan_lzh
 */
public class GemData {
    
    public GemData(Player p,Level l,Gem g){
        this.Owner = p.getName();
        this.Levels = l;
        this.GemName = g.getName();
    }

    private Level Levels;
    private String Owner;
    private String GemName;

    public GemData(FileConfiguration config, String PlayerName, String GemName) {
        String path = PlayerName + ".Gems.GemDatas." + GemName + ".";
        this.GemName = config.getString(path + "Type");
        this.Levels = Level.getLevel(config.getInt(path+"Level"));
        this.Owner = PlayerName;
    }

    public Level getLevel() {
        return this.Levels;
    }

    public String getPlayer() {
        return this.Owner;
    }

    public Gem getGem() {
        return Gem.getGem(GemName);
    }
}
