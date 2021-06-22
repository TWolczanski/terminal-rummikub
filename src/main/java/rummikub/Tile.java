package rummikub;

import java.io.Serializable;

public class Tile implements Serializable {
    public static int tileLook;
    public static final String RESET = "\u001B[0m";
    public static final String RED = "\033[0;91m";
    public static final String GREEN = "\033[0;92m";
    public static final String YELLOW = "\033[0;93m";
    public static final String BLUE = "\033[0;94m";
    // public static final String BLUE = "\033[0;96m"; cyan
    
    String color;
    int number;
    String id;
    
    public Tile(String color, int number){
        this.color = color;
        this.number = number;
        this.id = color.charAt(0) + Integer.toString(number);
    }
    
    public boolean hasId(String id){
        return this.id.equals(id);
    }
    
    public boolean sameAs(Tile t){
        return this.color.equals(t.color) && this.number == t.number;
    }
    
    public boolean sameColorAs(Tile t){
        return this.color.equals(t.color);
    }
    
    public String toString(){
        String s;
        if(number < 10){
            s = " ";
        }
        else {
            s = "";
        }
        switch (color) {
            case "red":
                s = s + RED + Integer.toString(number) + RESET;
                break;
            case "green":
                s = s + GREEN + Integer.toString(number) + RESET;
                break;
            case "yellow":
                s = s + YELLOW + Integer.toString(number) + RESET;
                break;
            case "blue":
                s = s + BLUE + Integer.toString(number) + RESET;
                break;
        }
        return s;
    }
}