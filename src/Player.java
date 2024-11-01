import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Player {
    private List<Card> hand;

    public Player() {
        hand = new ArrayList<>();
    }

    public void drawCard(Deck deck) {
        for (int i = 0; i < 2; i++)
            hand.add(deck.draw());
    }

    public List<Card> getHand(){
        return hand;
    }

    private int getRankIndex(String rank) {
        switch (rank) {
            case "2":
                return 0;
            case "3":
                return 1;
            case "4":
                return 2;
            case "5":
                return 3;
            case "6":
                return 4;
            case "7":
                return 5;
            case "8":
                return 6;
            case "9":
                return 7;
            case "10":
                return 8;
            case "J":
                return 9;
            case "Q":
                return 10;
            case "K":
                return 11;
            case "A":
                return 12;
            default:
                return 0;
        }
    }

    public HandRank getValue(Table table) {
        Map<String, Integer> rankCount = new HashMap<>();
        Map<String, Integer> suitCount = new HashMap<>();
        boolean isFlush = false;
        int consecutive = 0;
        int previousRankIndex = -1;

        List<Card> allCards = new ArrayList<>(hand);
        allCards.addAll(table.getTable());

        if (allCards.isEmpty()) return HandRank.HIGH_CARD;

        for (Card card : allCards) {
            String rank = card.getRank();
            String suit = card.getSuit();

            rankCount.put(rank, rankCount.getOrDefault(rank, 0) + 1);
            suitCount.put(suit, suitCount.getOrDefault(suit, 0) + 1);

            int currentRankIndex = getRankIndex(rank);
            if (previousRankIndex == -1 || currentRankIndex == previousRankIndex + 1) {
                consecutive++;
            } else if (previousRankIndex != -1 && currentRankIndex != previousRankIndex) {
                consecutive = 1;
            }
            previousRankIndex = currentRankIndex;
        }

        isFlush = suitCount.values().stream().anyMatch(count -> count >= 5);

        boolean hasFourOfAKind = rankCount.containsValue(4);
        boolean hasThreeOfAKind = rankCount.containsValue(3);
        boolean hasPair = rankCount.containsValue(2);
        boolean hasTwoPairs = rankCount.values().stream().filter(count -> count == 2).count() == 2;

        if (isFlush && consecutive == 5 && previousRankIndex == 12) {
            return HandRank.ROYAL_FLUSH;
        } else if (isFlush && consecutive == 5) {
            return HandRank.STRAIGHT_FLUSH;
        } else if (hasFourOfAKind) {
            return HandRank.FOUR_OF_A_KIND;
        } else if (hasThreeOfAKind && hasPair) {
            return HandRank.FULL_HOUSE;
        } else if (isFlush) {
            return HandRank.FLUSH;
        } else if (consecutive >= 5) {
            return HandRank.STRAIGHT;
        } else if (hasThreeOfAKind) {
            return HandRank.THREE_OF_A_KIND;
        } else if (hasTwoPairs) {
            return HandRank.TWO_PAIR;
        } else if (hasPair) {
            return HandRank.ONE_PAIR;
        } else {
            return HandRank.HIGH_CARD;
        }
    }

}
