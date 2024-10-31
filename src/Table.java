import java.util.ArrayList;
import java.util.List;

public class Table {
    private List<Card> table;
    private Deck deck;
    public Table(Deck deck) {
        this.deck = deck;
        table = new ArrayList<>();

        // We immediately populate the table only for simulation purposes
        for (int i = 0; i < 5; i++)
            table.add(deck.draw());
    }

    public List<Card> getTable(){
        return table;
    }
}
