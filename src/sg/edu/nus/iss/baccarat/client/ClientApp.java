package sg.edu.nus.iss.baccarat.client;

import java.io.BufferedWriter;
import java.io.Console;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;



public class ClientApp {

    public static void main(String[] args) {

            String[] connInfo = args[0].split(":");
            String portName = connInfo[0];
            int portNo = Integer.parseInt(connInfo[1]);
            System.out.println("ClientApp: " + portName + " " + portNo);

        try {
            Socket clientSock = new Socket(portName, portNo);
            System.out.println("Client connected to server.");

            Console cons = System.console();
            String input = "";
            String response = "";
            List<String> gameRecords = new ArrayList<>();
            gameRecords.clear();
            

            NetworkIO nio = new NetworkIO(clientSock);
            while (!input.equalsIgnoreCase("exit")) {
                input = cons.readLine("Send input to server: ");
                nio.writeTo(input);
                response = nio.readFrom();
                

                if (response.contains("outcome_")) {
                    response = response.replace("outcome_", "");
                    System.out.println(response);
                    String[] strArr = response.split(",");
                    String playerHand = strArr[0].replace("P", "");
                    String bankerHand = strArr[1].replace("B", "");
                    int sumPlayerHand = calculatedHand(playerHand);
                    int sumBankerHand = calculatedHand(bankerHand);

                    if (sumPlayerHand == sumBankerHand) {
                        System.out.println("It's a draw!");
                        gameRecords.add("D");
                    } else if (sumPlayerHand > sumBankerHand) {
                        System.out.println("Player wins with " + sumPlayerHand + " points.");
                        gameRecords.add("P");
                    } else if (sumPlayerHand < sumBankerHand) {
                        System.out.println("Banker wins with " + sumBankerHand + " points.");
                        gameRecords.add("B");
                    }
                    
                } else if (response.contains("closure")) {
                    break;
                } else {
                    System.out.println(response);
                }

                //System.out.println(gameRecords);
                
            }
            
            System.out.println(gameRecords); //debug
            writeToCSV(gameRecords);
            System.out.println("Closing client connection...");
            nio.close();

        } catch (IOException e) {
            e.printStackTrace();
        }  
    }

    public static int calculatedHand(String hand) {
        int sum = 0;
        Scanner sc = new Scanner(hand).useDelimiter("\\|");
        while (sc.hasNext()) {
            int value = sc.nextInt();
            sum += value;
        }
        sum = sum % 10;
        sc.close();
        return sum;
    }

    public static void writeToCSV(List<String> gameRecords) throws IOException {
        String fileName = "game_history.csv";
        String savedLocation = "data\\" + fileName;
        System.out.println("Save location is: " +savedLocation);

        File gameHistoryFile = new File(savedLocation);
        if (!gameHistoryFile.exists()) {
            try {
                Path directory = Paths.get("data");
                if (!Files.exists(directory)) {
                    Files.createDirectory(directory);
                    System.out.println("Directory created successfully.");
                }
            } catch (FileAlreadyExistsException e) {
                System.out.println("Directory already exists.");
            }
            gameHistoryFile.createNewFile();
        }

        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(gameHistoryFile)));
        //StringBuilder sb = new StringBuilder();
        int count = 0;
        for (int i = 0; i < gameRecords.size(); i++) {
            count++;
            bw.write(gameRecords.get(i));
            if (count % 6 == 0) {
                bw.write("\n");
            } else {
                bw.write(",");
            }

        }
        
        bw.flush();
        bw.close();
    }


}
