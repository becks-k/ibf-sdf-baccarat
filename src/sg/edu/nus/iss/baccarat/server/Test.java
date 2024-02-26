package sg.edu.nus.iss.baccarat.server;

import java.util.Scanner;

public class Test {
    public static void main(String[] args) {
        // String response = "bet|50";
        // String[] strArr = response.split("\\|");
        // for (String i : strArr) {
        //     System.out.println(i);
        // }
        // double value = Double.parseDouble(strArr[1]);
        // System.out.println(value);

        String str = "P|3|1|10|9,B|8|8";
        String[] strArr = str.split(",");
        //String playerHand = strArr[0];
        //String bankerHand = strArr[1];
        String playerHand = strArr[0].replace("P", "");
        String bankerHand = strArr[1].replace("B", "");
        System.out.println(playerHand);
        System.out.println(bankerHand);
        sumOfHand(playerHand);
        sumOfHand(bankerHand);
        // Scanner sc = new Scanner(playerHand).useDelimiter("\\|");
        // while (sc.hasNextLine()) {
        //     System.out.println(sc.nextInt());
        // }
        
        //System.out.println(sumOfHand(playerHand));
        //System.out.println(sumOfHand(bankerHand));
        
    }

    public static int sumOfHand(String hand) {
        int sum = 0;
        int countOfTens = 0;
        Scanner sc = new Scanner(hand).useDelimiter("\\|");
        while (sc.hasNextInt()) {
            int value = sc.nextInt();
            System.out.println(value);
            if (value == 10) {
                countOfTens++;
            }
            sum += value;
        }
        System.out.println("No. of tens: " + countOfTens);
        sum  = sum - (countOfTens * 10);
        System.out.println("Sum: " + sum);
        sc.close();
        return sum;
    }
    
    

                    
}
