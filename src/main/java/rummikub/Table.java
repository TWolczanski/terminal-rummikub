package rummikub;

import java.util.ArrayList;

public class Table {
    ArrayList<ArrayList<Tile>> sequences = new ArrayList<ArrayList<Tile>>();
    ArrayList<Tile> takenTiles = new ArrayList<Tile>();
    
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
    private boolean areSequence(ArrayList<Tile> tiles){
        return areRun(tiles) || areGroup(tiles);
    }
    public void putTiles(ArrayList<Tile> tiles) throws TableException {
        if(areSequence(tiles)){
            sequences.add(tiles);
        }
        else {
            throw new TableException("The tiles don't make a valid sequence!");
        }
    }
    public void addTile(int sequenceNumber, Tile tile) throws TableException {
        if(sequenceNumber < 1 || sequenceNumber > sequences.size()){
            throw new TableException("Invalid sequence number!");
        }
        ArrayList<Tile> tiles = sequences.get(sequenceNumber - 1);
        tiles.add(0, tile);
        if(!areSequence(tiles)){
            tiles.remove(0);
            tiles.add(tile);
            if(!areSequence(tiles)){
                tiles.remove(tiles.size() - 1);
                throw new TableException("Sequence " + sequenceNumber + " is not valid after adding the tile!");
            }
        }
    }
    public Table tableBackup(){
        Table backup = new Table();
        for(ArrayList<Tile> sequence : sequences){
            ArrayList<Tile> s = new ArrayList<Tile>();
            for(Tile t : sequence){
                s.add(t);
            }
            backup.sequences.add(s);
        }
        return backup;
    }
    public Tile takeTile(int sequenceNumber, String id) throws TableException {
        if(sequenceNumber < 1 || sequenceNumber > sequences.size()){
            throw new TableException("Invalid sequence number!");
        }
        ArrayList<Tile> sequence = sequences.get(sequenceNumber - 1);
        ArrayList<Tile> cp = new ArrayList<Tile>();
        for(Tile tile : sequence){
            cp.add(tile);
        }
        for (Tile tile : cp) {
            if (tile.hasId(id)) {
                takenTiles.add(tile);
                sequence.remove(tile);
                return tile;
            }
        }
        throw new TableException("There is no such tile as " + id + " in the sequence!");
    }
    public boolean areSequencesValid(){
        if(takenTiles.isEmpty()){
            
        }
        return false;
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
