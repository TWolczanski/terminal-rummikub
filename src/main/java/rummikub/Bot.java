package rummikub;

import java.util.ArrayList;
import java.util.Scanner;

public class Bot extends Player {
    public Bot(String name){
        super(name);
    }
    
    // finding all possible runs
    private ArrayList<ArrayList<Tile>> findRuns(){
        ArrayList<ArrayList<Tile>> runs = new ArrayList<ArrayList<Tile>>();
        for(Tile tile : rack.tiles){
            ArrayList<Tile> run = new ArrayList<Tile>();
            run.add(tile);
            for(int i = 0; i < rack.tiles.size(); i++){
                Tile t1 = run.get(run.size() - 1);
                Tile t2 = rack.tiles.get(i);
                if((t2.number == t1.number + 1) && (t2.sameColorAs(t1))){
                    ArrayList<Tile> newRun = new ArrayList<Tile>();
                    for(Tile t : run){
                        newRun.add(t);
                    }
                    newRun.add(t2);
                    run = newRun;
                    if(run.size() >= 3){
                        runs.add(run);
                    }
                    i = -1;
                }
            }
        }
        return runs;
    }
    
    // finding all possible groups
    private ArrayList<ArrayList<Tile>> findGroups(){
        ArrayList<ArrayList<Tile>> groups = new ArrayList<ArrayList<Tile>>();
        for(int n = 1; n < 14; n++){
            ArrayList<Tile> tiles = new ArrayList<Tile>();
            for(Tile t : rack.tiles){
                if(t.number == n){
                    tiles.add(t);
                }
            }
            for(int i = 0; i < tiles.size(); i++){
                Tile t1 = tiles.get(i);
                for(int j = i + 1; j < tiles.size(); j++){
                    Tile t2 = tiles.get(j);
                    if(t2.sameColorAs(t1)){
                        continue;
                    }
                    for(int k = j + 1; k < tiles.size(); k++){
                        Tile t3 = tiles.get(k);
                        if(t3.sameColorAs(t1) || t3.sameColorAs(t2)){
                            continue;
                        }
                        ArrayList<Tile> group = new ArrayList<Tile>();
                        group.add(t1);
                        group.add(t2);
                        group.add(t3);
                        groups.add(group);
                        for(int l = k + 1; l < tiles.size(); l++){
                            Tile t4 = tiles.get(l);
                            if(t4.sameColorAs(t1) || t4.sameColorAs(t2) || t4.sameColorAs(t3)){
                                continue;
                            }
                            group = new ArrayList<Tile>();
                            group.add(t1);
                            group.add(t2);
                            group.add(t3);
                            group.add(t4);
                            groups.add(group);
                        }
                    }
                }
            }
        }
        return groups;
    }
    
    private ArrayList<ArrayList<Tile>> betterPlay(ArrayList<ArrayList<Tile>> play1, ArrayList<ArrayList<Tile>> play2){
        int val1 = 0;
        int len1 = 0;
        for (ArrayList<Tile> meld : play1) {
            len1 += meld.size();
            for (Tile tile : meld) {
                val1 += tile.number;
            }
        }
        int val2 = 0;
        int len2 = 0;
        for (ArrayList<Tile> meld : play2) {
            len2 += meld.size();
            for (Tile tile : meld) {
                val2 += tile.number;
            }
        }
        if (initialPlay) {
            if (len1 > len2) {
                return play1;
            }
            else if (len1 == len2){
                if(val1 > val2){
                    return play1;
                }
            }
            return play2;
        } else {
            if (val1 > val2) {
                return play1;
            }
            return play2;
        }
    }
    
    private boolean overlap(ArrayList<Tile> meld1, ArrayList<Tile> meld2){
        for(Tile t1 : meld1){
            for(Tile t2 : meld2){
                if(t1 == t2){
                    return true;
                }
            }
        }
        return false;
    }
    
