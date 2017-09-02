/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Br.EpicAttributes.Skills;

import Br.EpicAttributes.Data;
import Br.EpicAttributes.Datas.PlayerData;

/**
 *
 * @author Bryan_lzh
 */
public abstract class Skill{

    protected String Name;
    protected String DisplayName;
    protected ColdDown CD = null;
    protected SkillType SkiType;
    protected String FindCode;
    
    public void setName(String name){
        this.Name = name;
    }

    public void setFindCode(String s) {
        this.FindCode = s.toUpperCase();
    }

    public String getFindCode() {
        return this.FindCode;
    }

    /**
     * 初始化冷却时间
     */
    public void init() {
        if (CD == null) {
            return;
        }
        CD.runTaskTimer(Data.Main, 0l, 1l);
    }

    public void setSkillType(SkillType st) {
        this.SkiType = st;
    }

    public SkillType getSkillType() {
        return this.SkiType;
    }

    public String getName() {
        return this.Name;
    }

    public String getDisplayName() {
        return this.DisplayName;
    }

    public abstract String[] getDescription();

    public abstract boolean CouldLearn(PlayerData pd);
}
