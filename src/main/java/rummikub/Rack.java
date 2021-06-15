package rummikub;

import java.util.ArrayList;

public class Rack {
    ArrayList<Tile> tiles = new ArrayList<Tile>();
    ArrayList<ArrayList<Tile>> groups = new ArrayList<ArrayList<Tile>>();
    
    public void addTile(Tile t){
        tiles.add(t);
    }
    public void removeTile(Tile t){
        tiles.remove(t);
    }
    public Tile getTile(String id, int which) throws InvalidTileException {
        int i = which;
        for (ArrayList<Tile> group : groups) {
            for (Tile tile : group) {
                if (tile.hasId(id)) {
                    if (i == 1) {
                        return tile;
                    } else {
                        i--;
                    }
                }
            }
        }
        for (Tile tile : tiles) {
            if (tile.hasId(id)) {
                if (i == 1) {
                    return tile;
                } else {
                    i--;
                }
            }
        }
        if(which == 2){
            throw new InvalidTileException("You don't have two tiles with id " + id + " on your rack!");
        }
        throw new InvalidTileException("There is no such tile as " + id + " on your rack.");
    }
    private boolean isGrouped(Tile t){
        for(ArrayList<Tile> group : groups){
            for(Tile tile : group){
                if(tile == t){
                    return true;
                }
            }
        }
        return false;
    }
    public void groupTiles(ArrayList<Tile> tiles) throws InvalidTileException {
        for(Tile t : tiles){
            if(isGrouped(t)){
                throw new InvalidTileException("One of the tiles is already grouped!");
            }
        }
        groups.add(0, tiles);
    }
    public void ungroupTiles(ArrayList<Tile> tiles) throws InvalidTileException {
        boolean found = false;
        for(ArrayList<Tile> group : groups){
            if(tiles.size() == group.size()){
                boolean check = true;
                for(int i = 0; i < tiles.size(); i++) {
                    if(!(tiles.get(i) == group.get(i))){
                        check = false;
                        break;
                    }
                }
                if(check){
                    found = true;
                    break;
                }
            }
        }
        if(found){
            groups.remove(tiles);
        }
        else {
            throw new InvalidTileException("There is no such group on your rack!");
        }
    }
    public String toString(){
        String s = "";
        for(ArrayList<Tile> group : groups){
            for(Tile t : group){
                s += " __ ";
            }
            s += " ";
        }
        for(Tile t : tiles){
            if(!isGrouped(t)){
                s += " __  ";
            }
        }
        s += "\n";
        for(ArrayList<Tile> group : groups){
            for(Tile t : group){
                s = s + "|" + t.toString() + "|";
            }
            s += " ";
        }
        for(Tile t : tiles){
            if(!isGrouped(t)){
                s = s + "|" + t.toString() + "| ";
            }
        }
        s += "\n";
        for(ArrayList<Tile> group : groups){
            for(Tile t : group){
                s += "|__|";
            }
            s += " ";
        }
        for(Tile t : tiles){
            if(!isGrouped(t)){
                s += "|__| ";
            }
        }
        return s;
    }
}