    private ArrayList<ArrayList<Tile>> findRackPlay(){
        
        ArrayList<ArrayList<Tile>> possibleRuns = findRuns();
        ArrayList<ArrayList<Tile>> possibleGroups = findGroups();
        ArrayList<ArrayList<Tile>> possibleMelds = new ArrayList<ArrayList<Tile>>();
        for(ArrayList<Tile> run : possibleRuns){
            possibleMelds.add(run);
        }
        for(ArrayList<Tile> group : possibleGroups){
            possibleMelds.add(group);
        }
        
        ArrayList<ArrayList<Tile>> bestPlay = new ArrayList<ArrayList<Tile>>();
        
        for(ArrayList<Tile> meld : possibleMelds){
            ArrayList<ArrayList<Tile>> play = new ArrayList<ArrayList<Tile>>();
            play.add(meld);
            for(ArrayList<Tile> meld1 : possibleMelds){
                boolean overlapping = false;
                for(ArrayList<Tile> meld2 : play){
                    if(overlap(meld1, meld2)){
                        overlapping = true;
                    }
                }
                if(!overlapping){
                    play.add(meld1);
                }
            }
            bestPlay = betterPlay(play, bestPlay);
        }
        return bestPlay;
    }
    
    private void makeRackPlay(Game game, ArrayList<ArrayList<Tile>> rackPlay){
        Scanner scanner = new Scanner(System.in);
        for(ArrayList<Tile> meld : rackPlay){
            try {
                game.table.putTiles(meld);
                String ids = "";
                for(Tile tile : meld){
                    rack.removeTile(tile);
                    ids = ids + tile.id + " ";
                }
                String msg = name + " puts the sequence " + ids + "onto the table.";
                String line = "";
                for(int i = 0; i < msg.length(); i++){
                    line += "-";
                }
                System.out.println(line);
                System.out.println(msg);
                System.out.println(line);
                System.out.println();
                System.out.println("Table:");
                System.out.println(game.table);
                System.out.println(name + "'s rack:");
                System.out.println(rack);
                System.out.println("Press Enter to see " + name + "'s next play.");
                String read = scanner.nextLine();
                while(!read.isEmpty()){
                    read = scanner.nextLine();
                }
            }
            catch (BadArgumentException e){
                System.out.println("Something impossible happened!");
            }
        }
    }
    
    private void makeAddPlay(Game game, int sequenceNumber, Tile tile){
        Scanner scanner = new Scanner(System.in);
        try {
            game.table.addTile(sequenceNumber, tile);
            rack.removeTile(tile);
            String msg = name + " adds the tile " + tile.id + " to sequence " + sequenceNumber + ".";
            String line = "";
            for(int i = 0; i < msg.length(); i++){
                line += "-";
            }
            System.out.println(line);
            System.out.println(msg);
            System.out.println(line);
            System.out.println();
            System.out.println("Table:");
            System.out.println(game.table);
            System.out.println(name + "'s rack:");
            System.out.println(rack);
            System.out.println("Press Enter to see " + name + "'s next play.");
            String read = scanner.nextLine();
            while(!read.isEmpty()){
                read = scanner.nextLine();
            }
        }
        catch (BadArgumentException e){
            System.out.println("Something impossible happened!");
        }
    }
    
    private void makeTakePlay(Game game, int sequenceNumber, Tile tile){
        Scanner scanner = new Scanner(System.in);
        try {
            game.table.takeTile(sequenceNumber, tile.id);
            String msg = name + " takes the tile " + tile.id + " from sequence " + sequenceNumber + ".";
            String line = "";
            for(int i = 0; i < msg.length(); i++){
                line += "-";
            }
            System.out.println(line);
            System.out.println(msg);
            System.out.println(line);
            System.out.println();
            System.out.println("Table:");
            System.out.println(game.table);
            System.out.println(name + "'s rack:");
            System.out.println(rack);
            System.out.println("Press Enter to see " + name + "'s next play.");
            String read = scanner.nextLine();
            while(!read.isEmpty()){
                read = scanner.nextLine();
            }
        }
        catch (BadArgumentException e){
            System.out.println("Something impossible happened!");
        }
    }
    
