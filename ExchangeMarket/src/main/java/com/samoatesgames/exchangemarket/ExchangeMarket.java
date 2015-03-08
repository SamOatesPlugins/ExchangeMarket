package com.samoatesgames.exchangemarket;

import com.samoatesgames.samoatesplugincore.plugin.SamOatesPlugin;
import org.bukkit.ChatColor;

/**
 * The main plugin class
 *
 * @author Sam Oates <sam@samoatesgames.com>
 */
public final class ExchangeMarket extends SamOatesPlugin {
    
    /**
     * Class constructor
     */
    public ExchangeMarket() {
        super("ExchangeMarket", "Market", ChatColor.AQUA);
    }

    /**
     * Called when the plugin is enabled
     */
    @Override
    public void onEnable() {
        super.onEnable();

        this.logInfo("Succesfully enabled.");
    }

    /**
     * Register all configuration settings
     */
    public void setupConfigurationSettings() {

    }
    
    /**
     * Called when the plugin is disabled
     */
    @Override
    public void onDisable() {
        
    }
}
