package rummikub;

import static org.junit.Assert.*;
// import org.junit.Test;

import java.util.ArrayList;

public class GameTest {
    public static ArrayList<Tile> backup(ArrayList<Tile> ts){
        ArrayList<Tile> cp = new ArrayList<Tile>();
        for(Tile t : ts){
            cp.add(t);
        }
        return cp;
    }
    @org.junit.Test
    public static void main(String[] args){
        ArrayList<ArrayList<Tile>> ss = new ArrayList<ArrayList<Tile>>();
        ArrayList<Tile> s = new ArrayList<Tile>();
        s.add(new Tile("red", 12));
        s.add(new Tile("blue", 7));
        ss.add(s);
        ArrayList<Tile> cp = backup(s);
        cp.add(new Tile("blue", 3));
        
        for(Tile t : cp){
            System.out.print(t + " ");
        }
        System.out.println();
        
        for(Tile t : s){
            System.out.print(t + " ");
        }
        System.out.println();
    }
}
