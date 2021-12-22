public class CreateItem {
    ItemStack item;

    public CreateItem(String displayName, Material material, int amount, ArrayList<String> lore)
    {
        item = new ItemStack(material, amount);
        ItemMeta meta = item.getItemMeta();

        if (displayName != null)
            meta.setDisplayName(displayName);

        meta.setLore(lore);
        item.setItemMeta(meta);
    }

    public CreateItem(String displayName, int itemID, int itemData, int amount, ArrayList<String> lore)
    {
        item = new ItemStack(itemID, amount, (byte) itemData);
        ItemMeta meta = item.getItemMeta();

        if (displayName != null)
            meta.setDisplayName(displayName);

        meta.setLore(lore);
        item.setItemMeta(meta);
    }

    public CreateItem(String displayName, Material material, int amount)
    {
        item = new ItemStack(material, amount);
        ItemMeta meta = item.getItemMeta();

        if (displayName != null)
            meta.setDisplayName(displayName);

        item.setItemMeta(meta);
    }

    public CreateItem(String displayName, int itemID, int itemData, int amount)
    {
        item = new ItemStack(itemID, amount, (byte) itemData);
        ItemMeta meta = item.getItemMeta();

        if (displayName != null)
            meta.setDisplayName(displayName);

        item.setItemMeta(meta);
    }

    public CreateItem(String displayName, PotionType potion, int level, ArrayList<String> lore, boolean splash, boolean hideFlags)
    {
        Potion createPot = new Potion(potion, level);
        createPot.setSplash(splash);

        item = createPot.toItemStack(1);
        ItemMeta meta = item.getItemMeta();

        if (displayName != null)
            meta.setDisplayName(displayName);

        meta.setLore(lore);

        if (hideFlags)
            meta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);

        item.setItemMeta(meta);
    }

    public CreateItem(String displayName, PotionType potion, int level, boolean splash, boolean hideFlags)
    {
        Potion createPot = new Potion(potion, level);
        createPot.setSplash(splash);

        item = createPot.toItemStack(1);
        ItemMeta meta = item.getItemMeta();

        if (displayName != null)
            meta.setDisplayName(displayName);

        if (hideFlags)
            meta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);

        item.setItemMeta(meta);
    }

    public ItemStack getItem()
    {
        return this.item;
    }
}
