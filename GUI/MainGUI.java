public class MainGUI extends CreateGUI {

    public MainGUI(Player ply, String invName, int rows, boolean pageButtons)
    {
        super(invName, rows, pageButtons);

        if (!ShopGUI.blockAmount.containsKey(ply.getUniqueId()))
            ShopGUI.blockAmount.put(ply.getUniqueId(), 1);

        setItem(new CreateItem(ChatColor.GREEN + "" + ChatColor.BOLD + "Block Shop", Material.GRASS, 1).getItem(), 11);

        setItem(new CreateItem(ChatColor.RED + "" + ChatColor.BOLD + "Mob Shop", Material.ROTTEN_FLESH, 1).getItem(), 12);

        setItem(new CreateItem(ChatColor.AQUA + "" + ChatColor.BOLD + "Mineral Shop", Material.DIAMOND, 1).getItem(), 13);

        setItem(new CreateItem(ChatColor.BLUE + "" + ChatColor.BOLD + "Potion Shop", PotionType.INSTANT_HEAL, 1, false, true).getItem(), 14);

        setItem(new CreateItem(ChatColor.GOLD + "" + ChatColor.BOLD + "Misc Shop", Material.WATER_LILY, 1).getItem(), 15);
    }

    @Override
    public boolean onInventoryClick(String clickType, String invName, Player ply, ItemStack item, int slot) {
        String blockName = item.getItemMeta().getDisplayName();

        if (invName.equals(ShopGUI.shopName))
        {
            switch (ChatColor.stripColor(blockName))
            {
                case "Block Shop":
                    new ShopGUI(ply, "blocks", ChatColor.GREEN + "Block Shop" + ChatColor.WHITE + " » ", 4, true).open(ply);
                    return true;
                case "Mob Shop":
                    new ShopGUI(ply, "mobs", ChatColor.RED + "Mob Shop" + ChatColor.WHITE + " » ", 4, true).open(ply);
                    return true;
                case "Mineral Shop":
                    new ShopGUI(ply, "minerals", ChatColor.AQUA + "Mineral Shop" + ChatColor.WHITE + " » ", 4, true).open(ply);
                    return true;
                case "Potion Shop":
                    new ShopGUI(ply, "potions", ChatColor.BLUE + "Potion Shop" + ChatColor.WHITE + " » ", 4, true).open(ply);
                    return true;
                case "Misc Shop":
                    new ShopGUI(ply, "misc", ChatColor.GOLD + "Misc Shop" + ChatColor.WHITE + " » ", 4, true).open(ply);
                    return true;
            }
            return false;
        }
        else if (invName.equals(ChatColor.GOLD + "Gifting" + ChatColor.WHITE + " » "))
        {
            String displayName = item.getItemMeta().getDisplayName();

            if (displayName.equalsIgnoreCase(ChatColor.RED + "None"))
            {
                GiftGUI.giftingPlayer.remove(ply.getUniqueId());
                new MainGUI(ply, ShopGUI.shopName, 3, false).open(ply);
                return true;
            }

            for (Player onlinePlayer : Bukkit.getOnlinePlayers())
            {
                if (onlinePlayer.getName().equalsIgnoreCase(displayName))
                {
                    GiftGUI.giftingPlayer.put(ply.getUniqueId(), onlinePlayer);
                    new MainGUI(ply, ShopGUI.shopName, 3, false).open(ply);
                    return true;
                }
            }
            return false;
        }
        return itemClick(clickType, invName, ply, ShopGUI.userCategory.get(ply.getUniqueId()), Main.getINST().shopConfig, item);
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

    public static boolean itemClick(String clickType, String invName, Player ply, String category, CreateConfig config, ItemStack item)
    {
        if (Main.getINST().getClickDelay(ply.getUniqueId()))
        {
            ply.sendMessage(HandleBlocks.colorText(Main.getINST().settingsConfig.getString("shop.cooldown-message")));
            return false;
        }

        Main.getINST().addClickDelay(ply.getUniqueId());
        boolean potion;

        for (int i = 0; i <= 50; i++) {
            if (config.getConfig().contains(category + "." + i))
            {
                String[] getBlockData = HandleBlocks.convert(config.getString(category + "." + i + ".item-id"));
                double buyPrice = config.getDouble(category + "." + i + ".buy-price") * ShopGUI.blockAmount.get(ply.getUniqueId());
                double sellPrice = config.getDouble(category + "." + i + ".sell-price") * ShopGUI.blockAmount.get(ply.getUniqueId());

                if (item.getType().getId() != Integer.parseInt(getBlockData[0]))
                    continue;

                potion = item.getType().getId() == 373;

                switch (clickType)
                {
                    case "LEFT":
                        if (!GiftGUI.giftingPlayer.containsKey(ply.getUniqueId()))
                        {
                            if (!PlayerInv.buyItem(ply, item, buyPrice, ShopGUI.blockAmount.get(ply.getUniqueId()), potion))
                                return false;
                            return false;
                        }

                        Player giftingPlayer = GiftGUI.giftingPlayer.get(ply.getUniqueId());

                        for (Player onlinePlayer : Bukkit.getOnlinePlayers())
                        {
                            if (onlinePlayer.getName().equalsIgnoreCase(giftingPlayer.getName()))
                            {
                                if (!PlayerInv.buyItem(ply, onlinePlayer, item, buyPrice, ShopGUI.blockAmount.get(ply.getUniqueId()), potion))
                                    return false;

                                GiftGUI.giftingPlayer.remove(ply.getUniqueId());
                            }
                        }
                        return true;
                    case "RIGHT":
                        return PlayerInv.sellItem(ply, item, sellPrice, ShopGUI.blockAmount.get(ply.getUniqueId()), potion);
                }
            }
        }
        return false;
    }
}