    @Override
    public void takeTurn(Game game){
        
        ArrayList<ArrayList<Tile>> rackPlay = findRackPlay();
        
        if(initialPlay){
            boolean canDraw = true;
            makeRackPlay(game, rackPlay);
            if(!rackPlay.isEmpty()){
                canDraw = false;
            }
            
            for(int i = 0; i < game.table.sequences.size(); i++){
                ArrayList<Tile> sequence = game.table.sequences.get(i);
                boolean added = false;
                
                if(game.table.isRun(sequence)){
                    Tile t = sequence.get(0);
                    if(t.number > 1){
                        Tile before = new Tile(t.color, t.number - 1);
                        try {
                            before = rack.getTile(before.id, 1);
                            makeAddPlay(game, i + 1, before);
                            added = true;
                        }
                        catch (BadArgumentException e){};
                    }
                    t = sequence.get(sequence.size() - 1);
                    if(t.number < 13){
                        Tile after = new Tile(t.color, t.number + 1);
                        try {
                            after = rack.getTile(after.id, 1);
                            makeAddPlay(game, i + 1, after);
                            added = true;
                        }
                        catch (BadArgumentException e){};
                    }
                }
                else if(game.table.isGroup(sequence) && sequence.size() == 3){
                    ArrayList<String> colors = new ArrayList<String>();
                    colors.add("red");
                    colors.add("blue");
                    colors.add("yellow");
                    colors.add("green");
                    for(Tile t : sequence){
                        colors.remove(t.color);
                    }
                    String color = colors.get(0);
                    int number = sequence.get(0).number;
                    Tile t = new Tile(color, number);
                    try {
                        t = rack.getTile(t.id, 1);
                        makeAddPlay(game, i + 1, t);
                        added = true;
                    }
                    catch (BadArgumentException e){};
                }
                if(added){
                    canDraw = false;
                    i--;
                }
            }
            
            for(int i = 0; i < game.table.sequences.size(); i++){
                ArrayList<Tile> sequence = game.table.sequences.get(i);
                if(sequence.size() == 3){
                    continue;
                }
                boolean taken = false;
                
                if(game.table.isRun(sequence)){
                    Tile first = sequence.get(0);
                    rack.addTile(first);
                    rackPlay = findRackPlay();
                    boolean found = false;
                    for(ArrayList<Tile> meld : rackPlay){
                        for(Tile tile : meld){
                            if(tile == first){
                                found = true;
                            }
                        }
                    }
                    if(found){
                        makeTakePlay(game, i + 1, first);
                        makeRackPlay(game, rackPlay);
                        taken = true;
                    }
                    else {
                        rack.removeTile(first);
                        Tile last = sequence.get(sequence.size() - 1);
                        rack.addTile(last);
                        rackPlay = findRackPlay();
                        found = false;
                        for(ArrayList<Tile> meld : rackPlay){
                            for(Tile tile : meld){
                                if(tile == last){
                                    found = true;
                                }
                            }
                        }
                        if(found){
                            makeTakePlay(game, i + 1, last);
                            makeRackPlay(game, rackPlay);
                            taken = true;
                        }
                        rack.removeTile(last);
                    }
                }
                else if(game.table.isGroup(sequence)){
                    for(Tile t : sequence){
                        rack.addTile(t);
                        rackPlay = findRackPlay();
                        boolean found = false;
                        for(ArrayList<Tile> meld : rackPlay){
                            for(Tile tile : meld){
                                if(tile == t){
                                    found = true;
                                }
                            }
                        }
                        if(found){
                            makeTakePlay(game, i + 1, t);
                            makeRackPlay(game, rackPlay);
                            taken = true;
                            break;
                        }
                        rack.removeTile(t);
                    }
                }
                if(taken){
                    canDraw = false;
                    i--;
                }
            }
            
            if(canDraw){
                Tile t = game.pile.draw();
                rack.addTile(t);
                System.out.println(name + " draws a tile from the pile.");
            }
        }
        else {
            int points = 0;
            for(ArrayList<Tile> meld : rackPlay){
                for(Tile tile : meld){
                    points += tile.number;
                }
            }
            if(points >= 30){
                makeRackPlay(game, rackPlay);
                initialPlay = true;
            }
            else {
                Tile t = game.pile.draw();
                rack.addTile(t);
                System.out.println(name + " draws a tile from the pile.");
                System.out.println();
            }
        }
        
        System.out.println(name + " ends their turn.");
    }
}
