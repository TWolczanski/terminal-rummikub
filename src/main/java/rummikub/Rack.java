package rummikub;

import java.io.Serializable;
import java.util.ArrayList;

public class Rack implements Serializable {
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
    
    public Tile getTile(String id, int which) throws BadArgumentException {
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
            throw new BadArgumentException("You don't have two tiles with id " + id + " on your rack!");
        }
        throw new BadArgumentException("There is no such tile as " + id + " on your rack!");
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
    
    public void groupTiles(ArrayList<Tile> tiles) throws BadArgumentException {
        if(tiles.size() == 1){
            throw new BadArgumentException("You can't group only one tile!");
        }
        for(int i = 0; i < tiles.size(); i++){
            Tile t = tiles.get(i);
            if(isGrouped(t)){
                removeTile(t);
                addTile(t);
            }
            for(int j = 0; j < i; j++){
                Tile tile = tiles.get(j);
                if(t == tile){
                    throw new BadArgumentException("You can't use a tile multiple times in one group!");
                }
            }
        }
        groups.add(0, tiles);
    }
    
    public void ungroupTiles(ArrayList<Tile> tiles) throws BadArgumentException {
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
            throw new BadArgumentException("There is no such group on your rack!");
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
    
    public int value(){
        int value = 0;
        for(Tile t : tiles){
            value += t.number;
        }
        return value;
    }
    
    public String toString(){
        String s = "";
        
        if(Main.tileLook == 1){
            for(ArrayList<Tile> group : groups){
                for(Tile t : group){
                    s += " __ ";
                }
                s += "\n";
                for(Tile t : group){
                    s = s + "|" + t.toString() + "|";
                }
                s += "\n";
                for(Tile t : group){
                    s += "|__|";
                }
                s += "\n";
            }
            ArrayList<ArrayList<Tile>> rows = new ArrayList<ArrayList<Tile>>();
            ArrayList<Tile> row = new ArrayList<Tile>();
            int i = 0;
            for(Tile t : tiles){
                if(!isGrouped(t)){
                    i++;
                    row.add(t);
                    if (i == 14) {
                        rows.add(row);
                        row = new ArrayList<Tile>();
                        i = 0;
                    }
                }
            }
            if(!row.isEmpty()){
                rows.add(row);
            }
            for(ArrayList<Tile> r : rows){
                for(Tile t : r){
                    s += " __  ";
                }
                s += "\n";
                for(Tile t : r){
                    s = s + "|" + t.toString() + "| ";
                }
                s += "\n";
                for(Tile t : r){
                    s += "|__| ";
                }
                s += "\n";
            }
        }
        else {
            if(!groups.isEmpty()){
                for(ArrayList<Tile> group : groups){
                    for(Tile t : group){
                        s = s + "[" + t + "]";
                    }
                    s += "\n\n";
                }
            }
            int i = 0;
            for(Tile t : tiles){
                if(!isGrouped(t)){
                    i++;
                    s = s + "[" + t + "] ";
                    if(i == 14){
                        s += "\n\n";
                        i = 0;
                    }
                }
            }
            if(i == 0 && !isEmpty()){
                s = s.substring(0, s.length() - 2);
            }
        }
        return s;
    }
}
