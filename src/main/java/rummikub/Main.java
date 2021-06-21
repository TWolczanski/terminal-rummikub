package rummikub;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
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
        System.out.println("1:          2: ");
        System.out.println("    __");
        System.out.println("   |\033[0;91m12\u001B[0m|       [\033[0;91m12\u001B[0m]");
        System.out.println("   |__|");
        System.out.println();
        while(true){
            try {
                System.out.print("Choose your preferred look of tiles: ");
                Tile.tileLook = scanner.nextInt();
                scanner.nextLine();
                if(Tile.tileLook == 1 || Tile.tileLook == 2){
                    break;
                }
                else {
                    System.out.println("Wrong input. Try again.");
                }
            }
            catch(InputMismatchException e){
                System.out.println("Wrong input. Try again.");
                scanner.nextLine();
                continue;
            }
        }
        
        int mode;
        while(true){
            try {
                System.out.print("Press 1 to start a new game or 2 to load a game from file: ");
                mode = scanner.nextInt();
                scanner.nextLine();
                if(mode == 1 || mode == 2){
                    break;
                }
                else {
                    System.out.println("Wrong input. Try again.");
                }
            }
            catch(InputMismatchException e){
                System.out.println("Wrong input. Try again.");
                scanner.nextLine();
                continue;
            }
        }
        System.out.println();
        
        if(mode == 1){
            System.out.print("Enter your name: ");
            String playerName = scanner.nextLine();
            int numberOfBots = 0;
            while(true){
                try {
                    System.out.print("Choose the number of opponents (1-3): ");
                    numberOfBots = scanner.nextInt();
                    scanner.nextLine();
                    if (numberOfBots >= 1 && numberOfBots <= 3) {
                        break;
                    }
                    System.out.println("Wrong input. Try again.");
                }
                catch(InputMismatchException e){
                    System.out.println("Wrong input. Try again.");
                    scanner.nextLine();
                    continue;
                }
            }
            System.out.println();
            Game game = new Game(playerName, numberOfBots);
            game.startGame();
        }
        else {
            try {
                System.out.print("Enter the name of the file: ");
                String filename = scanner.nextLine();
                filename += ".ser";
                FileInputStream fin = new FileInputStream(filename);
                ObjectInputStream in = new ObjectInputStream(fin);
                Game game = (Game) in.readObject();
                fin.close();
                in.close();
                game.startGame();
            }
            catch(IOException | ClassNotFoundException e){
                System.out.println(e.getMessage());
            }
        }
        scanner.close();
    }
}
