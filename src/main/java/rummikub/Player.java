package rummikub;

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
    public void myTurn(Table table, Pile pile){
        Scanner scanner = new Scanner(System.in);
        boolean end = false;
        while(!end){
            System.out.print("> ");
            String input = scanner.nextLine();
            System.out.println();
            String[] s = input.split("\\s+");
            String cmd = s[0];
            String[] args = new String[s.length - 1];
            switch(cmd){
                case "end":
                    end = true;
                    break;
                case "draw":
                    if(pile.isEmpty()){
                        System.out.println("There are no more tiles on the pile!");
                    }
                    else if (s.length == 1) {
                        Tile t = pile.draw();
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
                    if(args.length < 2){
                        System.out.println("Wrong number of tiles to be grouped.");
                    }
                    else {
                        boolean valid = true;
                        for (int i = 1; i < s.length; i++) {
                            if(isValid(s[i])){
                                args[i - 1] = s[i];
                            }
                            else {
                                System.out.println(s[i] + " is not a valid tile id.");
                                System.out.println();
                                valid = false;
                                break;
                            }
                        }
                        if(valid){
                            try {
                                rack.groupTiles(args);
                                System.out.println("Your rack:");
                                System.out.println(rack);
                                System.out.println();
                            }
                            catch(InvalidTileIdException e){
                                System.out.println(e.getMessage());
                                System.out.println();
                            }
                        }
                    }
                    break;
                case "ungroup":
                    boolean valid = true;
                    for (int i = 1; i < s.length; i++) {
                        if(isValid(s[i])){
                            args[i - 1] = s[i];
                        }
                        else {
                            System.out.println(s[i] + " is not a valid tile id.");
                            System.out.println();
                            valid = false;
                            break;
                        }
                    }
                    if (valid) {
                        try {
                            rack.ungroupTiles(args);
                            System.out.println("Your rack:");
                            System.out.println(rack);
                            System.out.println();
                        } catch (InvalidTileIdException e) {
                            System.out.println(e.getMessage());
                            System.out.println();
                        }
                    }
                    break;
            }
        }
    }
}
