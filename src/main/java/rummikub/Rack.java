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
    public void groupTiles(String[] ids) throws InvalidTileIdException {
        ArrayList<Tile> group = new ArrayList<Tile>();
        for(String id : ids){
            boolean found = false;
            for(Tile t : tiles){
                if(t.hasId(id) && !isGrouped(t)){
                    found = true;
                    group.add(t);
                }
            }
            if(!found){
                throw new InvalidTileIdException("Tile " + id + " is either already grouped or not present on your rack.");
            }
        }
        groups.add(0, group);
    }
    public void ungroupTiles(String[] ids) throws InvalidTileIdException {
        ArrayList<Tile> toBeRemoved = new ArrayList<Tile>();
        boolean found = false;
        for(ArrayList<Tile> group : groups){
            if(ids.length == group.size()){
                boolean check = true;
                for(int i = 0; i < ids.length; i++) {
                    if(!(group.get(i).hasId(ids[i]))){
                        check = false;
                    }
                }
                if(check){
                    toBeRemoved = group;
                    found = true;
                    break;
                }
            }
        }
        if(found){
            groups.remove(toBeRemoved);
        }
        else {
            throw new InvalidTileIdException("There is no such group as " + ids + " on your rack.");
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
