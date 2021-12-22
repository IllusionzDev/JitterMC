public class shopCommands implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (label.equalsIgnoreCase(Main.getINST().settingsConfig.getString("shop.command")))
        {
            if (args.length == 0)
            {
                if (!(sender instanceof Player))
                {
                    sender.sendMessage(ChatColor.RED + "Commands need to be sent from a player.");
                    return true;
                }

                Player ply = (Player) sender;
                new MainGUI(ply, ShopGUI.shopName, 3, false).open(ply);
            }
            else
                {
                    String arg1 = args[0];

                    if (arg1.equalsIgnoreCase("reload"))
                    {
                        if (!sender.hasPermission(Main.getINST().settingsConfig.getString("shop.permission.reload")))
                        {
                            sender.sendMessage(HandleBlocks.colorText(Main.getINST().settingsConfig.getString("shop.prefix")) + HandleBlocks.colorText(Main.getINST().settingsConfig.getString("shop.permission.reload.message")));
                            return true;
                        }

                        Main.getINST().shopConfig.reload();
                        Main.getINST().settingsConfig.reload();
                        sender.sendMessage(HandleBlocks.colorText(Main.getINST().settingsConfig.getString("shop.prefix")) + HandleBlocks.colorText(Main.getINST().settingsConfig.getString("shop.reload-message")));
                    }
                }
        }
        return true;
    }
}
