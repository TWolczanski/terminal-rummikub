package rummikub;

import java.util.ArrayList;
import java.util.Scanner;

public class Player {
    Rack rack = new Rack();
    public String name;
    
    public Player(String name){
        this.name = name;
    }
    private boolean isValid(String id){
        if(id.length() != 2 && id.length() != 3){
            return false;
        }
        char color = id.charAt(0);
        if(color == 'r' || color == 'g' || color == 'y' || color == 'b'){
            char fst = id.charAt(1);
            if (id.length() == 2) {
                if (fst > 48 && fst < 58) {
                    return true;
                }
            }
            else {
                char snd = id.charAt(2);
                if(fst == 49 && snd > 47 && snd < 52){
                    return true;
                }
            }
        }
        return false;
    }
    public void myTurn(Game game){
        Scanner scanner = new Scanner(System.in);
        boolean end = false;
        while(!end){
            System.out.print("> ");
            String input = scanner.nextLine();
            System.out.println();
            String[] s = input.split("\\s+");
            String cmd = s[0];
            // String[] args = new String[s.length - 1];
            switch(cmd){
                case "end":
                    end = true;
                    break;
                case "rack":
                    System.out.println(rack);
                    System.out.println();
                    break;
                case "table":
                    System.out.println(game.table);
                    System.out.println();
                    break;
                case "draw":
                    if(game.pile.isEmpty()){
                        System.out.println("There are no more tiles on the pile!");
                        System.out.println();
                    }
                    else if (s.length == 1) {
                        Tile t = game.pile.draw();
                        rack.addTile(t);
                        System.out.println("You drew:");
                        System.out.println(" __ ");
                        System.out.println("|" + t + "|");
                        System.out.println("|__|");
                        System.out.println();
                        System.out.println("Your rack:");
                        System.out.println(rack);
                        System.out.println();
                    }
                    break;
                case "group":
                    if (s.length > 1){
                        try {
                            ArrayList<Tile> tiles = new ArrayList<Tile>();
                            for (int i = 1; i < s.length; i++) {
                                if (isValid(s[i])) {
                                    tiles.add(rack.getTile(s[i], 1));
                                } else if (s[i].length() == 3 || s[i].length() == 4) {
                                    char fst = s[i].charAt(0);
                                    s[i] = s[i].substring(1);
                                    if (fst == 50 && isValid(s[i])) {
                                        tiles.add(rack.getTile(s[i], 2));
                                    }
                                } else {
                                    throw new InvalidTileException(s[i] + " is not a valid tile id.");
                                }
                            }
                            rack.groupTiles(tiles);
                            System.out.println("Your rack:");
                            System.out.println(rack);
                            System.out.println();
                        } catch (InvalidTileException e) {
                            System.out.println(e.getMessage());
                            System.out.println();
                        }
                    }
                    break;
                case "ungroup":
                    if (s.length > 1){
                        try {
                            ArrayList<Tile> tiles = new ArrayList<Tile>();
                            for (int i = 1; i < s.length; i++) {
                                if (isValid(s[i])) {
                                    tiles.add(rack.getTile(s[i], 1));
                                } else if (s[i].length() == 3 || s[i].length() == 4) {
                                    char fst = s[i].charAt(0);
                                    s[i] = s[i].substring(1);
                                    if (fst == 50 && isValid(s[i])) {
                                        tiles.add(rack.getTile(s[i], 2));
                                    }
                                } else {
                                    throw new InvalidTileException(s[i] + " is not a valid tile id.");
                                }
                            }
                            rack.ungroupTiles(tiles);
                            System.out.println("Your rack:");
                            System.out.println(rack);
                            System.out.println();
                        } catch (InvalidTileException e) {
                            System.out.println(e.getMessage());
                            System.out.println();
                        }

                    }
                    break;
                case "put":
                    if (s.length > 1) {
                        try {
                            ArrayList<Tile> tiles = new ArrayList<Tile>();
                            for (int i = 1; i < s.length; i++) {
                                if (isValid(s[i])) {
                                    tiles.add(rack.getTile(s[i], 1));
                                } else if (s[i].length() == 3 || s[i].length() == 4) {
                                    char fst = s[i].charAt(0);
                                    s[i] = s[i].substring(1);
                                    if (fst == 50 && isValid(s[i])) {
                                        tiles.add(rack.getTile(s[i], 2));
                                    }
                                } else {
                                    throw new InvalidTileException(s[i] + " is not a valid tile id.");
                                }
                            }
                            game.table.putTiles(tiles);
                            for (Tile t : tiles) {
                                rack.removeTile(t);
                            }
                            System.out.println("Table:");
                            System.out.println(game.table);
                            System.out.println();
                            System.out.println("Your rack:");
                            System.out.println(rack);
                            System.out.println();
                        } catch (InvalidTileException e) {
                            System.out.println(e.getMessage());
                            System.out.println();
                        }
                    }
                    break;
            }
        }
    }
}
