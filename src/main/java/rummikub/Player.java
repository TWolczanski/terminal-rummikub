package rummikub;

import java.util.ArrayList;
import java.util.Scanner;

public class Player {
    Rack rack = new Rack();
    String name;
    
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
        Rack rackBackup = rack.rackBackup();
        Table tableBackup = game.table.tableBackup();
        boolean end = false;
        boolean canDraw = true;
        
        while(!end){
            System.out.print("> ");
            String input = scanner.nextLine();
            String[] s = input.split("\\s+");
            String cmd = s[0];
            switch(cmd){
                case "quit":
                    System.exit(0);
                case "end":
                    if(canDraw){
                        System.out.println();
                        System.out.println("You have to draw or make at least one play in order to end your turn!");
                        System.out.println();
                    }
                    else {
                        if (!game.table.isTableValid()) {
                            System.out.println();
                            System.out.println("Some sequences on the table are not valid! The table and your rack will be now reset to the state from the beginning of your turn.");
                            game.table = tableBackup;
                            rack = rackBackup;
                        }
                        end = true;
                    }
                    break;
                case "rack":
                    System.out.println();
                    System.out.println(rack);
                    System.out.println();
                    break;
                case "table":
                    System.out.println(game.table);
                    break;
                case "draw":
                    if(game.pile.isEmpty()){
                        System.out.println();
                        System.out.println("There are no more tiles on the pile!");
                        System.out.println();
                    }
                    else if(!canDraw){
                        System.out.println();
                        System.out.println("You can't draw - you've made some moves this turn!");
                        System.out.println();
                    }
                    else if (s.length == 1) {
                        Tile t = game.pile.draw();
                        rack.addTile(t);
                        end = true;
                        System.out.println();
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
                                    throw new BadInputException(s[i] + " is not a valid tile id.");
                                }
                            }
                            rack.groupTiles(tiles);
                            System.out.println();
                            System.out.println("Your rack:");
                            System.out.println(rack);
                            System.out.println();
                        } catch (BadInputException | BadArgumentException e) {
                            System.out.println();
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
                                    throw new BadInputException(s[i] + " is not a valid tile id.");
                                }
                            }
                            rack.ungroupTiles(tiles);
                            System.out.println();
                            System.out.println("Your rack:");
                            System.out.println(rack);
                            System.out.println();
                        } catch (BadInputException | BadArgumentException e) {
                            System.out.println();
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
                                    throw new BadInputException(s[i] + " is not a valid tile id.");
                                }
                            }
                            game.table.putTiles(tiles);
                            for (Tile t : tiles) {
                                rack.removeTile(t);
                            }
                            System.out.println();
                            System.out.println("Table:");
                            System.out.println(game.table);
                            System.out.println("Your rack:");
                            System.out.println(rack);
                            System.out.println();
                            canDraw = false;
                        } catch (BadInputException | BadArgumentException e) {
                            System.out.println();
                            System.out.println(e.getMessage());
                            System.out.println();
                        }
                    }
                    break;
                case "add":
                    if(s.length == 3){
                        try {
                            if (isValid(s[2])) {
                                Tile t = rack.getTile(s[2], 1);
                                if(s[1].matches("\\d+")){
                                    game.table.addTile(Integer.parseInt(s[1]), t);
                                    rack.removeTile(t);
                                }
                                else {
                                    throw new BadInputException(s[1] + "is not a valid sequence number!");
                                }
                            } else if (s[2].length() == 3 || s[2].length() == 4) {
                                char fst = s[2].charAt(0);
                                s[2] = s[2].substring(1);
                                if (fst == 50 && isValid(s[2])) {
                                    Tile t = rack.getTile(s[2], 2);
                                    if(s[1].matches("\\d+")){
                                        game.table.addTile(Integer.parseInt(s[1]), t);
                                        rack.removeTile(t);
                                    }
                                    else {
                                        throw new BadInputException(s[1] + "is not a valid sequence number!");
                                    }
                                }
                            } else {
                                throw new BadInputException(s[2] + " is not a valid tile id!");
                            }
                            System.out.println();
                            System.out.println("Table:");
                            System.out.println(game.table);
                            System.out.println("Your rack:");
                            System.out.println(rack);
                            System.out.println();
                            canDraw = false;
                        }
                        catch(BadInputException | BadArgumentException e){
                            System.out.println();
                            System.out.println(e.getMessage());
                            System.out.println();
                        }
                    }
                    break;
                case "take":
                    if(s.length == 3){
                        try {
                            if (isValid(s[2])) {
                                if(s[1].matches("\\d+")){
                                    Tile t = game.table.takeTile(Integer.parseInt(s[1]), s[2]);
                                    rack.addTile(t);
                                }
                                else {
                                    throw new BadInputException(s[1] + "is not a valid sequence number!");
                                }
                            } else if (s[2].length() == 3 || s[2].length() == 4) {
                                char fst = s[2].charAt(0);
                                s[2] = s[2].substring(1);
                                if (fst == 50 && isValid(s[2])) {
                                    if(s[1].matches("\\d+")){
                                        Tile t = game.table.takeTile(Integer.parseInt(s[1]), s[2]);
                                        rack.addTile(t);
                                    }
                                    else {
                                        throw new BadInputException(s[1] + "is not a valid sequence number!");
                                    }
                                }
                            } else {
                                throw new BadInputException(s[2] + " is not a valid tile id!");
                            }
                            System.out.println();
                            System.out.println("Table:");
                            System.out.println(game.table);
                            System.out.println("Your rack:");
                            System.out.println(rack);
                            System.out.println();
                            canDraw = false;
                        }
                        catch(BadInputException | BadArgumentException e){
                            System.out.println();
                            System.out.println(e.getMessage());
                            System.out.println();
                        }
                    }
                    break;
                default:
                    System.out.println();
                    break;
            }
        }
    }
    public String toString(){
        return name;
    }
}
