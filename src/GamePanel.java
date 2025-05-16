import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {
    private CrashGame game;

    public GamePanel(CrashGame game) {
        this.game = game;
        setPreferredSize(new Dimension(700, 800));
        setBackground(new Color(10, 10, 40));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draw player balance
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.PLAIN, 20));
        g.drawString("Balance: $" + String.format("%.2f", game.getPlayer().getBalance()), 20, 30);

        // Draw multiplier value
        g.setFont(new Font("Arial", Font.BOLD, 64));
        g.setColor(game.hasCrashed() ? Color.RED : Color.WHITE);
        g.drawString(String.format("x%.2f", game.getMultiplier()), 250, 300);

        // Draw last multipliers
        g.setFont(new Font("Arial", Font.PLAIN, 20));
        g.setColor(Color.WHITE);
        g.drawString("Last Rounds:", 30, 160);
        int x = 150;
        for (Double d : game.getLastMultipliers()) {
            g.drawString(String.format("%.2fx", d), x, 160);
            x += 80;
        }

        // Draw countdown if active
        if (game.isInCountdown()) {
            g.setFont(new Font("Arial", Font.BOLD, 40));
            g.setColor(Color.YELLOW);
            g.drawString("Next round in: " + game.getCountdownSeconds(), 220, 400);
        }

        // Draw animated exponential curve based on multiplier growth
        if (!game.isInCountdown()) {
            g.setColor(Color.GREEN);
            int baseY = 700;  // Bottom of the screen i think 
            int startX = 0;
            int startY = baseY;
            int curveLength = (int)(game.getMultiplier() * 50);

            double curvePower = 2.0; 

            for (int i = 1; i <= curveLength; i++) 
            {
                int nextX = startX + 5;
                double scaledMultiplier = game.getMultiplier() * i / curveLength;
                int nextY = baseY - (int)(Math.pow(scaledMultiplier, curvePower) * 100); 
                // if (nextY < 50) nextY = 50; // Prevent overflow on to the top of the screen possibly idk tho 

                g.drawLine(startX, startY, nextX, nextY);

                startX = nextX;
                startY = nextY;
            }
        }
    }
}
