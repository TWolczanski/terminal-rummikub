package rummikub;

import java.util.ArrayList;

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
                    i = 0;
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
    
    private ArrayList<ArrayList<Tile>> findBestPlay(ArrayList<ArrayList<Tile>> bestPlay, ArrayList<ArrayList<Tile>> currentPlay, ArrayList<ArrayList<Tile>> possibleMelds){
        if(possibleMelds.isEmpty()){
            return betterPlay(currentPlay, bestPlay);
        }
        ArrayList<ArrayList<Tile>> disjoint = new ArrayList<ArrayList<Tile>>();
        for(ArrayList<Tile> meld1 : possibleMelds){
            boolean overlapping = false;
            for(ArrayList<Tile> meld2 : currentPlay){
                if(overlap(meld1, meld2)){
                    overlapping = true;
                    break;
                }
            }
            if(!overlapping){
                disjoint.add(meld1);
            }
        }
        possibleMelds = disjoint;
        for(ArrayList<Tile> meld : possibleMelds){
            ArrayList<ArrayList<Tile>> without = new ArrayList<ArrayList<Tile>>();
            for(ArrayList<Tile> m : possibleMelds){
                if(m != meld){
                    ArrayList<Tile> cp = new ArrayList<Tile>();
                    for(Tile t : m){
                        cp.add(t);
                    }
                    without.add(cp);
                }
            }
            currentPlay.add(meld);
            ArrayList<ArrayList<Tile>> play1 = findBestPlay(bestPlay, currentPlay, without);
            currentPlay.remove(meld);
            ArrayList<ArrayList<Tile>> play2 = findBestPlay(bestPlay, currentPlay, without);
            bestPlay = betterPlay(play1, play2);
        }
        return bestPlay;
        /*
        for(int i = 0; i < possibleMelds.size(); i++){
            ArrayList<Tile> sequence = possibleMelds.get(i);
            currentMeld.add(sequence);
            ArrayList<ArrayList<Tile>> with = new ArrayList<ArrayList<Tile>>();
            ArrayList<ArrayList<Tile>> without = new ArrayList<ArrayList<Tile>>();
            for(int j = 0; j < possibleMelds.size(); j++){
                ArrayList<Tile> seq = possibleMelds.get(j);
                if(i != j){
                    without.add(seq);
                }
                boolean overlapping = false;
                for(ArrayList<Tile> s : currentMeld){
                    if(overlap(s, seq)){
                        overlapping = true;
                        break;
                    }
                }
                if(!overlapping){
                    with.add(seq);
                }
            }
            ArrayList<ArrayList<Tile>> meldWith = findBestMeld(bestMeld, currentMeld, with);
            currentMeld.remove(sequence);
            ArrayList<ArrayList<Tile>> meldWithout = findBestMeld(bestMeld, currentMeld, without);
            bestMeld = betterMeld(meldWith, meldWithout);
        }
        return bestMeld;
        */
    }
    
    @Override
    public void takeTurn(Game game){
        ArrayList<ArrayList<Tile>> runs = findRuns();
        ArrayList<ArrayList<Tile>> groups = findGroups();
        ArrayList<ArrayList<Tile>> sequences = new ArrayList<ArrayList<Tile>>();
        for(ArrayList<Tile> run : runs){
            sequences.add(run);
        }
        for(ArrayList<Tile> group : groups){
            sequences.add(group);
        }
        ArrayList<ArrayList<Tile>> bestPlay = findBestPlay(new ArrayList<ArrayList<Tile>>(), new ArrayList<ArrayList<Tile>>(), sequences);
        for(ArrayList<Tile> meld : bestPlay){
            for(Tile tile : meld){
                System.out.print(tile + " ");
            }
            System.out.println();
        }
    }
}
