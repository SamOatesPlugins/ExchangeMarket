package com.samoatesgames.exchangemarket.commands;

import com.samoatesgames.exchangemarket.ExchangeMarket;
import com.samoatesgames.exchangemarket.callbacks.create.CancelListingCallback;
import com.samoatesgames.exchangemarket.callbacks.create.ConfirmListingCallback;
import com.samoatesgames.samoatesplugincore.commands.BasicCommandHandler;
import com.samoatesgames.samoatesplugincore.commands.PluginCommandManager;
import com.samoatesgames.samoatesplugincore.gui.GuiInventory;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author Sam
 */
public class MarketCreateCommand extends BasicCommandHandler {

    /**
     * Class constructor
     */
    public MarketCreateCommand() {
        super("exchangemarket.command.market.create");
    }    
    
    /**
     * /exchange create <price> [amount]
     * @param manager
     * @param sender
     * @param arguments
     * @return 
     */
    @Override
    public boolean execute(PluginCommandManager manager, CommandSender sender, String[] arguments) {
        
        if (arguments.length == 0 || arguments.length > 2) {
            showHelp(manager, sender);
            return true;
        }
        
        if (!(sender instanceof Player)) {
            // Can't create if not a player
            manager.sendMessage(sender, "Only in game players can use this command.");
            return true;
        }
        
        final Player player = (Player)sender;
        ItemStack item = player.getItemInHand();
        
        if (item == null || item.getType() == Material.AIR) {
            // Can't sell nothing
            manager.sendMessage(sender, "Please have the item you want to sell in your hand.");
            return true;
        }
        
        float price = Float.parseFloat(arguments[0]);
        int amount = item.getAmount();
        if (arguments.length == 2) {
            amount = Integer.parseInt(arguments[1]);
        }
        
        if (amount > item.getAmount()) {
            manager.sendMessage(sender, "You can not sell more than you currently have in your hand.");
            return true;
        }
        
        showCreateListingGUI(manager, player, item, price, amount);        
        return true;
    }

    /**
     * Show a confirm listing gui
     * @param manager
     * @param player
     * @param item
     * @param price
     * @param amount
     */
    private void showCreateListingGUI(PluginCommandManager manager, Player player, ItemStack item, float price, int amount) {
        
        ExchangeMarket plugin = (ExchangeMarket)manager.getPlugin();
        
        GuiInventory inventory = new GuiInventory(plugin);
        inventory.createInventory("Confirm Listing", 3);
        
        // Accept
        ItemStack confirmItem = new ItemStack(Material.EMERALD_BLOCK);
        inventory.addMenuItem("Confirm",
                confirmItem, 
                new String[] { 
                    "Sell " + amount + " " + plugin.formatMaterial(item), 
                    "for " + plugin.formatPrice(price) + "?" 
                }, 
                15, 
                1, 
                new ConfirmListingCallback(manager, player, item, price, amount)
        );
        
        // Decline
        ItemStack declineItem = new ItemStack(Material.REDSTONE_BLOCK);
        inventory.addMenuItem("Decline", 
                declineItem, 
                new String[] { 
                    "Cancel the creating of the listing." 
                }, 
                11, 
                1, 
                new CancelListingCallback(manager, player, item)
        );
        
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
