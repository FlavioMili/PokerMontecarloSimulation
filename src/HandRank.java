import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Arrays;

public class HandRank {
    public static final HandRank HIGH_CARD = new HandRank(1, "High Card");
    public static final HandRank ONE_PAIR = new HandRank(2, "One Pair");
    public static final HandRank TWO_PAIR = new HandRank(3, "Two Pair");
    public static final HandRank THREE_OF_A_KIND = new HandRank(4, "Three of a Kind");
    public static final HandRank STRAIGHT = new HandRank(5, "Straight");
    public static final HandRank FLUSH = new HandRank(6, "Flush");
    public static final HandRank FULL_HOUSE = new HandRank(7, "Full House");
    public static final HandRank FOUR_OF_A_KIND = new HandRank(8, "Four of a Kind");
    public static final HandRank STRAIGHT_FLUSH = new HandRank(9, "Straight Flush");
    public static final HandRank ROYAL_FLUSH = new HandRank(10, "Royal Flush");

    private static final String[] RANKS = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A"};
    private static final List<String> RANK_LIST = Collections.unmodifiableList(Arrays.asList(RANKS));

    private final int rankValue;
    private final String name;
    private final List<Card> kickers;

    private HandRank(int rankValue, String name) {
        this.rankValue = rankValue;
        this.name = name;
        this.kickers = new ArrayList<>();
    }

    // Copy constructor
    public HandRank(HandRank other) {
        this.rankValue = other.rankValue;
        this.name = other.name;
        this.kickers = new ArrayList<>(other.kickers);
    }

    public int getRankValue() {
        return rankValue;
    }

    public String getName() {
        return name;
    }

    public List<Card> getKickers() {
        return new ArrayList<>(kickers);
    }

    public void setKickers(List<Card> newKickers) {
        kickers.clear();
        kickers.addAll(newKickers);
    }

    // Thread-safe comparison of kickers
    public boolean hasHigherKickers(HandRank other) {
        if (this.rankValue != other.rankValue) {
            return false;
        }

        List<Card> thisKickers = new ArrayList<>(this.kickers);
        List<Card> otherKickers = new ArrayList<>(other.kickers);

        thisKickers.sort((c1, c2) -> getRankIndex(c2.getRank()) - getRankIndex(c1.getRank()));
        otherKickers.sort((c1, c2) -> getRankIndex(c2.getRank()) - getRankIndex(c1.getRank()));

        int size = Math.min(thisKickers.size(), otherKickers.size());
        for (int i = 0; i < size; i++) {
            int thisKickerValue = getRankIndex(thisKickers.get(i).getRank());
            int otherKickerValue = getRankIndex(otherKickers.get(i).getRank());

            if (thisKickerValue > otherKickerValue) {
                return true;
            }
            if (thisKickerValue < otherKickerValue) {
                return false;
            }
        }
        return thisKickers.size() > otherKickers.size();
    }

    private static int getRankIndex(String rank) {
        return RANK_LIST.indexOf(rank);
    }

    @Override
    public String toString() {
        return name;
    }
}