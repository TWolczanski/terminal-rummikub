package rummikub;

public class Test {
    public static void main(String[] args){
        Tile redTile = new Tile("red", 12);
        Tile redTile2 = new Tile("red", 12);
        System.out.println(redTile);
        System.out.println(redTile.sameAs(redTile2));
    }
}
