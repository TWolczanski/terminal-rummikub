package rummikub;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to the terminal version of the Rummikub game!");
        System.out.println();
        System.out.print("Enter your name: ");
        String playerName = scanner.nextLine();
        int numberOfBots = 0;
        while(true){
            System.out.print("Choose the number of opponents (1-3): ");
            try {
                numberOfBots = scanner.nextInt();
            }
            catch(InputMismatchException e){
                System.out.println("Wrong input. Try again.");
                scanner.nextLine();
                continue;
            }
            if (numberOfBots >= 1 && numberOfBots <= 3) {
                break;
            }
            System.out.println("Invalid number of opponents. It should be between 1 and 3.");
        }
        Game game = new Game(playerName, numberOfBots);
        game.startGame();
        scanner.close();
    }
}
