package com.samoatesgames.exchangemarket.data;

import java.util.Comparator;
import java.util.UUID;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author Sam
 */
public class ItemListing {
    
    private final UUID m_uuid;
    private final ItemStack m_item;
    private final int m_amount;
    private final float m_price;
    
    /**
     * 
     * @param uuid
     * @param item
     * @param amount
     * @param price 
     */
    public ItemListing(UUID uuid, ItemStack item, int amount, float price) {
        m_uuid = uuid;
        m_item = item;
        m_amount = amount;
        m_price = price;
    }
    
    /**
     * 
     * @return 
     */
    public ItemStack getItem() {
        return m_item;
    }
    
    /**
     * 
     * @return 
     */
    public float getPrice() {
        return m_price;
    }
    
    /**
     * 
     * @return 
     */
    public UUID getSeller() {
        return m_uuid;
    }
}
