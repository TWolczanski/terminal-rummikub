package rummikub;

import java.util.ArrayList;

public class Table {
    ArrayList<ArrayList<Tile>> sequences = new ArrayList<ArrayList<Tile>>();
    ArrayList<Tile> missingTiles = new ArrayList<Tile>();
    
    public boolean isRun(ArrayList<Tile> sequence){
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
    public boolean isGroup(ArrayList<Tile> sequence){
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
            ArrayList<Tile> cp = new ArrayList<Tile>();
            for (Tile t : missingTiles) {
                cp.add(t);
            }
            for (Tile tile : tiles) {
                for (Tile t : cp) {
                    if (t.sameAs(tile)) {
                        missingTiles.remove(t);
                    }
                }
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
        ArrayList<Tile> cp = new ArrayList<Tile>();
        for (Tile t : missingTiles) {
            cp.add(t);
        }
        for (Tile t : cp) {
            if (t.sameAs(tile)) {
                missingTiles.remove(t);
            }
        }
    }
    public void addTiles(int sequenceNumber, ArrayList<Tile> tiles) throws BadArgumentException {
        if(sequenceNumber < 1 || sequenceNumber > sequences.size()){
            throw new BadArgumentException("Invalid sequence number!");
        }
        Table backup = tableBackup();
        try {
            for (Tile tile : tiles) {
                addTile(sequenceNumber, tile);
            }
        }
        catch (BadArgumentException e){
            sequences = backup.sequences;
            missingTiles = backup.missingTiles;
            try {
                for (int i = tiles.size() - 1; i >= 0; i--) {
                    addTile(sequenceNumber, tiles.get(i));
                }
            }
            catch(BadArgumentException e2){
                sequences = backup.sequences;
                missingTiles = backup.missingTiles;
                if(tiles.size() == 1){
                    throw new BadArgumentException("You can't add this tile to this sequence!");
                }
                throw new BadArgumentException("You can't add these tiles to this sequence!");
            }
        }
    }
    public Tile takeTile(int sequenceNumber, String id) throws BadArgumentException {
        if(sequenceNumber < 1 || sequenceNumber > sequences.size()){
            throw new BadArgumentException("Invalid sequence number!");
        }
        ArrayList<Tile> sequence = sequences.get(sequenceNumber - 1);
        boolean found = false;
        if(isRun(sequence)){
            Tile tile = sequence.get(0);
            if (tile.hasId(id)) {
                found = true;
                missingTiles.add(tile);
            } else {
                tile = sequence.get(sequence.size() - 1);
                if (tile.hasId(id)) {
                    found = true;
                    missingTiles.add(tile);
                }
            }
        }
        else {
            for(Tile tile : sequence){
                if(tile.hasId(id)){
                    found = true;
                    missingTiles.add(tile);
                }
            }
        }
        if (found) {
            sequence.remove(missingTiles.get(missingTiles.size() - 1));
            if (sequence.size() == 0) {
                sequences.remove(sequence);
            }
            return missingTiles.get(missingTiles.size() - 1);
        }
        else {
            throw new BadArgumentException("There is no such tile as " + id + " available to take!");
        }
    }
    public ArrayList<Tile> takeTiles(int sequenceNumber, String[] ids) throws BadArgumentException {
        if(sequenceNumber < 1 || sequenceNumber > sequences.size()){
            throw new BadArgumentException("Invalid sequence number!");
        }
        Table backup = tableBackup();
        ArrayList<Tile> tiles = new ArrayList<Tile>();
        try {
            for(int i = 0; i < ids.length; i++){
                tiles.add(takeTile(sequenceNumber, ids[i]));
            }
        }
        catch (BadArgumentException e){
            sequences = backup.sequences;
            missingTiles = backup.missingTiles;
            tiles.clear();
            try {
                for (int i = ids.length - 1; i >= 0; i--) {
                    tiles.add(takeTile(sequenceNumber, ids[i]));
                }
            }
            catch (BadArgumentException e2){
                sequences = backup.sequences;
                missingTiles = backup.missingTiles;
                if(ids.length == 1){
                    throw new BadArgumentException("You can't take this tile!");
                }
                throw new BadArgumentException("You can't take these tiles!");
            }
        }
        return tiles;
    }
    public void splitSequence(int sequenceNumber, String id) throws BadArgumentException {
        if(sequenceNumber < 1 || sequenceNumber > sequences.size()){
            throw new BadArgumentException("Invalid sequence number!");
        }
        ArrayList<Tile> sequence = sequences.get(sequenceNumber - 1);
        ArrayList<Tile> sequence1 = new ArrayList<Tile>();
        ArrayList<Tile> sequence2 = new ArrayList<Tile>();
        boolean found = false;
        for(Tile tile : sequence){
            if(tile.hasId(id)){
                found = true;
            }
            if(!found){
                sequence1.add(tile);
            }
            else {
                sequence2.add(tile);
            }
        }
        if(!found){
            throw new BadArgumentException("There is no such tile as " + id + " in the sequence!");
        }
        if(!sequence1.isEmpty() && !sequence2.isEmpty()){
            sequences.remove(sequence);
            sequences.add(sequence1);
            sequences.add(sequence2);
        }
    }
    public void joinSequences(int sequenceNumber1, int sequenceNumber2) throws BadArgumentException {
        if(sequenceNumber1 < 1 || sequenceNumber1 > sequences.size()){
            throw new BadArgumentException("Invalid sequence number!");
        }
        if(sequenceNumber2 < 1 || sequenceNumber2 > sequences.size()){
            throw new BadArgumentException("Invalid sequence number!");
        }
        ArrayList<Tile> sequence1 = sequences.get(sequenceNumber1 - 1);
        ArrayList<Tile> sequence2 = sequences.get(sequenceNumber2 - 1);
        ArrayList<Tile> sequence3 = new ArrayList<Tile>();
        for (Tile t : sequence1) {
            sequence3.add(t);
        }
        if(isRun(sequence1) && isRun(sequence2)){
            try {
                for (Tile t : sequence2) {
                    sequence3.add(0, t);
                    if (!isRun(sequence3)) {
                        sequence3.remove(0);
                        sequence3.add(t);
                        if (!isRun(sequence3)) {
                            throw new BadArgumentException("You can't join these two sequences!");
                        }
                    }
                }
            }
            catch (BadArgumentException e){
                sequence3.clear();
                for (Tile t : sequence1) {
                    sequence3.add(t);
                }
                for(int i = sequence2.size() - 1; i >= 0; i--){
                    Tile t = sequence2.get(i);
                    sequence3.add(0, t);
                    if (!isRun(sequence3)) {
                        sequence3.remove(0);
                        sequence3.add(t);
                        if (!isRun(sequence3)) {
                            throw new BadArgumentException("You can't join these two sequences!");
                        }
                    }
                }
            }
        }
        else if(isGroup(sequence1) && isGroup(sequence2)){
            for(Tile t : sequence2){
                sequence3.add(t);
                if(!isGroup(sequence3)){
                    throw new BadArgumentException("You can't join these two sequences!");
                }
            }
        }
        else {
            throw new BadArgumentException("You can't join these two sequences!");
        }
        sequences.remove(sequence1);
        sequences.remove(sequence2);
        sequences.add(sequence3);
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
    public boolean isTableValid(){
        if(missingTiles.isEmpty()){
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
        if(!missingTiles.isEmpty()){
            s += "\nMissing tiles: \n";
            for (Tile t : missingTiles) {
                s += " __  ";
            }
            s += "\n";
            for (Tile t : missingTiles) {
                s = s + "|" + t.toString() + "| ";
            }
            s += "\n";
            for (Tile t : missingTiles) {
                s += "|__| ";
            }
            s += "\n";
        }
        return s;
    }
}
