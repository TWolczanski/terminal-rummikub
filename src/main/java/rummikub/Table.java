package rummikub;

import java.util.ArrayList;

public class Table {
    ArrayList<ArrayList<Tile>> sequences = new ArrayList<ArrayList<Tile>>();
    ArrayList<Tile> takenTiles = new ArrayList<Tile>();
    
    private boolean isRun(ArrayList<Tile> sequence){
        if(sequence.size() == 1){
            return true;
        }
        for(int i = 1; i < sequence.size(); i++){
            if(sequence.get(i).number != sequence.get(i - 1).number + 1){
                return false;
            }
        }
        return true;
    }
    private boolean isGroup(ArrayList<Tile> sequence){
        for(Tile t1 : sequence){
            for(Tile t2 : sequence){
                if(t1 != t2 && (t1.sameColorAs(t2) || t1.number != t2.number)){
                    return false;
                }
            }
        }
        return true;
    }
    private boolean isSequenceValid(ArrayList<Tile> sequence){
        if(sequence.size() < 3){
            return false;
        }
        return isRun(sequence) || isGroup(sequence);
    }
    public void putTiles(ArrayList<Tile> tiles) throws BadArgumentException {
        if(isSequenceValid(tiles)){
            for(Tile tile : tiles){
                takenTiles.remove(tile);
            }
            sequences.add(tiles);
        }
        else {
            throw new BadArgumentException("The tiles don't make a valid sequence!");
        }
    }
    public void addTile(int sequenceNumber, Tile tile) throws BadArgumentException {
        if(sequenceNumber < 1 || sequenceNumber > sequences.size()){
            throw new BadArgumentException("Invalid sequence number!");
        }
        ArrayList<Tile> sequence = sequences.get(sequenceNumber - 1);
        sequence.add(0, tile);
        if(!isRun(sequence) && !isGroup(sequence)){
            sequence.remove(0);
            sequence.add(tile);
            if(!isRun(sequence) && !isGroup(sequence)){
                sequence.remove(sequence.size() - 1);
                throw new BadArgumentException("You can't add such tile to this sequence!");
            }
        }
        takenTiles.remove(tile);
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
    public Tile takeTile(int sequenceNumber, String id) throws BadArgumentException {
        if(sequenceNumber < 1 || sequenceNumber > sequences.size()){
            throw new BadArgumentException("Invalid sequence number!");
        }
        ArrayList<Tile> sequence = sequences.get(sequenceNumber - 1);
        Tile tile = sequence.get(0);
        if(tile.hasId(id)){
            takenTiles.add(tile);
            sequence.remove(0);
        }
        else {
            tile = sequence.get(sequence.size() - 1);
            if (tile.hasId(id)) {
                takenTiles.add(tile);
                sequence.remove(sequence.size() - 1);
            }
            else {
                throw new BadArgumentException("There is no such tile as " + id + " available to take!");
            }
        }
        if (sequence.size() == 0) {
            sequences.remove(sequence);
        }
        return tile;
    }
    public boolean isTableValid(){
        if(takenTiles.isEmpty()){
            for(ArrayList<Tile> sequence : sequences){
                if(!isSequenceValid(sequence)){
                    return false;
                }
            }
            return true;
        }
        return false;
    }
    public String toString(){
        int i = 1;
        String s = "";
        for(ArrayList<Tile> sequence : sequences){
            s += "\n";
            s = s + "#" + Integer.toString(i);
            if(!isSequenceValid(sequence)){
                s += " (not valid)";
            }
            s += "\n";
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
        if(!takenTiles.isEmpty()){
            s += "\n Taken tiles: \n";
            for (Tile t : takenTiles) {
                s += " __ ";
            }
            s += "\n";
            for (Tile t : takenTiles) {
                s = s + "|" + t.toString() + "|";
            }
            s += "\n";
            for (Tile t : takenTiles) {
                s += "|__|";
            }
            s += "\n";
        }
        return s;
    }
}
