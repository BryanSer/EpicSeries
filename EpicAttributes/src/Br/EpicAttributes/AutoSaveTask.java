/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Br.EpicAttributes;

import Br.EpicAttributes.Datas.PlayerData;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.HttpsURLConnection;
import org.bukkit.event.HandlerList;
import org.bukkit.scheduler.BukkitRunnable;

/**
 *
 * @author Bryan_lzh
 */
public class AutoSaveTask extends BukkitRunnable {

    int unconnect = 0;

    @Override
    public void run() {
        for (Map.Entry<String, PlayerData> E : Data.PlayerDatas.entrySet()) {
            E.getValue().save();
        }
    }

}
