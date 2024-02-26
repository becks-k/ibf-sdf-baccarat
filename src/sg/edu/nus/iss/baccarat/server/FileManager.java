package sg.edu.nus.iss.baccarat.server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class FileManager {

    private File repository;
    
    public FileManager(String repository) {
        this.repository = new File(repository);
    }

    public File getRepository() {
        return repository;
    }

    public HashSet<String> getUserList() {
        HashSet<String> userList = new HashSet<>();
        for (File f : repository.listFiles()) {
            userList.add(f.getName().replace(".db", ""));
        }
        return userList;
    }

    public void saveFile(Player player) throws IOException {
        String fileName = player.getName() + ".db";
        String savedLocation = repository.getPath() + "\\" + fileName;
        //System.out.println("Save location is: " +savedLocation);

        File playerDBFile = new File(savedLocation);
        if (!playerDBFile.exists()) {
            try {
                Path directory = Paths.get(repository.getPath());
                if (!Files.exists(directory)) {
                    Files.createDirectory(directory);
                    System.out.println("Directory created successfully.");
                }
            } catch (FileAlreadyExistsException e) {
                System.out.println("Directory already exists.");
            }
            playerDBFile.createNewFile();
        }

        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(playerDBFile)));
        bw.write(player.getName());
        bw.newLine();
        bw.write(Double.toString(player.getValue()));
        bw.flush();
        bw.close();
    }


    public List<String> loadUserFile(String username) throws IOException{
        List<String> output = new ArrayList<>();
        String fileName = username + ".db";
        String savedLocation = repository.getPath() + "\\" + fileName;

        BufferedReader br = new BufferedReader(new FileReader(savedLocation));
        String line;
        while ((line = br.readLine()) != null) {
            output.add(line);
        }

        br.close();
        return output;
    }

    //overload saveFile method
    public void saveFile(Card card) throws IOException {
        String fileName = "cards.db";
        String savedLocation = repository.getPath() + "\\" + fileName;
        //System.out.println("Save location is: " +savedLocation);

        File cardsDBFile = new File(savedLocation);
        if (!cardsDBFile.exists()) {
            try {
                Path directory = Paths.get(repository.getPath());
                if (!Files.exists(directory)) {
                    Files.createDirectory(directory);
                    System.out.println("Directory created successfully.");
                }
            } catch (FileAlreadyExistsException e) {
                System.out.println("Directory already exists.");
            }
            cardsDBFile.createNewFile();
        }

        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(cardsDBFile)));
        for (double c : card.getCardList()) {
            bw.write(Double.toString(c));
            bw.newLine();
        }
        bw.flush();
        bw.close();
    }


    public List<Double> loadCardFile(String cardFile) throws IOException{
        List<Double> output = new ArrayList<>();
        String fileName = "cards.db";
        String savedLocation = repository.getPath() + "\\" + fileName;

        BufferedReader br = new BufferedReader(new FileReader(savedLocation));
        String line;
        while ((line = br.readLine()) != null) {
            output.add(Double.parseDouble(line));
        }

        br.close();
        return output;
    }


    // public static void main(String[] args) {
    //     FileManager repo = new FileManager("data");
    //     repo.getRepository();
    //     Player p1 = new Player("Kenneth", 500);
    //     try {
    //         repo.writeUserFile(p1);
    //         List<String> output = repo.readUserFile("Becky");
    //         System.out.println(output);
    //     } catch (IOException e) {
    //         e.printStackTrace();
    //     }
    // }

    

}
