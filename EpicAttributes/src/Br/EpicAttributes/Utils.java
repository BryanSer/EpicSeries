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
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.HttpsURLConnection;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

/**
 *
 * @author Administrator
 */
public class Utils {

    public static Random Random = new Random();

    public static double getHealth(int point) {
        return 20d + (point * 2d);
    }

    public static String getDecimalString(double d) {
        return "" + ((float) d);
    }

    public static PlayerData getOfflineData(OfflinePlayer p) {
        PlayerData pd = PlayerData.LoadConfig_of(p);
        return pd;
    }

    public static void Absorb(Player p, int level, int second) {
        p.removePotionEffect(PotionEffectType.ABSORPTION);
        p.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, second * 20, level));
    }

    public static boolean RandomforBoolean(int i) {
        if (i >= 100) {
            return true;
        }
        int result = Random.nextInt(100) + 1;
        return i >= result;
    }

    public static boolean RandomforBoolean(double i) {
        if (i >= 1d) {
            return true;
        }
        int result = Random.nextInt(1000) + 1;
        int e = (int) (i * 1000d);
        return e >= result;
    }
}
