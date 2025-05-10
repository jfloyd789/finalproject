import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {
    private CrashGame game;

    public GamePanel(CrashGame game) {
        this.game = game;
        setPreferredSize(new Dimension(700, 800));
        setBackground(new Color(10, 10, 40));
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.PLAIN, 20));
        g.drawString("Balance: $" + String.format("%.2f", game.getPlayer().getBalance()), 20, 30);

        g.setFont(new Font("Arial", Font.BOLD, 64));
        g.setColor(game.hasCrashed() ? Color.RED : Color.WHITE);
        g.drawString(String.format("x%.2f", game.getMultiplier()), 250, 300);

        g.setFont(new Font("Arial", Font.PLAIN, 20));
        g.setColor(Color.WHITE);
        g.drawString("Last Rounds:", 30, 160);
        int x = 150;
        for (Double d : game.getLastMultipliers()) {
            g.drawString(String.format("%.2fx", d), x, 160);
            x += 80;
        }

        if (game.isInCountdown()) {
            g.setFont(new Font("Arial", Font.BOLD, 40));
            g.setColor(Color.YELLOW);
            g.drawString("Next round in: " + game.getCountdownSeconds(), 220, 400);
        }
    }
}
