import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Scanner;
import java.util.Locale;

public class Main {
    public static void main(String[] args) {
        try (Scanner in = new Scanner(System.in)) {
            Deck deck = new Deck();
            deck.shuffle();

            Player player = new Player();
            player.drawCard(deck);

            // Display the player's hand
            System.out.println("Your hand:");
            for (Card card : player.getHand()) {
                System.out.println(card.toString());
            }

            // Run simulations
            System.out.println("\nHow many simulations? (recommended: 1,000,000+)");
            int numSim = in.nextInt();

            if (numSim <= 0) {
                System.out.println("Number of simulations must be positive.");
                return;
            }

            // Record start time with nanosecond precision
            long startTime = System.nanoTime();

            try {
                // Initialize and run the Monte Carlo simulation
                MonteCarloSimulation simulation = new MonteCarloSimulation(deck, player, numSim);
                simulation.simulate();

                // Calculate execution time in milliseconds
                double executionTime = (System.nanoTime() - startTime) / 1_000_000.0;

                // Output results
                printResults(simulation.getTotalWins(), numSim, executionTime);
            } catch (RuntimeException e) {
                System.err.println("Simulation failed: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    private static void printResults(long wins, int numSim, double executionTime) {
        DecimalFormat df = new DecimalFormat("0.00");
        NumberFormat nf = NumberFormat.getNumberInstance(Locale.US);
        double percentage = (double) wins / numSim * 100;

        System.out.println("\nResults:");
        System.out.println("Player won " + nf.format(wins) + " out of " + nf.format(numSim) + " simulations.");
        System.out.println("Win percentage: " + df.format(percentage) + "%");
        System.out.println("Execution time: " + df.format(executionTime) + "ms");
        System.out.printf("Speed: %.2f simulations/second%n",
                (numSim / (executionTime / 1000.0)));
    }
}