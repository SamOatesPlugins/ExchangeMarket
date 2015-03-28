package com.samoatesgames.exchangemarket.commands;

import com.samoatesgames.exchangemarket.ExchangeMarket;
import com.samoatesgames.exchangemarket.callbacks.listings.ShowItemListingsCallback;
import com.samoatesgames.exchangemarket.data.ItemListing;
import com.samoatesgames.samoatesplugincore.commands.BasicCommandHandler;
import com.samoatesgames.samoatesplugincore.commands.PluginCommandManager;
import com.samoatesgames.samoatesplugincore.gui.GuiInventory;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author Sam
 */
public class MarketListingsCommand extends BasicCommandHandler {

    /**
     * 
     */
    public MarketListingsCommand() {
        super("exchangemarket.command.market.listings");
    }

    /**
     * Handle 
     * @param manager
     * @param sender
     * @param arguments
     * @return 
     */
    @Override
    public boolean execute(PluginCommandManager manager, CommandSender sender, String[] arguments) {
        
        if (arguments.length != 0) {
            showHelp(manager, sender);
            return true;
        }
        
        if (!(sender instanceof Player)) {
            // Can't create if not a player
            manager.sendMessage(sender, "Only in game players can use this command.");
            return true;
        }
        
        final Player player = (Player)sender;
        showListingsGui(manager, player);
        
        return true;
    }

    /**
     * 
     * @param player 
     */
    private void showListingsGui(PluginCommandManager manager, Player player) {
        
        ExchangeMarket plugin = (ExchangeMarket)manager.getPlugin();
        
        GuiInventory inventory = new GuiInventory(plugin);
        inventory.createInventory("Market Listing", 6);
        
        Map<Material, List<ItemListing>> perMaterialListings = new EnumMap<Material, List<ItemListing>>(Material.class);        
        List<ItemListing> listings = plugin.getListings();
        for (ItemListing listing : listings)
        {
            final ItemStack item = listing.getItem();
            final Material type = item.getType();
            
            List<ItemListing> items;
            if (!perMaterialListings.containsKey(type)) {
                items = new ArrayList<ItemListing>();
            } else {
                items = perMaterialListings.get(type);
            }
            
            items.add(listing);
            perMaterialListings.put(type, items);
        }
        
        for (Entry<Material, List<ItemListing>> listingCollection : perMaterialListings.entrySet())
        {
            ItemStack icon = new ItemStack(listingCollection.getKey());
            List<ItemListing> itemListings = listingCollection.getValue();
            
            float lowestPrice = Float.MAX_VALUE;
            for (ItemListing listing : itemListings) {
                if (listing.getPrice() < lowestPrice) {
                    lowestPrice = listing.getPrice();
                }
            }
            
            String[] details = {
                "Lowest Price: " + plugin.formatPrice(lowestPrice),
                itemListings.size() + (itemListings.size() == 1 ?  " Seller" : " Sellers")
            };            
            inventory.addMenuItem(plugin.formatMaterial(icon), icon, details, new ShowItemListingsCallback(manager, player, icon.getType(), itemListings));
        }
        
        inventory.open(player);
        
    }
    
    /**
     * Show the command help
     * @param manager
     * @param sender 
     */
    private void showHelp(PluginCommandManager manager, CommandSender sender) {
        
        manager.sendMessage(sender, ChatColor.UNDERLINE + "-- Market Create Command Help --");
        
        manager.sendTooltipMessage(sender, 
                "/exchange create <price> [amount]", 
                new String[] { 
                    "Use this command to create a new market listing.",
                    "price: The amount you want to sell the item for.", 
                    "amount: (optional) The number of items to list." 
                }
        );
        
    }
    
}
