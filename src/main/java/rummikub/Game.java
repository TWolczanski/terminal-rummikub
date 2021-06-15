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
        for(int i = 0; i < 97; i++){
            pile.draw();
        }
        while(!pile.isEmpty()){
            players[0].myTurn(table, pile);
        }
    }
}