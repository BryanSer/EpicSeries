/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Br.EpicGuys.Buff;

import Br.EpicGuys.Guy.Guy;
import java.util.Collection;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author Administrator
 */
public interface Buff extends Listener{

    public boolean isEnable(Guy g);

    public void addGuy(Guy g);

    public void removeGuy(Guy g);

    public Collection<String> getEnable();
    
    public ItemStack getDisplay();
    
    public int NeedLevel();
    
    public String getName();
    
    public String getDisplayName();
}
