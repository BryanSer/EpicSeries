/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Br.EpicSet.Enums;

/**
 *
 * @author Administrator
 */
public enum Level {
        One, Two, Three, Four, Five;
        public static Level getLevel(int i) {
            switch (i) {
                case 1:
                    return Level.One;
                case 2:
                    return Level.Two;
                case 3:
                    return Level.Three;
                case 4:
                    return Level.Four;
                case 5:
                    return Level.Five;
            }
            return null;
        }
        
        public int getInt(){
            switch(this){
                case One:
                    return 1;
                case Two:
                    return 2;
                case Three:
                    return 3;
                case Four:
                    return 4;
                case Five:
                    return 5;
            }
            return 0;
        }
        
        public String getDisplay(){
            switch(this){
                case One:
                    return "I";
                case Two:
                    return "II";
                case Three:
                    return "III";
                case Four:
                    return "IV";
                case Five:
                    return "V";
            }
            return "ERROR";
        }
    }
