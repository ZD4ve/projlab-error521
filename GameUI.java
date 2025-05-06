import javax.swing.*;
import java.awt.*;
import javax.swing.border.Border;

public class GameUI extends JFrame {
    private java.util.List<JButton> playerButtons = new java.util.ArrayList<>();
    private JButton selectedButton = null;
    private final Border selectedBorder = BorderFactory.createLineBorder(Color.BLACK, 2);
    private final Border unselectedBorder = BorderFactory.createEmptyBorder();

    public GameUI() {
        setTitle("Fungorium");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLayout(new BorderLayout());

        JPanel playerListPanel = new JPanel();
        playerListPanel.setLayout(new BoxLayout(playerListPanel, BoxLayout.Y_AXIS));
        playerListPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0)); // top, left, bottom, right

        // Group 1 Panel (Red + Blue)
        playerListPanel.add(Box.createVerticalStrut(20));
        JLabel groupHeader = new JLabel("Gombászok:");
        groupHeader.setAlignmentX(Component.LEFT_ALIGNMENT);
        playerListPanel.add(groupHeader);
        playerListPanel.add(Box.createVerticalStrut(10));
        JPanel groupPanel1 = new JPanel();
        groupPanel1.setLayout(new BoxLayout(groupPanel1, BoxLayout.Y_AXIS));
        groupPanel1.setAlignmentX(Component.LEFT_ALIGNMENT);
        groupPanel1.add(createPlayerRow(Color.RED, 2));
        groupPanel1.add(Box.createVerticalStrut(10));
        groupPanel1.add(createPlayerRow(Color.BLUE, 1));

        playerListPanel.add(groupPanel1);
        playerListPanel.add(Box.createVerticalStrut(20));

        // Header between groups
        groupHeader = new JLabel("Rovarászok:");
        groupHeader.setAlignmentX(Component.LEFT_ALIGNMENT);
        playerListPanel.add(groupHeader);
        playerListPanel.add(Box.createVerticalStrut(10));

        // Group 2 Panel (Yellow + Green)
        JPanel groupPanel2 = new JPanel();
        groupPanel2.setLayout(new BoxLayout(groupPanel2, BoxLayout.Y_AXIS));
        groupPanel2.setAlignmentX(Component.LEFT_ALIGNMENT);
        groupPanel2.add(createPlayerRow(Color.YELLOW, 5));
        groupPanel2.add(Box.createVerticalStrut(10));
        groupPanel2.add(createPlayerRow(Color.GREEN, 0));

        playerListPanel.add(groupPanel2);

        selectedButton = playerButtons.get(2);
        updateButtonOpacities();

        // Outer left panel with border
        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.setPreferredSize(new Dimension(200, getHeight()));
        leftPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        JScrollPane scrollPane = new JScrollPane(playerListPanel);
        scrollPane.setBorder(null); // optional: removes border if you want a cleaner look
        scrollPane.getVerticalScrollBar().setUnitIncrement(10); // smooth scrolling
        leftPanel.add(scrollPane, BorderLayout.CENTER);

        // Right panel (future canvas)
        JPanel rightPanel = new JPanel();
        rightPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        rightPanel.setBackground(Color.WHITE); // canvas background

        // Add panels to frame
        add(leftPanel, BorderLayout.WEST);
        add(rightPanel, BorderLayout.CENTER);

        // Countdown timer label
        JLabel timerLabel = new JLabel("Time left: 60s", SwingConstants.CENTER);
        timerLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        leftPanel.add(timerLabel, BorderLayout.SOUTH);

        // Timer logic
        final int[] timeLeft = {5*60}; // 60 seconds countdown
        Timer countdownTimer = new Timer(1000, e -> {
            timeLeft[0]--;
            int minutes = timeLeft[0] / 60;
            int seconds = timeLeft[0] % 60;
            timerLabel.setText(String.format("Hátralévő idő: %02d:%02d", minutes, seconds));
            if (timeLeft[0] <= 0) {
                ((Timer) e.getSource()).stop();
                timerLabel.setText("Játék vége!");
            }
        });
        countdownTimer.start();

        setVisible(true);
    }

    private JPanel createPlayerRow(Color color, int score) {
        JPanel playerRow = new JPanel();
        playerRow.setLayout(new FlowLayout(FlowLayout.LEFT));
        playerRow.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
    
        final JButton playerButton = new JButton();
        playerButton.setPreferredSize(new Dimension(60, 30));
        playerButton.setBackground(new Color(color.getRed(), color.getGreen(), color.getBlue(), 40)); // start semi-transparent
        playerButton.setOpaque(true);
        playerButton.setBorderPainted(true);
        playerButton.setFocusPainted(false);
        playerButton.setRolloverEnabled(false); // disables hover visual effects
        playerButton.setContentAreaFilled(true);
        playerButton.setToolTipText("Player color");
    
        playerButton.addActionListener(e -> {
            selectedButton = playerButton;
            updateButtonOpacities();
        });
    
        playerButtons.add(playerButton); // keep track
    
        JLabel scoreLabel = new JLabel("pont: " + Integer.toString(score));
    
        playerRow.add(playerButton);
        playerRow.add(Box.createHorizontalStrut(10));
        playerRow.add(scoreLabel);
    
        return playerRow;
    }

    private void updateButtonOpacities() {
        for (JButton button : playerButtons) {
            Color c = button.getBackground();
            int alpha = (button == selectedButton) ? 255 : 100;
            button.setBackground(new Color(c.getRed(), c.getGreen(), c.getBlue(), alpha));
            Border border = button == selectedButton ? selectedBorder : unselectedBorder;
            button.setBorder(border);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(GameUI::new);
    }
}
