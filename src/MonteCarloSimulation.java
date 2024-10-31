import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class MonteCarloSimulation {
    private final Deck originalDeck;
    private final Player player;
    private final int numSim;
    private final AtomicInteger wins;
    private final int numberOfThreads;

    public MonteCarloSimulation(Deck deck, Player player, int numSim) {
        this.originalDeck = deck;
        this.player = player;
        this.numSim = numSim;
        this.wins = new AtomicInteger(0);
        this.numberOfThreads = Runtime.getRuntime().availableProcessors();
    }

    public void simulate() {
        ExecutorService executor = Executors.newFixedThreadPool(numberOfThreads);
        int simulationsPerThread = numSim / numberOfThreads;
        int remainingSimulations = numSim % numberOfThreads;

        List<Future<Integer>> futures = new ArrayList<>();

        for (int i = 0; i < numberOfThreads; i++) {
            int simulationsForThisThread = simulationsPerThread + (i < remainingSimulations ? 1 : 0);
            futures.add(executor.submit(() -> runBatchSimulation(simulationsForThisThread)));
        }

        for (Future<Integer> future : futures) {
            try {
                wins.addAndGet(future.get());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }

        executor.shutdown();
        try {
            if (!executor.awaitTermination(60, TimeUnit.SECONDS)) {
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
        }
    }

    private int runBatchSimulation(int batchSize) {
        int batchWins = 0;
        List<Player> opponents = new ArrayList<>(4);
        for (int i = 0; i < 4; i++) {
            opponents.add(new Player());
        }
        List<HandRank> handRanks = new ArrayList<>(5);

        for (int i = 0; i < batchSize; i++) {
            Deck deckForSim = new Deck(originalDeck);
            deckForSim.shuffle();

            for (Player opponent : opponents) {
                opponent.getHand().clear();
                opponent.drawCard(deckForSim);
            }

            Table table = new Table(deckForSim);
            handRanks.clear();

            HandRank playerHandRank = player.getValue(table);
            handRanks.add(playerHandRank);

            for (Player opponent : opponents) {
                handRanks.add(opponent.getValue(table));
            }

            if (isWinningHand(playerHandRank, handRanks)) {
                batchWins++;
            }
        }
        return batchWins;
    }

    private boolean isWinningHand(HandRank playerHandRank, List<HandRank> handRanks) {
        for (int j = 1; j < handRanks.size(); j++) {
            HandRank opponentHandRank = handRanks.get(j);
            if (opponentHandRank.getRankValue() > playerHandRank.getRankValue() ||
                    (opponentHandRank.getRankValue() == playerHandRank.getRankValue()
                            && !playerHandRank.hasHigherKickers(opponentHandRank))) {
                return false;
            }
        }
        return true;
    }

    public int getTotalWins() {
        return wins.get();
    }
}