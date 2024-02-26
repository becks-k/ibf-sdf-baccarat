package sg.edu.nus.iss.baccarat.server;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;


public class Card {
    
    public static HashMap<Double, Integer> cardValue = new HashMap<>();
    private static LinkedList<Double> newDeck = new LinkedList<>();
    private List<Double> cardList = new LinkedList<>();

    //on initialization returns shuffled x no. of decks and creates a hashmap of key value pairs
    public Card(int noOfDecks) {
        this.cardList = createDeck(noOfDecks);
        cardValue = getCardValue(newDeck);
    }

    public List<Double> getCardList() {
        return cardList;
    }

    public List<Double> createDeck(int noOfDecks) {
        for (int i = 1; i < 14; i++) {
            for (double k = 0.1; k < 0.5; k +=0.1) {
                newDeck.add(i + k);
            }
        }

        for (int i = 0; i < noOfDecks; i++) {
            cardList.addAll(newDeck);
        }
        Collections.shuffle(cardList);
        return cardList;
        // return newDeck;
    }

    public HashMap<Double, Integer> getCardValue(List<Double> newDeck) {
        for (double i : newDeck) {
            if (i < 11) {
                cardValue.put(i, (int)i);
            } else {
                cardValue.put(i, 10);
            }
        }
        return cardValue;
    }

    //deal 1 card //add to player hand //update cards.db
    public void dealOne(Card card, FileManager repository, Player player) {
        double dealtCard = 0;
        if (card.getCardList() != null) {
            dealtCard =  ((LinkedList<Double>) card.getCardList()).pop();
        } else {
            System.err.println("There are no cards left to deal.");
        }
        
        int dealtValue = cardValue.get(dealtCard);
        player.getPlayerHand().add(dealtValue);
        try {
            repository.saveFile(card);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public String toString() {
        return "Card [cardList=" + cardList + "]";
    }


    // public static void main(String[] args) {
    //     Player p1 = new Player("Becky", 100);
    //     Card test = new Card(5);
    //     System.out.println(test.getCardList());
    //     System.out.println(test.getCardList().size());
    //     System.out.println(Card.cardValue.entrySet());
    //     FileManager repo = new FileManager("data");
    //     try {
    //         repo.saveFile(test);
    //     } catch (IOException e) {
    //         e.printStackTrace();
    //     }
    //     test.dealOne(test, repo, p1);
    //     System.out.println(p1.getPlayerHand());
    //     test.dealOne(test, repo, p1);
    //     System.out.println(p1.getPlayerHand());
    //     test.dealOne(test, repo, p1);
    //     System.out.println(p1.getPlayerHand());
    //     String testStr = p1.playerHandToString(p1.getPlayerHand());
    //     System.out.println(testStr);
    // }

}
