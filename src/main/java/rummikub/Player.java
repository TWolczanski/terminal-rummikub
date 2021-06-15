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
        while(true){
            boolean end = false;
            System.out.print("> ");
            String input = scanner.nextLine();
            String[] s = input.split("\\s+");
            String cmd = s[0];
            switch(cmd){
                case "end":
                    end = true;
                    break;
                case "draw":
                    if (s.length == 1 && !pile.isEmpty()) {
                        Tile t = pile.draw();
                        rack.addTile(t);
                        System.out.println("You drew:");
                        System.out.println(" __ ");
                        System.out.println("|" + t + "|");
                        System.out.println("|__|");
                        System.out.println();
                        System.out.println("Your rack:");
                        System.out.println(rack);
                    }
                    break;
                case "group":
                    String[] args = new String[s.length - 1];
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
                            }
                            catch(InvalidTileIdException e){
                                System.out.println(e.getMessage());
                            }
                        }
                    }
                    break;
            }
            if(end){
                break;
            }
        }
        scanner.close();
    }
}
