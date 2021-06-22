package rummikub;

import java.util.ArrayList;

public class GameTest {
    public static void main(String[] args){
        Game game = new Game("Tomek", 1);
        Tile.tileLook = 1;
        game.players[0].rack.tiles = new ArrayList<Tile>();
        game.players[0].initialPlay = true;
        game.players[0].rack.addTile(new Tile("red", 5));
        game.players[0].rack.addTile(new Tile("blue", 5));
        game.players[0].rack.addTile(new Tile("green", 5));
        for(int i = 0; i < 7; i++){
            game.players[0].rack.addTile(game.pile.draw());
        }
        ArrayList<Tile> tiles = new ArrayList<Tile>();
        tiles.add(new Tile("yellow", 1));
        tiles.add(new Tile("yellow", 2));
        tiles.add(new Tile("yellow", 3));
        tiles.add(new Tile("yellow", 4));
        tiles.add(new Tile("yellow", 5));
        tiles.add(new Tile("yellow", 6));
        try {
            game.table.putTiles(tiles);
        }
        catch(BadArgumentException e){};
        System.out.println();
        System.out.println();
        while(true){
            game.players[0].takeTurn(game);
        }
    }
}
