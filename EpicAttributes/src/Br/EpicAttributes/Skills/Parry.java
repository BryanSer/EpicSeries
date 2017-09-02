/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Br.EpicAttributes.Skills;

import Br.EpicAttributes.Datas.AbType;
import Br.EpicAttributes.Datas.PlayerData;

public class Parry extends Skill {

    public Parry() {
        super.DisplayName = "§6招架";
    }

    @Override
    public String[] getDescription() {
        return new String[]{
            "§a[" + this.DisplayName + "§a]",
            "§b技能星级: §d★",
            "§b技能效果: §a有§b20%的几率§a减少受到的§b45%§a的伤害",
            "§a未装备剑时不生效"
        };
    }

    @Override
    public boolean CouldLearn(PlayerData pd) {
        return pd.getAttribute(AbType.Avoid) + pd.getAttribute(AbType.Def) >= 45;
    }

}
