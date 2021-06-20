package rummikub;

import static org.junit.Assert.*;
// import org.junit.Test;

import java.util.ArrayList;

public class GameTest {
    @org.junit.Test
    public static void main(String[] args){
        Game game = new Game("Tomek", 1);
        for(int i = 0; i < 20; i++){
            game.players[1].rack.addTile(game.pile.draw());
        }
        System.out.println(game.players[1].rack);
        System.out.println();
        game.players[1].takeTurn(game);
    }
}
