public class GiftGUI extends CreateGUI {
    public static Map<UUID, Player> giftingPlayer = Maps.newHashMap();

    public GiftGUI(Player ply, String category, String invName, int rows, boolean pageButtons)
    {
        super(invName, rows, pageButtons);

        if (!CreateGUI.inGifting.contains(ply.getUniqueId()))
            CreateGUI.inGifting.add(ply.getUniqueId());

        setupCategory(ply, category, Main.getINST().shopConfig);
    }

    private void setupCategory(Player ply, String category, CreateConfig config)
    {
        setItem(new CreateItem(ChatColor.RED + "None", 160, 14, 1).getItem());

        for (Player onlinePlayer : Bukkit.getOnlinePlayers())
        {
            if (onlinePlayer.getName().equalsIgnoreCase(ply.getName()))
                continue;

            Player giftPlayer = giftingPlayer.getOrDefault(ply.getUniqueId(), null);
            setItem(new CreateItem(onlinePlayer.getName(), giftPlayer != null && onlinePlayer.getName().equalsIgnoreCase(giftPlayer.getName()) ? Material.RED_ROSE : Material.CHEST, 1).getItem());
        }
    }

    @Override
    public boolean onInventoryClick(String clickType, String invName, Player ply, ItemStack item, int slot) {
        return MainGUI.itemClick(clickType, invName, ply, ShopGUI.userCategory.get(ply.getUniqueId()), Main.getINST().shopConfig, item);
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
