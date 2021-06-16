package rummikub;

import java.util.ArrayList;

public class Table {
    ArrayList<ArrayList<Tile>> sequences = new ArrayList<ArrayList<Tile>>();
    
    private boolean areRun(ArrayList<Tile> tiles){
        if(tiles.size() < 3){
            return false;
        }
        for(int i = 1; i < tiles.size(); i++){
            if(tiles.get(i).number != tiles.get(i - 1).number + 1){
                return false;
            }
        }
        return true;
    }
    private boolean areGroup(ArrayList<Tile> tiles){
        if(tiles.size() != 3 && tiles.size() != 4){
            return false;
        }
        for(Tile t1 : tiles){
            for(Tile t2 : tiles){
                if(t1 != t2 && (t1.sameColorAs(t2) || t1.number != t2.number)){
                    return false;
                }
            }
        }
        return true;
    }
    public void putTiles(ArrayList<Tile> tiles) throws InvalidTileException {
        if(areRun(tiles) || areGroup(tiles)){
            sequences.add(tiles);
        }
        else {
            throw new InvalidTileException("The tiles don't make a run nor group!");
        }
    }
    public String toString(){
        int i = 1;
        String s = "";
        for(ArrayList<Tile> sequence : sequences){
            s += "\n";
            s = s + Integer.toString(i) + ": \n";
            for (Tile t : sequence) {
                s += " __ ";
            }
            s += "\n";
            for (Tile t : sequence) {
                s = s + "|" + t.toString() + "|";
            }
            s += "\n";
            for(Tile t : sequence){
                s += "|__|";
            }
            s += "\n";
            i++;
        }
        return s;
    }
}
