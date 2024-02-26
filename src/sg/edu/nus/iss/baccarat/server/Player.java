package sg.edu.nus.iss.baccarat.server;

import java.util.ArrayList;
import java.util.List;

public class Player {

    private String name;
    private double value;
    private List<Integer> playerHand;
    private boolean betOn = false;
    private double betValue = 0;
    
    public Player(String name, double value) {
        this.name = name;
        this.value = value;
        this.playerHand = new ArrayList<>();
    }

    public Player(String name) {
        this.name = name;
        this.playerHand = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public boolean isBetOn() {
        return betOn;
    }

    public void setBetOn(boolean betOn) {
        this.betOn = betOn;
    }

    public double getBetValue() {
        return betValue;
    }

    public void setBetValue(double betValue) {
        this.betValue = betValue;
    }

    public List<Integer> getPlayerHand() {
        return playerHand;
    }

    //calculate player's hand
    public int calculateHand() {
        int sum = 0;
        int sumOfHand = sumOfHand();
        sum = (sumOfHand < 10) ? sumOfHand : (sumOfHand % 10);
        //System.out.println("Calculated hand: " + sum); //debug
        return sum;
    }

     //count sum of player's hand
    public int sumOfHand() {
        List<Integer> playerHand = getPlayerHand();
        int sumOfHand = 0;
        for (int i = 0; i < playerHand.size(); i++) {
            int value = playerHand.get(i);
            value = (value == 10) ? 0 : value;
            sumOfHand += value;
        }
        //System.out.println("Sum of hand is: " + sumOfHand); //debug
        return sumOfHand;
    }

    public String playerHandToString(List<Integer> playerHand) {
        String output = "";
        for (int i = 0; i < playerHand.size(); i++) {
            output += "|" + String.valueOf(playerHand.get(i)) ;
        }
        return output;
    }

    @Override
    public String toString() {
        return "Player [name=" + name + ", value=" + value + ", playerHand=" + playerHand + "]";
    }
    

}
