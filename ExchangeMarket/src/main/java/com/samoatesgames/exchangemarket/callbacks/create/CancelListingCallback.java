/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.samoatesgames.exchangemarket.callbacks.create;

import com.samoatesgames.exchangemarket.ExchangeMarket;
import com.samoatesgames.samoatesplugincore.commands.PluginCommandManager;
import com.samoatesgames.samoatesplugincore.gui.GuiCallback;
import com.samoatesgames.samoatesplugincore.gui.GuiInventory;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author Sam
 */
public class CancelListingCallback implements GuiCallback {

    final PluginCommandManager m_manager;
    final Player m_player;
    final ItemStack m_item;
    
    /**
     * 
     * @param manager
     * @param player
     * @param item 
     */
    public CancelListingCallback(PluginCommandManager manager, Player player, ItemStack item) {
        m_manager = manager;
        m_player = player;
        m_item = item;
    }

    /**
     * 
     * @param inventory
     * @param clickEvent 
     */
    public void onClick(GuiInventory inventory, InventoryClickEvent clickEvent) {
        
        ExchangeMarket plugin = (ExchangeMarket)m_manager.getPlugin();        
        m_manager.sendMessage(m_player, "Canceled the creation of the " + plugin.formatMaterial(m_item) + " listing.");
        inventory.close(m_player);
        m_player.closeInventory();
    }
    
}
