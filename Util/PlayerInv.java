public class PlayerInv {
    public static boolean buyItem(Player ply, ItemStack item, double price, int amount, boolean potion)
    {
        HandleEconomy eco = new HandleEconomy(ply);
        ItemStack getItem = potion ? item : new CreateItem(null, Material.getMaterial(item.getType().toString()).getId(), item.getData().getData() != 0 ? item.getData().getData() : 0, 1).getItem();

        double balance = eco.getBalance();

        if (balance < price)
        {
            ply.sendMessage(ShopGUI.shopName + ChatColor.RED + "You don't have enough money to purchase this!");
            return false;
        }

        int invSpace = getFreeSpace(ply, getItem);

        if (amount == 0)
        {
            ply.sendMessage(ShopGUI.shopName + ChatColor.RED + "You can't purchase 0 items!");
            return false;
        }

        if (invSpace < amount)
        {
            ply.sendMessage(ShopGUI.shopName + ChatColor.RED + "You don't have enough inventory space!");
            return false;
        }

        if (getItem.getMaxStackSize() == 1 || getItem.getMaxStackSize() == 16)
        {
            getItem.setAmount(1);
            for (int i = 0; i < amount; i++)
            {
                ply.getInventory().addItem(getItem);
            }
        }
        else
            {
                getItem.setAmount(amount);
                ply.getInventory().addItem(getItem);
            }

        Main.getINST().getEco().withdrawPlayer(ply, price);
        ply.sendMessage(HandleBlocks.colorText(Main.getINST().settingsConfig.getString("shop.prefix"))
                + HandleBlocks.formatText(Main.getINST().settingsConfig.getString("shop.purchase-message"), getItem, amount, price));
        return true;
    }

    public static boolean buyItem(Player ply, Player giftPlayer, ItemStack item, double price, int amount, boolean potion)
    {
        HandleEconomy eco = new HandleEconomy(ply);
        ItemStack getItem = potion ? item : new CreateItem(null, Material.getMaterial(item.getType().toString()).getId(), item.getData().getData() != 0 ? item.getData().getData() : 0, 1).getItem();

        double balance = eco.getBalance();

        if (balance < price)
        {
            ply.sendMessage(ShopGUI.shopName + ChatColor.RED + "You don't have enough money to purchase this!");
            return false;
        }

        int invSpace = getFreeSpace(giftPlayer, getItem);

        if (amount == 0)
        {
            ply.sendMessage(ShopGUI.shopName + ChatColor.RED + "You can't purchase 0 items!");
            return false;
        }

        if (invSpace < amount)
        {
            ply.sendMessage(ShopGUI.shopName + ChatColor.RED + giftPlayer.getName() + " doesn't have enough inventory space!");
            return false;
        }

        if (getItem.getMaxStackSize() == 1 || getItem.getMaxStackSize() == 16)
        {
            getItem.setAmount(1);
            for (int i = 0; i < amount; i++)
            {
                giftPlayer.getInventory().addItem(getItem);
            }
        }
        else
        {
            getItem.setAmount(amount);
            giftPlayer.getInventory().addItem(getItem);
        }

        Main.getINST().getEco().withdrawPlayer(ply, price);
        ply.sendMessage(HandleBlocks.colorText(Main.getINST().settingsConfig.getString("shop.prefix"))
                + HandleBlocks.formatText(Main.getINST().settingsConfig.getString("shop.gift-message"), getItem, amount, price, giftPlayer));

        ply.sendMessage(HandleBlocks.colorText(Main.getINST().settingsConfig.getString("shop.prefix"))
                + HandleBlocks.formatText(Main.getINST().settingsConfig.getString("shop.gift-receive-message"), getItem, amount, price, ply));
        return true;
    }

    public static boolean sellItem(Player ply, ItemStack item, double price, int amount, boolean potion)
    {
        ItemStack getItem = potion ? item : new CreateItem(null, Material.getMaterial(item.getType().toString()).getId(), item.getData().getData() != 0 ? item.getData().getData() : 0, 1).getItem();

        int itemAmount = getItemAmount(ply, getItem);
        if (itemAmount < amount || itemAmount == 0)
        {
            ply.sendMessage(ShopGUI.shopName + ChatColor.RED + "You don't have enough " + getItem.getData().getItemType().name() + " to sell!");
            return false;
        }

        takeItems(ply, getItem, amount);
        Main.getINST().getEco().depositPlayer(ply, price);
        ply.sendMessage(HandleBlocks.colorText(Main.getINST().settingsConfig.getString("shop.prefix"))
                + HandleBlocks.formatText(Main.getINST().settingsConfig.getString("shop.sell-message"), getItem, amount, price));
        return true;
    }

    public static int getFreeSpace(Player ply, ItemStack item)
    {
        int spaceCount = 0;
        for (int i = 0; i <= 35; i++)
        {
            ItemStack items = ply.getInventory().getItem(i);

            if (items == null || items.getType() == Material.AIR)
            {
                spaceCount += item.getMaxStackSize();
            }
            else
                {
                    if (items.isSimilar(item))
                    {
                        spaceCount += Math.max(0, items.getMaxStackSize() - items.getAmount());
                    }
                }
        }
        return spaceCount;
    }

    public static int getItemAmount(Player ply, ItemStack item)
    {
        if (item == null)
            return 0;

        int amount = 0;
        for (int i = 0; i < 36; i++)
        {
            final ItemStack slot = ply.getInventory().getItem(i);

            if (slot == null || !slot.isSimilar(item))
                continue;

            amount += slot.getAmount();
        }
        return amount;
    }

    public static void takeItems(Player ply, ItemStack item, int amount)
    {
        Material mat = item.getType();
        Map<Integer, ? extends ItemStack> total = ply.getInventory().all(mat);

        int found = 0;
        for (ItemStack stack : total.values())
        {
            found += stack.getAmount();
        }

        if (amount > found)
            return;

        for (Integer i : total.keySet())
        {
            ItemStack stack = total.get(i);

            if (stack.isSimilar(item))
            {
                int removed = Math.min(amount, stack.getAmount());
                amount -= removed;

                if (stack.getAmount() == removed)
                    ply.getInventory().setItem(i, null);
                else
                    stack.setAmount(stack.getAmount() - removed);

                if (amount <= 0)
                    break;
            }
        }
        ply.updateInventory();
    }
}
