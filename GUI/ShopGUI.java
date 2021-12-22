public class ShopGUI extends CreateGUI {
    public final static String shopName = ChatColor.AQUA + "" + ChatColor.BOLD + "JitterShop" + ChatColor.WHITE + "" + ChatColor.BOLD + " » " + ChatColor.RESET;

    public static Map<UUID, String> userCategory = Maps.newHashMap();
    public static Map<UUID, Integer> blockAmount = Maps.newHashMap();
    ArrayList<String> itemLore = new ArrayList<>();

    public ShopGUI(Player ply, String category, String invName, int rows, boolean pageButtons)
    {
        super(invName, rows, pageButtons);

        if (CreateGUI.inGifting.contains(ply.getUniqueId()))
            CreateGUI.inGifting.remove(ply.getUniqueId());

        setupCategory(ply, category, Main.getINST().shopConfig);
    }

    private void setupCategory(Player ply, String category, CreateConfig config)
    {
        userCategory.put(ply.getUniqueId(), category);

        for (int i = 0; i <= 50; i++)
        {
            if (config.getConfig().contains(category + "." + i))
            {
                String[] getBlockData = HandleBlocks.convert(config.getString(category + "." + i + ".item-id"));
                Double getBuyPrice = config.getDouble(category + "." + i + ".buy-price");
                Double getSellPrice = config.getDouble(category + "." + i + ".sell-price");

                if (itemLore.size() >= 1)
                    itemLore.clear();

                for (Material mat : Material.values())
                {
                    if (mat.getId() != Integer.parseInt(getBlockData[0]))
                        continue;

                    itemLore.add(ChatColor.GRAY + "[Left Click] Buy: " + ChatColor.YELLOW + Main.getINST().settingsConfig.getString("shop.default-currency") + getBuyPrice);
                    itemLore.add(ChatColor.GRAY + "[Right Click] Sell: " + ChatColor.YELLOW + Main.getINST().settingsConfig.getString("shop.default-currency") + getSellPrice);

                    if (mat.getId() != 373)
                        setItem(new CreateItem(HandleBlocks.colorText(config.getString(category + "." + i + ".name")), Integer.parseInt(getBlockData[0]), getBlockData.length == 2 ? Byte.parseByte(getBlockData[1]) : 0, 1, itemLore).getItem());
                    else
                        setItem(new CreateItem(HandleBlocks.colorText(config.getString(category + "." + i + ".potion-name")), PotionType.valueOf(config.getString(category + "." + i + ".potion-type").toUpperCase()), config.getInt(category + "." + i + ".potion-level"), itemLore, config.getBool(category + "." + i + ".potion-splash"), false).getItem());
                }
            }
        }
    }

    @Override
    public boolean onInventoryClick(String clickType, String invName, Player ply, ItemStack item, int slot) {
        return MainGUI.itemClick(clickType, invName, ply, userCategory.get(ply.getUniqueId()), Main.getINST().shopConfig, item);
    }

    @Override
    public ItemStack amountItem(Player ply)
    {
        ArrayList<String> itemLore = new ArrayList<>();
        itemLore.add(ChatColor.YELLOW + "Click to increase the");
        itemLore.add(ChatColor.YELLOW + "amount of items you");
        itemLore.add(ChatColor.YELLOW + "purchase / sell at once.");
        return (new CreateItem(ChatColor.AQUA + "Multiplier", Material.DIAMOND, ShopGUI.blockAmount.get(ply.getUniqueId()), itemLore).getItem());
    }
}
