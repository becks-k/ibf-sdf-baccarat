package sg.edu.nus.iss.baccarat.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

public class ServerApp {

    public static void main(String[] args) {

        NetworkIO nio = null;

        int portNo = Integer.parseInt(args[0]);
        int noOfDecks = Integer.parseInt(args[1]);
        System.out.println("ServerApp: " + portNo);

        try {
            ServerSocket server = new ServerSocket(portNo);
            System.out.println("Sever initialized. Waiting for client...");

            Socket sock = server.accept();
            System.out.println("Client connected...");
            
            nio = new NetworkIO(sock);
            String response = "";
            FileManager repo = new FileManager("data");
            Player player = null;
            Player banker = new Player("Banker");
            Card card = new Card(noOfDecks);
            
            
            while (!response.equalsIgnoreCase("exit")) {
                response = nio.readFrom();
                //System.out.println(response);
                String command = "";
                String action = "";
                if (response.contains("|")) {
                    String[] responseArr = response.toLowerCase().split("\\|");
                    command = responseArr[0];
                    action = responseArr[1];
                }
                // System.out.println(command);
                
                // System.out.println(action);

                switch(command) {

                    case "login":
                        String playerName = action.toLowerCase();
                        Double playerValue = 100.0;
                        
                        if (repo.getUserList().contains(playerName)) {
                            List<String> userInfo = repo.loadUserFile(playerName);
                            playerName = userInfo.get(0);
                            playerValue = Double.parseDouble(userInfo.get(1));
                            player = new Player(playerName, playerValue);
                            nio.writeTo(String.format("%s has $%.2f in account.", player.getName(), player.getValue()));
                        } else {
                            player = new Player(playerName, playerValue);
                            repo.saveFile(player);
                            nio.writeTo("User login created. You have $100 to bet.");
                        }
                        break;
                        
                    
                    case "bet":
                        if (player != null) {
                            double betValue = Double.parseDouble(action);
                            //System.out.println(betValue);
                            if (betValue <= player.getValue()) {
                                player.setBetValue(betValue);
                                nio.writeTo(String.format("Bet $%.2f registered", player.getBetValue()));
                            } else {
                                nio.writeTo("You have $" + player.getValue() + ". Bet within your means!");
                            }
                            break;
                        } else {
                            nio.writeTo("Please login!");
                        }
                        break;
                        
                    
                    case "deal":
                        if (player.getBetValue() != 0) {
                            if (action.equalsIgnoreCase("p")) {
                                player.setBetOn(true);
                            } else if (action.equalsIgnoreCase("b")){
                                banker.setBetOn(true);
                            } else {
                                nio.writeTo("Set bet on P or B only."); 
                            }
                            startGame(player, banker, card, nio, repo);
                        } else {
                            nio.writeTo(String.format("Please set bet value."));
                        }
                        break;
                        
                    
                    case "exit":
                        nio.writeTo("Server acknowledged closure.");
                        break;

                    default:
                        nio.writeTo("Please enter a valid input.");
                        break;
                    }

                }
            

                nio.close();
                System.out.println("Closing server connection...");
                server.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }

    //-----------------------------------------------------------------------------//

    public static String msgToClient(Player player, Player banker) {
        String message = "outcome_P" + player.playerHandToString(player.getPlayerHand()) + "," + "B" + banker.playerHandToString(banker.getPlayerHand());
        return message;
    }
    public static void startGame(Player player, Player banker, Card card, NetworkIO nio, FileManager repo) throws IOException {
        boolean gameOn = true;
        double newValue = 0;
        player.getPlayerHand().clear();
        banker.getPlayerHand().clear();
        
        while (gameOn) {
            int sizeOfDeck = card.getCardList().size();
            //System.out.println(sizeOfDeck);
            if (sizeOfDeck < 7) {
                nio.writeTo("Insufficient cards... please exit game.");
                break;
            }
            
            while ((player.sumOfHand() <= 15) && (player.getPlayerHand().size() < 3)) {
                card.dealOne(card, repo, player);
            }
            while ((banker.sumOfHand() <= 15) && (banker.getPlayerHand().size() < 3)) {
                card.dealOne(card, repo, banker);
            }

            int pHand = player.calculateHand();
            int bHand = banker.calculateHand();
            if (pHand == bHand) {
                newValue = player.getValue();
                gameOn = false;
            } else if (pHand > bHand) {
                if (player.isBetOn()) {
                    newValue = player.getValue() + player.getBetValue();
                } else {
                    newValue = player.getValue() - player.getBetValue();
                }
                player.setValue(newValue);
                gameOn = false;
            } else if (pHand < bHand) {
                if (bHand == 6 && banker.getPlayerHand().size() < 4) {
                    if(player.isBetOn()) {
                        newValue = player.getValue() - player.getBetValue();
                    } else {
                        newValue = player.getValue() + (player.getBetValue()/2);
                    }
                } else {
                    if(player.isBetOn()) {
                        newValue = player.getValue() - player.getBetValue();
                    } else {
                        newValue = player.getValue() + player.getBetValue();
                    }
                }
                gameOn = false;
            }

            nio.writeTo(msgToClient(player, banker));
            System.out.println(msgToClient(player, banker));
            player.setValue(newValue);
            repo.saveFile(player);
        }
        
    }
}
