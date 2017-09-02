/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Br.EpicAttributes.Skills;

import Br.EpicAttributes.Datas.PlayerData;

/**
 *
 * @author Administrator
 */
public abstract class GuySkill extends Skill {

    @Override
    public final boolean CouldLearn(PlayerData pd) {
        return false;
    }

    public abstract boolean CouldLearn(PlayerData pd, String guy);

}
