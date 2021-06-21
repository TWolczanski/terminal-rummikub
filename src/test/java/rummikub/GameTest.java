package rummikub;

public class GameTest {
    public static void main(String[] args){
        Game game = new Game("Tomek", 1);
        Tile.tileLook = 2;
        for(int i = 0; i < 14; i++){
            game.players[0].rack.addTile(game.pile.draw());
        }
        System.out.println(game.players[0].rack);
        System.out.println();
        game.players[0].takeTurn(game);
    }
}
