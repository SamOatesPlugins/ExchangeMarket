package com.samoatesgames.exchangemarket;

import com.samoatesgames.exchangemarket.commands.MarketCommand;
import com.samoatesgames.exchangemarket.data.ItemListing;
import com.samoatesgames.samoatesplugincore.plugin.SamOatesPlugin;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.RegisteredServiceProvider;
import uk.thecodingbadgers.bDatabaseManager.Database.BukkitDatabase;
import uk.thecodingbadgers.bDatabaseManager.bDatabaseManager;

/**
 * The main plugin class
 *
 * @author Sam Oates <sam@samoatesgames.com>
 */
public final class ExchangeMarket extends SamOatesPlugin {

    /**
     * The connection to the sql database
     */
    private BukkitDatabase m_database = null;
    
    /**
     * The economy plugin (provided by vault)
     */
    private Economy m_economy = null;

    /**
     * All item listings
     */
    private final List<ItemListing> m_listings = new ArrayList<ItemListing>();

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

        m_commandManager.registerCommandHandler("exchange", new MarketCommand());

        // Setup the economy
        RegisteredServiceProvider<Economy> economyProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
        if (economyProvider != null) {
            m_economy = economyProvider.getProvider();
        }
        else {
            this.logError("Failed to find Vault economy plugin.");
        }
        
        // Setup the database
        m_database = bDatabaseManager.createDatabase(this.getSetting(Setting.databaseName, "my_database"), this, bDatabaseManager.DatabaseType.SQL);
        if (m_database.login(
                this.getSetting(Setting.databaseHost, "localhost"),
                this.getSetting(Setting.databaseUsername, "user"),
                this.getSetting(Setting.databasePassword, "password"),
                this.getSetting(Setting.databasePort, 3306))) {

            this.logInfo("Connected to database.");

            if (!m_database.tableExists("ExchangeMarket_Listings")) {
                m_database.query("CREATE TABLE `ExchangeMarket_Listings` (player TEXT, item TEXT, amount int, price FLOAT)", true);
            }

            loadListings();

        } else {
            this.logError("Failed to login to database.");
        }

        this.logInfo("Succesfully enabled.");
    }

    /**
     * Register all configuration settings
     */
    public void setupConfigurationSettings() {

        this.registerSetting(Setting.databaseHost, "localhost");
        this.registerSetting(Setting.databasePort, 3306);
        this.registerSetting(Setting.databaseName, "my_database");
        this.registerSetting(Setting.databaseUsername, "user");
        this.registerSetting(Setting.databasePassword, "password");

    }

    /**
     * Called when the plugin is disabled
     */
    @Override
    public void onDisable() {

    }

    /**
     * Load all listings from database
     */
    private void loadListings() {

        ResultSet result = m_database.queryResult("SELECT * FROM `ExchangeMarket_Listings`");
        if (result != null) {
            try {
                while (result.next()) {
                    UUID uuid = UUID.fromString(result.getString("player"));
                    int amount = result.getInt("amount");
                    float price = result.getFloat("price");
                    ItemStack item = parseItemStack(result.getString("item"));
                    ItemListing listing = new ItemListing(uuid, item, amount, price);
                    m_listings.add(listing);
                }
            } catch (Exception ex) {
                this.logException("Failed to listings.", ex);
            }
        }

    }

    /**
     * Format a material to a nice name
     *
     * @param material
     * @return
     */
    public String formatMaterial(Material material) {
        return toTitleCase(material.name());
    }
    
    /**
     * Format an itemstack to a nice name
     *
     * @param item
     * @return
     */
    public String formatMaterial(ItemStack item) {

        // Is the item named?
        if (item.hasItemMeta()) {
            ItemMeta itemMeta = item.getItemMeta();
            if (itemMeta.hasDisplayName()) {
                return itemMeta.getDisplayName();
            }
        }

        // Just use the material name
        return formatMaterial(item.getType());
    }

    /**
     * 
     * @param input
     * @return 
     */
    private static String toTitleCase(String input) {
        
        String output = "";
        String[] parts = input.split("_");
        for (String part : parts) {
            
            String word = part.substring(0, 1).toUpperCase();
            if (part.length() > 1) {
                word += part.substring(1).toLowerCase();
            }
            
            output += word + " ";
        }
        return output.trim();
    }

    /**
     * Format a price to a nice price
     *
     * @param price
     * @return
     */
    public String formatPrice(float price) {        
        return m_economy.format(price);
    }

    /**
     *
     * @param uuid
     * @return
     */
    public String playerNameFromUUID(UUID uuid) {
        OfflinePlayer player = Bukkit.getOfflinePlayer(uuid);
        return player.getName();
    }

    /**
     *
     * @param rawItem
     * @return
     */
    private ItemStack parseItemStack(String rawItem) {
        return null;
    }

    /**
     *
     * @param listing
     */
    public void addListing(ItemListing listing) {
        m_listings.add(listing);
    }

    /**
     *
     * @return
     */
    public List<ItemListing> getListings() {
        return m_listings;
    }
}
