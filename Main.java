    /*
     * Plugin Made By IllusionDev (Illusion#3247)
     */

public final class Main extends JavaPlugin {
    private static Main INSTANCE;
    public CreateConfig shopConfig, settingsConfig;

    private Economy eco;

    @Override
    public void onEnable() {
        if (!setupEconomy()) {
            this.getLogger().severe("No Vault dependency found!");
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }

        INSTANCE = this;

        System.out.println("Successfully started!");
        this.getCommand("shop").setExecutor(new shopCommands());

        shopConfig = new CreateConfig("shop.yml", "JitterMC/Shop");
        settingsConfig = new CreateConfig("settings.yml", "JitterMC/Shop");
        new ConfigStartup(shopConfig).setDefault("blocks");
        new ConfigStartup(shopConfig).setDefault("minerals");
        new ConfigStartup(shopConfig).setDefault("mobs");
        new ConfigStartup(shopConfig).setDefault("misc");
        new ConfigStartup(shopConfig).setDefault("potions");
        new ConfigStartup(settingsConfig).setDefaultSettings();
    }

    @Override
    public void onDisable() {

    }

    private boolean setupEconomy() {
        if (Bukkit.getPluginManager().getPlugin("Vault") == null) {
            return false;
        }

        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        eco = rsp.getProvider();
        return eco != null;
    }

    public Economy getEco()
    {
        return eco;
    }

    public static Main getINST()
    {
        return INSTANCE;
    }
}
