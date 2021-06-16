package rummikub;

import java.util.ArrayList;
import java.util.Collections;

public class Pile {
    ArrayList<Tile> tiles = new ArrayList<Tile>(104);
    
    public Pile(){
        for(int i = 0; i < 2; i++){
            for(int j = 1; j <= 13; j++){
                Tile red = new Tile("red", j);
                Tile blue = new Tile("blue", j);
                Tile green = new Tile("green", j);
                Tile yellow = new Tile("yellow", j);
                tiles.add(red);
                tiles.add(blue);
                tiles.add(green);
                tiles.add(yellow);
            }
        }
        Collections.shuffle(tiles);
    }
    public Tile draw(){
        return tiles.remove(tiles.size() - 1);
    }
    public boolean isEmpty(){
        return tiles.isEmpty();
    }
}
