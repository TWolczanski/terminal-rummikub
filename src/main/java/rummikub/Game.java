package rummikub;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

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
        for(int i = 0; i < 15; i++){
            players[0].rack.addTile(pile.draw());
        }
        Random r = new Random();
        ArrayList<String> colors = new ArrayList<String>();
        colors.add("red");
        colors.add("green");
        colors.add("yellow");
        colors.add("blue");
        for (int i = 0; i < 6; i++) {
            int type = r.nextInt(2);
            Collections.shuffle(colors);
            ArrayList<Tile> tiles = new ArrayList<Tile>();
            if(type == 0){
                int number = r.nextInt(13) + 1;
                int len = r.nextInt(2) + 3;
                for(int j = 0; j < len; j++){
                    Tile t = new Tile(colors.get(j), number);
                    for(Tile tile : pile.tiles){
                        if(tile.sameAs(t)){
                            t = tile;
                        }
                    }
                    tiles.add(t);
                    pile.tiles.remove(t);
                }
            }
            else {
                int number = r.nextInt(10) + 1;
                int len = r.nextInt(13 - (number + 1)) + 3;
                String color = colors.get(0);
                for(int j = 0; j < len; j++){
                    Tile t = new Tile(color, number + j);
                    for(Tile tile : pile.tiles){
                        if(tile.sameAs(t)){
                            t = tile;
                        }
                    }
                    tiles.add(t);
                    pile.tiles.remove(t);
                }
            }
            try {
                table.putTiles(tiles);
            } catch (BadArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
        System.out.println(table);
        System.out.println(players[0].rack);
        System.out.println();
        while(!pile.isEmpty() && !players[0].rack.isEmpty()){
            System.out.println("=========");
            System.out.println("Your turn");
            System.out.println("=========");
            System.out.println();
            players[0].myTurn(this);
        }
    }
}