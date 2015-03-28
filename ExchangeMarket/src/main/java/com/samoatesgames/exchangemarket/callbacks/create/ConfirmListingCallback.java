package com.samoatesgames.exchangemarket.callbacks.create;

import com.samoatesgames.exchangemarket.ExchangeMarket;
import com.samoatesgames.exchangemarket.data.ItemListing;
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
public class ConfirmListingCallback implements GuiCallback {

    final PluginCommandManager m_manager;
    final Player m_player;
    final ItemStack m_item;
    final float m_price;
    final int m_amount;
    
    /**
     * 
     * @param manager
     * @param player
     * @param item
     * @param price
     * @param amount 
     */
    public ConfirmListingCallback(PluginCommandManager manager, Player player, ItemStack item, float price, int amount) {
        m_manager = manager;
        m_player = player;
        m_item = item;
        m_price = price;
        m_amount = amount;
    }

    /**
     * 
     * @param inventory
     * @param clickEvent 
     */
    public void onClick(GuiInventory inventory, InventoryClickEvent clickEvent) {        
        
        ExchangeMarket plugin = (ExchangeMarket)m_manager.getPlugin();   
        
        inventory.close(m_player);
        m_player.closeInventory();
                
        if (m_amount == m_item.getAmount()) {
            m_player.setItemInHand(null);
        }
        else {
            ItemStack item = m_item.clone();
            item.setAmount(item.getAmount() - m_amount);
            m_player.setItemInHand(item);
            m_item.setAmount(m_amount);
        }
        
        createListing();
                
        m_manager.sendMessage(m_player, "Created " + plugin.formatMaterial(m_item) + " listing.");
        
    }

    /**
     * Create a listing for the data supplied
     */
    private void createListing() {
        
        final ExchangeMarket plugin = (ExchangeMarket)m_manager.getPlugin();
        ItemListing newListing = new ItemListing(m_player.getUniqueId(), m_item, m_amount, m_price);
        plugin.addListing(newListing);
        
    }
    
}
