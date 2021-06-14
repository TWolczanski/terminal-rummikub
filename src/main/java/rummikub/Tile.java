package rummikub;

public class Tile {
    String color;
    int number;
    
    public Tile(String color, int number){
        this.color = color;
        this.number = number;
    }
    public boolean sameAs(String s){
        return this.toString().equals(s);
    }
    public boolean sameAs(Tile t){
        return this.color.equals(t.color) && this.number == t.number;
    }
    public boolean sameColorAs(Tile t){
        return this.color.equals(t.color);
    }
    public String toString(){
        return color.charAt(0) + Integer.toString(number);
    }
}