package rummikub;

import java.util.ArrayList;
import java.util.Collections;

public class Game {
    Table table = new Table();
    Pile pile = new Pile();
    Player[] players;
    
    public Game(String playerName, int numberOfBots){
        players = new Player[numberOfBots + 1];
        ArrayList<String> botNames = new ArrayList<String>();
        botNames.add("John");
        botNames.add("Jessica");
        botNames.add("Bob");
        botNames.add("Alice");
        botNames.add("Garry");
        botNames.add("George");
        botNames.add("Tom");
        botNames.add("Frank");
        botNames.add("Emma");
        botNames.add("Sofia");
        Collections.shuffle(botNames);
        Player player = new Player(playerName);
        players[0] = player;
        for(int i = 1; i < players.length; i++){
            Player bot = new Bot(botNames.get(i));
            players[i] = bot;
        }
    }
    
    public void startGame(){
        System.out.print("You'll play against ");
        switch(players.length){
            case 2:
                System.out.print(players[1].name + ".");
                break;
            case 3:
                System.out.print(players[1].name + " and " + players[2].name + ".");
                break;
            case 4:
                System.out.print(players[1].name + ", " + players[2].name + " and " + players[3].name + ".");
                break;
        }
        System.out.println();
        System.out.println();
        
        for(Player player : players){
            for(int i = 0; i < 14; i++){
                player.rack.addTile(pile.draw());
            }
        }
        
        while(true){
            boolean end = false;
            for(Player player : players){
                String msg = player.name + "'s turn";
                String line = "";
                for(int i = 0; i < msg.length(); i++){
                    line += "=";
                }
                System.out.println();
                System.out.println(line);
                System.out.println(msg);
                System.out.println(line);
                System.out.println();
                player.takeTurn(this);
                if(player.rack.isEmpty() || pile.isEmpty()){
                    end = true;
                    break;
                }
            }
            if(end){
                break;
            }
        }
        
        Player winner = players[0];
        for(Player player : players){
            if(player.rack.value() < winner.rack.value()){
                winner = player;
            }
        }
        int winnerPoints = 0;
        for(Player player : players){
            if(player != winner){
                winnerPoints += player.rack.value();
            }
        }
        
        if(pile.isEmpty()){
            System.out.println();
            System.out.println("The pile is empty! Nobody emptied their rack, so the winner is the person with minimum value of tiles remaining in the rack.");
            System.out.println();
            System.out.println("The winner is: " + winner);
            System.out.println();
            System.out.println("Full score:");
            for(Player player : players){
                if(player == winner){
                    System.out.println();
                    System.out.println(player + " (winner): " + winnerPoints);
                    System.out.println(player.rack);
                }
                else {
                    System.out.println();
                    System.out.println(player + ": " + (-player.rack.value()));
                    System.out.println(player.rack);
                }
            }
        }
        else {
            System.out.println(winner + " emptied their rack!");
            System.out.println();
            System.out.println("Full score:");
            System.out.println();
            System.out.println(winner + " (winner): " + winnerPoints);
            for(Player player : players){
                if(player != winner){
                    System.out.println();
                    System.out.println(player + ": " + (-player.rack.value()));
                    System.out.println(player.rack);
                }
            }
        }
    }
}