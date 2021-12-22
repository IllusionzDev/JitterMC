public class HandleEconomy {
    private Player ply;

    public HandleEconomy(Player ply)
    {
        this.ply = ply;
    }

    public double getBalance()
    {
        return Main.getINST().getEco().getBalance(ply);
    }
}
