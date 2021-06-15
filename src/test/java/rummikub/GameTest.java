package rummikub;

import static org.junit.Assert.*;
// import org.junit.Test;

public class GameTest {
    @org.junit.Test
    public static void main(String[] args){
        Tile redTile = new Tile("red", 12);
        Tile redTile2 = new Tile("red", 12);
        Tile redTile3 = new Tile("red", 3);
        Tile redTile4 = new Tile("blue", 12);
        assertTrue(redTile.sameAs(redTile2));
        assertTrue(redTile.sameColorAs(redTile2));
        assertTrue(redTile.sameColorAs(redTile3));
        assertTrue(!redTile.sameColorAs(redTile4));
        assertTrue(redTile.hasId("r12"));
        assertTrue(!redTile.hasId("r7"));
        assertTrue(!redTile.hasId("b12"));
        
        // Pile pile = new Pile();
        
        Rack rack = new Rack();
        rack.addTile(new Tile("blue", 5));
        rack.addTile(new Tile("blue", 6));
        rack.addTile(new Tile("blue", 4));
        rack.addTile(new Tile("green", 13));
        rack.addTile(new Tile("green", 5));
        rack.addTile(new Tile("green", 1));
        rack.addTile(new Tile("green", 4));
        rack.addTile(new Tile("yellow", 6));
        rack.addTile(new Tile("yellow", 1));
        rack.addTile(new Tile("yellow", 7));
        rack.addTile(new Tile("green", 10));
        rack.addTile(new Tile("blue", 2));
        rack.addTile(new Tile("blue", 7));
        rack.addTile(new Tile("red", 5));
        System.out.println(rack);
        
        String[] ids = "b4 b5 b6".split(" ");
        try {
            rack.groupTiles(ids);
        } catch (InvalidTileIdException ex) {
            System.out.println(ex);
        }
        System.out.println(rack);
        
        ids = "y7 b7".split(" ");
        try {
            rack.groupTiles(ids);
        } catch (InvalidTileIdException ex) {
            System.out.println(ex);
        }
        System.out.println(rack);
        
        ids = "r5".split(" ");
        try {
            rack.groupTiles(ids);
        } catch (InvalidTileIdException ex) {
            System.out.println(ex);
        }
        System.out.println(rack);
        
        ids = "y7 b7".split(" ");
        try {
            rack.ungroupTiles(ids);
        } catch (InvalidTileIdException ex) {
            System.out.println(ex);
        }
        System.out.println(rack);
    }
}
