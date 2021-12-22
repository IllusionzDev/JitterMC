public class HandleBlocks {
    public static String getProperName(ItemStack item)
    {
        String newName = item.getType().name().replace("_", " ").toLowerCase();

        StringBuilder sb = new StringBuilder(newName);
        int i = 0;

        do
        {
            sb.replace(i, i + 1, sb.substring(i, i + 1).toUpperCase());
            i = sb.indexOf(" ", i) + 1;
        } while(i > 0 && i < sb.length());
      
        return sb.toString();
    }

    public static String[] convert(String text)
    {
        if (text == null)
            return null;

        if (text.contains(":"))
        {
            return text.split(":");
        }

        return new String[] {text};
    }

    public static String colorText(String text)
    {
        return ChatColor.translateAlternateColorCodes('&', text);
    }

    public static String formatText(String text, ItemStack item, int amount, double price)
    {
        if (text.contains("{AMOUNT}"))
            text = text.replace("{AMOUNT}", String.valueOf(amount));

        if (text.contains("{ITEM}"))
            text = text.replace("{ITEM}", HandleBlocks.getProperName(item) + (item.getData().getData() != 0 ? " (" + item.getData().getData() + ")" : ""));

        if (text.contains("{CURRENCY}"))
            text = text.replace("{CURRENCY}", Main.getINST().settingsConfig.getString("shop.default-currency"));

        if (text.contains("{PRICE}"))
            text = text.replace("{PRICE}", String.valueOf(price));

        return colorText(text);
    }

    public static String formatText(String text, ItemStack item, int amount, double price, Player ply)
    {
        if (text.contains("{AMOUNT}"))
            text = text.replace("{AMOUNT}", String.valueOf(amount));

        if (text.contains("{ITEM}"))
            text = text.replace("{ITEM}", HandleBlocks.getProperName(item) + (item.getData().getData() != 0 ? " (" + item.getData().getData() + ")" : ""));

        if (text.contains("{CURRENCY}"))
            text = text.replace("{CURRENCY}", Main.getINST().settingsConfig.getString("shop.default-currency"));

        if (text.contains("{PRICE}"))
            text = text.replace("{PRICE}", String.valueOf(price));

        if (text.contains("{GIFT_NAME}"))
            text = text.replace("{GIFT_NAME}", ply.getName());

        if (text.contains("{RECEIVE_NAME}"))
            text = text.replace("{RECEIVE_NAME}", ply.getName());

        return colorText(text);
    }
}
