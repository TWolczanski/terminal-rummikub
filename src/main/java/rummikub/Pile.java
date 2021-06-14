package rummikub;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;

public class Pile {
    Deque<Tile> tiles;
    
    public Pile(){
        ArrayList<Tile> ts = new ArrayList<Tile>();
        for(int i = 0; i < 2; i++){
            for(int j = 1; j <= 13; j++){
                Tile red = new Tile("red", j);
                Tile blue = new Tile("blue", j);
                Tile green = new Tile("green", j);
                Tile yellow = new Tile("yellow", j);
                ts.add(red);
                ts.add(blue);
                ts.add(green);
                ts.add(yellow);
            }
        }
        Collections.shuffle(ts);
        tiles = new ArrayDeque<Tile>();
        for(Tile t : ts){
            tiles.push(t);
        }
    }
    public Tile draw(){
        return tiles.pop();
    }
    public boolean isEmpty(){
        return tiles.isEmpty();
    }
}
