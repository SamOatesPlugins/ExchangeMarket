package com.samoatesgames.exchangemarket.commands;

import com.samoatesgames.samoatesplugincore.commands.BasicCommandHandler;
import com.samoatesgames.samoatesplugincore.commands.ICommandHandler;
import com.samoatesgames.samoatesplugincore.commands.PluginCommandManager;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

/**
 *
 * @author Sam
 */
public class MarketCommand extends BasicCommandHandler {

    /**
     * All market sub commands
     */
    Map<String, ICommandHandler> m_subCommands = new HashMap<String, ICommandHandler>();
    
    /**
     * Class constructor
     */
    public MarketCommand() {
        super("exchangemarket.command.market");
        m_subCommands.put("listings", new MarketListingsCommand());
        m_subCommands.put("create", new MarketCreateCommand());
    }

    /**
     * Handle the /market command
     * @param manager
     * @param sender
     * @param arguments
     * @return 
     */
    @Override
    public boolean execute(PluginCommandManager manager, CommandSender sender, String[] arguments) {
        
        if (!manager.hasPermission(sender, this.getPermission())) {
            manager.sendMessage(sender, "You do not have permission to use the market.");
            return true;
        }

        if (arguments.length == 0) {
            // Show help
            showHelp(manager, sender);
            return true;
        }
        
        final String subcommand = arguments[0].toLowerCase();
        if (!m_subCommands.containsKey(subcommand)) {
            // Unknown subcommand
            manager.sendMessage(sender, "Unknown market command...");
            showHelp(manager, sender);
            return true;
        }
        
        final String[] subcommandArgs = Arrays.copyOfRange(arguments, 1, arguments.length);
        return m_subCommands.get(subcommand).execute(manager, sender, subcommandArgs);
    }

    /**
     * Show the plugin help
     * @param manager
     * @param sender 
     */
    private void showHelp(PluginCommandManager manager, CommandSender sender) {
        
        manager.sendMessage(sender, ChatColor.UNDERLINE + "-- Market Help Command Commands --");
        
        manager.sendTooltipMessage(sender, 
                "/exchange create <price> [amount]", 
                new String[] { 
                    "Use this command to create a new market listing.",
                    "price: The amount you want to sell the item for.", 
                    "amount: (optional) The number of items to list." 
                }
        );
        
        manager.sendTooltipMessage(sender, "/exchange listings", "Show all the current market listings.");
        
    }
    
}
