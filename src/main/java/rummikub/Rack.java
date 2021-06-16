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
        ArrayList<Tile> containingGroup = new ArrayList<Tile>();
        for(ArrayList<Tile> group : groups){
            for(Tile tile : group){
                if(tile == t){
                    containingGroup = group;
                }
            }
        }
        containingGroup.remove(t);
        if(containingGroup.size() == 1){
            groups.remove(containingGroup);
        }
    }
    public Tile getTile(String id, int which) throws RackException {
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
            throw new RackException("You don't have two tiles with id " + id + " on your rack!");
        }
        throw new RackException("There is no such tile as " + id + " on your rack!");
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
    public void groupTiles(ArrayList<Tile> tiles) throws RackException {
        if(tiles.size() < 2){
            throw new RackException("You can't group only one tile!");
        }
        for(Tile t : tiles){
            if(isGrouped(t)){
                throw new RackException("One of the tiles is already grouped!");
            }
        }
        groups.add(0, tiles);
    }
    public void ungroupTiles(ArrayList<Tile> tiles) throws RackException {
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
            throw new RackException("There is no such group on your rack!");
        }
    }
    public boolean isEmpty(){
        return tiles.isEmpty();
    }
    public Rack rackBackup(){
        Rack backup = new Rack();
        for(Tile t : tiles){
            backup.tiles.add(t);
        }
        for(ArrayList<Tile> group : groups){
            ArrayList<Tile> g = new ArrayList<Tile>();
            for(Tile t : group){
                g.add(t);
            }
            backup.groups.add(g);
        }
        return backup;
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
