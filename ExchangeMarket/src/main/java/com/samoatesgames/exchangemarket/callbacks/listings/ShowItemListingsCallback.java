package com.samoatesgames.exchangemarket.callbacks.listings;

import com.samoatesgames.exchangemarket.ExchangeMarket;
import com.samoatesgames.exchangemarket.data.ItemListing;
import com.samoatesgames.exchangemarket.data.ItemListingComparator;
import com.samoatesgames.samoatesplugincore.commands.PluginCommandManager;
import com.samoatesgames.samoatesplugincore.gui.GuiCallback;
import com.samoatesgames.samoatesplugincore.gui.GuiInventory;
import java.util.Collections;
import java.util.List;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author Sam
 */
public class ShowItemListingsCallback implements GuiCallback {

    /**
     * 
     */
    private final PluginCommandManager m_manager;
    
    /**
     * 
     */
    private final Player m_player;
    
    /**
     * 
     */
    private final Material m_type;
    
    /**
     * 
     */
    private final List<ItemListing> m_itemListings;
    
    /**
     * 
     * @param manager
     * @param player
     * @param type
     * @param itemListings
     */
    public ShowItemListingsCallback(PluginCommandManager manager, Player player, Material type, List<ItemListing> itemListings) {
        m_manager = manager;
        m_player = player;
        m_type = type;
        m_itemListings = itemListings;
    }
    
    /**
     * 
     * @param inventory
     * @param clickEvent 
     */
    public void onClick(GuiInventory inventory, InventoryClickEvent clickEvent) {
        
        inventory.close(m_player, true);
        
        ExchangeMarket plugin = (ExchangeMarket)inventory.getPlugin();        
        GuiInventory listingsInventory = new GuiInventory(plugin);
        listingsInventory.createInventory(plugin.formatMaterial(m_type) + " Listings", 6);
        
        Collections.sort(m_itemListings, new ItemListingComparator());
        
        int listingIndex = 0;
        for (ItemListing listing : m_itemListings) {
            
            ItemStack item = listing.getItem();
            String[] details = {
                "Price: " + plugin.formatPrice(listing.getPrice()),
                "Seller: " + plugin.playerNameFromUUID(listing.getSeller())
            };
            
            listingsInventory.addMenuItem(plugin.formatMaterial(item), item, details, null);
            
            listingIndex++;
            if (listingIndex >= 36) {
                break;
            }
        }
        
        for (int breakerIndex = 36; breakerIndex < 36 + 9; ++breakerIndex) {
            ItemStack item = new ItemStack(Material.BEDROCK);
            listingsInventory.addMenuItem("--------", item, new String[] {}, breakerIndex, null);
        }
        
        ItemStack backIcon = new ItemStack(Material.BARRIER);
        listingsInventory.addMenuItem("Return to listings...", backIcon, new String[] {}, 53, new BackToListingsCallback(inventory, m_player));
        
        listingsInventory.open(m_player);
        
    }
    
}
