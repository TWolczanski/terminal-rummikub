package rummikub;

import java.util.ArrayList;

public class Bot extends Player {
    public Bot(String name){
        super(name);
    }
    
    @Override
    public void myTurn(Game game){
        ArrayList<ArrayList<Tile>> sequences = new ArrayList<ArrayList<Tile>>();
        // finding all possible runs
        for(Tile tile : rack.tiles){
            ArrayList<Tile> run = new ArrayList<Tile>();
            run.add(tile);
            for(int i = 0; i < rack.tiles.size(); i++){
                Tile t1 = run.get(run.size() - 1);
                Tile t2 = rack.tiles.get(i);
                if((t2.number == t1.number + 1) && (t2.sameColorAs(t1))){
                    ArrayList<Tile> newRun = new ArrayList<Tile>();
                    for(Tile t : run){
                        newRun.add(t);
                    }
                    newRun.add(t2);
                    run = newRun;
                    if(run.size() >= 3){
                        sequences.add(run);
                    }
                    i = 0;
                }
            }
        }
        for(ArrayList<Tile> sequence : sequences){
            for(Tile tile : sequence){
                System.out.print(tile + " ");
            }
            System.out.println();
        }
    }
}
