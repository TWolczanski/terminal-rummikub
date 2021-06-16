package rummikub;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        File file = new File("ascii-art.txt");
        Scanner scanner = new Scanner(file);
        System.out.println("\033[0;91m");
        while(scanner.hasNextLine()){
            System.out.println(scanner.nextLine());
        }
        scanner.close();
        scanner = new Scanner(System.in);
        System.out.println("\u001B[0m");
        System.out.println();
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
        System.out.println();
        Game game = new Game(playerName, numberOfBots);
        game.startGame();
        scanner.close();
    }
}
