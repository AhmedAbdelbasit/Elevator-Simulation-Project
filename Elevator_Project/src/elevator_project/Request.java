/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package elevator_project;

/**
 * @author : Ahmed Abdelbasit Mohamed
 * emial : ahmed_abdelbasit94@hotmail.com
 */

public class Request {
    private final int destinationFloor;
    private final Keypad keypad;
    
    public Request(int destF, Keypad kpad){
        destinationFloor = destF;
        keypad = kpad;
    }
    
    public int getDestination(){
        return destinationFloor;
    }
    
    public Keypad getSourceKeypad(){
        return keypad;
    }
}
