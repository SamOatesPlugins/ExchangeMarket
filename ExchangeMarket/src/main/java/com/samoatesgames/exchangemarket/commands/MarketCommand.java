package com.samoatesgames.exchangemarket.commands;

import com.samoatesgames.samoatesplugincore.commands.BasicCommandHandler;
import com.samoatesgames.samoatesplugincore.commands.PluginCommandManager;
import org.bukkit.command.CommandSender;

/**
 *
 * @author Sam
 */
public class MarketCommand extends BasicCommandHandler {

    /**
     * 
     */
    public MarketCommand() {
        super("exchangemarket.command.market");
    }

    /**
     * 
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

        
        
        return true;
    }
    
}
