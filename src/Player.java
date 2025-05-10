public class Player {
    private double balance;
    private double currentBet;
    private boolean inRound;

    public Player(double startingBalance) {
        this.balance = startingBalance;
        this.currentBet = 0;
        this.inRound = false;
    }

    public boolean placeBet(double amount) {
        if (amount <= 0 || amount > balance || inRound) return false;
        currentBet = amount;
        balance -= amount;
        inRound = true;
        return true;
    }

    public void cashOut(double multiplier) {
        double winnings = currentBet * multiplier;
        balance += winnings;
        currentBet = 0;
        inRound = false;
    }

    public void loseBet() {
        currentBet = 0;
        inRound = false;
    }

    public void resetBettingState() {
        currentBet = 0;
        inRound = false;
    }

    public double getBalance() { return balance; }
    public boolean isInRound() { return inRound; }
    public double getCurrentBet() { return currentBet; }
}
