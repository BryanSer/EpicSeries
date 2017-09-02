/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Br.EpicSet.Enums;

import Br.EpicSet.Data.GemData;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Bryan_lzh
 */
public enum Solt {
    Solt1, Solt2, Solt3, Solt4, Solt5, Solt6, Solt7, Solt8, Solt9;

    public static List<Solt> getDefaultUnlocked() {
        List<Solt> l = new ArrayList<>();
        l.add(Solt1);
        l.add(Solt2);
        l.add(Solt3);
        return l;
    }

    public static Map<Solt, GemData> getEmptyMap() {
        Map<Solt, GemData> m = new HashMap<>();
        m.put(Solt1, null);
        m.put(Solt2, null);
        m.put(Solt3, null);
        m.put(Solt4, null);
        m.put(Solt5, null);
        m.put(Solt6, null);
        m.put(Solt7, null);
        m.put(Solt8, null);
        m.put(Solt9, null);
        return m;
    }

    public int getInt() {
        return Solt.getInt(this);
    }

    public static Solt getSolt(String s) {
        switch (s) {
            case "Solt1":
                return Solt1;
            case "Solt2":
                return Solt2;
            case "Solt3":
                return Solt3;
            case "Solt4":
                return Solt4;
            case "Solt5":
                return Solt5;
            case "Solt6":
                return Solt6;
            case "Solt7":
                return Solt7;
            case "Solt8":
                return Solt8;
            case "Solt9":
                return Solt9;
            default:
                return Solt1;
        }
    }

    public static int getInt(Solt s) {
        switch (s) {
            case Solt1:
                return 1;
            case Solt2:
                return 2;
            case Solt3:
                return 3;
            case Solt4:
                return 4;
            case Solt5:
                return 5;
            case Solt6:
                return 6;
            case Solt7:
                return 7;
            case Solt8:
                return 8;
            case Solt9:
                return 9;
            default:
                return 0;
        }
    }

    public static Solt getSolt(int i) {
        switch (i) {
            case 1:
                return Solt1;
            case 2:
                return Solt2;
            case 3:
                return Solt3;
            case 4:
                return Solt4;
            case 5:
                return Solt5;
            case 6:
                return Solt6;
            case 7:
                return Solt7;
            case 8:
                return Solt8;
            case 9:
                return Solt9;
            default:
                return null;
        }
    }
}
