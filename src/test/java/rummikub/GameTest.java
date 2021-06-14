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
        assertTrue(redTile.sameAs("r12"));
        assertTrue(!redTile.sameAs("r7"));
        assertTrue(!redTile.sameAs("b12"));
        Pile pile = new Pile();
        System.out.println(pile.draw());
        System.out.println(pile.draw());
        System.out.println(pile.draw());
    }
}
