package rummikub;

public class Tile {
    String color;
    int number;
    String id;
    
    public Tile(String color, int number){
        this.color = color;
        this.number = number;
        this.id = color.charAt(0) + Integer.toString(number);
    }
    public String toString(){
        return id;
    }
}