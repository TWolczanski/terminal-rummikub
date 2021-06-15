package rummikub;

import java.util.Scanner;

public class Player {
    Rack rack = new Rack();
    public String name;
    
    public Player(String name){
        this.name = name;
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
                            if(s[i].length() != 2 && s[i].length() != 3){
                                valid = false;
                                System.out.println(s[i] + " is not a valid tile id.");
                                break;
                            }
                            char color = s[i].charAt(0);
                            if(color == 'r' || color == 'g' || color == 'y' || color == 'b'){
                                char fst = s[i].charAt(1);
                                if (s[i].length() == 2) {
                                    if (fst > 48 && fst < 58) {
                                        args[i - 1] = s[i];
                                        continue;
                                    }
                                }
                                else {
                                    char snd = s[i].charAt(2);
                                    if(fst == 49 && snd > 47 && snd < 52){
                                        args[i - 1] = s[i];
                                        continue;
                                    }
                                }
                            }
                            valid = false;
                            System.out.println(s[i] + " is not a valid tile id.");
                            break;
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
                            }
                        }
                    }
                    break;
                case "ungroup":
                    boolean valid = true;
                    for (int i = 1; i < s.length; i++) {
                        if (s[i].length() != 2 && s[i].length() != 3) {
                            valid = false;
                            System.out.println(s[i] + " is not a valid tile id.");
                            break;
                        }
                        char color = s[i].charAt(0);
                        if (color == 'r' || color == 'g' || color == 'y' || color == 'b') {
                            char fst = s[i].charAt(1);
                            if (s[i].length() == 2) {
                                if (fst > 48 && fst < 58) {
                                    args[i - 1] = s[i];
                                    continue;
                                }
                            } else {
                                char snd = s[i].charAt(2);
                                if (fst == 49 && snd > 47 && snd < 52) {
                                    args[i - 1] = s[i];
                                    continue;
                                }
                            }
                        }
                        valid = false;
                        System.out.println(s[i] + " is not a valid tile id.");
                        break;
                    }
                    if (valid) {
                        try {
                            rack.ungroupTiles(args);
                            System.out.println("Your rack:");
                            System.out.println(rack);
                            System.out.println();
                        } catch (InvalidTileIdException e) {
                            System.out.println(e.getMessage());
                        }
                    }
                    break;
            }
        }
    }
}
