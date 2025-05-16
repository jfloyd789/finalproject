import javax.swing.Timer;
import javax.swing.*;
import java.awt.Color;
import java.awt.event.*;
import java.util.List;
import java.util.ArrayList;

public class CrashGame implements ActionListener {
    private double multiplier;
    private double crashPoint;
    private boolean isCrashed;
    private boolean inCountdown;
    private int countdownSeconds;
    private CrashGameView window;
    private javax.swing.Timer timer;
    private Player player;
    private final int MAX_MULTIPLIERS = 10;
    private final List<Double> lastMultipliers = new ArrayList<>();
    private long postCrashTimestamp = -1;

    public CrashGame() {
        this.player = new Player(5.0);
        this.multiplier = 1.00;
        this.crashPoint = generateCrashPoint();
        this.isCrashed = false;
        this.inCountdown = false;
        this.countdownSeconds = 0;

        this.window = new CrashGameView(this);
        this.timer = new javax.swing.Timer(30, this);
        this.timer.start();
    }

    public void actionPerformed(ActionEvent e) {
        if (!isCrashed && !inCountdown) {
            double growthRate = 0.01 * Math.pow(multiplier, 0.5); // made it so the multiplier grows slower  at the beginning 
            multiplier += growthRate;

            if (multiplier >= crashPoint) {
                multiplier = crashPoint;
                isCrashed = true;
                if (player.isInRound()) player.loseBet();
                lastMultipliers.add(0, crashPoint);
                if (lastMultipliers.size() > MAX_MULTIPLIERS) {
                    lastMultipliers.remove(MAX_MULTIPLIERS);
                }
                postCrashTimestamp = System.currentTimeMillis();
            }
        } else if (isCrashed && !inCountdown) {
            if (System.currentTimeMillis() - postCrashTimestamp >= 5000) {
                startCountdown();
            }
        } else if (inCountdown) {
            long elapsed = System.currentTimeMillis() - postCrashTimestamp - 5000;
            countdownSeconds = 4 - (int)(elapsed / 1000);
            if (countdownSeconds <= 0) {
                resetRound();
            }
        }
        window.repaintPanel();
    }

    private void startCountdown() {
        inCountdown = true;
        countdownSeconds = 10;
    }

    private double generateCrashPoint() {
        double instantCrashChance = 0.05;  // 5% chance to crash at 1.00x 
        if (Math.random() < instantCrashChance) {
            return 1.00;  // Instant bust
        }
    
        double houseEdge = 0.35;  // 10% house edge
        double r = Math.random();
        if (r == 0.0) r = 0.000001;

    
        double rawMultiplier = (1 - houseEdge) / r;
        crashPoint = Math.max(1.00, Math.floor(rawMultiplier * 100.0) / 100.0);
        if (crashPoint > 30.0) {
            crashPoint = 30.0;
        }
        return crashPoint;
    }
    
    

    public double getMultiplier() { return multiplier; }
    public boolean hasCrashed() { return isCrashed; }
    public boolean isInCountdown() { return inCountdown; }
    public int getCountdownSeconds() { return countdownSeconds; }
    public Player getPlayer() { return player; }
    public List<Double> getLastMultipliers() { return lastMultipliers; }

    public void resetRound() {
        multiplier = 1.00;
        crashPoint = generateCrashPoint();
        isCrashed = false;
        inCountdown = false;
        countdownSeconds = 0;
        postCrashTimestamp = -1;

        if (!player.isInRound()) {
            player.resetBettingState();
        }
    }

    public static void main(String[] args) {
        new CrashGame();
    }
}
