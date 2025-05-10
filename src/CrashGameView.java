import javax.swing.*;
import java.awt.*;

public class CrashGameView extends JFrame {
    private final CrashGame game;
    private final JTextField betField;
    private final JButton betButton;
    private final JButton cashOutButton;
    private GamePanel panel;

    public CrashGameView(CrashGame game) {
        this.game = game;
        setTitle("Crash Game");
        setSize(1000, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        panel = new GamePanel(game);
        panel.setBounds(0, 0, 700, 800);
        add(panel);

        betField = new JTextField();
        betField.setBounds(750, 100, 200, 30);
        add(betField);

        betButton = new JButton("Bet");
        betButton.setBounds(750, 140, 200, 40);
        add(betButton);

        cashOutButton = new JButton("Cash Out");
        cashOutButton.setBounds(750, 190, 200, 40);
        cashOutButton.setBackground(Color.ORANGE);
        add(cashOutButton);

        // ✅ Fix: allow bets only during countdown with >1 second left
        betButton.addActionListener(e -> {
            try {
                double bet = Double.parseDouble(betField.getText());
                if (game.isInCountdown() && game.getCountdownSeconds() > 1) {
                    boolean success = game.getPlayer().placeBet(bet);
                    if (success) {
                        System.out.println("Bet accepted: $" + bet);
                    } else {
                        System.out.println("Bet failed: Already in round or not enough balance.");
                    }
                } else {
                    System.out.println("You can only bet during the countdown (at least 2 seconds left).");
                }
            } catch (NumberFormatException ignored) {
                System.out.println("Invalid bet amount.");
            }
        });

        
        cashOutButton.addActionListener(e -> {
            boolean inRound = game.getPlayer().isInRound();
            boolean countdown = game.isInCountdown();
            boolean crashed = game.hasCrashed();

            System.out.println("DEBUG: inRound=" + inRound + ", isInCountdown=" + countdown + ", hasCrashed=" + crashed);

            if (inRound && !countdown && !crashed) {
                game.getPlayer().cashOut(game.getMultiplier());
                System.out.println("Successfully cashed out!");
            } else {
                System.out.println("Cannot cash out right now.");
            }
        });

        setVisible(true);
    }

    public void repaintPanel() {
        panel.repaint();
    }
}
