package com.samoatesgames.exchangemarket.callbacks.listings;

import com.samoatesgames.samoatesplugincore.gui.GuiCallback;
import com.samoatesgames.samoatesplugincore.gui.GuiInventory;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

/**
 *
 * @author Sam
 */
public class BackToListingsCallback implements GuiCallback {

    /**
     * 
     */
    private final GuiInventory m_inventory;
    
    /**
     * 
     */
    private final Player m_player;
    
    /**
     * 
     * @param inventory 
     */
    BackToListingsCallback(GuiInventory inventory, Player player) {
        m_inventory = inventory;
        m_player = player;
    }

    /**
     * 
     * @param inventory
     * @param clickEvent 
     */
    public void onClick(GuiInventory inventory, InventoryClickEvent clickEvent) {
        inventory.close(m_player, true);
        m_inventory.open(m_player);
    }
    
}
