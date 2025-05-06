import javax.swing.*;
import java.awt.*;

public class StarterScreen {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new StarterScreen().createAndShowGUI());
    }

    private void createAndShowGUI() {
        JFrame frame = new JFrame("Fungorium");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLocationRelativeTo(null); // Center the window

        // Main panel with padding
        JPanel mainPanel = new JPanel();
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        // Title
        JLabel titleLabel = new JLabel("Új játék");
        titleLabel.setFont(new Font("SansSerif", Font.PLAIN, 20));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(titleLabel);
        mainPanel.add(Box.createVerticalStrut(20));

        // Spinner fields
        JSpinner tektonokSpinner = createLabeledSpinner(mainPanel, "Tektonok:");
        JSpinner gombaszokSpinner = createLabeledSpinner(mainPanel, "Gombászok:");
        JSpinner rovaraszokSpinner = createLabeledSpinner(mainPanel, "Rovarászok:");

        mainPanel.add(Box.createVerticalStrut(20));

        // Start button
        JButton startButton = new JButton("Kezdés");
        startButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(startButton);

        // Add main panel
        frame.add(mainPanel);
        frame.setVisible(true);
    }

    private JSpinner createLabeledSpinner(JPanel parent, String labelText) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel label = new JLabel(labelText);

        // Spinner for integers: min=0, max=99, step=1
        JSpinner spinner = new JSpinner(new SpinnerNumberModel(0, 0, 99, 1));
        spinner.setPreferredSize(new Dimension(60, 25));

        panel.add(label);
        panel.add(spinner);
        parent.add(panel);

        return spinner;
    }
}
